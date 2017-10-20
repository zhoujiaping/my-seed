package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.Menu;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
import cn.howso.mybatis.model.Example;
@Table(name="sys_menu")
public interface MenuMapper extends BaseMapper<Menu, Example, Integer>{
}