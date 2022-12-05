--liquibase formatted sql

--changeset daryakoziuk:1
ALTER TABLE users
ADD COLUMN  image VARCHAR(64);
