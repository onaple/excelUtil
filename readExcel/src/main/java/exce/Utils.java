package exce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 *
 * @author reacherwang
 * @date 2016/12/5
 */
public final class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static final class StreamUtil {

        public static void close(Closeable closeable) {
            if (null == closeable) {
                return;
            }
            try {
                closeable.close();
            } catch (Exception e) {
                LOGGER.error("文件流关闭失败.");
            }
        }
    }

    public static final class ExcelFileUtil {

        private static final String XLS = ".xls";
        private static final String XLSX = ".xlsx";

        public static boolean checkExcelFileType(String fileName) {
            if (null == fileName) {
                return false;
            }
            if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
                return false;
            }
            return true;
        }
    }

    // 利用类的反射机制完成数据转换工作，将数据转换为对象。

    public static final class ReflectUtil {

        private static final String SET = "set";
        private static final String GET = "get";

        //调用set方法
        public static boolean invokeSetMethod(Object object, Field field, String value) {
            Class<? extends Object> clazz = object.getClass();
            Method method = invokeMethod(clazz, field.getName(), SET, field.getType());
            try {
                method.invoke(object, transfer(field.getType(), value));
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        //调用get方法。
        public static Object invokeGetMethod(Object object, Field field) {
            Class<? extends Object> clazz = object.getClass();
            Method method = invokeMethod(clazz, field.getName(), GET);
            try {
                return method.invoke(object);
            } catch (Exception e) {
                return null;
            }
        }

        public static Method invokeMethod(Class<?> clazz, String fieldName, String type, Class<?>... parameterTypes) {
            if (null == clazz) {
                return null;
            }
            if (null == fieldName) {
                return null;
            }
            if (!type.equals(SET) && !type.equals(GET)) {
                return null;
            }

            //构造方法名
            String methodName = type + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            return invokeMethod(clazz, methodName, parameterTypes);
        }

        //通过反射得到类的方法。
        public static Method invokeMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
            if (null == clazz) {
                return null;
            }
            if (null == methodName) {
                return null;
            }
            Method method = null;
            try {
                method = clazz.getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            return method;
        }

        //通过反射得到类的属性。
        public static List<Field> fieldNames(Class<?> clazz) {
            if (null == clazz) {
                return null;
            }
            Field fields[] = clazz.getDeclaredFields();
            if (0 >= fields.length) {
                return null;
            }
            List<Field> fieldsTemp = new ArrayList<Field>();
            for (int i = 0; i < fields.length; ++i) {
                fieldsTemp.add(fields[i]);
            }
            return fieldsTemp;
        }

        //将excel中读出的字符串装换为相应的java类。
        private static Object transfer(Class<?> type, String value) {
            if (type.equals(int.class) || type.equals(Integer.class)) {
                return Integer.parseInt(value);
            }
            if (type.equals(float.class) || type.equals(Float.class)) {
                return Float.parseFloat(value);
            }
            if (type.equals(double.class) || type.equals(Double.class)) {
                return Double.parseDouble(value);
            }

            return value;
        }

    }

}
