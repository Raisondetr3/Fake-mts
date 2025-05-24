-- Заполнение таблицы operations с разными датами и типами операций

-- Операция 1: Пополнение баланса пользователя 1 (INCOME, 2016-10-08 23:52:52)
INSERT INTO operations (name, description, operation_type, time, price, user_id, created_at)
VALUES ('Пополнение баланса',
        'Пополнение баланса на 500 рублей',
        'INCOME',
        '2016-10-08 23:52:52',
        500.00,
        7,
        '2016-10-08 23:52:52');

-- Операция 2: Списание за тариф "Стандарт" пользователя 1 (OUTCOME, 2017-03-15 14:25:10)
INSERT INTO operations (name, description, operation_type, time, price, user_id, created_at)
VALUES ('Списание за тариф',
        'Списание за тариф "Стандарт"',
        'OUTCOME',
        '2017-03-15 14:25:10',
        -399.99,
        7,
        '2017-03-15 14:25:10');

-- Операция 3: Начисление CASHBACK пользователю 2 (CASHBACK, 2018-07-20 09:18:45)
INSERT INTO operations (name, description, operation_type, time, price, user_id, created_at)
VALUES ('Начисление CASHBACK',
        'Начисление CASHBACK за покупки',
        'CASHBACK',
        '2018-07-20 09:18:45',
        50.00,
        2,
        '2018-07-20 09:18:45');

-- Операция 4: Подключение дополнительной услуги пользователя 2 (OUTCOME, 2019-11-01 17:32:08)
INSERT INTO operations (name, description, operation_type, time, price, user_id, created_at)
VALUES ('Подключение услуги',
        'Подключение услуги "Расширенный пакет СМС"',
        'OUTCOME',
        '2019-11-01 17:32:08',
        -100.00,
        2,
        '2019-11-01 17:32:08');

-- Операция 5: Возврат средств пользователю 2 (INCOME, 2020-04-10 21:55:22)
INSERT INTO operations (name, description, operation_type, time, price, user_id, created_at)
VALUES ('Возврат средств',
        'Возврат средств за неиспользованную услугу',
        'INCOME',
        '2020-04-10 21:55:22',
        100.00,
        2,
        '2020-04-10 21:55:22');

-- Операция 6: Списание комиссии за перевод пользователю 3 (OUTCOME, 2021-08-05 06:41:59)
INSERT INTO operations (name, description, operation_type, time, price, user_id, created_at)
VALUES ('Списание комиссии',
        'Списание комиссии за перевод с карты',
        'OUTCOME',
        '2021-08-05 06:41:59',
        -5.00,
        3,
        '2021-08-05 06:41:59');

-- Операция 7: Списание за роуминг пользователю 1 (OUTCOME, 2022-12-12 11:00:37)
INSERT INTO operations (name, description, operation_type, time, price, user_id, created_at)
VALUES ('Списание за роуминг',
        'Списание за роуминг в Европе',
        'OUTCOME',
        '2022-12-12 11:00:37',
        -750.50,
        1,
        '2022-12-12 11:00:37');

-- Операция 8: Начисление CASHBACK пользователю 3 (CASHBACK, 2023-06-28 19:27:01)
INSERT INTO operations (name, description, operation_type, time, price, user_id, created_at)
VALUES ('Начисление CASHBACK',
        'Начисление CASHBACK за покупки',
        'CASHBACK',
        '2023-06-28 19:27:01',
        25.00,
        3,
        '2023-06-28 19:27:01');


INSERT INTO operations (name, description, operation_type, time, price, user_id, created_at)
VALUES
    ('Operation A', 'Income from sale', 'INCOME', '2025-03-15T12:00:00', 100.0, 1, '2025-03-15T12:00:00'),
    ('Operation B', 'Payment for service', 'OUTCOME', '2025-03-15T13:00:00', 50.0, 1, '2025-03-15T13:00:00'),
    ('Operation C', 'Cashback promo', 'CASHBACK', '2025-03-15T14:00:00', 10.0, 2, '2025-03-15T14:00:00'),
    ('Operation D', 'Income from sale', 'INCOME', '2025-03-15T15:00:00', 200.0, 3, '2025-03-15T15:00:00'),
    ('Operation E', 'Payment for product', 'OUTCOME', '2025-03-15T16:00:00', 75.0, 3, '2025-03-15T16:00:00');
