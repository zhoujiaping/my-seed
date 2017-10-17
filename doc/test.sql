--由于id采用自增策略，为了能够指定id，指定的id必须小于1！！！
insert into sys_user(id,name,password,nick)values(-1,'zhou','123456','z');
insert into sys_role(id,name)values(-1,'admin');
insert into sys_user_role(user_id,role_id)values(-1,-1);
insert into sys_perm_space(id,name)
values
(-1,'技侦'),
(-2,'指挥中心'),
(-3,'滨湖分局'),
(-4,'惠山分局');
insert into sys_perm(id,pattern,space_id)
values
(-1,'users:create',-1),
(-2,'users:view',-1),
(-3,'users:update',-1),
(-4,'users:delete',-1),
(-5,'users:deleteOne',-1),
(-6,'users:viewOne',-1);
insert into sys_role_perm(role_id,perm_id)
values
(-1,-1),
(-1,-2),
(-1,-3),
(-1,-4),
(-1,-5),
(-1,-6);

insert into sys_module(id,name)
values
(-1,'重点区域');
insert into sys_role_module(role_id,module_id)values(-1,-1);

insert into sys_uri_perm(id,method,uri,note,perm_id)
values
(-1,'get','/seed/users/{id}','根据id查询用户',-6);