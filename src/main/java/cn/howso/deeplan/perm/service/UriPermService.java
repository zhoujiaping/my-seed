package cn.howso.deeplan.perm.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.dto.UriPermWithPerm;
import cn.howso.deeplan.perm.mapper.UriPermMapper;

@Service
public class UriPermService {
    @Resource private UriPermMapper uriPermMapper;

    public Map<String,String> query() {
        List<UriPermWithPerm> uriPerms = uriPermMapper.queryFetchPerm();
        return uriPerms.stream().collect(Collectors.toMap(i->{
            return i.getMethod().toLowerCase()+" "+i.getUri();
        },i->{
            return i.getPerm().getPattern();
        }));
    }
}
