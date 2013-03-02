# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table command (
  id                        bigint not null,
  pin_id                    bigint,
  pin_status                integer,
  commander_user_name       varchar(255),
  date_create               timestamp,
  date_modify               timestamp,
  constraint pk_command primary key (id))
;

create table home_user (
  user_name                 varchar(255) not null,
  password                  varchar(255),
  admin                     boolean,
  date_create               timestamp,
  date_modify               timestamp,
  constraint pk_home_user primary key (user_name))
;

create table pin (
  pin_id                    bigint not null,
  type                      varchar(255),
  status                    integer,
  date_create               timestamp,
  date_modify               timestamp,
  version                   integer not null,
  constraint pk_pin primary key (pin_id))
;

create sequence command_seq;

create sequence home_user_seq;

create sequence pin_seq;

alter table command add constraint fk_command_commander_1 foreign key (commander_user_name) references home_user (user_name);
create index ix_command_commander_1 on command (commander_user_name);



# --- !Downs

drop table if exists command cascade;

drop table if exists home_user cascade;

drop table if exists pin cascade;

drop sequence if exists command_seq;

drop sequence if exists home_user_seq;

drop sequence if exists pin_seq;

