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
  email                     varchar(255),
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

create table task (
  task_id                   bigint not null,
  commander_user_name       varchar(255),
  pin_pin_id                bigint,
  status                    integer,
  execute_time              timestamp,
  date_create               timestamp,
  date_modify               timestamp,
  constraint pk_task primary key (task_id))
;

create sequence command_seq;

create sequence home_user_seq;

create sequence pin_seq;

create sequence task_seq;

alter table command add constraint fk_command_commander_1 foreign key (commander_user_name) references home_user (user_name);
create index ix_command_commander_1 on command (commander_user_name);
alter table task add constraint fk_task_commander_2 foreign key (commander_user_name) references home_user (user_name);
create index ix_task_commander_2 on task (commander_user_name);
alter table task add constraint fk_task_pin_3 foreign key (pin_pin_id) references pin (pin_id);
create index ix_task_pin_3 on task (pin_pin_id);



# --- !Downs

drop table if exists command cascade;

drop table if exists home_user cascade;

drop table if exists pin cascade;

drop table if exists task cascade;

drop sequence if exists command_seq;

drop sequence if exists home_user_seq;

drop sequence if exists pin_seq;

drop sequence if exists task_seq;

