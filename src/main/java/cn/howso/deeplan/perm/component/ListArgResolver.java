package cn.howso.deeplan.perm.component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
/**
 * 
 * @Description 使controller中可以使用List接收参数，但是只支持7大基本类型+String类型的列表。
 * @author zhoujiaping
 * @Date 2017年10月18日 上午11:02:06
 * @version 1.0.0
 */
public class ListArgResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean support = parameter.getParameterType() == List.class;
        return support;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String name = parameter.getParameterName();
        Type type = parameter.getGenericParameterType();
        ParameterizedType pt = (ParameterizedType) type;
        Type[] args = pt.getActualTypeArguments();
        Type actualType = args[0];
        HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String,String[]> paramMap = req.getParameterMap();
        Set<Entry<String,String[]>> entryset = paramMap.entrySet();
        
        Pattern pattern = Pattern.compile("^"+name+"\\[(?<index>\\d+)\\]$");
        int[] indexs = new int[entryset.size()];
        int size = 0;
        for(Entry<String,String[]> entry:entryset){
            Matcher matcher = pattern.matcher(entry.getKey());
            if(matcher.find()){
                String index = matcher.group("index");
                indexs[size] = Integer.parseInt(index);
                size++;
            }
        }
        List<Object> list = new ArrayList<>(size);
        for(int i=0;i<size;i++){
            list.add(convert(actualType,req.getParameter(name+"["+i+"]")));
        }
        return list;
    }
    private Object convert(Type type,String value){
        if(StringUtils.isEmpty(value)){
            return null;
        }
        String typename = type.getTypeName();
        if(typename=="java.lang.Integer"){
            return Integer.parseInt(value);
        }else if(typename=="java.lang.Long"){
            return Long.parseLong(value);
        }else if(typename=="java.lang.Boolean"){
            return Boolean.valueOf(value);
        }else if(typename=="java.lang.Float"){
            return Float.parseFloat(value);
        }else if(typename=="java.lang.Double"){
            return Double.parseDouble(value);
        }else if(typename=="java.lang.Short"){
            return Short.parseShort(value);
        }else if(typename=="java.lang.Byte"){
            return Byte.parseByte(value);
        }else if(typename=="java.lang.Character"){
            return value.charAt(0);
        }else{
            throw new RuntimeException("do not support the type:"+typename);
        }
    }

}
