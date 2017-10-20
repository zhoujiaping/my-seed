package cn.howso.deeplan.perm.mapper;

import java.util.List;

import cn.howso.deeplan.perm.model.Perm;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
import cn.howso.mybatis.model.Example;
@Table(name="sys_perm")
public interface PermMapper extends BaseMapper<Perm,Example,Integer>{

    List<Perm> queryByUserName(String username);

    List<Perm> queryUserPerms(String username);
}