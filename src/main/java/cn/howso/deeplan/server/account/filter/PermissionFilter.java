package cn.howso.deeplan.server.account.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import cn.howso.deeplan.framework.exception.BussinessException;
import cn.howso.deeplan.framework.model.AjaxResult;
import cn.howso.deeplan.web.util.WebUtils;

public class PermissionFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
        Subject subject = this.getSubject(req, resp);
        boolean isPermitted = false;
        if (mappedValue == null) {
            isPermitted = true;
        } else {
            for (String per : (String[]) mappedValue) {
                if (subject.isPermitted(per)) {
                    isPermitted = true;
                    break;
                }
            }
        }
        return isPermitted;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
        if (WebUtils.isAjax((HttpServletRequest) req)) {
            WebUtils.sendResponse((HttpServletResponse) resp,
                    new AjaxResult(BussinessException.ERR_PERM_DENY, "没有权限").toString());
        } else {
            this.saveRequest(req);
            // HttpServletRequest request = (HttpServletRequest)req;
            // HttpServletResponse response = (HttpServletResponse)resp;
            // response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/login.jsp");
            this.saveRequestAndRedirectToLogin(req, resp);
        }
        return false;
    }

}
