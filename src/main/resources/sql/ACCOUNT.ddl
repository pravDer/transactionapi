drop table if exists ACCOUNT;

create table ACCOUNT
(
    id int not null,
    name varchar(255) not null,
    email varchar(255) not null,
    address varchar(255) not null,
    base_ccy varchar(255) not null
);

