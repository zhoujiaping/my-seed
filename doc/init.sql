--日志表
CREATE TABLE sys_log
(
  id serial NOT NULL,
  uri character varying,
  user_id integer,
  request_params character varying,
  success boolean,
  "time" character varying,
  ip character varying,
  CONSTRAINT sys_log_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sys_log
  OWNER TO deeplan;
--权限表
CREATE TABLE sys_permission
(
  id serial NOT NULL,
  pattern character varying,
  CONSTRAINT sys_perm_pkey PRIMARY KEY (id),
  CONSTRAINT sys_perm_name_key UNIQUE (pattern)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sys_permission
  OWNER TO deeplan;
--角色表
CREATE TABLE sys_role
(
  id serial NOT NULL,
  name character varying(50),
  CONSTRAINT sys_role_pkey PRIMARY KEY (id),
  CONSTRAINT sys_role_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sys_role
  OWNER TO deeplan;
--角色权限关联表
CREATE TABLE sys_role_permission
(
  permission_id integer,
  role_id integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sys_role_permission
  OWNER TO deeplan;
--用户表
CREATE TABLE sys_user
(
  id serial NOT NULL,
  name character varying(50),
  password character varying NOT NULL,
  CONSTRAINT sys_user_pkey PRIMARY KEY (id),
  CONSTRAINT sys_user_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sys_user
  OWNER TO deeplan;
--用户角色关联表
  CREATE TABLE sys_user_role
(
  user_id integer,
  role_id integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sys_user_role
  OWNER TO deeplan;