package cn.howso.deeplan.crowd.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.crowd.mapper.UrbanMapper;
import cn.howso.deeplan.crowd.model.Urban;
import cn.howso.mybatis.model.LimitPage;
import cn.howso.mybatis.model.PageRes;

@Service
public class UrbanService {
    //@Resource
    UrbanMapper urbanMapper;

    public PageRes<Urban> queryByPage() {
        Map<String, Object> condition = new HashMap<>();
        condition.put("name", "%%");
        LimitPage page = LimitPage.of(10, 0);
        PageRes<Urban> pageRes = PageRes.of(1, urbanMapper.selectByPage(condition , page ));
        return pageRes;
    }
}
