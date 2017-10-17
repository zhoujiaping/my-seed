package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.Log;
import cn.howso.deeplan.perm.model.LogExample;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
@Table(name="sys_log")
public interface LogMapper extends BaseMapper<Log, LogExample, Integer>{
}