package cn.howso.deeplan.framework.model;

import cn.howso.deeplan.framework.exception.BussinessException;
import cn.howso.deeplan.util.json.JSONUtils;

/**
 * 封装ajax响应结果，以便浏览器端对结果是否正确做统一判断。
 * 
 * @author zhoujiaping
 */
public class AjaxResult {
    
    private int code = BussinessException.SUCCESS;
    private String desc;
    private Object content;

    public AjaxResult() {
    }
    public AjaxResult(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public AjaxResult(Object content) {
        this.content = content;
    }
    public AjaxResult(int code,String desc,Object content) {
        this.code = code;
        this.desc = desc;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }
    @Override
    public String toString() {
        return JSONUtils.toJSONString(this);
    }
}
