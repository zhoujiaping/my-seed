package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.Module;
import cn.howso.deeplan.perm.model.ModuleExample;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
@Table(name="sys_module")
public interface ModuleMapper extends BaseMapper<Module, ModuleExample, Integer>{
}