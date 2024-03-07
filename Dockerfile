FROM docker/compose:1.29.2

WORKDIR /app

COPY docker-compose.yml /app

CMD ["docker-compose", "up", "--build"]