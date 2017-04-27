package cn.howso.deeplan.perm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.howso.deeplan.perm.model.SysUserRole;
import cn.howso.deeplan.perm.model.SysUserRoleExample;

public interface SysUserRoleMapper {
    int countByExample(SysUserRoleExample example);

    int deleteByExample(SysUserRoleExample example);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    List<SysUserRole> selectByExample(SysUserRoleExample example);

    int updateByExampleSelective(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int updateByExample(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);
}