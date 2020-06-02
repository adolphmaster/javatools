package com.adolph.javatools.common.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author adolph
 * @Date 2020/5/26 11:25
 * @Version 1.0
 * @Description POI-OOXML方式处理excel 2007
 **/
public class ExcelPoiUtils {

    private static final int XLSX_MAX_ROWS = 1048575;
    private static final String[] ABTEST_CSV_HEAD = {"",""};


    /**
     * 导出excel
     *
     * @param excelPath 导出的excel路径（需要带.xlsx)
     * @param dataList   excel数据
     * @throws Exception
     */
    public static void createExcel(String excelPath, List<List<String>> dataList) throws Exception {
        // 创建新的Excel 工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 在Excel工作簿中建一工作表，其名为缺省值
        XSSFSheet sheet = workbook.createSheet();
        // 在索引0的位置创建行（最顶端的行）
        XSSFRow row = sheet.createRow(0);
        // 设置excel头（第一行）的头名称
        for (int i = 0; i < ABTEST_CSV_HEAD.length; i++) {
            // 在索引0的位置创建单元格（左上端）
            row.createCell(i).setCellValue(ABTEST_CSV_HEAD[i]);
        }
        //添加数据
        int sheetNum=0;
        for (int n = 0; n < dataList.size(); n++) {
            if(n/200000>sheetNum){
                sheetNum++;
                sheet = workbook.createSheet();
                System.out.println("sheet【"+sheetNum+"】 add");
            }
            if((n-20000*sheetNum)/20000>(sheetNum+1)){
                System.out.println((n-20000*sheetNum)/20000+"【"+sheetNum+"】 add");
            }
            // 在索引1的位置创建行（最顶端的行）
            XSSFRow row_value = sheet.createRow(n + 1);
            List<String> recordData = dataList.get(n);
            row_value.createCell(0).setCellValue(recordData.get(0));
            row_value.createCell(1).setCellValue(recordData.get(1));
            row_value.createCell(2).setCellValue(recordData.get(2));
            row_value.createCell(3).setCellValue(recordData.get(3));
            row_value.createCell(4).setCellValue(recordData.get(4));
            row_value.createCell(5).setCellValue(recordData.get(5));
        }
        // 新建一输出文件流
        FileOutputStream fos = new FileOutputStream(excelPath);
        // 把相应的Excel 工作簿存盘
        workbook.write(fos);
        fos.flush();
        // 操作结束，关闭文件
        fos.close();
    }


    /**
     * 合并多个 Excel 文件
     *
     * @param mergedFile 合并后的文件
     * @param files      待合并的文件
     * @throws IOException       合并异常
     */
    public static void mergeExcel(File mergedFile, List<File> files) throws IOException {
        if (mergedFile == null || files == null) {
            return;
        }
        try (Workbook mergedWorkbook = new SXSSFWorkbook();
             FileOutputStream out = new FileOutputStream(mergedFile)) {
            Sheet newSheet = mergedWorkbook.createSheet();
            int start = 0;
            for (File file : files) {
                if (file == null) {
                    continue;
                }
                try (Workbook oldWorkbook = new XSSFWorkbook(new FileInputStream(file))) {
                    int oldSheetSize = oldWorkbook.getNumberOfSheets();
                    for (int i = 0; i < oldSheetSize; i++) {
                        Sheet oldSheet = oldWorkbook.getSheetAt(i);
                        int oldRowSize = oldSheet.getLastRowNum();
                        for (int j = 0; j <= oldRowSize; j++) {
                            if (start == XLSX_MAX_ROWS) {
                                newSheet = mergedWorkbook.createSheet();
                                start = newSheet.getLastRowNum();
                            }
                            Row oldRow = oldSheet.getRow(j);
                            Row newRow = newSheet.createRow(start);
                            copyRow(oldRow, newRow);
                            start++;
                        }
                    }
                }
            }
            mergedWorkbook.write(out);
        }
    }

    private static void copyRow(Row oldRow, Row newRow) {
        newRow.setHeight(oldRow.getHeight());
        for (int i = oldRow.getFirstCellNum(); i <= oldRow.getLastCellNum(); i++) {
            Cell oldCell = oldRow.getCell(i);
            if (null != oldCell) {
                copyCell(oldCell, newRow.createCell(i));
            }
        }
    }

    private static void copyCell(Cell oldCell, Cell newCell) {
        switch (oldCell.getCellTypeEnum()) {
            case FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(oldCell)) {
                    newCell.setCellValue(DateUtil.dayStr(oldCell.getDateCellValue(),DateUtil.TIME_PATTERN));
                } else {
                    newCell.setCellValue(oldCell.getNumericCellValue());
                }
                break;
            case BLANK:
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            case BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case STRING:
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            default:
                break;
        }
    }
}

