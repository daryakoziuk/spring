CREATE TABLE rent_auto.users
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR(128) UNIQUE,
    firstname VARCHAR(128) NOT NULL,
    lastname  VARCHAR(128) NOT NULL,
    password  VARCHAR(128) NOT NULL,
    role      VARCHAR(64)  NOT NULL
);


CREATE TABLE rent_auto.car
(
    id     SERIAL PRIMARY KEY,
    model  VARCHAR(64),
    status VARCHAR(64)
);

CREATE TABLE rent_auto.tariff
(
    id    SERIAL PRIMARY KEY,
    type  VARCHAR(64),
    price DECIMAL(3, 2)
);


CREATE TABLE rent_auto.request
(
    id           BIGSERIAL PRIMARY KEY,
    date_request TIMESTAMP                                                                  NOT NULL,
    date_return  TIMESTAMP                                                                  NOT NULL,
    tariff_id    INT REFERENCES rent_auto.tariff (id) ON UPDATE CASCADE ON DELETE CASCADE   NOT NULL,
    car_id       INT REFERENCES rent_auto.car (id) ON UPDATE CASCADE ON DELETE CASCADE      NOT NULL,
    user_id      BIGINT REFERENCES rent_auto.users (id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL
);

CREATE TABLE rent_auto.car_characteristic
(
    id            SERIAL PRIMARY KEY,
    car_id        INT         NOT NULL UNIQUE REFERENCES rent_auto.car (id) ON UPDATE CASCADE ON DELETE CASCADE,
    engine_volume INT         NOT NULL,
    type          VARCHAR(64) NOT NULL,
    transmission  VARCHAR(64) NOT NULL,
    date_release  DATE        NOT NULL

);




