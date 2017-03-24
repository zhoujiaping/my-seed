package cn.howso.deeplan.framework.datasource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 用于实现多数据源
 * @author zhoujiaping
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DatabaseContextHolder.getCustomerType();
	}

}