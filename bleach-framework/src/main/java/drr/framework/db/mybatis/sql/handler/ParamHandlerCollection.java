
package drr.framework.db.mybatis.sql.handler;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import drr.constant.common.PunctuationConst;
import drr.constant.mybatis.ParamHandlerConst;
import drr.framework.db.mybatis.sql.annotation.SqlTemplateToken;

@SuppressWarnings("rawtypes")
@Component(ParamHandlerConst.PARAM_COLLECTION_HANDLER)
public class ParamHandlerCollection extends ParamHandlerBase {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getContext(SqlTemplateToken annotation, Object attribute) {
		Map<String, Object> map = getResultMap();

		if (attribute instanceof Collection) {
			Collection c = (Collection) attribute;
			List result = getValueList(c);
			updateDollarMap(map, annotation.value(), String.join(PunctuationConst.COMMA, result));
		}

		return map;
	}

}
