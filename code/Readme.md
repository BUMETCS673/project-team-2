# Solosavings

## Database

This contains the relevant SQL information to define the MySQL database.

## SolosavingsApp

This contains the relevant code for the Springboot application.

### Docker configuration

We are leveraging docker to conatinerize both the web application and the backing MySQL database.

There is a dependency for a shared docker network between the two containers.

To create this please run:

```
docker network create solosavings-net
```

Within each sub-directory is a Setup-Docker script (.ps1 for Windows, .sh for UNIX-based).

This script has the following parameters:

```
- Build     : Generates the docker image 
  - solosavings-db for the database
  - solosavings-app for the appliaction
- Start     : Start the docker container
  - solosavings-db listens on TCP/9000 on the container host and 3306 for the database
  - solosavings-db listens on TCP/8888 on the container host and 8888 for the application
- Detached  : This parameter detaches the container from stdout
- Stop      : This parameter stops the docker container
```

For container discovery, the `application.properties` leverages the following for its database URL:

```
spring.datasource.url=jdbc:mysql://${SPRING_MYSQL_HOST:localhost}:3306/solosavings
```

If the SPRING_MYSQL_HOST environment variable is defined (which it is in the solosavings-app docker image) it will leverage solosavings-db as the hostname and resolve it through container DNS.

Otherwise, the value will default to localhost.


**TODO(will):** Write the shell scripts for UNIX-like systems