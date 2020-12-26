CREATE DATABASE test_db
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE "sold_car" (
  "id" SERIAL PRIMARY KEY,
  "car_type" varchar(100),
  "brand" varchar(100),
  "model" varchar(100),
  "customer_id" int
);

CREATE TABLE "car" (
  "id" SERIAL PRIMARY KEY,
  "car_type" varchar(100),
  "brand" varchar(100),
  "model" varchar(100)
);

CREATE TABLE "customer" (
  "id" SERIAL PRIMARY KEY,
  "fullname" varchar(50),
  "date_of_birth" date,
  "sex" varchar(10)
);

ALTER TABLE "sold_car" ADD FOREIGN KEY ("customer_id") REFERENCES "customer" ("id");
