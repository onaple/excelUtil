/**
 *
 */
package exce;


import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析excel
 * <p>
 * 将excel的数据读入内存。数据过多时会出现数据覆盖。数据都以字符串的形式保存。
 * @author reacherwa
 * @date 2016/12/5
 */
public class ExcelRead {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelRead.class);

    private static final int START = 1;//跳过excel的头

    private ExcelRead() {

    }

    private static class ExcelReadHolder {
        private static ExcelRead excelRead = new ExcelRead();
    }

    public static ExcelRead instance() {
        return ExcelReadHolder.excelRead;
    }

    public List<List<List<String>>> read(File file) {
        if (null == file) {
            return null;
        }
        if (!Utils.ExcelFileUtil.checkExcelFileType(file.getName())) {
            return null;
        }
        Workbook workBook = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            workBook = Workbook.getWorkbook(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("excel error:" + e);
            return new ArrayList<List<List<String>>>();
        } finally {
            Utils.StreamUtil.close(inputStream);
        }
        return read(workBook);
    }

    private List<List<List<String>>> read(Workbook workBook) {
        if (null == workBook) {
            return null;
        }
        List<List<List<String>>> results = new ArrayList<List<List<String>>>();
        for (Sheet sheet : workBook.getSheets()) {
            if (null == sheet) {
                continue;
            }
            List<List<String>> objects = this.read(sheet);
            if (null == objects || 0 == objects.size()) {
                continue;
            }
            results.add(objects);
        }
        return results;
    }

    private List<List<String>> read(Sheet sheet) {

        if (null == sheet) {
            return null;
        }
        List<List<String>> results = new ArrayList<List<String>>();
        for (int i = START; i < sheet.getRows(); ++i) {
            List<String> objects = this.read(sheet, i);
            if (null == objects || 0 == objects.size()) {
                continue;
            }
            results.add(objects);
        }
        return results;
    }

    private static int n = 0;

    private List<String> read(Sheet sheet, int row) {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < sheet.getColumns(); ++i) {
            Cell cell = sheet.getCell(i, row);
            String value = cell.getContents();
            results.add(value);
        }
        return results;
    }
}
