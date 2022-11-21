--liquibase formatted sql

--changeset daryakoziuk:1
CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR(128) UNIQUE,
    firstname VARCHAR(128) NOT NULL,
    lastname  VARCHAR(128) NOT NULL,
    password  VARCHAR(128) NOT NULL,
    role      VARCHAR(64)  NOT NULL
);
--rollback DROP TABLE users;

--changeset daryakoziuk:2
CREATE TABLE car
(
    id     BIGSERIAL PRIMARY KEY,
    model  VARCHAR(64),
    status VARCHAR(64)
);
--rollback DROP TABLE car;

--changeset daryakoziuk:3
CREATE TABLE tariff
(
    id    SERIAL PRIMARY KEY,
    type  VARCHAR(64),
    price DECIMAL(4, 2)
);
--rollback DROP TABLE tariff;

--changeset daryakoziuk:4
CREATE TABLE request
(
    id           BIGSERIAL PRIMARY KEY,
    date_request TIMESTAMP                                                                  NOT NULL,
    date_return  TIMESTAMP                                                                  NOT NULL,
    tariff_id    INT REFERENCES tariff (id) ON UPDATE CASCADE ON DELETE CASCADE   NOT NULL,
    car_id       BIGINT REFERENCES car (id) ON UPDATE CASCADE ON DELETE CASCADE      NOT NULL,
    user_id      BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL
);
--rollback DROP TABLE request;

--changeset daryakoziuk:5
CREATE TABLE car_characteristic
(
    id            BIGSERIAL PRIMARY KEY,
    car_id        BIGINT         NOT NULL UNIQUE REFERENCES car (id) ON UPDATE CASCADE ON DELETE CASCADE,
    engine_volume INT         NOT NULL,
    type          VARCHAR(64) NOT NULL,
    transmission  VARCHAR(64) NOT NULL,
    date_release  DATE        NOT NULL

);
--rollback DROP TABLE car_characteristic;