drop table if exists TRANSACTION;

create table TRANSACTION
(
    id int not null,
    customerId int not null,
    debitCreditFlag varchar(1) not null,
    amount numeric(20,5) not null,
    currency varchar(255) not null,
    businessDate timestamp,
    counterParty varchar(255),
    source varchar(255),
    comment varchar(255)
);

