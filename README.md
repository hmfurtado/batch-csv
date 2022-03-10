# batch-csv
Read CSV file and insert on database.

# Configuration to run Postgres on docker :
  docker run -d -p 5432:5432
  -v postgres_default:/var/lib/postgresql/data
  -e POSTGRES_PASSWORD=postgres
  -e POSTGRES_USER=postgres
  --name imdb postgres
 in PSQL create database imdb_tsv

## Download this file, extract and put on /resources with data.tsv name
https://datasets.imdbws.com/title.akas.tsv.gz