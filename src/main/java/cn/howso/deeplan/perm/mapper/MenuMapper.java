package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.Menu;
import cn.howso.deeplan.util.Example;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
@Table(name="sys_menu")
public interface MenuMapper extends BaseMapper<Menu, Example, Integer>{
}