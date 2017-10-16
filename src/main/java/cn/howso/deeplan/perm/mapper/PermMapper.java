package cn.howso.deeplan.perm.mapper;

import java.util.List;

import cn.howso.deeplan.perm.model.Perm;
import cn.howso.deeplan.perm.model.PermExample;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
@Table(name="sys_perm")
public interface PermMapper extends BaseMapper<Perm,PermExample,Integer>{

    List<Perm> queryByUserName(String username);
}