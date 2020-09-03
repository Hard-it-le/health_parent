package com.yjl.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

/**
 * @author yjl
 * @create 2020-08-03-13:35
 **/
public class POITest {
    //使用poi读取excel文件数据

    /**
     * XSSFWorkbook：工作簿
     * XSSFSheet：工作表
     * Row：行
     * Cell：单元格
     * @throws IOException
     */
    /*@Test
    public void test01() throws IOException {
        //加载指定文件，创建excel工作对象（工作本）
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("e://poi.xlsx")));
        //读取excel文件中的第一个sheet标签页
        XSSFSheet sheet = excel.getSheetAt(0);
        //遍历sheet标签页，获得每一行的数据
        for (Row row : sheet) {
            //遍历行，获得每个单元格对象
            for (Cell cell : row){
                System.out.println(cell.getStringCellValue());
            }
        }
        //关闭资源
        excel.close();
    }*/


    /**
     * 另外一种读取excel文件的数据
     * @throws IOException
     */
    /*@Test
    public void test02() throws IOException {
        //加载指定文件，创建excel工作对象（工作本）
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("e://poi.xlsx")));
        //读取excel文件中的第一个sheet标签页
        XSSFSheet sheet = excel.getSheetAt(0);
        //获得当前工作表中最后一个行号，需要注意，行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            //根据行号获得每一行
            XSSFRow row = sheet.getRow(i);
            //获得当前行最后一个单元格索引
            short lastCellNum = row.getLastCellNum();
            for (int j =0;j<lastCellNum;j++){
                //根据单元格索引获得单元格对象
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }
    }*/

    /**
     * 使用poi向excel文件写入数据，并且通过输出流将创建的excel文件保存到本地磁盘
     */
    @Test
    public void test03() throws IOException {
        //在内存中创建一个excel文件
        XSSFWorkbook excel = new XSSFWorkbook();
        //创建工作biao，指定工作表名称
        XSSFSheet sheet = excel.createSheet("余嘉乐");
        //在工作表中创建对象
        XSSFRow row = sheet.createRow(0);
        //在行中创建单元格对象
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("地址");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("10");
        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("小王");
        row2.createCell(2).setCellValue("20");

        //创建一个输出流，通过输出流将内存中的excel保存到磁盘中
        FileOutputStream out = new FileOutputStream(new File("e:\\hello.xlsx"));

        excel.write(out);
        out.flush();
        excel.close();

    }
}
