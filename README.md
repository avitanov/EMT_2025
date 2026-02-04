## EMT Advanced

Full‑stack application for exploring appliance products (refrigerators – `frizideri` – and air conditioners/inverters – `inverteri`) with AI‑powered similar‑product recommendations.

The project is split into:
- **Backend** – Spring Boot (Java 17, Maven, PostgreSQL, Docker, Google Gemini).
- **Frontend** – React + Vite with React Router and MUI for UI components.

---

## Project Structure

- **Backend/**
  - Spring Boot app (`EmtAdvancedApplication`) exposing REST API under `/api/products`.
  - Domain models: `Product`, `FriziderProduct`, `InverterProduct`, specifications, and `ProductDTO`.
  - Services: `FriziderProductServiceImpl`, `InverterProductServiceImpl`, `GeminiServiceImpl`.
  - Persistence: Spring Data JPA with PostgreSQL.
  - AI: `GeminiService` using `google-genai` and `.env` (`GEMINI_API_KEY`) to find similar products.
  - DB & data:
    - `db/01_schema.sql`, `db/02_load_data.sql` – schema and seed data.
    - `data/frizideri.csv`, `data/inverteri.csv` – imported into the database via Docker init.
  - `docker-compose.yml` – PostgreSQL service (`products-postgres`).

- **frontend/**
  - React + Vite app (see `App.jsx`, `main.jsx`).
  - Routing (React Router):
    - `/products` – all products (default view).
    - `/products/:category` – products filtered by category (`frizideri` or `inverteri`).
    - `/products/:category/:id` – product detail view, including similar products.
  - Data layer:
    - `src/axios/axios.js` – Axios instance (base URL to backend).
    - `src/repository/productRepository.js` – API calls:
      - `/products/frizideri`, `/products/frizideri/{id}`, `/products/frizideri/{id}/findSimilar`
      - `/products/inverteri`, `/products/inverteri/{id}`, `/products/inverteri/{id}/findSimilar`
    - `src/hooks/useProducts.js`, `src/hooks/useProductDetails.js` – custom hooks.
  - UI:
    - Layout (`Layout`, `Header`) and product components (`ProductsGrid`, `ProductCard`).

---

## Tech Stack

- **Backend**
  - Java 17, Spring Boot 3.5
  - Spring Web, Spring Data JPA, Spring Security (CORS config only)
  - PostgreSQL, Hibernate
  - `springdoc-openapi` for Swagger UI
  - Google Gemini (`google-genai`), `dotenv-java`

- **Frontend**
  - React 19, React DOM
  - React Router
  - Vite
  - MUI (`@mui/material`, `@mui/icons-material`)
  - Axios

---

## Prerequisites

- **Java** 17+
- **Maven** (or use the included `mvnw` / `mvnw.cmd` wrapper)
- **Node.js** 18+ and **npm**
- **Docker** and **Docker Compose**
- A **Google Gemini API key**

---

## Backend: Setup & Run

All backend commands are run from the `Backend` directory.

### 1. Configure environment

- **Database config** is in `Backend/src/main/resources/application.properties`:
  - Host: `localhost`
  - Port: `5438`
  - DB name: `products`
  - User/password: `products` / `products`
  - HTTP port: **9091**

- **Gemini API key** – the backend expects `GEMINI_API_KEY` to be available.
  - Recommended: create `Backend/.env`:

    ```env
    GEMINI_API_KEY=your_real_api_key_here
    ```

  - The key is loaded via `dotenv-java` in `GeminiServiceImpl`.

### 2. Start PostgreSQL with Docker

From `Backend`:

```bash
cd Backend
docker compose up -d
```

This will:
- Start `postgres:15` on `localhost:5438`.
- Create the `products` database and user.
- Run schema and seed SQL from `db/`.
- Mount CSV data from `data/`.

### 3. Build & run the Spring Boot app

From `Backend`:

```bash
cd Backend
./mvnw spring-boot:run
# or on Windows
mvnw.cmd spring-boot:run
```

The API will be available at:
- **Base URL**: `http://localhost:9091/api/products`

### 4. API overview

- **Frizideri (refrigerators)**
  - `GET /api/products/frizideri`
    - Returns all refrigerator products (`FriziderProduct`).
  - `GET /api/products/frizideri/{id}`
    - Returns a single `ProductDTO` with product data and specification list.
  - `GET /api/products/frizideri/{id}/findSimilar`
    - Uses Gemini to return a list of **similar refrigerator products** (filtered subset of `/frizideri`).

- **Inverteri (inverters/AC units)**
  - `GET /api/products/inverteri`
  - `GET /api/products/inverteri/{id}`
  - `GET /api/products/inverteri/{id}/findSimilar`

The AI flow for `/findSimilar` endpoints:
- Fetches the chosen product and its characteristics.
- Fetches a set of candidate products within a price window.
- Sends structured product data to Gemini (`gemini-2.0-flash-001`) requesting a JSON list of similar product IDs.
- Maps returned IDs back to `Product` entities and returns them as JSON.

### 5. Swagger / OpenAPI

The project uses `springdoc-openapi-starter-webmvc-ui`. Once the backend is running, OpenAPI UI is typically available at:

- `http://localhost:9091/swagger-ui/index.html`

> If the exact path differs, check the generated OpenAPI endpoints when the app is running.

---

## Frontend: Setup & Run

All frontend commands are run from the `frontend` directory.

### 1. Install dependencies

```bash
cd frontend
npm install
```

### 2. Configure backend URL and CORS

- The backend CORS config (`SecurityConfig`) currently allows:
  - `http://localhost:3000`

To match this:
- Either **run Vite on port 3000**:

  ```bash
  npm run dev -- --port 3000
  ```

- Or **update** `SecurityConfig.corsConfigurationSource()` to also allow the Vite default (`http://localhost:5173`) and rebuild the backend.

Make sure the Axios base URL (in `src/axios/axios.js`) points to:

```text
http://localhost:9091/api
```

### 3. Run the dev server

```bash
cd frontend
npm run dev -- --port 3000
```

Then open:
- `http://localhost:3000/products`

### 4. Frontend routes & behavior

- **`/products`**
  - Main catalog view with all products (often showing one category by default).

- **`/products/:category`**
  - Category‑specific listing.
  - `:category` should be one of:
    - `frizideri`
    - `inverteri`

- **`/products/:category/:id`**
  - Product detail page:
    - Shows product details (name, website, price, image).
    - Shows specifications.
    - Calls `/findSimilar` endpoint to display AI‑recommended similar products.

Core components:
- `Layout` + `Header` – shared layout and navigation.
- `ProductsPage` – lists products using `ProductsGrid` and `ProductCard`.
- `ProductDetails` – detail view, including similar products.
- Hooks (`useProducts`, `useProductDetails`) encapsulate API access via `productRepository`.

---

## Notes & Troubleshooting

- **DB connection errors**
  - Ensure `docker compose up -d` has started `products-postgres`.
  - Confirm port `5438` is free and reachable.

- **CORS errors in the browser**
  - Confirm the frontend origin matches the allowed origin in `SecurityConfig`.

- **Gemini / AI errors**
  - Ensure `GEMINI_API_KEY` is set and valid.
  - If Gemini returns an unexpected format, the service falls back to an empty list of similar products.

---

## License

This project is intended for educational and internal use (EMT Advanced). Add a specific license here if you plan to distribute it publicly.

