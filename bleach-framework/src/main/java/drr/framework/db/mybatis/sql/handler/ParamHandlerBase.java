
package drr.framework.db.mybatis.sql.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drr.constant.common.PunctuationConst;

public abstract class ParamHandlerBase implements ParamHandler {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List getValueList(Collection c) {
		List result = new ArrayList(c.size());
		c.stream().forEach(a -> {
			if (a instanceof String) {
				result.add(dealString(a));
			}
			if (a instanceof Number) {
				result.add(a);
			}
		});

		return result;
	}

	private String dealString(Object a) {
		StringBuilder sb = new StringBuilder();
		sb.append("\'").append(a.toString()).append("\'");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	protected Map<String, Object> getResultMap() {
		Map<String, Object> map = new HashMap<>();
		map.put(PunctuationConst.DOLLAR, new HashMap());
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void updateDollarMap(Map map, String key, Object value) {
		Map dollarMap = (Map) map.get(PunctuationConst.DOLLAR);
		dollarMap.put(key, value);
	}
}
