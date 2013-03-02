# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table home_user (
  user_name                 varchar(255) not null,
  password                  varchar(255),
  admin                     boolean,
  date_create               timestamp,
  date_modify               timestamp,
  constraint pk_home_user primary key (user_name))
;

create sequence home_user_seq;




# --- !Downs

drop table if exists home_user cascade;

drop sequence if exists home_user_seq;

