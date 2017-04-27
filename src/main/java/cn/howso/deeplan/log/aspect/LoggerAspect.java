package cn.howso.deeplan.log.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.howso.deeplan.log.annotation.LogAnno;
import cn.howso.deeplan.log.mapper.SysLogMapper;
import cn.howso.deeplan.log.model.SysLog;
import cn.howso.deeplan.util.JSONUtils;
import cn.howso.deeplan.util.LogUtil;
import cn.howso.deeplan.util.WebUtils;


@Aspect
@Component
public class LoggerAspect {
	private static Logger logger = LogUtil.getLogger();
	@Resource SysLogMapper sysLogMapper;
	/**
	 * services层切入点
	 */
	@Pointcut("execution(* cn.howso..*.service.*.*(..)) and args(..)")
	public void servicePointCut(){};
	/**
	 * 控制层切入点
	 */
	@Pointcut("execution(* cn.howso..*.controller.*.*(..)) and args(..)")
	public void controllerPointCut(){};
	/**
	 * 执行前
	 * @param jp
	 * @throws Throwable 
	 */
	@Around("controllerPointCut()")
	public Object doAround(ProceedingJoinPoint jp) throws Throwable{
	    Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        LogAnno info = method.getAnnotation(LogAnno.class);
        if(info==null){
            return jp.proceed();
        }
        Object ret = jp.proceed();
        ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        String uri = request.getRequestURI();
        Map<String,String[]> parameterMap = request.getParameterMap();
        SysLog sysLog = new SysLog();
        sysLog.setIp(WebUtils.getRemoteAddress(request));
        sysLog.setRequestParams(JSONUtils.toJSONString(parameterMap));
        sysLog.setSuccess(true);
        sysLog.setTime(LocalDateTime.now().toString());
        sysLog.setUri(uri);
        sysLog.setUserId(null);//TODO
        sysLogMapper.insertSelective(sysLog);
        return ret;
	}
}
