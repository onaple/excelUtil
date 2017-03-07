import com.sun.org.apache.regexp.internal.RE;
import exce.ExcelReadHelper;
import javafx.scene.effect.Light;
import model.Restaurant;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.List;

/**
 * Created by onaple on 2017/3/6.
 */
public class MainAPP {

    private final static int PRINT_NUM = 10;
    public static void main(String[] args) throws Exception {


        String fileName = "/Users/onaple/git/excelUtil/readExcel/src/main/java/file/123.xls";
        File file = new File(fileName);
        List<Restaurant> lists = ExcelReadHelper.readExcel(file, Restaurant.class);

        int i = 0;

        for(Restaurant restaurant : lists) {

            ++i;
            if (i > PRINT_NUM){
                break;
            }
            System.out.println(restaurant);
        }
    }
}
