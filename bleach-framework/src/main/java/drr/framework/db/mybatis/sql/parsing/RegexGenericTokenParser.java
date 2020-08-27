
package drr.framework.db.mybatis.sql.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

/**
 * {@link GenericTokenParser}，正则表达式token匹配
 */
public class RegexGenericTokenParser {

	private final TokenHandler handler;
	private final Pattern pattern;

	public RegexGenericTokenParser(String regex, TokenHandler handler) {
		this.handler = handler;
		pattern = Pattern.compile(regex);
	}

	public String parse(String text) {
		if (text == null || text.isEmpty()) {
			return "";
		}

		Matcher matcher = pattern.matcher(text);
		if (!matcher.find()) {
			return text;
		}

		char[] src = text.toCharArray();
		int offset = 0;
		final StringBuilder builder = new StringBuilder();
		do {
			String group = matcher.group();
			int start = matcher.start();
			builder.append(src, offset, start - offset);
			builder.append(handler.handleToken(group));
			offset = start + group.length();
		} while (matcher.find());

		if (offset < src.length) {
			builder.append(src, offset, src.length - offset);
		}

		return builder.toString();
	}
}
