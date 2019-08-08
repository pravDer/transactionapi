drop table if exists BALANCE;

create table BALANCE
(
    customer_id int not null,
    currency varchar(255) not null,
    business_date timestamp not null,
    balance numeric(20,5)
);

