package cn.howso.deeplan.perm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.dto.PermUriWithPerm;
import cn.howso.deeplan.perm.mapper.PermUriMapper;

@Service
public class UriPermService {
    @Resource private PermUriMapper uriPermMapper;

    public Map<String,Set<String>> query() {
        List<PermUriWithPerm> uriPerms = uriPermMapper.queryFetchPerm();
        return uriPerms.stream().collect(Collectors.toMap(i->{
            return i.getMethod().toLowerCase()+" "+i.getUri();
        },i->{
            return i.getPerms().stream().map(x->x.getPattern()).collect(Collectors.toSet());
        }));
    }
}
