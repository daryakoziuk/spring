INSERT INTO car (id, model, status)
VALUES (1, 'BMW', 'FREE'),
       (2, 'AUDI', 'FREE'),
       (3, 'RENAULT', 'FREE'),
       (4, 'FORD', 'USED');
SELECT SETVAL('car_id_seq', (SELECT MAX(id) FROM car));

INSERT INTO car_characteristic (id, car_id, engine_volume, type, transmission, date_release)
VALUES (1, 1, 1900, 'PETROL', 'MANUAL', '2011-01-01'),
       (2, 2, 2500, 'DIESEL', 'AUTOMATIC', '2018-01-01'),
       (3, 3, 1900, 'PETROL', 'MANUAL', '2002-01-01'),
       (4, 4, 1500, 'PETROL', 'AUTOMATIC', '2015-01-01');
SELECT SETVAL('car_characteristic_id_seq', (SELECT MAX(id) FROM car_characteristic));

INSERT INTO tariff (id, type, price)
VALUES (1, 'DAYTIME', 23.5),
       (2, 'HOURLY', 45.5);
SELECT SETVAL('tariff_id_seq', (SELECT MAX(id) FROM tariff));

INSERT INTO users (id, username, firstname, lastname, password, role)
VALUES (1, 'irina@gmail.com', 'Irina', 'Popova', '123456', 'ADMIN'),
       (2, 'vladimir@gmail.com', 'Vladimir', 'Vladimirov', '654321', 'USER'),
       (3, 'olga@gmail.com', 'Olga', 'Orlova', '0987', 'ADMIN'),
       (4, 'szeta@gmail.com', 'Svetlana', 'Svetikova', '{noop}3333', 'ADMIN'),
       (5, 'test11@gmail.com', 'Sergey', 'Serov', '{bcrypt}$2a$10$ZtOlYJxVEctj5.Bsrg.TK.PivQqn7YXpNDOHcIj04OcWrWigC2cyS', 'USER');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO request (id, date_request, date_return, tariff_id, car_id, user_id, status)
VALUES (1, '2022-01-01', '2022-01-01', 1, 1, 2, 'OPEN'),
       (2, '2022-05-11', '2022-05-12', 2, 3, 1, 'OPEN'),
       (3, '2022-10-01', '2022-10-02', 1, 4, 3, 'OPEN'),
       (4, '2022-08-20', '2022-08-21', 2, 2, 4, 'OPEN'),
       (5, '2022-02-01', '2022-02-01', 1, 3, 1, 'OPEN');
SELECT SETVAL('request_id_seq', (SELECT MAX(id) FROM request));