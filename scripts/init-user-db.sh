#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER flightright;
    CREATE DATABASE flightright;
    GRANT ALL PRIVILEGES ON DATABASE flightright TO flightright;
EOSQL
