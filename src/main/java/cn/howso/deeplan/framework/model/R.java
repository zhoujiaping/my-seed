package cn.howso.deeplan.framework.model;

import java.util.HashMap;
/***/
public class R extends HashMap<String,Object>{
    private static String CODE = "code";
    private static String MSG = "msg";
    private static final long serialVersionUID = 1L;
    private R(){
        
    }
    public static R ok(){
        R r = new R();
        r.put(CODE, ReturnCode.SUCCESS);
        r.put(MSG, ReturnCode.SUCCESS_MSG);
        return r;
    }
    public static R ok(String msg){
        R r = new R();
        r.put(CODE, ReturnCode.SUCCESS);
        r.put(MSG, msg);
        return r;
    }
    public static R error(String msg){
        R r = new R();
        r.put(CODE, ReturnCode.SERVER_ERROR);
        r.put(MSG, msg);
        return r;
    }
    public static R error(){
        R r = new R();
        r.put(CODE, ReturnCode.SERVER_ERROR);
        r.put(MSG, ReturnCode.SERVER_ERROR_MSG);
        return r;
    }
    public static R error(String code,String msg){
        R r = new R();
        r.put(CODE, code);
        r.put(MSG, msg);
        return r;
    }
    public R set(String key,Object value){
        this.put(key, value);
        return this;
    }
    public R setCount(Number count){
        this.put("count", count);
        return this;
    }
    public R setData(Object data){
        this.put("data", data);
        return this;
    }
}
