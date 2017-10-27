package cn.howso.deeplan.util.httpproxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpProxy {
    
    public static int proxy(String targetUrl,HttpServletRequest request,HttpServletResponse response) throws HttpException, IOException{
        HttpClient client = new HttpClient();
        Enumeration<String> headerNames = request.getHeaderNames();
        String method = request.getMethod();
        InputStream in = request.getInputStream();
        OutputStream out = response.getOutputStream();
        Enumeration<String> paramNames = request.getParameterNames();
        HttpMethodParams params = new HttpMethodParams();
        int status = 0;
        if(paramNames!=null){//请求参数
            while(paramNames.hasMoreElements()){
                String name = paramNames.nextElement();
                params.setParameter(name,request.getParameter(name));
            }
        }
        if("get".equalsIgnoreCase(method)){
            GetMethod reqMethod = new GetMethod(targetUrl);
            if(headerNames!=null){//请求头
                while(headerNames.hasMoreElements()){
                    String name = headerNames.nextElement();
                    reqMethod.addRequestHeader(name, request.getHeader(name));
                }
            }
            //没有请求体
            reqMethod.setParams(params );
            status = client.executeMethod(reqMethod);
            response.setStatus(status);//响应码
            Header[] headers = reqMethod.getResponseHeaders();
            if(headers!=null){//响应头
                for(Header h:headers){
                    response.setHeader(h.getName(),h.getValue());
                }
            }
            //响应体
            InputStream respIn = reqMethod.getResponseBodyAsStream();
            int len = -1;
            byte[] buf = new byte[1024*5];
            while( (len=respIn.read(buf))>-1){
                out.write(buf,0,len);
            }
        }else if("post".equalsIgnoreCase(method)){
            PostMethod reqMethod = new PostMethod(targetUrl);
            if(headerNames!=null){//
                while(headerNames.hasMoreElements()){
                    String name = headerNames.nextElement();
                    reqMethod.addRequestHeader(name, request.getHeader(name));
                }
            }
            RequestEntity requestEntity = new InputStreamRequestEntity(in);//
            reqMethod.setRequestEntity(requestEntity );
            reqMethod.setParams(params);
            status = client.executeMethod(reqMethod);//
            response.setStatus(status);
            Header[] headers = reqMethod.getResponseHeaders();
            if(headers!=null){//
                for(Header h:headers){
                    response.setHeader(h.getName(),h.getValue());
                }
            }
            InputStream respIn = reqMethod.getResponseBodyAsStream();//
            int len = -1;
            byte[] buf = new byte[1024*5];
            while( (len=respIn.read(buf))>-1){
                out.write(buf,0,len);
            }
        }else if("delete".equalsIgnoreCase(method)){
            DeleteMethod reqMethod = new DeleteMethod(targetUrl);
            if(headerNames!=null){
                while(headerNames.hasMoreElements()){
                    String name = headerNames.nextElement();
                    reqMethod.addRequestHeader(name, request.getHeader(name));
                }
            }
            reqMethod.setParams(params);
            status = client.executeMethod(reqMethod);
            response.setStatus(status);
            
            Header[] headers = reqMethod.getResponseHeaders();
            if(headers!=null){
                for(Header h:headers){
                    response.setHeader(h.getName(),h.getValue());
                }
            }
            InputStream respIn = reqMethod.getResponseBodyAsStream();
            int len = -1;
            byte[] buf = new byte[1024*5];
            while( (len=respIn.read(buf))>-1){
                out.write(buf,0,len);
            }
        }else if("put".equalsIgnoreCase(method)){
            PutMethod reqMethod = new PutMethod(targetUrl);
            if(headerNames!=null){
                while(headerNames.hasMoreElements()){
                    String name = headerNames.nextElement();
                    reqMethod.addRequestHeader(name, request.getHeader(name));
                }
            }
            RequestEntity requestEntity = new InputStreamRequestEntity(in);//
            reqMethod.setRequestEntity(requestEntity );
            reqMethod.setParams(params);
            status = client.executeMethod(reqMethod);
            response.setStatus(status);
            Header[] headers = reqMethod.getResponseHeaders();
            if(headers!=null){
                for(Header h:headers){
                    response.setHeader(h.getName(),h.getValue());
                }
            }
            InputStream respIn = reqMethod.getResponseBodyAsStream();
            int len = -1;
            byte[] buf = new byte[1024*5];
            while( (len=respIn.read(buf))>-1){
                out.write(buf,0,len);
            }
        }else if("patch".equalsIgnoreCase(method)){
            throw new RuntimeException(String.format("method %s is not supported", method));
        }else{
            throw new RuntimeException(String.format("method %s is not supported", method));
        }
        return status;
    }
}
