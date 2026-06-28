#!/bin/sh
set -eu

: "${POSTGRES_HOST:?POSTGRES_HOST is required}"
: "${POSTGRES_DB:?POSTGRES_DB is required}"
: "${POSTGRES_USER:?POSTGRES_USER is required}"
: "${POSTGRES_PASSWORD:?POSTGRES_PASSWORD is required}"

POSTGRES_PORT="${POSTGRES_PORT:-5432}"
SEED_VERSION="${SEED_VERSION:-v1}"
SEED_TRUNCATE_BEFORE_LOAD="${SEED_TRUNCATE_BEFORE_LOAD:-false}"
PGSSLMODE="${PGSSLMODE:-disable}"

export PGPASSWORD="$POSTGRES_PASSWORD"
export PGSSLMODE

psql_cmd() {
  psql \
    -v ON_ERROR_STOP=1 \
    --host="$POSTGRES_HOST" \
    --port="$POSTGRES_PORT" \
    --username="$POSTGRES_USER" \
    --dbname="$POSTGRES_DB" \
    "$@"
}

echo "Waiting for PostgreSQL at ${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}..."
until pg_isready \
  --host="$POSTGRES_HOST" \
  --port="$POSTGRES_PORT" \
  --username="$POSTGRES_USER" \
  --dbname="$POSTGRES_DB"; do
  sleep 5
done

echo "Applying schema..."
psql_cmd --file=/seed/db/01_schema.sql

psql_cmd <<'SQL'
CREATE TABLE IF NOT EXISTS public.emt_seed_history (
    seed_version text PRIMARY KEY,
    applied_at timestamptz NOT NULL DEFAULT now(),
    description text
);
SQL

already_applied="$(psql_cmd -At -v seed_version="$SEED_VERSION" <<'SQL'
SELECT CASE
  WHEN EXISTS (
    SELECT 1
    FROM public.emt_seed_history
    WHERE seed_version = :'seed_version'
  )
  THEN 'true'
  ELSE 'false'
END;
SQL
)"

if [ "$already_applied" = "true" ]; then
  echo "Seed version ${SEED_VERSION} already applied. Nothing to do."
  exit 0
fi

if [ "$SEED_TRUNCATE_BEFORE_LOAD" = "true" ]; then
  echo "Truncating existing demo data before loading seed version ${SEED_VERSION}..."
  psql_cmd <<'SQL'
TRUNCATE TABLE
  public.inverteri_product,
  public.frizideri_product
RESTART IDENTITY CASCADE;
SQL
else
  existing_product_count="$(psql_cmd -At <<'SQL'
SELECT
  (SELECT count(*) FROM public.inverteri_product)
  +
  (SELECT count(*) FROM public.frizideri_product);
SQL
)"

  if [ "$existing_product_count" != "0" ]; then
    echo "Existing product rows found and SEED_TRUNCATE_BEFORE_LOAD=false. Marking seed version ${SEED_VERSION} as applied without reloading data."
    psql_cmd -v seed_version="$SEED_VERSION" <<'SQL'
INSERT INTO public.emt_seed_history (seed_version, description)
VALUES (:'seed_version', 'Skipped because product tables already contained data')
ON CONFLICT (seed_version) DO NOTHING;
SQL
    exit 0
  fi
fi

echo "Loading seed data..."
psql_cmd --file=/seed/db/02_load_data.sql

psql_cmd -v seed_version="$SEED_VERSION" <<'SQL'
INSERT INTO public.emt_seed_history (seed_version, description)
VALUES (:'seed_version', 'Loaded schema and product CSV seed data')
ON CONFLICT (seed_version) DO NOTHING;
SQL

echo "Seed version ${SEED_VERSION} applied successfully."
