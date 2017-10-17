package cn.howso.deeplan.perm.filter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import cn.howso.deeplan.framework.exception.BusinessException;
import cn.howso.deeplan.util.WebUtils;

public class PermissionFilter extends AccessControlFilter{
	
	@PostConstruct
	public void initUriPermMap(){
		//获取uri和权限的映射
		
	}
	Map<String,Set<String>> uriPermMap = new HashMap<>();
	{
		Set<String> perms = new HashSet<>();
		 perms.addAll(Arrays.asList(new String[]{"users:view"}));
		uriPermMap.put("get /seed/users",perms);
	}
	@Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
    	Subject subject = this.getSubject(req, resp);
        HttpServletRequest request = (HttpServletRequest) req;
        String permSpaceId = request.getParameter("_permSpaceId");
        //根据url获取所需的权限
        String uri = request.getRequestURI();
        int index = uri.indexOf(';');
        if(index>=0){
        	uri = uri.substring(0, index);
        }
        String method = request.getMethod().toLowerCase();
        Set<String> perms = uriPermMap.get(method+" "+uri);
        if(perms==null){//某些资源不需要权限，比如get /login,post /login
        	return true;
        }
        if(!StringUtils.isEmpty(permSpaceId)){
        	for(String perm:perms){
        		if(subject.isPermitted(permSpaceId+":"+perm)){
        			return true;
        		}
        	}
        	return false;
        }else{
        	for(String perm:perms){
        		if(subject.isPermitted(perm)){
        			return true;
        		}
        	}
        	return false;
        }
/*        Subject subject = this.getSubject(req, resp);
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
*/    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
        //HttpServletResponse response = (HttpServletResponse) resp; 
        //HttpServletRequest request = (HttpServletRequest) req;
        if (WebUtils.isAjax((HttpServletRequest) req)) {
            throw new BusinessException("没有权限");
        } else {
            this.saveRequest(req);
            this.saveRequestAndRedirectToLogin(req, resp);
        }
        return false;
    }

}
