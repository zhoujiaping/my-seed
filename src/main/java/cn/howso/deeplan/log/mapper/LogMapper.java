package cn.howso.deeplan.log.mapper;

import cn.howso.deeplan.log.model.Log;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
import cn.howso.mybatis.model.Example;

@Table(name="sys_log")
public interface LogMapper extends BaseMapper<Log,Example,Integer>{

}
