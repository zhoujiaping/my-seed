package cn.howso.deeplan.crowd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.crowd.constant.Crowd;
import cn.howso.deeplan.crowd.constant.Dict;
import cn.howso.deeplan.crowd.mapper.UrbanMapper;
import cn.howso.deeplan.crowd.model.Urban;
import cn.howso.deeplan.framework.model.Page;

@Service
public class UrbanService {
    @Resource Crowd crowd;
    @Resource
    UrbanMapper urbanMapper;

    public Page<Urban> queryByPage() {
        Page<Urban> page = new Page<>(2, 2);
        Map<String, Object> condition = new HashMap<>();
        condition.put("name", "%%");
        page.setRows(urbanMapper.selectByPage(condition , page));
        crowd.ratio();
        List list;
        int type = Dict.GRAIN_MINUTE;
        int scopeShape = Dict.SCOPE_SHAPE_OPEN;
        return page;
    }
}
