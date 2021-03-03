package com.ruoyi.project.tool;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;

public class ExcelTool {

	/**
	 * fjx
	 * 获取Workbook
	 * @param file
	 * @return
	 * @throws Exception
	 *      一次导入的时候报了以下错误
	 *      java.lang.IndexOutOfBoundsException: Block 768 not found - java.lang.IndexOutOfBoundsException: Unable to read 512 bytes from 393728 in stream of length 393728
	 *
	 *      网上找到的说法是这个文件可能有一点损坏，然后用代码直接读取的时候会发生问题。可以先用本地电脑尝试能不能打开，如果可以打开，就什么都不用改，直接点保存。然后尝试重新导入。
	 */
	public static Workbook getWb(MultipartFile file) throws Exception {

		InputStream inputStream = null;
		String url = file.getOriginalFilename();//文件的原名
		String suffix = url.substring(url.lastIndexOf("."));// 文件的后缀名
		inputStream=file.getInputStream();
		Workbook book = WorkbookFactory.create(inputStream);
		//Workbook book=null;

//        if (".xls".equals(suffix)) {// 支持07版本以前的excel
//            book = new HSSFWorkbook(inputStream);
//        }else if (".xlsx".equals(suffix)) {// 支持07版本以后的excel
//            book = new XSSFWorkbook(inputStream);
//        }else {
//            throw new RuntimeException("不是excel文件");
//        }
		return book;
	}

	/**
	 * 获取单元格的值
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell){
		if(cell == null) return "";
		String returnValue = null;
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: // 数字
				Double doubleValue = cell.getNumericCellValue();

				// 格式化科学计数法，取一位整数
				DecimalFormat df = new DecimalFormat("0");
				returnValue = df.format(doubleValue);
				break;
			case Cell.CELL_TYPE_STRING: // 字符串
				returnValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN: // 布尔
				Boolean booleanValue = cell.getBooleanCellValue();
				returnValue = booleanValue.toString();
				break;
			case Cell.CELL_TYPE_BLANK: // 空值
				break;
			case Cell.CELL_TYPE_FORMULA: // 公式
				returnValue = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR: // 故障
				break;
			default:
				break;
		}
		return returnValue;
	}

	/**
	 * 判断某坐标单元格是否为合并单元格
	 * @param sheet
	 * @param row 行坐标
	 * @param column 列坐标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for(int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if(row >= firstRow && row <= lastRow) {
				if(column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 写出excel文件到浏览器(下载文件)
	 * @param wb excel对象
	 * @param name 文件名，带后缀
	 */
	public static void export(Workbook wb, String name, HttpServletResponse res){

		BufferedInputStream bis = null;
		try (
				ServletOutputStream out = res.getOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(out);
		){
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] content = os.toByteArray();

			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			res.reset();
			res.setContentType("application/vnd.ms-excel;charset=utf-8");
			res.setHeader("Content-Disposition",
					"attachment;filename=" + new String(name.getBytes(), "iso-8859-1"));


			bis = new BufferedInputStream(is);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (bis != null)
					bis.close();

			}catch (IOException e){
				throw new RuntimeException(e);
			}
		}
	}
}
