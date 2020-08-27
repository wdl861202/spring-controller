
package drr.framework.file;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadUtils {

	private FileDownloadUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 文件下载设置基本响应头 调用的地方较多如要修改请慎重
	 *
	 * @param response
	 * @param contentType
	 * @param fileName
	 * @throws IOException
	 */
	public static void setResponseHeaderBlob(HttpServletRequest request, HttpServletResponse response, String contentType, String fileName) throws IOException {
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + getFileNameReferBrowser(request, fileName));
		response.setHeader("Content-Type", BLOB);
		response.setContentType(contentType);
		response.setCharacterEncoding(UTF_8);
	}

	/**
	 * 根据浏览器设置生成最终的文件名
	 *
	 * @param request
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static String getFileNameReferBrowser(HttpServletRequest request, String fileName) throws IOException {
		return URLEncoder.encode(fileName, UTF_8);
	}

	public static final String UTF_8 = "UTF-8";

	public static final String ANY_TYPE = "application/*";

	public static final String JSON = "application/json";

	public static final String VND_MS_EXCEL_TYPE = "application/vnd.ms-excel";

	public static final String ZIP = "application/zip";

	public static final String BLOB = "blob";
}
