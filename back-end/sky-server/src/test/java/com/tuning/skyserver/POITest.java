package com.tuning.skyserver;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class POITest {

  @Test
  public void test() throws IOException {
    // 在内存中创建一个Excel文件对象
    XSSFWorkbook excel = new XSSFWorkbook();
    // 创建Sheet页
    XSSFSheet sheet = excel.createSheet("itcast");

    // 在Sheet页中创建行，0表示第1行
    XSSFRow row1 = sheet.createRow(0);
    // 创建单元格并在单元格中设置值，单元格编号也是从0开始，1表示第2个单元格
    row1.createCell(1).setCellValue("姓名");
    row1.createCell(2).setCellValue("城市");

    XSSFRow row2 = sheet.createRow(1);
    row2.createCell(1).setCellValue("张三");
    row2.createCell(2).setCellValue("北京");

    XSSFRow row3 = sheet.createRow(2);
    row3.createCell(1).setCellValue("李四");
    row3.createCell(2).setCellValue("上海");

    FileOutputStream out = new FileOutputStream(new File("D:\\itcast.xlsx"));
    // 通过输出流将内存中的Excel文件写入到磁盘上
    excel.write(out);

    // 关闭资源
    out.flush();
    out.close();
    excel.close();
  }
}
