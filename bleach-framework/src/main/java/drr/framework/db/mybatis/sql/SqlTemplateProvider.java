
package drr.framework.db.mybatis.sql;

import java.util.Map;
import java.util.Properties;

import drr.constant.common.PaddingConst;
import drr.constant.common.PunctuationConst;
import drr.constant.mybatis.SqlTokenConst;
import drr.framework.db.mybatis.sql.parsing.SqlTemplatePretreatmentParser;

/**
 * 动态sql
 *
 * @author wangdl
 *
 */
public class SqlTemplateProvider {

	public String generateSql(Map<String, Object> param) {
		return SqlTemplatePretreatmentParser.parse(getSql(param), getDollarProperties(param));
	}

	/**
	 * 末尾增加空白字符，为后面token处理准备
	 *
	 * @param param
	 * @return
	 */
	private String getSql(Map<String, Object> param) {
		StringBuilder sb = new StringBuilder();
		sb.append(param.get(SqlTokenConst.SQL).toString()).append(PaddingConst.BLANK);
		return sb.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Properties getDollarProperties(Map<String, Object> param) {
		Properties properties = new Properties();
		properties.putAll((Map) param.get(PunctuationConst.DOLLAR));

		return properties;
	}

}
