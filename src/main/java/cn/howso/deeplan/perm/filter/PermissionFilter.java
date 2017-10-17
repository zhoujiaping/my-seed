package cn.howso.deeplan.perm.filter;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import cn.howso.deeplan.framework.exception.BusinessException;
import cn.howso.deeplan.perm.cache.RedisCache;
import cn.howso.deeplan.perm.service.UriPermService;
import cn.howso.deeplan.util.WebUtils;

public class PermissionFilter extends AccessControlFilter {

    private UriPermService uriPermService;
    private RedisCache dataCache;

    public void setUriPermService(UriPermService uriPermService) {
        this.uriPermService = uriPermService;
    }
    
    private Map<String,Set<String>> getUriPermMap(){
     // 获取uri和权限的映射
        Map<String,Set<String>> cachedMap = (Map<String, Set<String>>) dataCache.get("uriPermMap");
        if(cachedMap==null){
            Map<String, Set<String>> uriPermMap = uriPermService.query();
            dataCache.put("uriPermMap", uriPermMap);
            return uriPermMap;
        }
        return cachedMap;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
        Subject subject = this.getSubject(req, resp);
        HttpServletRequest request = (HttpServletRequest) req;
        String permSpaceId = request.getParameter("_permSpaceId");
        // 根据url获取所需的权限
        String uri = request.getRequestURI();
        int index = uri.indexOf(';');
        if (index >= 0) {
            uri = uri.substring(0, index);
        }
        uri = uri.replaceAll("/-?\\d+", "/{id}");
        String method = request.getMethod().toLowerCase();
        Set<String> perms = getUriPermMap().get(method + " " + uri);
        if (perms == null) {// 某些资源不需要权限，比如get /login,post /login
            return true;
        }
        if (!StringUtils.isEmpty(permSpaceId)) {
            for(String perm:perms){
                if (subject.isPermitted(permSpaceId + ":" + perm)) {
                    return true;
                }
            }
            return false;
        } else {
            for(String perm:perms){
                if (subject.isPermitted(perm)) {
                    return true;
                }
            }
            return false;
        }
        /*
         * Subject subject = this.getSubject(req, resp); boolean isPermitted = false; if (mappedValue == null) {
         * isPermitted = true; } else { for (String per : (String[]) mappedValue) { if (subject.isPermitted(per)) {
         * isPermitted = true; break; } } } return isPermitted;
         */ }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
        // HttpServletResponse response = (HttpServletResponse) resp;
        // HttpServletRequest request = (HttpServletRequest) req;
        if (WebUtils.isAjax((HttpServletRequest) req)) {
            throw new BusinessException("没有权限");
        } else {
            this.saveRequest(req);
            this.saveRequestAndRedirectToLogin(req, resp);
        }
        return false;
    }

    public void setDataCache(RedisCache dataCache) {
        this.dataCache = dataCache;
    }

}
