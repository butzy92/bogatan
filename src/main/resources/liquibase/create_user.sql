--liquibase formatted sql

--changeset bogatan:1

create SCHEMA bogatan;

create table bogatan.users (
  id  varchar(255) primary key,
  email varchar(255) UNIQUE not null ,
  name varchar(255) not null ,
  profile_picture varchar(255) not null ,
  add_date DATETIME not null DEFAULT sysdate,

);
insert into bogatan.users(id, email, name, profile_picture) values (1,'test@test', 'test','test_profile_picture');
