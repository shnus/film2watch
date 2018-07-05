create table films (
id int8 unsigned auto_increment primary key,
userId int8 not null,
filmId int8 not null,
vote_average dec not null,
vote dec not null,
title varchar(100) not null,
poster_path varchar(300) not null,
original_language varchar(10) not null,
overview varchar(1000),
release_date varchar(200),
unique (userId, filmId)
);