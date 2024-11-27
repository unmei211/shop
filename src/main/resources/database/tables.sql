CREATE TABLE IF NOT EXISTS CATEGORY
(
    id   varchar primary key,
    name varchar unique not null
);

CREATE TABLE IF NOT EXISTS PRODUCT
(
    id          varchar primary key,
    amount      bigint,
    category_id varchar references CATEGORY (id),
    name        varchar not null,
    deleted     boolean not null,
    unique (category_id, id)
);


CREATE TABLE IF NOT EXISTS PRICE
(
    id         varchar primary key,
    price      bigint,
    start_date timestamp,
    end_date   timestamp,
    product_id varchar unique references PRODUCT (id)
);

CREATE TABLE IF NOT EXISTS PAYMENTTYPE
(
    id   varchar primary key,
    type varchar not null unique
);

CREATE TABLE IF NOT EXISTS USERS
(
    id    varchar primary key,
    name  varchar NOT NULL unique,
    email varchar NOT NULL unique
);

CREATE TABLE IF NOT EXISTS PURCHASESTATUS
(
    id     varchar primary key,
    status varchar NOT NULL unique
);

CREATE TABLE IF NOT EXISTS DISCOUNTSTRATEGY
(
    id          varchar primary key,
    description varchar not null,
    method      varchar not null unique
);

CREATE TABLE IF NOT EXISTS DISCOUNT
(
    id                   varchar primary key,
    description          varchar   not null,
    start_date           timestamp not null,
    end_date             timestamp not null,
    enabled              boolean   not null,
    discount_strategy_id varchar   not null references DISCOUNTSTRATEGY (id)
);

CREATE TABLE IF NOT EXISTS PRODUCT_DISCOUNT
(
    id          varchar primary key,
    product_id  varchar not null,
    discount_id varchar not null,
    unique (product_id, discount_id)
);

CREATE TABLE IF NOT EXISTS PURCHASES
(
    id                 varchar primary key,
    product_id         varchar   not null references PRODUCT (id),
    user_id            varchar   not null references USERS (id),
    payment_type_id    varchar   not null references PAYMENTTYPE (id),
    date               timestamp not null,
    purchase_status_id varchar   not null references PURCHASESTATUS (id)
);

ALTER TABLE purchases ADD COLUMN price bigint NOT NULL default 0