package cn.howso.deeplan.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtils{
	
	/**
	 * 发送响应 
	 * @author wzf
	 * @param response
	 * @param jso
	 */
	public static void sendResponse(HttpServletResponse response, String content) {
		try {
			PrintWriter out = response.getWriter();
			out.write(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			
		}
	}
	/**
	 * 判断请求是否是Ajax请求
	 * @author wzf
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
	}
	public static  String getRemoteAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }
}
