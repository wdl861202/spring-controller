
package bleach.utils;

import java.util.Map;

import org.springframework.boot.json.GsonJsonParser;

public class GsonUtils {

	public static Map<String, Object> parseMap(String json) {
		GsonJsonParser gjp = new GsonJsonParser();
		return gjp.parseMap(json);
	}

}
