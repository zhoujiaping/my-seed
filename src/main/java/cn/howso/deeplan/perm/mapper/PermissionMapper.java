package cn.howso.deeplan.perm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.howso.deeplan.perm.model.Permission;
import cn.howso.deeplan.perm.model.PermissionExample;

public interface PermissionMapper {
    int countByExample(PermissionExample example);

    int deleteByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> queryByUserName(String username);
}