package com.atguigu.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/3
 */
public class TestPoi {

    /*
      测试读取Excel文件中的内容
     */
    @Test
    public void testReal() throws IOException {
        //1.创建工作蒲对象 - 代表一个Excel文件
        Workbook workbook = new XSSFWorkbook("E:\\尚硅谷\\数据.xlsx");

        //2.获取第一个工作表
        Sheet sheet = workbook.getSheetAt(0);

        //3.遍历工作表获得行对象
        for (Row row : sheet) {
            //4.遍历行对象获取单元格对象
            for (Cell cell : row) {
                //获得单元格中的值
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
        //4.关闭工作蒲对象
        workbook.close();
    }

    // 导出excel，获取最后一行
    @Test
    public void testReal02() throws IOException {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("E:\\尚硅谷\\数据.xlsx");
        //获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取当前工作表最后一行的行号，行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<=lastRowNum;i++){
            //根据行号获取行对象
            XSSFRow row = sheet.getRow(i);
            // 再获取单元格对象
            short lastCellNum = row.getLastCellNum();
            for(short j=0;j<lastCellNum;j++){
                // 获取单元格对象的值
                String value = row.getCell(j).getStringCellValue();
                System.out.println(value);
            }
        }
        workbook.close();
    }

}
