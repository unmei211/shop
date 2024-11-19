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
    name        varchar not null UNIQUE,
    unique (category_id, id)
)