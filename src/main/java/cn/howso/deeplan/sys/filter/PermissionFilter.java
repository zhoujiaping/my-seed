package cn.howso.deeplan.sys.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import cn.howso.deeplan.framework.exception.BusinessException;
import cn.howso.deeplan.util.WebUtils;

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
        HttpServletResponse response = (HttpServletResponse) resp; 
        HttpServletRequest request = (HttpServletRequest) req;
        if (WebUtils.isAjax((HttpServletRequest) req)) {
            throw new BusinessException("没有权限");
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
