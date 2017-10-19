package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.RoleMenu;
import cn.howso.deeplan.util.Example;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;

@Table(name="sys_role_menu_mid")
public interface RoleMenuMapper extends BaseMapper<RoleMenu, Example, Object>{

}
