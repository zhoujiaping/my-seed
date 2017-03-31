package cn.howso.deeplan.web.account.controller;

import java.io.IOException;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import cn.howso.deeplan.server.log.annotation.LogAnno;

public class FileUploadTestController {
    @RequestMapping("import")
    @ResponseBody
    @LogAnno(desc="测试")
    public ModelMap test(String name,@RequestPart MultipartFile file) throws IOException{
        System.out.println(name);
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        System.out.println(new String(file.getBytes()));
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        ParameterizableViewController pvc;
        return null;
    }
}
