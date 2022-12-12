--liquibase formatted sql

--changeset daryakoziuk:1
ALTER TABLE request
ADD COLUMN  status VARCHAR(64);
