/**
 *
 */
package exce;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析excel
 * 使用反射将excel的数据转化为对应的java类型
 * @author reacherwang
 * @date 2016/12/5
 *  */
public final class ExcelReadHelper {

    public static <T> List<T> readExcel(File file, Class<T> clazz) throws Exception {
        List<List<List<String>>> results = ExcelRead.instance().read(file);
        if (null == results || 0 == results.size()) {
            return new ArrayList<T>();
        }

        //将sheet转化为对象列表
        List<T> objects = new ArrayList<T>();
        for (List<List<String>> sheet : results) {
            List<T> values = readExcelSheet(sheet, clazz);
            if (null == values) {
                return null;
            }
            if (0 == values.size()) {
                continue;
            }
            objects.addAll(values);
        }

        return objects;
    }

    public static <T> List<T> readExcelSheet(List<List<String>> sheet, Class<T> clazz) throws Exception {
        List<Field> fields = Utils.ReflectUtil.fieldNames(clazz);
        if (null == fields) {
            return null;
        }
        List<T> objects = new ArrayList<T>();
        for (List<String> row : sheet) {
            if (null == row || 0 == row.size()) {
                continue;
            }
            T value = readExcelRow(row, fields, clazz);
           
            if (null == value) {
                return null;
            }
            objects.add(value);
        }

        return objects;
    }

    public static <T> T readExcelRow(List<String> row, List<Field> fields, Class<T> clazz) throws Exception {
        if (null == row || 0 == row.size()) {
            return null;
        }
        if (null == clazz) {
            return null;
        }
        if (row.size() != fields.size()) {
            return null;
        }
        T object = clazz.newInstance();
        for (int i = 0; i < fields.size(); ++i) {
            Utils.ReflectUtil.invokeSetMethod(object, fields.get(i), row.get(i));

        }
        return object;
    }

}
