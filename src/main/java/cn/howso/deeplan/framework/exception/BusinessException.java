package cn.howso.deeplan.framework.exception;


public class BusinessException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public BusinessException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public BusinessException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
    
}
