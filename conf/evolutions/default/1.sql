# --- First database schema

# --- !Ups

set ignorecase true;

create table company (
    code                      bigint not null
  , name                      varchar(255) not null
  , name_en                   varchar(255) not null
  , phone_number              varchar( 12) not null
  , constraint pk_company primary key (code))
;



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists company;

SET REFERENTIAL_INTEGRITY TRUE;

