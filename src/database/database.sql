create database OnlineCode character set utf8mb4;

create table LaTeXCache (
    uuid varchar(64) primary key ,
    md5sha1 varchar(128) unique not null,
    latex varchar(2048) not null
) character set utf8mb4;

