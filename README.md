## EMT Advanced

Full-stack application for exploring appliance products with AI-powered similar-product recommendations.

The repository contains:

- `backend/` - Spring Boot API, PostgreSQL persistence, Google Gemini integration.
- `frontend/` - React + Vite UI served locally by Vite or in containers by Nginx.
- `docker-compose.yml` - local orchestration for frontend, backend, and PostgreSQL.

## Configuration

Runtime configuration is environment-driven from the root `.env` file. Committed files contain placeholders only.

Use the root template:

- `.env` - local machine values, ignored by Git.
- `.env.example` - same keys with placeholder values.

Important variables:

See `.env.example` for the complete key list. Keep `.env` values environment-specific.

Do not commit real secrets. Local `.env` files are ignored by Git.
For CI/CD, set `IMAGE_TAG` to the Git commit SHA instead of using `latest`.

## Run Everything With Docker Compose

From the repository root:

```bash
docker compose up --build
```

This starts:

- PostgreSQL only on the private Docker network
- Backend on `localhost:${BACKEND_PORT}`
- Frontend on `localhost:${FRONTEND_PORT}`

The `postgres`, `backend`, and `frontend` services run on the same private Docker bridge network named by `COMPOSE_NETWORK_NAME`. The backend waits for PostgreSQL to become healthy, and the frontend waits for the backend health check before starting.

The frontend uses `VITE_API_BASE_URL`, and the Nginx container proxies API requests to `BACKEND_INTERNAL_URL`.

## Backend Development

Start only PostgreSQL:

```bash
docker compose up -d postgres
```

Run the backend:

```bash
cd backend
./mvnw spring-boot:run
```

The backend reads configuration from process environment variables or the root `.env`.

Useful endpoints:

- `GET /api/products/frizideri`
- `GET /api/products/frizideri/{id}`
- `GET /api/products/frizideri/{id}/findSimilar`
- `GET /api/products/inverteri`
- `GET /api/products/inverteri/{id}`
- `GET /api/products/inverteri/{id}/findSimilar`
- Swagger UI: `http://localhost:${BACKEND_PORT}/swagger-ui/index.html`

If `GEMINI_API_KEY` is empty, the backend still starts and similar-product endpoints return an empty list.

## Frontend Development

Install and run:

```bash
cd frontend
npm install
npm run dev
```

The Vite dev server reads `FRONTEND_PORT`, `VITE_API_BASE_URL`, and `VITE_API_PROXY_TARGET` from the root `.env`. The Axios client calls `VITE_API_BASE_URL`, and Vite proxies that path to `VITE_API_PROXY_TARGET`.

Main routes:

- `/products`
- `/products/frizideri`
- `/products/inverteri`
- `/products/:category/:id`

## Build Checks

Backend:

```bash
cd backend
./mvnw test
```

Frontend:

```bash
cd frontend
npm run build
```

## Deployment Notes

Application code, Dockerfiles, Docker Compose, and CI files belong in this repository. Kubernetes, Helm, Argo CD, and production database configuration belong in `../ARGOCD_CONFIGURATION`.

Production deployments should inject environment variables through Kubernetes ConfigMaps and Secrets rather than committed files.
