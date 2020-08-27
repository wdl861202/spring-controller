
package drr.framework.db.mybatis.sql.parsing;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import drr.constant.common.PunctuationConst;
import drr.framework.db.mybatis.sql.SqlTemplateContext;
import drr.framework.db.mybatis.sql.annotation.SqlTemplateToken;
import drr.framework.db.mybatis.sql.handler.ParamHandler;
import drr.framework.db.mybatis.sql.handler.ParamHandlerRegistry;
import drr.framework.db.mybatis.sql.template.SqlTemplateBase;
import drr.framework.reflect.DRRReflectUtils;

/**
 * 解析参数
 *
 * @author wangdl
 *
 */
@Component
public class SqlTemplateParser {

	@Autowired
	private ParamHandlerRegistry paramHandlerRegistry;

	/**
	 * 解析sql模板
	 *
	 * @param sqlTemplate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public SqlTemplateContext parse(SqlTemplateBase sqlTemplate) throws Exception {
		SqlTemplateContext context = getSqlTemplateContext();
		Field[] fields = sqlTemplate.getClass().getDeclaredFields();
		for (Field f : fields) {
			updateContext(context, f, sqlTemplate);
		}
		return context;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SqlTemplateContext getSqlTemplateContext() {
		SqlTemplateContext context = new SqlTemplateContext<>();
		// 初始化直接替换的值
		context.put(PunctuationConst.DOLLAR, new HashMap());
		return context;
	}

	/**
	 * 更新上下文信息
	 *
	 * @param context
	 * @param f
	 * @param sqlTemplate
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void updateContext(SqlTemplateContext context, Field f, SqlTemplateBase sqlTemplate) throws Exception {
		SqlTemplateToken annotation = f.getAnnotation(SqlTemplateToken.class);
		if (Optional.ofNullable(annotation).isPresent()) {
			String[] handlers = annotation.handler();
			for (String handler : handlers) {
				ParamHandler paramHandler = paramHandlerRegistry.handlers().get(handler);
				Object value = getValue(f, sqlTemplate);
				if (Optional.ofNullable(value).isPresent()) {
					Map<String, Object> map = paramHandler.getContext(annotation, value);
					map.forEach((k, v) -> {
						if (PunctuationConst.DOLLAR.equalsIgnoreCase(k)) {
							Map dollar = (Map) context.get(PunctuationConst.DOLLAR);
							dollar.putAll((Map) v);
						} else {
							context.put(k, v);
						}
					});
				}
			}
		}
	}

	/**
	 * 获取对象中的值
	 *
	 * @param f
	 * @param sqlTemplate
	 * @return
	 * @throws Exception
	 */
	private Object getValue(Field f, SqlTemplateBase sqlTemplate) throws Exception {
		Method method = sqlTemplate.getClass().getMethod(DRRReflectUtils.getMethodName(f.getName()));
		return method.invoke(sqlTemplate);
	}
}
