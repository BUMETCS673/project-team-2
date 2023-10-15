#!/bin/bash

# Variables
ImageName="solosavings-app"
NetworkName="solosavings-net"
PortNumber=8888

# Functions
Stop-Container() {
  echo "Stopping $ImageName"
  docker stop $ImageName
  echo "Stopped $ImageName"
}

Start-Container() {
  Detached=$1
  echo "Starting $ImageName Detached: $Detached"
  if [ "$Detached" = true ] ; then
    echo "Running docker command: docker run --network $NetworkName -p $PortNumber:$PortNumber --name $ImageName -d $ImageName"
    docker run --network $NetworkName -p $PortNumber:$PortNumber --name $ImageName -d $ImageName
  else
    echo "Running docker command: docker run --network $NetworkName -p $PortNumber:$PortNumber --name $ImageName $ImageName"
    docker run --network $NetworkName -p $PortNumber:$PortNumber --name $ImageName $ImageName
  fi
  echo "Started $ImageName"
}

Remove-Container() {
  containerId=$(docker container ls -a -f name=$ImageName -q)
  if [ ! -z "$containerId" ] ; then
    echo "Removing old $ImageName container"
    docker container rm $containerId
  fi
}

# Preflight checks
if ! command -v docker &> /dev/null ; then
  echo "Docker not found in PATH please check your docker installation"
  exit 1
fi

Nets=$(docker network ls -f name=$NetworkName -q)
if [ -z "$Nets" ] ; then
  echo "Missing docker network $NetworkName"
  exit 1
fi

# Command-line arguments
while [ "$1" != "" ]; do
    case $1 in
        -b | --build )           shift
                                Build=true
                                ;;
        -r | --restart )         shift
                                Restart=true
                                ;;
        -s | --start )           shift
                                Start=true
                                ;;
        -d | --detached )        shift
                                Detached=true
                                ;;
        -st | --stop )           shift
                                Stop=true
                                ;;
        * )                     exit 1
    esac
    shift
done

if [ "$Build" = true ] ; then
  docker build -t $ImageName .
  Stop-Container
  Remove-Container
fi

if [ "$Stop" = true ] || [ "$Restart" = true ] ; then
  Stop-Container
fi

if [ "$Start" = true ] || [ "$Restart" = true ] ; then
  Start-Container 
fi

