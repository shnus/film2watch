create table users (
id int8 unsigned auto_increment primary key,
username varchar(30) not null unique,
password varchar(300) not null,
firstname varchar(30) not null,
lastname varchar(30) not null,
gender varchar(30) not null,
birthday varchar(30) not null,
location varchar(100),
bio varchar(200),
image longblob,
enabled TINYINT NOT NULL DEFAULT 1
)