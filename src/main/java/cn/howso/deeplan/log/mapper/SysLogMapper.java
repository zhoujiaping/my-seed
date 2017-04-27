package cn.howso.deeplan.log.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.howso.deeplan.log.model.SysLog;
import cn.howso.deeplan.log.model.SysLogExample;
import cn.howso.deeplan.perm.model.SysUserRoleExample;

public interface SysLogMapper {
    int countByExample(SysLogExample example);

    int deleteByExample(SysLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    int insertSelective(SysLog sysLog);

    List<SysLog> selectByExample(SysLogExample example);

    SysLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysLog record, @Param("example") SysLogExample example);

    int updateByExample(@Param("record") SysLog record, @Param("example") SysLogExample example);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
    
    int nextval();
}