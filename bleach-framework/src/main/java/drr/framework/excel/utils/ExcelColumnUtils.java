
package drr.framework.excel.utils;

public class ExcelColumnUtils {

	/**
	 * Excel column index begin 1
	 *
	 * @param colStr
	 * @param length
	 * @return
	 */
	public static int excelColStrToNum(String colStr) {
		int num = 0;
		int result = 0;
		int length = colStr.length();
		for (int i = 0; i < length; i++) {
			char ch = colStr.charAt(length - i - 1);
			num = ch - 'A' + 1;
			num *= Math.pow(26, i);
			result += num;
		}
		return result;
	}

	/**
	 * Excel column index begin 1
	 *
	 * @param columnIndex
	 * @return
	 */
	public static String excelColIndexToStr(int columnIndex) {
		if (columnIndex <= 0) {
			return null;
		}
		String columnStr = "";
		columnIndex--;
		do {
			if (columnStr.length() > 0) {
				columnIndex--;
			}
			columnStr = ((char) (columnIndex % 26 + 'A')) + columnStr;
			columnIndex = (columnIndex - columnIndex % 26) / 26;
		} while (columnIndex > 0);
		return columnStr;
	}
}
