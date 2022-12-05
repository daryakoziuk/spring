--liquibase formatted sql

--changeset daryakoziuk:1
ALTER TABLE users
ALTER COLUMN password SET DEFAULT '{noop}123';
