FROM postgres:15.13-alpine

WORKDIR /seed

COPY backend/db/01_schema.sql /seed/db/01_schema.sql
COPY backend/db/02_load_data.sql /seed/db/02_load_data.sql
COPY backend/data/inverteri.csv /import/inverteri.csv
COPY backend/data/frizideri.csv /import/frizideri.csv
COPY db-seeder/seed.sh /seed/seed.sh

RUN chmod +x /seed/seed.sh

ENTRYPOINT ["/seed/seed.sh"]
