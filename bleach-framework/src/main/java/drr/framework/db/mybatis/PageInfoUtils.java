
package drr.framework.db.mybatis;

import java.util.List;

import com.github.pagehelper.PageInfo;

public final class PageInfoUtils {

	private PageInfoUtils() {
		throw new IllegalStateException("Utility class");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageInfo convertToPageInfo(List result) {
		PageInfo pi = new PageInfo(result);
		return pi;
	}
}
