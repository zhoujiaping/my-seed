package cn.howso.deeplan.crowd.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.howso.deeplan.crowd.model.Urban;
import cn.howso.deeplan.crowd.model.UrbanExample;
import cn.howso.deeplan.framework.model.Page;

public interface UrbanMapper {

    int countByExample(UrbanExample example);

    int deleteByExample(UrbanExample example);

    int deleteByPrimaryKey(String id);

    int insert(Urban record);

    int insertSelective(Urban record);

    List<Urban> selectByExample(UrbanExample example);

    Urban selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Urban record, @Param("example") UrbanExample example);

    int updateByExample(@Param("record") Urban record, @Param("example") UrbanExample example);

    int updateByPrimaryKeySelective(Urban record);

    int updateByPrimaryKey(Urban record);

    List<Urban> selectByPage(@Param("record")Map<String,Object> params,@Param("page")Page<Urban> page);
}
