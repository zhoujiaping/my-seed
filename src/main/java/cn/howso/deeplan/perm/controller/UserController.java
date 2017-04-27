package cn.howso.deeplan.perm.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.service.UserService;

@Controller
@RequestMapping("users")
public class UserController {
    @Resource UserService userService;
    
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public Object add(User user){
        return "创建用户成功";
    }
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable String id){
        //  .../user/1   delete 参数
        //  .../user/2   put 参数
        //  .../user/1   patch 参数
        //  .../user/1?name=aaa&phone=139&   get 参数
        //  .../user/1   post 参数
        return "删除用户成功";
    }
    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable String id,User user){
        return "更新用户成功";
    }
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public Object get(){
        return "查询用户列表";
    }
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable String id){
        return "查询用户";
    }
}
