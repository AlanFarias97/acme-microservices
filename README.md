# ACME Microservices (umbrella)

## Arranque local
```bash
cp .env.example .env   # setear secretos locales
docker compose -f docker-compose.yml -f docker-compose.dev.yml up -d --build
