package com.itheima.bos.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class PoITest {
	//使用POI解析指定的一个Excel文件
	@Test
	public void test1() throws Exception{
		InputStream in = new FileInputStream(new File("E:\\黑马39期\\BOS项目\\BOS_day05\\资料\\区域导入测试数据.xls"));
		//HSSFWorkbook代表一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		//读取第一个sheet页
		HSSFSheet sheet = workbook.getSheetAt(0);
		//遍历标签页，获得每一行
		for (Row row : sheet) {
			int rowNum = row.getRowNum();//行号,从0开始
			if(rowNum == 0){
				//读取到的是第一行，标题行，跳过
				continue;
			}
			//遍历行，获得每个单元格对象
			for (Cell cell : row) {
				//读取单元格中的文字
				String value = cell.getStringCellValue();
				System.out.print(value + " ");
			}
			System.out.println();
		}
	}
}
