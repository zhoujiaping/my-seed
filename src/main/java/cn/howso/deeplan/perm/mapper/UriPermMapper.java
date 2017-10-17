package cn.howso.deeplan.perm.mapper;

import java.util.List;

import cn.howso.deeplan.perm.dto.UriPermWithPerm;
import cn.howso.deeplan.perm.model.UriPerm;
import cn.howso.deeplan.perm.model.UriPermExample;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
@Table(name="sys_uri_perm")
public interface UriPermMapper extends BaseMapper<UriPerm,UriPermExample,Integer>{

    List<UriPermWithPerm> queryFetchPerm();
}