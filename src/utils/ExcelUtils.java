package utils;

import model.Table;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * вспомогательный класс для синхронной записи в файл excel файл из разных потоков
 * создал {@link utils.Person#SEMENOV}
 */
public abstract class ExcelUtils {

    private static HSSFWorkbook book;
    private static Sheet sheet;
    private static int tableCount = 0;

    private static synchronized void startInit(String fileName, Table table) {
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
        row.createCell(2).setCellValue("Длина пути");
        row.createCell(3).setCellValue("Количество кластеров всего");
        row.createCell(4).setCellValue("Средний размер кластера");
        row.createCell(5).setCellValue("Средния длина пути между кластерами");
        row.createCell(6).setCellValue("Количество путей между кластерами");
        row.createCell(7).setCellValue("Время");

        row.createCell(9).setCellValue("Ширина = " + table.getN());
        row.createCell(10).setCellValue("Высота = " + table.getM());
        row.createCell(11).setCellValue("Вероятность = " + table.getP());

        // Меняем размер столбца
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);

        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(10);
        sheet.autoSizeColumn(11);
        ExcelUtils.book = book;
        ExcelUtils.sheet = sheet;
    }

    /**
     * @param fileName имя выходного файла
     * @param table записываемая таблица
     * @param currIter итерация для таблицы
     * @param time время просчета таблицы
     * создал {@link utils.Person#SEMENOV}
     */
    public static synchronized void writeTableToFile(String fileName, Table table, int currIter, long time) {
        if (book == null) startInit(fileName, table);
        tableCount++;
        Row tableRow = sheet.getRow(currIter + 1);
        if (tableRow == null) tableRow = sheet.createRow(currIter + 1);
        tableRow.createCell(0).setCellValue(table.getRedCount());
        tableRow.createCell(1).setCellValue(table.getRoadWidth());
        tableRow.createCell(2).setCellValue(table.getRoadLength());
        tableRow.createCell(3).setCellValue(table.getClusterCount());
        tableRow.createCell(4).setCellValue(table.getClusterMiddleSize());
        tableRow.createCell(5).setCellValue(table.getMiddleRoadLenght());
        tableRow.createCell(6).setCellValue(table.getRoadCount());
        tableRow.createCell(7).setCellValue(time);

        if (tableCount == (Setup.MAX_ITERATION / Setup.MAX_THREADS) * Setup.MAX_THREADS) {
            // Записываем всё в файл
            try {
                book.write(new FileOutputStream(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
