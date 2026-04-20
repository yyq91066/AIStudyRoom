package org.example.aidemo;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@SpringBootTest
public class ApachePoiTest {
    @Test
    public void testApachePoiCreate() throws  Exception{
        // 创建一个Excel文件,在内存中创建
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表
        XSSFSheet sheet = workbook.createSheet("SheetYQ");
        // 创建行对象，下标从0开始
        XSSFRow row = sheet.createRow(0);
        // 创建一个单元格，下标从0开始  相当于第一列，仅是第一行的
        XSSFCell cell = row.createCell(0);
        // 设置单元格的值
        cell.setCellValue("name!");
        row.createCell(1).setCellValue("age");
        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("张三");
        row1.createCell(1).setCellValue("18");
        //写到磁盘文件
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\poi.xlsx"));
        workbook.write(fileOutputStream);
        System.out.println("创建成功");
        //关闭资源
        fileOutputStream.close();
        workbook.close();
    }
    @Test
    public void testApachePoiRead() throws Exception{
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\2026-03-11至2026-04-25count.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i < lastRowNum; i++) {

            XSSFRow row = sheet.getRow(i);
            //获得单元格
            String stringCellValue = row.getCell(0).getStringCellValue();
            System.out.println(stringCellValue);
            String stringCellValue1 = row.getCell(1).getStringCellValue();
            System.out.println(stringCellValue1);


            fileInputStream.close();
            workbook.close();

        }
    }
}
