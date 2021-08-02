package cn.howso.deeplan.framework.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.howso.deeplan.framework.model.R;
import cn.howso.deeplan.framework.model.ReturnCode;
import cn.howso.deeplan.util.LogUtil;
import cn.howso.deeplan.util.WebUtils;
public class ExceptionResolver implements HandlerExceptionResolver {
	
	private final Logger logger = LogUtil.getLogger();
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex) {
		logger.error(ex.getMessage(),ex);
		/*Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex);  
        boolean isAjax = WebUtils.isAjax(request);
		if (!isAjax) {
	        return new ModelAndView("error", model);  
		}*/
		response.setHeader("Content-Type","application/json;charset=utf-8");
		//response.setHeader("Status-Code", "500");
		R r = null;
		if(ex instanceof BusinessException){
		    r = R.error(ReturnCode.BUSINESS_ERROR,ex.getMessage());
		}else if(ex instanceof AccountException){
		    r = R.error(ReturnCode.AUTHEN_FAILED,ReturnCode.AUTHEN_FAILED_MSG);
		}else if(ex instanceof UnauthenticatedException){
		    r = R.error(ReturnCode.UNAUTHENED,ReturnCode.UNAUTHENED_MSG);
		}else if(ex instanceof UnauthorizedException){
	        r = R.error(ReturnCode.UNAUTHORED,ReturnCode.UNAUTHORED_MSG);
		}else{
		    r = R.error(ReturnCode.SERVER_ERROR,ReturnCode.SERVER_ERROR_MSG);
		}
		try {
            response.getWriter().print(JSONObject.toJSON(r));
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
        }
		return new ModelAndView();  
	}
}
