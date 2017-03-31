package cn.howso.deeplan.server.specialmp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.constant.Crowd;
import cn.howso.deeplan.constant.Dict;
import cn.howso.deeplan.framework.model.Page;
import cn.howso.deeplan.server.specialmp.mapper.UrbanMapper;
import cn.howso.deeplan.server.specialmp.model.Urban;

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
        int type = Dict.MpSpecialEciRes.Type.MINUTE;
        int scopeShape = Dict.Scope.Shape.OPEN;
        return page;
    }
}
