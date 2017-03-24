package cn.howso.deeplan.framework.exception;


public class BussinessException extends RuntimeException{

    /**
     * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
     */
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 0;
    public static final int ERR_NO_LOGIN = 102;
    public static final int ERR_PERM_DENY = 104;
    public static final int ERR_APP = 105;

}
