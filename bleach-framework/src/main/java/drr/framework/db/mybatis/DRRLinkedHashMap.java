
package drr.framework.db.mybatis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class DRRLinkedHashMap extends LinkedHashMap<String, Object> {

	/** 重名计数 */
	private final Map<String, Integer> nameToCount = new HashMap<>();
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object put(String key, Object value) {
		if (IGNORE_FIELDS.contains(key)) {
			return null;
		}

		if (StringUtils.isEmpty(key)) {
			return super.put(getNullKey(), value);
		} else {
			return super.put(getDuplicateKey(key), value);
		}
	}

	/** 为空值的情况 */
	private String getNullKey() {
		Integer count = 1;
		if (nameToCount.containsKey(FEILD)) {
			count = nameToCount.get(FEILD) + 1;
			nameToCount.put(FEILD, count);
		} else {
			nameToCount.put(FEILD, count);
		}
		StringBuilder sb = new StringBuilder();
		return sb.append(FEILD).append(count).toString();
	}

	/** 多个名字重复的情况 */
	private String getDuplicateKey(String key) {
		StringBuilder sb = new StringBuilder();
		Integer count = 0;
		if (nameToCount.containsKey(key)) {
			count = nameToCount.get(key) + 1;
			nameToCount.put(key, count);
			return sb.append(key).append(count).toString();
		} else {
			nameToCount.put(key, count);
			return sb.append(key).toString();
		}
	}

	private static final String FEILD = "field";

	private static final Set<String> IGNORE_FIELDS = new HashSet<>();
	{
		IGNORE_FIELDS.add("PAGE_ROW_NUMBER");
	}
}
