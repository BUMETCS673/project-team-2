# Deployment Instructions

1. Acquire a certificate from let's encrypt

```
certbot certonly -a standalone -d <DOMAIN NAME>
```

*Note:* This cert is only valid for 90 days.

2. Generate a PKCS#12 file from the PEM file

```
$ cd /etc/letsencrypt/live/<DOMAIN NAME>
$ openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name tomcat -cafile chain.pem -caname root
```

*Note:* You will be prompted to supply a password for the keystore.p12 file. Please keep track of this.

3. Set the following environment variables on the container host

```
export MYSQL_ROOT_PASSWORD=<MYSQL ROOT PASSWORD>
export SPRING_KEYPW=<KEYSTORE.p12 PASSWORD>
```

4. Run the docker-compose file located in `src/docker-compose.yml`. This will build the code in `src/SoloSavingsApp` and copy the `init-database.sql` from `src/database`.

```
sudo -E docker compose up -d
```
