FROM docker:20.10.11

WORKDIR /app

COPY docker-compose.yml /app

CMD ["docker-compose", "up", "--build"]