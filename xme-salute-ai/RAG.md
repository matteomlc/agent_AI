# PostgresML + pgvector Cheat Sheet

Questa guida riassume i comandi principali per:
- avviare il container Docker con PostgresML
- collegarsi al database
- verificare le estensioni e le tabelle vettoriali

---

## Avviare il container PostgresML

```bash
docker run \
  -d \
  --name postgresml \
  -v postgresml_data:/var/lib/postgresql \
  -p 5432:5432 \
  -p 8000:8000 \
  ghcr.io/postgresml/postgresml:2.7.12
```  

## Collegarsi al container

```bash
docker exec -it postgresml bash
``` 

## Accedere al database Postgres

```bash
sudo -u postgresml psql -d postgres
sudo -u postgresml psql -d springai
``` 