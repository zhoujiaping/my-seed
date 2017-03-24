package cn.howso.deeplan.web.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("perm/permission")
public class PermissionController {
    @RequestMapping("")
    public String index(){
        return "";
    }
}
