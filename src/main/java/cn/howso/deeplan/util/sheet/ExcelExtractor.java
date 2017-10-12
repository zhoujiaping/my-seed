package cn.howso.deeplan.util.sheet;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExtractor extends SheetExtractor{
    private InputStream in;
    private String excelType;
    private static final String EXCEL2003SUFFIX = ".xls";
    private static final String EXCEL2007SUFFIX = ".xlsx";
    private static NumberFormat nf = NumberFormat.getInstance();
    static{
        nf.setGroupingUsed(false);
    }
    
    public ExcelExtractor(InputStream in,String suffix) {
        this.in = in;
        this.excelType = suffix;
    }
    public List<List<String>> extractFirstSheet() throws IOException{
        return extractAllSheet().get(0);
    }
    public List<List<List<String>>> extractAllSheet() throws IOException{
        Workbook wb = null;
        try {
            if (excelType.equals(EXCEL2003SUFFIX)) {
                wb = new HSSFWorkbook(in);
            } else if (excelType.equals(EXCEL2007SUFFIX)) {
                wb = new XSSFWorkbook(in);
            } else {
                throw new RuntimeException("读取的不是excel文件");
            }
            List<List<List<String>>> res = new ArrayList<>();
            int sheetSize = wb.getNumberOfSheets();
            for (int i = 0; i < sheetSize; i++) {//遍历sheet页
                Sheet sheet = wb.getSheetAt(i);
                int rowSize = sheet.getLastRowNum() + 1;
                List<List<String>> sheets = new ArrayList<>();
                for (int j = 0; j < rowSize; j++) {//遍历行
                    List<String> fields = new ArrayList<>();
                    Row row = sheet.getRow(j);
                    if (row == null) {//略过空行
                        continue;
                    }
                    int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
                    for(int k=0;k<cellSize;k++){
                        Cell cell = row.getCell(k);
                        if(cell==null){
                            fields.add("");
                            continue;
                        }
                        String value = null;
                        switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            value = cell.getRichStringCellValue().getString();
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                //value = cell.getDateCellValue().toString();
                                throw new RuntimeException("暂时不支持日期类型");
                            } else {
                                value = nf.format(cell.getNumericCellValue());
                            }
                            break;
                        case XSSFCell.CELL_TYPE_FORMULA:
                            throw new RuntimeException("暂时不支持公式");
                            //value = cell.getCellFormula();
                            //break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:
                            throw new RuntimeException("暂时不支持boolean类型");
                            //value = String.valueOf(cell.getBooleanCellValue());
                            //break;
                        case XSSFCell.CELL_TYPE_BLANK:
                            value = String.valueOf(XSSFCell.CELL_TYPE_BLANK);
                            break;
                        case XSSFCell.CELL_TYPE_ERROR:
                            break;
                        default:
                            break;
                        }
                        fields.add(value);
                    }
                    sheets.add(fields);
                }
                res.add(sheets);
            }
            return res;
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
    }
    @Override
    public List<List<String>> extract() throws IOException{
        return extractFirstSheet();
    }
}
