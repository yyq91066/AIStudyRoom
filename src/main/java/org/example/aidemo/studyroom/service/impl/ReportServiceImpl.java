package org.example.aidemo.studyroom.service.impl;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.aidemo.studyroom.entity.ReportCountVO;
import org.example.aidemo.studyroom.mapper.ReportMapper;
import org.example.aidemo.studyroom.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Override
    public List<ReportCountVO> getCount(LocalDateTime startDate, LocalDateTime endDate) {
        return reportMapper.getCount(startDate, endDate);
    }

    @Override
    public String exportReport(LocalDateTime startDate, LocalDateTime endDate) {
        // 创建一个Excel文件,在内存中创建
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表
        XSSFSheet sheet = workbook.createSheet("Count");
        //设置表头
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("日期");
        row.createCell(1).setCellValue("订单量");
        //自适应列宽度
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        //获取数据 填充数据
        List<ReportCountVO> count = reportMapper.getCount(startDate, endDate);
        for(int i=0;i<count.size();i++){
            XSSFRow row1 = sheet.createRow(i+1);
            row1.createCell(0).setCellValue(count.get(i).getOrderDate().toString());
            row1.createCell(1).setCellValue(count.get(i).getOrderCount());
           //自适应列宽度
            sheet.autoSizeColumn(i);
        }
        try {
            //写到磁盘文件
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\"+startDate+"至"+endDate+"count.xlsx"));
            workbook.write(fileOutputStream);
            System.out.println("创建成功");
            //关闭资源
            fileOutputStream.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "D:\\"+startDate+"至"+endDate+"count.xlsx";
    }

    @Override
    public void exportDownload(LocalDateTime startDate, LocalDateTime endDate, HttpServletResponse  response)  {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("Templates/temp.xlsx");
        try {
            XSSFWorkbook excel = new XSSFWorkbook(in);
            XSSFSheet sheet1 = excel.getSheet("Sheet1");
            XSSFRow row = sheet1.createRow(1);
            row.createCell(0).setCellValue(startDate.toString()+"至"+endDate.toString());
            List<ReportCountVO> count = reportMapper.getCount(startDate, endDate);
            for(int i=0;i<count.size();i++){
                XSSFRow row1 = sheet1.createRow(i+3);
                row1.createCell(0).setCellValue(count.get(i).getOrderDate().toString());
                row1.createCell(1).setCellValue(count.get(i).getOrderCount());
                //自适应列宽度
                sheet1.autoSizeColumn(i+3);
            }


            //输出
            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);

            //关闭资源
            outputStream.close();
            excel.close();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
