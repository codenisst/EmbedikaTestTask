create table OilPrice
(
    FromDate TEXT not null,
    ToDate   TEXT not null,
    OilPrice TEXT not null,
    constraint OilPrice_pk
        primary key (FromDate, ToDate)
);