
package drr.framework.excel.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import drr.framework.excel.entity.ExcelSheetData;

public class ExcelWriteUtils {

	private static final Pattern colPattern = Pattern.compile("[A-Z]+");

	private static final Pattern rowPattern = Pattern.compile("\\d+");

	private ExcelWriteUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 返回workbook，注意使用后关闭资源
	 *
	 * @param datas
	 * @return
	 * @throws Throwable
	 */
	public static Workbook generateWorkbook(List<ExcelSheetData> datas) throws Throwable {
		SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and
		if (CollectionUtils.isEmpty(datas)) {
			return wb;
		}

		for (ExcelSheetData excelData : datas) {
			Sheet sh = wb.createSheet(excelData.getSheetName());
			writeHeader(sh, excelData);
			writeBody(wb, sh, excelData);
		}
		return wb;
	}

	private static void writeHeader(Sheet sh, ExcelSheetData excelData) {
		if (CollectionUtils.isEmpty(excelData.getHeader())) {
			return;
		}
		Row headerRow = sh.createRow(0);
		int headerSize = excelData.getHeader().size();
		for (int i = 0; i < headerSize; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(excelData.getHeader().get(i));
		}
	}

	@SuppressWarnings("unchecked")
	public static void writeBody(Workbook wb, Sheet sh, ExcelSheetData excelData) throws IOException {
		if (StringUtils.isEmpty(excelData.getStartPosition())) {
			return;
		}

		Map<String, CellStyle> styles = ExcelCellUtils.createStyles(wb);

		int startColumn = 0;
		int startRow = 1;
		Matcher colMatcher = colPattern.matcher(excelData.getStartPosition());
		Matcher rowMatcher = rowPattern.matcher(excelData.getStartPosition());
		if (colMatcher.find()) {
			startColumn = ExcelColumnUtils.excelColStrToNum(colMatcher.group()) - 1;
		}
		if (rowMatcher.find()) {
			startRow = Integer.parseInt(rowMatcher.group()) - 1;
		}

		int rownum = 1;
		int dataSize = excelData.getData().size();
		for (int i = 0; i < dataSize; i++) {
			Row row = sh.createRow(startRow + i);
			Map<String, Object> map = excelData.getData().get(i);
			int j = startColumn;
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				writeCell(wb, row, j, entry.getValue(), styles);
				j++;
				rownum++;
				if (rownum % 100 == 0) {
					((SXSSFSheet) sh).flushRows(100); // retain 100 last
														// rows
														// and
														// flush all others
					// ((SXSSFSheet)sh).flushRows() is a shortcut for
					// ((SXSSFSheet)sh).flushRows(0),
					// this method flushes all rows
				}
			}
		}
	}

	private static void writeCell(Workbook wb, Row row, int cellnum, Object v, Map<String, CellStyle> styles) {
		Cell cell = row.createCell(cellnum);
		boolean isNotNull = Optional.ofNullable(v).isPresent();
		if (isNotNull) {
			if (v instanceof Date) {
				cell.setCellValue((Date) v);
				cell.setCellStyle(styles.get(ExcelCellUtils.YYYY_MM_DD));
				return;
			}

			if (v instanceof Boolean) {
				cell.setCellValue((Boolean) v);
				return;
			}

			if (v instanceof Double) {
				cell.setCellValue((Double) v);
			}

			if (v instanceof BigDecimal) {
				BigDecimal b = (BigDecimal) v;
				cell.setCellValue(b.stripTrailingZeros().toPlainString());
				return;
			}

			cell.setCellValue(v.toString());
			return;
		}

		cell.setCellValue("");
	}

}
