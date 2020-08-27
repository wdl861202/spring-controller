
package drr.framework.reflect;

import org.apache.commons.lang.StringUtils;

public class DRRReflectUtils {

	private DRRReflectUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 获取get方法
	 *
	 * @param name
	 * @return
	 */
	public static String getMethodName(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("get").append(StringUtils.capitalize(name));
		return sb.toString();
	}
}
