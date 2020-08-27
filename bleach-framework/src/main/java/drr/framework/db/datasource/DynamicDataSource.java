package drr.framework.db.datasource;

import java.util.Map;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * 
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceType();
	}

	public DynamicDataSource defaultDataSource(Object dataSource) {
		this.setDefaultTargetDataSource(dataSource);
		return this;
	}
	
	public DynamicDataSource targetDataSources(Map<Object, Object> targetDataSources) {
		this.setTargetDataSources(targetDataSources);
		return this;
	}
	
	public static DynamicDataSource build() {
		return new DynamicDataSource();
	}
	
}