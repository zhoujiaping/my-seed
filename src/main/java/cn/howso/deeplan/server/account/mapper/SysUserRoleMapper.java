package cn.howso.deeplan.server.account.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import cn.howso.deeplan.server.account.model.SysUserRole;
import cn.howso.deeplan.server.account.model.SysUserRoleExample;

public interface SysUserRoleMapper {
    int countByExample(SysUserRoleExample example);

    int deleteByExample(SysUserRoleExample example);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    List<SysUserRole> selectByExample(SysUserRoleExample example);

    int updateByExampleSelective(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int updateByExample(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);
}