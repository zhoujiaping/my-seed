--由于id采用自增策略，为了能够指定id，指定的id必须小于1！！！
delete from sys_user;
insert into sys_user(id,name,password,nick,space_id)
values
(-1,'zhou','123456','z',-1);

delete from sys_role;
insert into sys_role(id,name,space_id)
values
(-1,'admin',-1);

delete from sys_user_role_mid;
insert into sys_user_role_mid(user_id,role_id)
values
(-1,-1);

delete from sys_perm_space;
insert into sys_perm_space(id,name)
values
(-1,'技侦'),
(-2,'指挥中心'),
(-3,'滨湖分局'),
(-4,'惠山分局');

/*delete from sys_perm;
insert into sys_perm(id,pattern,space_id,uri_id)
values
(-1,'users:create',-1,-4),
(-2,'users:view',-1,-3),
(-3,'users:update',-1,-7),
(-4,'users:delete',-1,-5),
(-5,'users:id:delete',-1,-6),
(-6,'users:id:view',-1,-1),
(-7,'users:id:roles-grant',-1,-2);*/

delete from sys_role_perm_mid;
insert into sys_role_perm_mid(role_id,perm_id)
select -1,id from sys_perm where space_id=-1;
/*values
(-1,-1),
(-1,-2),
(-1,-3),
(-1,-4),
(-1,-5),
(-1,-6),
(-1,-7);*/

delete from sys_menu;
insert into sys_menu(id,name,parent_id,seq)
values
(-1,'重点区域',0,1);

delete from sys_role_menu_mid;
insert into sys_role_menu_mid(role_id,menu_id)
values
(-1,-1);
/*
delete from sys_perm_uri;
insert into sys_perm_uri(id,method,uri,note)
values
(-1,'get','/seed/users/{id}','根据id查询用户'),
(-2,'post','/seed/users/{id}/roles-grant','授予角色'),
(-3,'get','/seed/users',''),
(-4,'post','/seed/users',''),
(-5,'delete','/seed/users',''),
(-6,'delete','/seed/users/{id}',''),
(-7,'put','/seed/users/{id}','');*/