package cn.howso.deeplan.perm.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.mapper.MenuMapper;
import cn.howso.deeplan.perm.mapper.RoleMenuMapper;
import cn.howso.deeplan.perm.model.RoleMenu;
import cn.howso.mybatis.model.Example;

@Service
public class MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    public Integer grantMenus(Integer roleId, List<Integer> menuIdList) {
        revokeMenus(roleId,menuIdList);
        List<RoleMenu> recordList = menuIdList.stream().map(x->{
            RoleMenu mid = new RoleMenu();
            mid.setRoleId(roleId);
            mid.setMenuId(x);
            return mid;
        }).collect(Collectors.toList());
        return roleMenuMapper.batchInsertSelective(recordList);
    }
    public Integer revokeMenus(Integer roleId, List<Integer> menuIdList) {
        Example example = new Example();
        example.createCriteria().and("role_id").equalTo(roleId).and("menu_id").in(menuIdList);
        return roleMenuMapper.deleteByExample(example);
    }
}
