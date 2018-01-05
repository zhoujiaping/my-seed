package cn.howso.deeplan.framework.model;

public class ReturnCode {
	
	public final static String SUCCESS="200";//成功
	public final static String SUCCESS_MSG="success";//成功
	
	public final static String SERVER_ERROR="500";
	public final static String SERVER_ERROR_MSG="服务器内部错误"; 
	
	public final static String BUSINESS_ERROR="500.x";
	
	public final static String UNAUTHENED = "401.1";
	public static final String UNAUTHENED_MSG = "未认证";
	
	public final static String UNAUTHORED = "401.2";
    public static final String UNAUTHORED_MSG = "未授权";
    
    public static final String AUTHEN_FAILED = "401";
    public static final String AUTHEN_FAILED_MSG = "认证失败";
	
}
