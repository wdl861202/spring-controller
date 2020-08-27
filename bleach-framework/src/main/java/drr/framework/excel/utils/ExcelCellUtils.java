
package drr.framework.excel.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelCellUtils {

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<>();
		CreationHelper creationHelper = wb.getCreationHelper();

		CellStyle cellStyle;
		cellStyle = wb.createCellStyle(); // 单元格样式类
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
		styles.put(YYYY_MM_DD, cellStyle);

		return styles;
	}
}
