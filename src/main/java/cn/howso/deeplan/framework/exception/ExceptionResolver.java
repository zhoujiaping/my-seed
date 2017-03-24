package cn.howso.deeplan.framework.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.howso.deeplan.framework.model.AjaxResult;
import cn.howso.deeplan.web.util.WebUtils;
public class ExceptionResolver implements HandlerExceptionResolver {
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex) {
		ex.printStackTrace();
		Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex);  
        boolean isAjax = WebUtils.isAjax(request);
		if (!isAjax) {
	            return new ModelAndView("error", model);  
		}
		AjaxResult res = new AjaxResult();
		res.setCode(BussinessException.ERR_APP);
		res.setDesc(ex.getMessage());
		WebUtils.sendResponse(response, res.toString());
		return null;
	}
}