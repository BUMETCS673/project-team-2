FROM mysql:8.0
WORKDIR /docker-entrypoint-initdb.d
ADD init-database.sql /docker-entrypoint-initdb.d

# Make port 3306 available to the world outside this container
EXPOSE 3306
CMD ["mysqld"]