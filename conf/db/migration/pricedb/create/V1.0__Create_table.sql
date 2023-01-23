create table OilPrice
(
    FromDate INT not null,
    ToDate   INT not null,
    OilPrice REAL not null,
    constraint OilPrice_pk
        primary key (FromDate, ToDate)
);