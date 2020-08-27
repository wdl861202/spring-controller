
package drr.framework.db.mybatis.sql.template;

import java.io.Serializable;

import drr.constant.db.DataSourceEnum;

public abstract class SqlTemplateBase implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Integer pageNum = 0;

	private Integer pageSize = 0;

	/** 市场组数据库 */
	private DataSourceEnum dataSource = DataSourceEnum.MARKET;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public DataSourceEnum getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSourceEnum dataSource) {
		this.dataSource = dataSource;
	}
}
