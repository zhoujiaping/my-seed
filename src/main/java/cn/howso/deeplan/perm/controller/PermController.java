package cn.howso.deeplan.perm.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.framework.model.R;
import cn.howso.deeplan.log.annotation.LogAnno;
import cn.howso.deeplan.perm.model.Perm;
import cn.howso.deeplan.perm.service.PermService;

@Controller
@RequestMapping("perms")
public class PermController {
    @Resource private PermService permService;
    
    @RequestMapping(value="",method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("perms:query")
    @LogAnno
    public R query(Integer _permSpaceId){
        R r = R.ok();
        r.put("perms", permService.query(_permSpaceId));
        return r;
    }
}
