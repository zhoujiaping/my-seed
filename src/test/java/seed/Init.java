package seed;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @Description 用户生成部分初始化脚本
 * 虽然对Controller中的注解比较严格，但这是故意的。有意强迫写法统一。
 * @author zhoujiaping
 * @Date 2017年10月20日 上午10:12:30
 * @version 1.0.0
 */
public class Init {

    public static void main(String[] args) {
    	String fname = Init.class.getResource("/").getFile();
    	File file = new File(fname);
    	file = file.getParentFile().listFiles(x->{
    		return Objects.equals(x.getName(),"classes");
    	})[0];
    	
        Map<String,String> uriPermMap = new LinkedHashMap<>();
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
            final String baseuri = mapping.value()[0];
            Method[] methods = clazz.getMethods();
            Stream.of(methods).forEach(method->{
                RequestMapping m = method.getAnnotation(RequestMapping.class);
                if(m==null){
                    return;
                }
                String uri = null;
                String[] mvalue = m.value();
                if(mvalue!=null && !"".equals(mvalue[0])){
                    uri="/seed/"+baseuri+"/"+mvalue[0];
                }else{
                    uri="/seed/"+baseuri;
                }
                String httpmethod = m.method()[0].name().toLowerCase();
                RequiresPermissions perms = method.getAnnotation(RequiresPermissions.class);
                if(perms!=null){
                    String key = httpmethod+" "+uri;
                    String value  = perms.value()[0];
                    uriPermMap.put(key,value);
                }
            });
        });
        /*uriPermMap.forEach((k,v)->{
        	System.out.println(k+"  -->  "+v);
        });*/
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        uriPermMap.forEach((k,v)->{
            map.put(k.replaceAll("\\{.+\\}", "{id}"), v);
        });
        final int[] index = new int[]{-1};
        String permsql = "delete from sys_perm;insert into sys_perm(id,pattern,space_id,uri_id)values"+
        map.entrySet().stream().map(item->{
            String id = ""+index[0];
            index[0]--;
            return "("+String.join(",",id,"'"+item.getValue()+"'","-1",id)+")";
        }).collect(Collectors.joining(",", "", ";"));
        System.out.println(permsql);
        index[0] = -1;
        String urisql = "delete from sys_perm_uri;insert into sys_perm_uri(id,method,uri)values"+
                map.entrySet().stream().map(item->{
                    String id = ""+index[0];
                    index[0]--;
                    String[] mu = item.getKey().split(" ");
                    return "("+String.join(",",id,"'"+mu[0]+"'","'"+mu[1]+"'")+")";
                }).collect(Collectors.joining(",", "", ";"));
        System.out.println(urisql);
        
        /*map.forEach((k,v)->{
            index[0]--;
            System.out.println(k+"===>"+v);
        });*/
    }
    
    private static List<String> collectClasses(File parent){
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
