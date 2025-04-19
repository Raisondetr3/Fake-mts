-- Заполнение таблицы tariffs

-- Тариф "Эконом"
INSERT INTO tariffs (name, description, gigabyte_count, minutes_count, sms_count, price, created_at)
VALUES ('Эконом',
        'Базовый тариф для экономных пользователей.',
        5, -- gigabyte_count
        100, -- minutes_count
        50, -- sms_count
        199.99, -- price
        NOW() -- created_at
       );

-- Тариф "Стандарт"
INSERT INTO tariffs (name, description, gigabyte_count, minutes_count, sms_count, price, created_at)
VALUES ('Стандарт',
        'Оптимальное соотношение цены и возможностей.',
        15, -- gigabyte_count
        300, -- minutes_count
        150, -- sms_count
        399.99, -- price
        NOW() -- created_at
       );

-- Тариф "Премиум"
INSERT INTO tariffs (name, description, gigabyte_count, minutes_count, sms_count, price, created_at)
VALUES ('Премиум',
        'Максимум возможностей для самых требовательных пользователей.',
        30, -- gigabyte_count
        1000, -- minutes_count
        500, -- sms_count
        799.99, -- price
        NOW() -- created_at
       );

-- Тариф "Безлимит"
INSERT INTO tariffs (name, description, gigabyte_count, minutes_count, sms_count, price, created_at)
VALUES ('Безлимит',
        'Полная свобода общения и доступа в интернет.',
        100, -- gigabyte_count  (Можно поставить и NULL, если действительно безлимит)
        -1, -- minutes_count (или 9999999999)  Для обозначения безлимита. Укажите, что будет показывать безлимит в вашем приложении.
        -1, -- sms_count   (или 9999999999)  Для обозначения безлимита
        1299.99, -- price
        NOW() -- created_at
       );

-- Тариф "Семейный"
INSERT INTO tariffs (name, description, gigabyte_count, minutes_count, sms_count, price, created_at)
VALUES ('Семейный',
        'Тариф для всей семьи с возможностью разделения трафика.',
        50, -- gigabyte_count
        500, -- minutes_count
        300, -- sms_count
        999.99, -- price
        NOW() -- created_at
       );

-- Тариф "Социальный"
INSERT INTO tariffs (name, description, gigabyte_count, minutes_count, sms_count, price, created_at)
VALUES ('Социальный',
        'Льготный тариф для социально незащищенных категорий населения.',
        3, -- gigabyte_count
        50, -- minutes_count
        20, -- sms_count
        99.99, -- price
        NOW() -- created_at
       );

-- Тариф "Детский"
INSERT INTO tariffs (name, description, gigabyte_count, minutes_count, sms_count, price, created_at)
VALUES ('Детский',
        'Тариф для детей с ограниченным доступом к контенту.',
        2, -- gigabyte_count
        30, -- minutes_count
        10, -- sms_count
        79.99, -- price
        NOW() -- created_at
       );

-- Тариф "Туристический"
INSERT INTO tariffs (name, description, gigabyte_count, minutes_count, sms_count, price, created_at)
VALUES ('Туристический',
        'Тариф для путешественников с доступом в роуминге.',
        10, -- gigabyte_count
        200, -- minutes_count
        100, -- sms_count
        499.99, -- price
        NOW() -- created_at
       );
