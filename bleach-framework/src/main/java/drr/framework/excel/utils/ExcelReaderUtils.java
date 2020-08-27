
package drr.framework.excel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.springframework.web.multipart.MultipartFile;

import drr.api.common.vo.select.SelectItemVO;
import drr.api.common.vo.select.SelectVO;

public class ExcelReaderUtils {

	private ExcelReaderUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * sheetName转化为select控件
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static SelectVO sheetNameToSelectVO(MultipartFile file) throws Exception {
		SelectVO select = new SelectVO();
		List<SelectItemVO> selectItems = new ArrayList<>();
		OPCPackage pkg = OPCPackage.open(file.getInputStream());
		XSSFReader r = new XSSFReader(pkg);
		SheetIterator sheetsData = (SheetIterator) r.getSheetsData();
		sheetsData.forEachRemaining(i -> {
			String sheetName = sheetsData.getSheetName();
			SelectItemVO vo = new SelectItemVO();
			vo.setValue(sheetName);
			vo.setLabel(sheetName);
			selectItems.add(vo);
		});
		select.setValues(selectItems);
		return select;
	}

	/**
	 * 获取sheet名字
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static List<String> getSheetName(MultipartFile file) throws Exception {
		List<String> sheetNames = new ArrayList<>();
		if (Optional.ofNullable(file).isPresent()) {
			OPCPackage pkg = OPCPackage.open(file.getInputStream());
			XSSFReader r = new XSSFReader(pkg);
			SheetIterator sheetsData = (SheetIterator) r.getSheetsData();
			sheetsData.forEachRemaining(i -> sheetNames.add(sheetsData.getSheetName()));
		}
		return sheetNames;
	}
}
