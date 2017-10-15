select * from sys_perm;
insert into sys_perm(pattern,space_id)values('1:users:create',1);
insert into sys_perm(pattern,space_id)values('1:users:view',1);
select * from sys_role;
insert into sys_role(name)values('admin');
select * from sys_user;
select * from sys_user_role;
insert into sys_user_role(user_id,role_id)values(1,1);
select * from sys_role_perm;
insert into sys_role_perm(role_id,perm_id)values(1,1);
insert into sys_role_perm(role_id,perm_id)values(1,2);
insert into sys_user(name,password,nick)values('zhou','123456','z');

insert into sys_perm_space(name)values('技侦','指挥中心','滨湖分局','惠山分局','梁山分局');
