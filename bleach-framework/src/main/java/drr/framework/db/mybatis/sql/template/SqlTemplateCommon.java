
package drr.framework.db.mybatis.sql.template;

import java.util.Collection;
import java.util.Map;

import drr.constant.mybatis.ParamHandlerConst;
import drr.constant.mybatis.SqlTokenConst;
import drr.framework.db.mybatis.sql.annotation.SqlTemplateToken;

/**
 * sql模板类
 *
 * @author wangdl
 *
 */
public class SqlTemplateCommon extends SqlTemplateBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@SqlTemplateToken(SqlTokenConst.SQL)
	private String sqlText;

	/** 组合ID */
	@SqlTemplateToken(SqlTokenConst.PORT)
	private String port;

	@SqlTemplateToken(value = SqlTokenConst.PORTS, handler = { ParamHandlerConst.PARAM_COLLECTION_HANDLER })
	private Collection<String> ports;

	/** 时间点 */
	@SqlTemplateToken(SqlTokenConst.EVALUATE_DATE)
	private String date;

	/** 开始时间 */
	@SqlTemplateToken(SqlTokenConst.BEGIN_DATE)
	private String startDate;

	/** 结束时间 */
	@SqlTemplateToken(SqlTokenConst.END_DATE)
	private String endDate;

	/** 自定义参数 */
	@SqlTemplateToken(handler = { ParamHandlerConst.PARAM_MAP_HANDLER })
	private transient Map<String, Object> customizeParams;

	/** 自定义逻辑 */
	@SqlTemplateToken(SqlTokenConst.CUSTOMIZE_LOGIC)
	private String customizeLogic;

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Collection<String> getPorts() {
		return ports;
	}

	public void setPorts(Collection<String> ports) {
		this.ports = ports;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map<String, Object> getCustomizeParams() {
		return customizeParams;
	}

	public void setCustomizeParams(Map<String, Object> customizeParams) {
		this.customizeParams = customizeParams;
	}

	public String getCustomizeLogic() {
		return customizeLogic;
	}

	public void setCustomizeLogic(String customizeLogic) {
		this.customizeLogic = customizeLogic;
	}

}
