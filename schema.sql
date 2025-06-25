create table orders
(
    id         BIGSERIAL primary key,
    amount     int,
    created_at timestamp default current_timestamp,
    user_id    bigint not null,
    status     text      default 'new' check (
        status in (
                   'new',
                   'finish',
                   'cancelled'
            )
        )
);


create table inbox_messages
(
    message_id  text primary key,
    received_at timestamp default current_timestamp
);

create table outbox_messages
(
    id             BIGSERIAL primary key,
    aggregate_type text   not null,
    aggregate_id   bigint not null,
    type           varchar   default '',

    payload        text   not null,
    processed      boolean   default false,
    created_at     timestamp default current_timestamp,
    sent           boolean   default false
)
;

create table accounts
(
    user_id bigint primary key,
    version bigint not null,
    balance int    not null default 0
);


-- DRop table if exists outbox_messages;
-- drop table if exists orders;