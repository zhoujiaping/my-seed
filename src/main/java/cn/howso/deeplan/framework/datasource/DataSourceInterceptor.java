package cn.howso.deeplan.framework.datasource;

import org.aspectj.lang.JoinPoint;
/**
 * 用于实现多数据源
 * @author zhoujiaping
 *
 */
public class DataSourceInterceptor{

	public void setDataSource(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType("dataSource");
	}
	
	public void setEfenceDataSource(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType("efenceDataSource");
	}

}