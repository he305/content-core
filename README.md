# Content-Core

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=he305_content-core&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=he305_content-core)

### Usage

Set up the whole `content` environment using `docker-compose.yml`. If necessary, provide streaming services credentials.
Use `prometheus.yml` config located in `./config/prod/` to enable grafana.

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
    networks:
      - content-network
  content-core:
    image: 'he305/content-core:latest'
    restart: always
    container_name: content-core
    ports:
      - 8081:8081
    depends_on:
      - content-core-db
      - content-discovery
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://content-core-db:5432/content-core-db
      - SPRING_DATASOURCE_USERNAME=admin
      - SPRING_DATASOURCE_PASSWORD=admin
      - EUREKA_HOST=http://content-discovery:9000/eureka
    networks:
      - content-network
  stream-feeder:
    restart: always
    image: he305/stream-feeder:latest
    container_name: stream-feeder
    depends_on:
      - content-core
      - content-discovery
    environment:
      - twitch-client-id-env= #IF TWITCH SUPPORT IS REQUIRED, PASS THE TWITCH CLIENT ID 
      - twitch-client-secret-env= #IF TWITCH SUPPORT IS REQUIRED, PASS THE TWITCH CLIENT SECRET 
      - wasd-token= #IF WASD SUPPORT IS REQUIRED, PASS THE WASD TOKEN
      - EUREKA_HOST=http://content-discovery:9000/eureka
    networks:
      - content-network
  content-web:
    image: 'he305/content-web:latest'
    restart: always
    depends_on:
      - content-core
    container_name: content-web
    ports:
      - 3000:80
    environment:
      - NODE_ENV=production
    networks:
      - content-network
  content-discovery:
    restart: always
    image: 'he305/content-discovery:latest'
    container_name: content-discovery
    ports:
      - 9000:9000
    networks:
      - content-network
  content-account-verifier:
    restart: always
    image: 'he305/content-account-verifier:latest'
    depends_on:
      - content-discovery
    container_name: content-account-verifier
    environment:
      - EUREKA_HOST=http://content-discovery:9000/eureka
    networks:
      - content-network

  prometheus:
    image: prom/prometheus
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    networks:
      - content-network
  grafana:
    image: grafana/grafana
    container_name: grafana
    ports:
      - 10001:3000
    volumes:
      - grafana-data:/var/lib/grafana
    environment:
      - GF_SECURITY_ADMIN_USER=admin
      - GF_SECURITY_ADMIN_PASSWORD=admin
    networks:
      - content-network

volumes:
  content-core-data:
  grafana-data:

networks:
  content-network:
```
