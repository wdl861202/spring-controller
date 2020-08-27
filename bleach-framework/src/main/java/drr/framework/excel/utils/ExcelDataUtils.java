
package drr.framework.excel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import drr.framework.db.mybatis.DRRLinkedHashMap;
import drr.framework.excel.entity.ExcelSheetData;

public class ExcelDataUtils {

	/** controller层转化excel数据 */
	public static List<ExcelSheetData> convertToExcelData(List<Map<String, Object>> list) throws Throwable {
		List<ExcelSheetData> result = new ArrayList<>();

		for (Map<String, Object> m : list) {
			ExcelSheetData obj = ExcelSheetData.class.newInstance();
			BeanUtils.populate(obj, m);
			result.add(obj);
		}

		return result;
	}

	/** service层转化excel数据 */
	public static List<ExcelSheetData> convertToExcelDataList(Map<String, List<DRRLinkedHashMap>> result) {
		List<ExcelSheetData> list = new ArrayList<>(result.size());
		result.forEach((k, v) -> {
			list.add(convertToExcelData(v, k));
		});

		return list;
	}

	/** service层转化excel数据 */
	public static ExcelSheetData convertToExcelData(List<DRRLinkedHashMap> result, String atName) {
		List<String> header = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(result)) {
			result.get(0).forEach((k, v) -> {
				header.add(k);
			});
		}
		ExcelSheetData data = new ExcelSheetData();
		data.setData(result);
		data.setSheetName(atName);
		data.setHeader(header);
		return data;
	}
}
