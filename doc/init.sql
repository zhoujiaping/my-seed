--日志表
CREATE TABLE sys_log
(
  id serial primary key,
  uri varchar,
  method varchar,
  user_id integer,
  req_params varchar,
  success boolean,
  req_time timestamp,
  host varchar
);
--权限表
CREATE TABLE sys_perm
(
  id serial primary key,
  note varchar,
  pattern varchar
);
--角色表
CREATE TABLE sys_role
(
  id serial primary key,
  name varchar
);
--角色权限关联表
CREATE TABLE sys_role_perm
(
  perm_id integer,
  role_id integer
);
--用户表
CREATE TABLE sys_user
(
  id serial primary key,
  name varchar,
  password varchar NOT NULL,
  nick varchar,
  create_time timestamp default now()
);
--用户角色关联表
  CREATE TABLE sys_user_role
(
  user_id integer,
  role_id integer
);
--用户权限关联表
create table sys_user_perm(
	user_id integer,
	perm_id integer
);