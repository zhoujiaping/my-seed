package cn.howso.deeplan.perm.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.howso.deeplan.framework.model.R;
import cn.howso.deeplan.framework.model.ReturnCode;
import cn.howso.deeplan.perm.cache.RedisCache;
import cn.howso.deeplan.perm.service.UriPermService;
import cn.howso.deeplan.util.WebUtils;

public class PermissionFilter extends AuthorizationFilter {

    private UriPermService uriPermService;
    private RedisCache dataCache;

    public void setUriPermService(UriPermService uriPermService) {
        this.uriPermService = uriPermService;
    }

    private Map<String, Set<String>> getUriPermMap() {
        // 获取uri和权限的映射
        Map<String, Set<String>> cachedMap = (Map<String, Set<String>>) dataCache.get("uriPermMap");
        if (cachedMap == null) {
            Map<String, Set<String>> uriPermMap = uriPermService.query();
            dataCache.put("uriPermMap", uriPermMap);
            return uriPermMap;
        }
        return cachedMap;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
        if(isLoginRequest(req, resp)){
            return true;
        }
        HttpServletRequest request = (HttpServletRequest) req;
        Subject subject = this.getSubject(req, resp);
        String permSpaceId = request.getParameter("_permSpaceId");
        // 根据url获取所需的权限
        String uri = request.getRequestURI();
        int index = uri.indexOf(';');
        if (index >= 0) {
            uri = uri.substring(0, index);
        }
        uri = uri.replaceAll("/-?\\d+", "/{id}");
        String method = request.getMethod().toLowerCase();
        if(method.equalsIgnoreCase("post")){
            method = request.getParameter("_method");
        }
        Set<String> perms = getUriPermMap().get(method + " " + uri);
        if(perms==null){
            return false;
        }
        if (!StringUtils.isEmpty(permSpaceId)) {
            for (String perm : perms) {
                if (subject.isPermitted(permSpaceId + ":" + perm)) {
                    return true;
                }
            }
            return false;
        } else {
            for (String perm : perms) {
                if (subject.isPermitted(perm)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        Subject subject = getSubject(req, resp);
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(req, resp);
        } else {
            if (WebUtils.isAjax(request)) {
                R r = R.error(ReturnCode.NO_PERMISSION, ReturnCode.NO_PERMISSION_MSG);
                response.getWriter().print(JSONObject.toJSON(r));
            } else {
                String unauthorizedUrl = getUnauthorizedUrl();
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.sendRedirect(request, response, unauthorizedUrl);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
        return false;
    }

    public void setDataCache(RedisCache dataCache) {
        this.dataCache = dataCache;
    }

}
