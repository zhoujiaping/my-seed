package cn.howso.deeplan.perm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.howso.deeplan.perm.model.SysRolePermission;
import cn.howso.deeplan.perm.model.SysRolePermissionExample;

public interface SysRolePermissionMapper {
    int countByExample(SysRolePermissionExample example);

    int deleteByExample(SysRolePermissionExample example);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    List<SysRolePermission> selectByExample(SysRolePermissionExample example);

    int updateByExampleSelective(@Param("record") SysRolePermission record, @Param("example") SysRolePermissionExample example);

    int updateByExample(@Param("record") SysRolePermission record, @Param("example") SysRolePermissionExample example);
}