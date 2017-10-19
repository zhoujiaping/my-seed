package seed;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class Perm {

    public static void main(String[] args) {
    	String fname = Perm.class.getResource("/").getFile();
    	System.out.println(fname);
    	File file = new File(fname);
    	file = file.getParentFile().listFiles(x->{
    		return Objects.equals(x.getName(),"classes");
    	})[0];
    	
        Map<String,String> uriPermMap = new HashMap<>();
        File base = file;
        List<String> javafiles = collectClasses(base);
        int prefixlen = base.getAbsolutePath().length()+1;
        List<Class<?>> clazzList = javafiles.stream().map(filename->{
            String clazzname = filename.substring(prefixlen).replaceAll("/|\\\\", ".");
            clazzname = clazzname.substring(0, clazzname.length()-".class".length());
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
                    uriPermMap.put(httpmethod+" "+concatPath("/seed",baseuri,part), perms.value()[0]);
                }
            });
        });
        uriPermMap.forEach((k,v)->{
        	System.out.println(k+"  -->  "+v);
        });
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
                if(file.getName().endsWith(".class")){
                    classes.add(file.getAbsolutePath());
                }
            }
        }
        return classes;
    }

}
