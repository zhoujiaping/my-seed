package cn.howso.deeplan.perm.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.howso.deeplan.framework.model.R;
import cn.howso.deeplan.framework.model.ReturnCode;
import cn.howso.deeplan.util.LogUtil;
import cn.howso.deeplan.util.WebUtils;

public class LoginFilter extends AccessControlFilter {
    private static final Logger logger = LogUtil.getLogger();
    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object arg2) throws Exception {
        // 已登录或者是登录请求则允许访问
        HttpServletRequest request = (HttpServletRequest) req;
        if(logger.isDebugEnabled()){
            logger.debug(request.getRequestURI());
        }
        if (SecurityUtils.getSubject().getPrincipal() != null) {
            return true;
        }
        if(isLoginRequest(req, resp)){
            this.saveRequest(req);//用于登录成功后跳转到登录前想访问的页面
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletResponse response = (HttpServletResponse) resp;
        //HttpServletRequest request = (HttpServletRequest) req;
        R r = R.error(ReturnCode.UNAUTHENED, ReturnCode.UNAUTHENED_MSG);
        WebUtils.sendResponse(response, JSON.toJSONString(r));
        return false;
        /*if(WebUtils.isAjax(request)){
            WebUtils.sendAjaxRedirect(request, response, getLoginUrl());
        }else{
            this.saveRequestAndRedirectToLogin(req, resp);//在禁用cookie的情况下，该方法会自动在重定向的地址后面添加JSESSIONID（如贵重定向到当前域）。
        }
        return false;*/
    }

}
