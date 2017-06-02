package cn.howso.deeplan.constant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
@Component
public class DynamicProperties {
    private ClassPathResource resource;
    private Properties prop;
    @PostConstruct 
    public void init(){
        refresh();
    }
    public void refresh(){
        prop = new Properties();
        try {
            resource = new ClassPathResource("dynamic.properties");
            prop.load(resource.getInputStream());
            //prop.load(DynamicProperties.class.getResourceAsStream("dynamic.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("dynamic.properties文件未找到");
        }
    }
    public void store(){
        try {
            String comments = "";
            File file = resource.getFile();
            Writer out = new FileWriter(file);
            prop.store(out, comments);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("保存dynamic.properties异常");
        }
    }
    public String get(String key){
        return prop.getProperty(key);
    }
}
