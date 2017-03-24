select * from sys_permission;
insert into sys_permission(pattern)values('user:create');
select * from sys_role;
insert into sys_role(name)values('admin');
select * from sys_user;
select * from sys_user_role;
insert into sys_user_role(user_id,role_id)values(1,1);
select * from sys_role_permission;
insert into sys_role_permission(role_id,permission_id)values(1,1);