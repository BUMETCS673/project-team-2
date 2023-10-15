#!/bin/bash

# Variables
ImageName="solosavings-db"
NetworkName="solosavings-net"
ExtPort=9000
PortNumber=3306
RootPW="cs673"

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
    echo "Running docker command: docker run --network $NetworkName -p $ExtPort:$PortNumber --name $ImageName -e MYSQL_ROOT_PASSWORD=$RootPW -d $ImageName"
    docker run --network $NetworkName -p $ExtPort:$PortNumber --name $ImageName -e MYSQL_ROOT_PASSWORD=$RootPW -d $ImageName
  else
    echo "Running docker command: docker run --network $NetworkName -p $ExtPort:$PortNumber --name $ImageName -e MYSQL_ROOT_PASSWORD=$RootPW $ImageName"
    docker run --network $NetworkName -p $ExtPort:$PortNumber --name $ImageName -e MYSQL_ROOT_PASSWORD=$RootPW $ImageName
  fi
  echo "Started $ImageName"
}

Remove-Container() {
  id=$(docker container ls -a -f name=$ImageName -q)
  if [ ! -z "$id" ] ; then
    echo "Removing old container"
    docker container rm $id
  fi
}

Show-Help() {
  echo "Options:"
  echo "-b | --build    Build"
  echo "-r | --restart  Restart"
  echo "-s | --start    Start"
  echo "-d | --detached Detached"
  echo "-st | --stop    Stop"
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
	-? | --help )		shift
				Help=true
				;;
        * )                     exit 1
    esac
    shift
done

if [ "$Build" = true ] ; then
  docker build -t $ImageName .
  Remove-Container
fi

if [ "$Stop" = true ] || [ "$Restart" = true ] ; then
  Stop-Container
fi

if [ "$Start" = true ] || [ "$Restart" = true ] ; then
  Start-Container 
fi
if [ "$Help" = true ] ; then
  Show-Help
fi
