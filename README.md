# Content-Core

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=he305_content-core&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=he305_content-core)

### Usage

Set up the whole `content` environment using `docker-compose.yml`. If necessary, provide streaming services credentials.

```yaml
version: '3'
services:
  content-core-db:
    image: postgres
    container_name: content-core-db
    restart: always
    volumes:
      - content-core-data:/var/lib/postgresql/data
    environment:
      POSTGRES_PASSWORD: admin
      POSTGRES_USER: admin
      POSTGRES_DB: content-core-db
  content-core:
    image: 'he305/content-core:latest'
    restart: always
    container_name: content-core
    ports:
      - 8081:8081
    depends_on:
      - content-core-db
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://content-core-db:5432/content-core-db
      - SPRING_DATASOURCE_USERNAME=admin
      - SPRING_DATASOURCE_PASSWORD=admin
  stream-feeder:
    restart: always
    image: he305/stream-feeder:latest
    container_name: stream-feeder
    depends_on:
      - content-core
    environment:
      - twitch-client-id-env= #IF TWITCH SUPPORT IS REQUIRED, PASS THE TWITCH CLIENT ID 
      - twitch-client-secret-env= #IF TWITCH SUPPORT IS REQUIRED, PASS THE TWITCH SECRET
      - wasd-token= #IF WASD SUPPORT IS REQUIRED, PASS THE WASD TOKEN
      - content-core-base-url=http://content-core:8081/api
  content-web:
    image: 'he305/content-web:latest'
    restart: always
    depends_on:
      - content-core
    container_name: content-web
    ports:
      - 80:80
    environment:
      - NODE_ENV=production

volumes:
  content-core-data:
```
