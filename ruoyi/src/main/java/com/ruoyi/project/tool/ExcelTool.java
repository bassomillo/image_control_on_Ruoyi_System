package com.ruoyi.project.tool;


import com.ruoyi.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Date;
import java.util.List;

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

	public static void excelCheck(Sheet sheet, List<String> errorMsg, int rowStart, int rowEnd) {
		int firstRowNum = sheet.getFirstRowNum();
		Row firstRow = sheet.getRow(firstRowNum);
		if(null == firstRow) {
			errorMsg.add("第一行未读取到数据");

			return ;
		}

		if(rowStart == rowEnd)
			errorMsg.add("Excel文件未读取到数据");
	}

	/**
	 *
	 * @param req
	 * @param filePath:webapp文件夹下的文件路径，如：指标模版.xlsx  ； 文件夹名/xxx.xx; static/xx/xx.xxx
	 * @return
	 * @throws Exception
	 * @throws InvalidFormatException
	 */
	public static Workbook read(HttpServletRequest req, String filePath) throws Exception{

		InputStream inputStream = null;
		Workbook workbook = null;

		try {
			//读入文件到流
			ClassPathResource classPathResource = new ClassPathResource(filePath);
			inputStream = classPathResource.getInputStream();
			//用流创建文件
			workbook = WorkbookFactory.create(inputStream);
			inputStream.close();
		} catch (Exception e) {
			if(inputStream != null) {
				inputStream.close();
			}
			throw e;
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
		}
		return workbook;
	}

	/**
	 * zxy
	 * 文字居中带边框的cellStyle
	 * @param wb
	 * @return
	 */
	public static CellStyle getStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		//水平居中
		style.setAlignment(HorizontalAlignment.CENTER);
		//垂直居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		//自动换行
        style.setWrapText(true);
		//下边框
//		style.setBorderBottom(BorderStyle.THIN);
//		//左边框
//		style.setBorderLeft(BorderStyle.THIN);
//		//上边框
//		style.setBorderTop(BorderStyle.THIN);
//		//右边框
//		style.setBorderRight(BorderStyle.THIN);
		return style;
	}

	/**
	 * fjx
	 * //创建一个cell并为其设置style和value
	 * @param row
	 * @param col
	 * @param style
	 * @param value:如果需要格式化请传入格式化好的String
	 * @return  XSSFCellStyle
	 */
	public static Cell createCell(Row row,int col,CellStyle style,Object value){
		Cell cell = row.createCell(col);
		//style为空应该设置一个默认值以避免
		cell.setCellStyle(style);
		if (value instanceof String) {
			cell.setCellValue( (String)value );
		} else if  (value instanceof Integer) {
			cell.setCellValue( (Integer)value );
		} else if (value instanceof Double) {
			cell.setCellValue( (Double)value );
		} else if (value instanceof Long) {
			cell.setCellValue( (Long)value );
		} else if (value instanceof Date) {
			cell.setCellValue( (Date)value );
		} else if (value instanceof Float) {
			cell.setCellValue( (Float)value );
		} else if (value instanceof Boolean) {
			cell.setCellValue( (Boolean)value );
		}

		return cell;
	}
}
