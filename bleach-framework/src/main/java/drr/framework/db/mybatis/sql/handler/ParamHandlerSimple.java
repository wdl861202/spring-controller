
package drr.framework.db.mybatis.sql.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import drr.constant.mybatis.ParamHandlerConst;
import drr.framework.db.mybatis.sql.annotation.SqlTemplateToken;

@Component(ParamHandlerConst.PARAM_SIMPLE_HANDLER)
public class ParamHandlerSimple extends ParamHandlerBase {

	@Override
	public Map<String, Object> getContext(SqlTemplateToken annotation, Object attribute) {
		Map<String, Object> map = new HashMap<>();
		if (isSimpleObject(attribute)) {
			map.put(annotation.value(), attribute);
		}
		return map;
	}

	private boolean isSimpleObject(Object attribute) {
		boolean isMap = attribute instanceof Map;
		boolean isIterable = attribute instanceof Iterable;

		return !isMap && !isIterable;
	}
}
