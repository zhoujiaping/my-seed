package cn.howso.deeplan.server.account.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import cn.howso.deeplan.framework.exception.BussinessException;
import cn.howso.deeplan.framework.model.AjaxResult;
import cn.howso.deeplan.web.util.WebUtils;

public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object arg2) throws Exception {
        //已登录或者是登录请求则允许访问
        if (SecurityUtils.getSubject().getPrincipal() != null || isLoginRequest(req, resp)) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
        if (WebUtils.isAjax((HttpServletRequest) req)) {
            WebUtils.sendResponse((HttpServletResponse) resp,
                    new AjaxResult(BussinessException.ERR_NO_LOGIN, "未登录").toString());
        } else {
            this.saveRequestAndRedirectToLogin(req, resp);
        }
        return false;
    }

}
