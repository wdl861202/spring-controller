
package drr.framework.db.mybatis.sql.handler;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import drr.constant.common.PunctuationConst;
import drr.constant.mybatis.ParamHandlerConst;
import drr.framework.db.mybatis.sql.annotation.SqlTemplateToken;

/**
 * 暂时只考虑嵌套一层
 */
@Component(ParamHandlerConst.PARAM_MAP_HANDLER)
public class ParamHandlerMap extends ParamHandlerBase {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> getContext(SqlTemplateToken annotation, Object attribute) {
		Map<String, Object> map = getResultMap();

		String prefix = annotation.value();

		if (attribute instanceof Map) {
			Map m = (Map) attribute;
			m.forEach((key, value) -> {
				if (value instanceof Collection) {
					Collection c = (Collection) value;
					List result = getValueList(c);
					updateDollarMap(map, getKey(prefix, key.toString()), String.join(PunctuationConst.COMMA, result));
				}
				if (value instanceof Integer) {
					map.put(getKey(prefix, key.toString()), value);
				}
				if (value instanceof String) {
					map.put(getKey(prefix, key.toString()), value);
				}
			});
		}

		return map;
	}

	private String getKey(String prefix, String key) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix).append(key);
		return sb.toString();
	}

}
