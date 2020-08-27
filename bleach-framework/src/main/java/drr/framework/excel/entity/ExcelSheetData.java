
package drr.framework.excel.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ExcelSheet页信息
 * 
 * @author wangdl
 *
 */
@SuppressWarnings("rawtypes")
public class ExcelSheetData implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String sheetName;

	private List<? extends LinkedHashMap> data;

	private List<String> header;

	private String startPosition = "A2";

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<? extends LinkedHashMap> getData() {
		return data;
	}

	public void setData(List<? extends LinkedHashMap> data) {
		this.data = data;
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public String getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(String startPosition) {
		this.startPosition = startPosition;
	}

}
