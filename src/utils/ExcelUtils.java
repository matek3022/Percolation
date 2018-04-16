package utils;

import model.Table;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class ExcelUtils {

    public static synchronized void writeTableToFile(String fileName, Table table, int currIter, long time) {
        HSSFWorkbook book;
        try {
            book = new HSSFWorkbook(new FileInputStream(fileName));
        } catch (IOException e) {
            book = new HSSFWorkbook();
        }
        String sheetName = "Table info";
        Sheet sheet = book.getSheet(sheetName);
        if (sheet == null) sheet = book.createSheet(sheetName);

        // Нумерация начинается с нуля
        Row row = sheet.getRow(0);
        if (row == null) row = sheet.createRow(0);

        row.createCell(0).setCellValue("Количество красных");
        row.createCell(1).setCellValue("Ширина пути");
        row.createCell(2).setCellValue("Количество кластеров всего");
        row.createCell(3).setCellValue("Время");

        row.createCell(5).setCellValue("Ширина = " + table.getN());
        row.createCell(6).setCellValue("Высота = " + table.getM());
        row.createCell(7).setCellValue("Вероятность = " + table.getP());

        // Меняем размер столбца
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);

        Row tableRow = sheet.getRow(currIter);
        if (tableRow == null) tableRow = sheet.createRow(currIter);
        tableRow.createCell(0).setCellValue(table.getMinLength());
        tableRow.createCell(1).setCellValue(table.getRoadWidth());
        tableRow.createCell(2).setCellValue(table.getClusterCount());
        tableRow.createCell(3).setCellValue(time);

        // Записываем всё в файл
        try {
            book.write(new FileOutputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static synchronized Table readFromFile(String fileName) {
//
//    }
}
