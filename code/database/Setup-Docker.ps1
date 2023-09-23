[CmdletBinding()]
param(
  [Parameter(ParameterSetName = "Build")]
  [switch]$Build,
  [Parameter(ParameterSetName = "Build")]
  [Parameter(ParameterSetName = "Restart")]
  [switch]$Restart,
  [Parameter(ParameterSetName = "Start")]
  [switch]$Start,
  [Parameter(ParameterSetName = "Restart")]
  [Parameter(ParameterSetName = "Start")]
  [bool]$Detached = $false,
  [Parameter(ParameterSetName = "Stop")]
  [switch]$Stop
)

$Script:ImageName = "solosavings-db"
$Script:NetworkName = "solosavings-net"
$Script:PortNumber = 3306
$Script:RootPW = "cs673"

function Stop-Container {
  Write-Host "Stopping $Script:ImageName"
  docker stop $Script:ImageName
  Write-Host "Stopped $Script:ImageName"
}

function Start-Container {
  param(
    [bool]$Detached
  )
  Write-Host "Starting $Script:ImageName Detached: $Detached"
  if($Detached) {
    Write-Verbose "Running docker command: docker run --network $Script:NetworkName  -p $Script:PortNumber:$Script:PortNumber --name $Script:ImageName  -e MYSQL_ROOT_PASSWORD=cs673 -d $Script:ImageName"
    docker run --network $Script:NetworkName  -p $Script:PortNumber`:$Script:PortNumber --name $Script:ImageName  -e MYSQL_ROOT_PASSWORD=cs673 -d $Script:ImageName
  } else {
    Write-Verbose "Running docker command:     docker run --network $Script:NetworkName -p $Script:PortNumber:$Script:PortNumber --name $Script:ImageName  -e MYSQL_ROOT_PASSWORD=cs673 $Script:ImageName"
    docker run --network $Script:NetworkName -p $Script:PortNumber`:$Script:PortNumber --name $Script:ImageName  -e MYSQL_ROOT_PASSWORD=cs673 $Script:ImageName
  }
  Write-Host "Started $Script:ImageName"
}



# Preflight checks
$Docker = Get-Command docker.exe
if($null -eq $Docker) {
  Write-Error "Docker not found in `$ENV:PATH please check your docker installation"
  return
}

$Nets = docker network ls -f name=$Script:NetworkName -q
if($null -eq $Nets) {
  Write-Error "Missing docker network $Script:NetworkName"
  return
}

Write-Host "IMPORTANT: Please be sure that you have configured docker to run linux containers" -ForegroundColor Yellow

if($Build) {
  docker build -t $Script:ImageName .
}

if($Stop -or $Restart) {

  Stop-Container
}
if ($Start -or $Restart) {

  Start-Container -Detached $Detached
}

