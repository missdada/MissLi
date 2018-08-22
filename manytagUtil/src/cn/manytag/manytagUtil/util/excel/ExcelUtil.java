package cn.manytag.manytagUtil.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.manytag.manytagUtil.util.StringUtil;

public class ExcelUtil {

	/** 03及以前版本 */
	public static final String VERSION_XLS = "xls";
	/** 07及以后版本 */
	public static final String VERSION_XLSX = "xlsx";

	public static Workbook createExcel(List<List<Object>> list) {
		return createExcel(list, VERSION_XLSX);
	}

	public static Workbook createExcel(List<List<Object>> list, String version) {
		Workbook book = null;
		if (VERSION_XLS.equals(version)) {
			book = new HSSFWorkbook();
		} else if (VERSION_XLSX.equals(version)) {
			book = new XSSFWorkbook();
		} else {
			throw new IllegalArgumentException("版本不正确，应当是：" + VERSION_XLS + "/" + VERSION_XLSX);
		}
		Sheet sheet = book.createSheet("Sheet1");
		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i);
			List<Object> rowData = list.get(i);
			for (int j = 0; j < rowData.size(); j++) {
				Object obj = rowData.get(j);
				if (obj != null) {
					setCellValue(row.createCell(j), obj);
				}
			}
		}

		return book;
	}

	public static Workbook createExcel(List<LinkedHashMap<String, Object>> list, Set<String> keys, String version) {
		Workbook book = null;
		if (VERSION_XLS.equals(version)) {
			book = new HSSFWorkbook();
		} else if (VERSION_XLSX.equals(version)) {
			book = new XSSFWorkbook();
		} else {
			throw new IllegalArgumentException("版本不正确，应当是：" + VERSION_XLS + "/" + VERSION_XLSX);
		}
		Sheet sheet = book.createSheet("Sheet1");
		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i);
			Map<String, Object> rowData = list.get(i);

			Set<String> keys2;
			if (keys == null) {
				keys2 = rowData.keySet();
			} else {
				keys2 = keys;
			}
			Iterator<String> ite = keys2.iterator();

			for (int j = 0; j < keys2.size() && ite.hasNext(); j++) {
				Object obj = rowData.get(ite.next());
				if (obj != null) {
					setCellValue(row.createCell(j), obj);
				}
			}
		}

		return book;
	}

	public static void setCellValue(Cell cell, Object obj) {
		if (obj != null) {
			if (obj instanceof Boolean) {
				cell.setCellValue((boolean) obj);
			} else if (obj instanceof Number) {
				cell.setCellValue(((Number) obj).doubleValue());
			} else if (obj instanceof Character) {
				cell.setCellValue(obj.toString());
			} else if (obj instanceof Date) {
				cell.setCellValue((Date) obj);
			} else if (obj instanceof Calendar) {
				cell.setCellValue((Calendar) obj);
			} else {
				cell.setCellValue(obj.toString());
			}
		}
	}

	/**
	 * 创建Excel文件
	 * 
	 * @param records 记录列表
	 * @param fileName 文件名（包含路径）
	 * @throws IOException
	 */
	public static void createExcel(List<String> records, HttpServletResponse response) throws IOException {
		// 创建Excel工作簿  
		@SuppressWarnings("resource")
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		// 创建Excel工作表  
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("page1");
		for (int i = 0; i < records.size(); i++) {
			String record = records.get(i);
			// 创建Excel单元行  
			HSSFRow hssfRow = hssfSheet.createRow(i);
			String[] cells = record.split(",");
			for (int j = 0; j < cells.length; j++) {
				// 创建Excel单元格  
				HSSFCell hssfCell = hssfRow.createCell(j);
				// 为单元格赋值  
				hssfCell.setCellValue(cells[j].toString());
			}
		}

		OutputStream os = response.getOutputStream();
		hssfWorkbook.write(os);
		os.close();
	}

	/**
	 * 获取2003-2007版本Excel单元格的值
	 * 
	 * @param hssfCell 单元格
	 * @return 单元格的数据
	 */
	public static String getValue(HSSFCell hssfCell) {
		String value = null;
		if (hssfCell != null) {
			switch (hssfCell.getCellType()) {
			case HSSFCell.CELL_TYPE_BOOLEAN:
				// 处理布尔值类型单元格
				value = (hssfCell.getBooleanCellValue() == true ? "Y" : "N");
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				// 处理数字或日期单元格
				if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
					Date date = hssfCell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
					} else {
						value = "";
					}
				} else {
					value = new DecimalFormat("#.###").format(hssfCell.getNumericCellValue());
					//value = String.valueOf(hssfCell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				// 导入时如果为公式生成的数据则无值
				/*if (!hssfCell.getStringCellValue().equals("")) {
					value = hssfCell.getStringCellValue();
				} else {
					value = hssfCell.getNumericCellValue() + "";
				}*/
				value = "";
				break;
			default:
				value = hssfCell.getStringCellValue();
			}
		} else {
			value = "";
		}
		return value;
	}

	public static Object value(Cell cell) {
		if (cell == null) {
			return null;
		}

		switch (cell.getCellTypeEnum()) {
		case BOOLEAN:
			// 处理布尔值类型单元格
			return cell.getBooleanCellValue();
		case NUMERIC:
			// 日期单元格
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				//处理数字
				return cell.getNumericCellValue();
			}
		case FORMULA:
			//公式
			return cell.getCellFormula();
		default:
			return cell.getStringCellValue();
		}
	}

	public static String getString(Cell cell) {
		if (cell == null) {
			return null;
		}
		return StringUtil.valueOfNull(value(cell));
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row 行下标
	 * @param column 列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getValue((HSSFCell) fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 验证模板是否标准
	 * 
	 * @param records 第一行标题
	 * @param hssfRow 第一行数据
	 * @return
	 */
	public static boolean isTemplate(List<String> records, HSSFRow hssfRow) {
		int s0 = records.size();
		int s1 = hssfRow.getLastCellNum();
		if (s0 == s1) {
			for (int columnNum = 0; columnNum < hssfRow.getLastCellNum(); columnNum++) {
				HSSFCell hssfCell = hssfRow.getCell(columnNum);
				String v0 = records.get(columnNum);
				String v1 = getValue(hssfCell);
				if (!v0.equals(v1)) {
					return false;
				}

			}
			return true;
		}
		return false;
	}

}
