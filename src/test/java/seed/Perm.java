package seed;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class Perm {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Map<String,String> uriPermMap = new HashMap<>();
        String path = "D:/git-repo/my-seed/src/main/java";
        File base = new File(path);
        List<String> javafiles = collectClasses(base);
        int prefixlen = path.length()+1;
        List<Class<?>> clazzList = javafiles.stream().map(filename->{
            String clazzname = filename.substring(prefixlen).replaceAll("/|\\\\", ".");
            clazzname = clazzname.substring(0, clazzname.length()-".java".length());
            Class<?> clazz = null;
            try {
                clazz = Class.forName(clazzname);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return clazz;
        }).collect(Collectors.toList());
        clazzList = clazzList.stream().filter(clazz->{
            Controller c = clazz.getAnnotation(Controller.class);
            return c!=null;
        }).collect(Collectors.toList());
        
        clazzList.stream().forEach(clazz->{
            RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
            final String baseuri = mapping.value().length>0?"/"+mapping.value()[0]:"/";
            Method[] methods = clazz.getMethods();
            Stream.of(methods).forEach(method->{
                RequestMapping m = method.getAnnotation(RequestMapping.class);
                if(m==null){
                    return;
                }
                String part = "";
                if(m.value()!=null && m.value().length>0){
                    part = m.value()[0];   
                    if(!"".equals(part)){
                        part += "/" + part;
                    }
                }
                String httpmethod = m.method()[0].name();
                RequiresPermissions perms = method.getAnnotation(RequiresPermissions.class);
                if(perms!=null){
                    uriPermMap.put(httpmethod+" /seed"+baseuri+part, perms.value()[0]);
                }
            });;
        });
        System.out.println(uriPermMap);
    }
    public static String concatPath(String ...paths){
        return String.join("/", paths).replaceAll("//", "/");
    }
    public static List<String> collectClasses(File parent){
        List<String> classes = new ArrayList<>();
        File[] files = parent.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                classes.addAll(collectClasses(file));
            }else{
                if(file.getName().endsWith(".java")){
                    classes.add(file.getAbsolutePath());
                }
            }
        }
        return classes;
    }

}
