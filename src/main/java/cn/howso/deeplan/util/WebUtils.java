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
	/**
     * 如果是ajax请求，则需要前端配合，判断自定义响应头是否包含X-Redirect-Page
     * */
    public static void sendRedirect(HttpServletRequest request,HttpServletResponse resp,String url) throws IOException{
        url = addJsessionid(request,url);
        if(isAjax(request)){
            resp.addHeader("X-Redirect-Url", url);
        }else{
            resp.sendRedirect(url);
        }
    }
    public static void sendRedirect(HttpServletRequest request,HttpServletResponse resp,String url,String sessionid) throws IOException{
        url = addJsessionid(request,url,sessionid);
        if(isAjax(request)){
            resp.addHeader("X-Redirect-Url", url);
        }else{
            resp.sendRedirect(url);
        }
    }
    private static String addJsessionid(HttpServletRequest request, String url, String sessionid) {
        if(url.startsWith("/")){//如果是重定向到当前域
            //String sessionId = getUriPathSegmentParamValue(request.getRequestURI(),"JSESSIONID");
            if(sessionid!=null){
                int index = url.indexOf("?");
                if(index>0){
                    url = url.substring(0,index)+";JSESSIONID="+sessionid+url.substring(index);
                }else{
                    url = url+";JSESSIONID="+sessionid;
                }
            }
        }
        return url;
    }
    public static void sendAjaxRedirect(HttpServletRequest request,HttpServletResponse resp,String url){
        url = addJsessionid(request,url);
        resp.addHeader("X-Redirect-Url", url);
    }
    private static String addJsessionid(HttpServletRequest request,String url){
        String sessionid = request.getRequestedSessionId();
        return addJsessionid(request, url, sessionid);
    }
    public static void main(String[] args) {
        String uri = "/fuck;JSESSIONID=12345;SESSIONID=6789?v=1";
        String sessionid = getUriPathSegmentParamValue(uri,"JSESSIONID");
        System.out.println(sessionid);
    }
    private static String getUriPathSegmentParamValue(String uri, String paramName) {
        int queryStartIndex = uri.indexOf('?');
        if (queryStartIndex >= 0) { //get rid of the query string
            uri = uri.substring(0, queryStartIndex);
        }

        int paramSegmentIndex = uri.indexOf(';'); //now check for path segment parameters:
        if (paramSegmentIndex < 0) {
            //no path segment params - return:
            return null;
        }

        //there are path segment params, let's get the last one that may exist:

        final String TOKEN = paramName + "=";

        String paramSegment = uri.substring(paramSegmentIndex+1); //uri now contains only the path segment params

        //we only care about the last JSESSIONID param:
        int tokenIndex = paramSegment.lastIndexOf(TOKEN);
        if (tokenIndex < 0) {
            //no segment param:
            return null;
        }
        paramSegment = paramSegment.substring(tokenIndex + TOKEN.length());
        int index = paramSegment.indexOf(';');//去掉片段中的其他参数
        if(index >= 0 ){
            paramSegment = paramSegment.substring(0, index);
        }
        return paramSegment; //what remains is the value
    }
}
