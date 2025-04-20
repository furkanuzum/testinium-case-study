package com.testinium.selenium.util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Util {
    private static NumberFormat nf = NumberFormat.getInstance(new Locale("tr-TR"));

    public static Number convertFromTextToDouble(String s) {
        try {
            return nf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("can not convert string" + s + "to number");
        }
    }

    public static String[] readInputsFromExcel() throws IOException {
        FileInputStream file = new FileInputStream("src/test/resources/input.xlsx");
        try (Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            return new String[] { row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue() };
        }
    }

    public static void writeToOutputFile(String s) throws IOException{
        try (FileWriter myWriter = new FileWriter("src/test/resources/output.txt")) {
            myWriter.write(s);
        }
    }

}
