
package drr.framework.db.datasource;

import drr.constant.db.DataSourceEnum;

/**
 * 动态数据源设置
 * 
 * @author wangdl
 *
 */
public class DataSourceContextHolder {

	private static final ThreadLocal<DataSourceEnum> contextHolder = new ThreadLocal<DataSourceEnum>();

	public static void setDataSourceType(DataSourceEnum customerType) {
		contextHolder.set(customerType);
	}

	public static DataSourceEnum getDataSourceType() {
		return contextHolder.get();
	}

	public static void clearDataSourceType() {
		contextHolder.remove();
	}
}