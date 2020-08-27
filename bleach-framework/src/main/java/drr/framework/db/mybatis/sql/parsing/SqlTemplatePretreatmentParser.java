
package drr.framework.db.mybatis.sql.parsing;

import java.util.Properties;

import org.apache.ibatis.parsing.PropertyParser;
import org.apache.ibatis.parsing.TokenHandler;

/**
 * {@link PropertyParser}} @XXX格式的占位符替换<br>
 * 若为集合类型，则直接替换；若为单值类型，则交给mybatis处理。
 *
 * @author wangdl
 *
 */
public class SqlTemplatePretreatmentParser {

	private static final String KEY_PREFIX = "java.util.Properties.SqlTemplateTokenParser.";

	public static final String KEY_ENABLE_DEFAULT_VALUE = KEY_PREFIX + "enable-default-value";

	public static final String KEY_DEFAULT_VALUE_SEPARATOR = KEY_PREFIX + "default-value-separator";

	private static final String ENABLE_DEFAULT_VALUE = "false";
	private static final String DEFAULT_VALUE_SEPARATOR = ":";

	private static final String REGEX = "@[^@);,\\s]*?[);,\\s]";

	private SqlTemplatePretreatmentParser() {
		throw new IllegalStateException("SqlTemplatePretreatmentParser class");
	}

	public static String parse(String string, Properties variables) {
		VariableTokenHandler handler = new VariableTokenHandler(variables);
		RegexGenericTokenParser parser = new RegexGenericTokenParser(REGEX, handler);
		return parser.parse(string);
	}

	private static class VariableTokenHandler implements TokenHandler {

		private final Properties variables;
		private final boolean enableDefaultValue;
		private final String defaultValueSeparator;

		private VariableTokenHandler(Properties variables) {
			this.variables = variables;
			enableDefaultValue = Boolean.parseBoolean(getPropertyValue(KEY_ENABLE_DEFAULT_VALUE, ENABLE_DEFAULT_VALUE));
			defaultValueSeparator = getPropertyValue(KEY_DEFAULT_VALUE_SEPARATOR, DEFAULT_VALUE_SEPARATOR);
		}

		private String getPropertyValue(String key, String defaultValue) {
			return (variables == null) ? defaultValue : variables.getProperty(key, defaultValue);
		}

		/**
		 * 将@替换为#，若是直接替换则需要在传入的Properties进行值的设置，结尾的字符串会重新设置回去
		 */
		@Override
		public String handleToken(String content) {
			String filterContent = content.substring(1, content.length() - 1);
			char endChar = content.charAt(content.length() - 1);
			StringBuilder sb = new StringBuilder();

			if (variables != null) {
				String key = content;
				if (enableDefaultValue) {
					final int separatorIndex = content.indexOf(defaultValueSeparator);
					String defaultValue = null;
					if (separatorIndex >= 0) {
						key = content.substring(1, separatorIndex);
						defaultValue = content.substring(separatorIndex + defaultValueSeparator.length(), content.length() - 1);
					}
					if (defaultValue != null) {
						return sb.append(variables.getProperty(key, defaultValue)).append(endChar).toString();
					}
				}
				if (variables.containsKey(filterContent)) {
					return sb.append(variables.getProperty(filterContent)).append(endChar).toString();
				}
			}

			sb.append("#{").append(filterContent).append("}").append(endChar);
			return sb.toString();
		}

	}
}
