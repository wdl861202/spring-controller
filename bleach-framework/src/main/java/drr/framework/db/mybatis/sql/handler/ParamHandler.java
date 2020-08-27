
package drr.framework.db.mybatis.sql.handler;

import java.util.Map;

import drr.framework.db.mybatis.sql.annotation.SqlTemplateToken;

public interface ParamHandler {

	public Map<String, Object> getContext(SqlTemplateToken annotation, Object attribute);

}
