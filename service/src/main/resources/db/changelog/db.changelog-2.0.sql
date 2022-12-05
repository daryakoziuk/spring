--liquibase formatted sql

--changeset daryakoziuk:1
ALTER TABLE car
ADD COLUMN  image VARCHAR(64);
