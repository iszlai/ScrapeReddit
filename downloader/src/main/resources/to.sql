alter table post add column processed integer default 0;
create table status (processed integer,name text)
insert into status values (0 ,'UNPROCESSED');
insert into status values (1 ,'PROCESSED');
insert into status values (2 ,'FORBIDDEN');
insert into status values (3 ,'TIMEOUT');

