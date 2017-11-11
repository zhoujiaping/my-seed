package cn.howso.deeplan.log.aspect;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.howso.deeplan.log.LogQueue;
import cn.howso.deeplan.log.annotation.LogAnno;
import cn.howso.deeplan.log.model.Log;
import cn.howso.deeplan.perm.constant.Const;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.util.JSONUtils;
import cn.howso.deeplan.util.LogUtil;
import cn.howso.deeplan.util.WebUtils;

@Aspect
@Component
public class LoggerAspect {

    private static Logger logger = LogUtil.getLogger();

    // @Resource LogMapper logMapper;
    /**
     * 控制层切入点
     */
    //@Pointcut("execution(* cn.howso..*.controller.*.*(..)) and args(..)")
    @Pointcut("@annotation(cn.howso.deeplan.log.annotation.LogAnno)")
    public void controllerPointCut() {
    };

    /**
     * 业务代码的执行，不应该受到日志记录的影响。如果日志代码发生异常，业务代码也应该正常执行并返回。
     * 日志生产者，多个线程生产。生产的日志放到队列。
     * @param jp
     * @throws Throwable
     */
    @Around("controllerPointCut()")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        Object ret = null;
        Exception e = null;
        try {
            ret = jp.proceed();
            return ret;
        } catch (Exception e1) {
            e = e1;
            throw e1;
        } finally {
            try {
                Log log = new Log();
                Signature signature = jp.getSignature();
                MethodSignature methodSignature = (MethodSignature) signature;
                Method method = methodSignature.getMethod();
                LogAnno info = method.getAnnotation(LogAnno.class);
                if (info != null) {
                    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                            .getRequestAttributes();
                    HttpServletRequest request = attrs.getRequest();
                    String uri = request.getRequestURI();
                    int index = uri.indexOf(';');
                    if(index>-1){
                        uri = uri.substring(0, index);
                    }
                    Map<String,Object> paramMap = new HashMap<>();
                    Enumeration<String> paramNames = request.getParameterNames();
                    String[] ignore = info.ignore();
                    Map<String,Object> ignoreMap = new HashMap<>();
                    for(int i=0;i<ignore.length;i++){
                        ignoreMap.put(ignore[i], "");
                    }
                    while(paramNames.hasMoreElements()){
                        String paramName = paramNames.nextElement();
                        if(ignoreMap.get(paramName)==null){
                            paramMap.put(paramName, request.getParameter(paramName));
                        }
                    }
                    log.setHost(WebUtils.getRemoteAddress(request));
                    String reqMethod = request.getMethod().toLowerCase();
                    if (reqMethod.equals("post")) {
                        String _method = request.getParameter("_method");
                        if (!StringUtils.isEmpty(_method)) {
                            reqMethod = _method;
                        }
                    }
                    log.setMethod(reqMethod);
                    log.setReqParams(JSONUtils.toJSONString(paramMap));
                    log.setReqTime(new Date());
                    log.setUri(uri);
                    if(e==null){
                        log.setSuccess(true);
                    }else{
                        log.setSuccess(false);
                        String errMsg = e.getMessage();
                        if(errMsg!=null && errMsg.length()>100){
                            errMsg = errMsg.substring(0,100);
                        }
                        log.setErrMsg(errMsg);
                    }
                    Session session = SecurityUtils.getSubject().getSession();
                    if (session == null) {
                        log.setUserId(null);
                    } else {
                        User user = (User) session.getAttribute(Const.SESSION_USER_KEY);
                        if (user == null) {
                            log.setUserId(null);
                        } else {
                            log.setUserId(user.getId());
                        }
                    }
                    LogQueue.QUEUE.offer(log);
                }
            } catch (Exception e2) {
                logger.error(e2.getMessage(),e2);
            }

        }
    }
}
