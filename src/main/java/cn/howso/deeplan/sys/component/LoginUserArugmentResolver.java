package cn.howso.deeplan.sys.component;

import org.apache.shiro.SecurityUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.howso.deeplan.sys.Const;
import cn.howso.deeplan.sys.anno.CurrentUser;

/**
 * 自动注入登录用户
 * */
public class LoginUserArugmentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean support = parameter.hasParameterAnnotation(CurrentUser.class);
/*        return parameter.hasParameterAnnotation(UserLogined.class)//通过注解方式  
                || parameter.getParameterType() == User.class;
*/ 
        return support;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        /*Object user  = webRequest.getNativeRequest(HttpServletRequest.class).  
                getSession().getAttribute("currentUser");*/
        Object user = SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER_KEY);
        return user;
    }

}
