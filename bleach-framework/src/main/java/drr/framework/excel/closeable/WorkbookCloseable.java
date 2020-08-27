
package drr.framework.excel.closeable;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import drr.framework.excel.utils.ExcelDataUtils;
import drr.framework.excel.utils.ExcelWriteUtils;

public class WorkbookCloseable implements Closeable {

	private Workbook workbook = null;
	private OutputStream os = null;
	private HttpServletResponse response;

	public WorkbookCloseable(List<Map<String, Object>> data, HttpServletResponse response) throws Throwable {
		workbook = ExcelWriteUtils.generateWorkbook(ExcelDataUtils.convertToExcelData(data));
		this.response = response;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public OutputStream getOs() throws IOException {
		if (os != null) {
			return os;
		} else {
			os = response.getOutputStream();
			return os;
		}
	}

	public boolean isOpenOutputStream() {
		return Optional.ofNullable(os).isPresent();
	}

	@Override
	public void close() throws IOException {
		if (Optional.ofNullable(workbook).isPresent()) {
			workbook.close();
			// 关闭资源
			if (workbook instanceof SXSSFWorkbook) {
				((SXSSFWorkbook) workbook).dispose();
			}
		}
		if (Optional.ofNullable(os).isPresent()) {
			os.close();
		}
	}

}
