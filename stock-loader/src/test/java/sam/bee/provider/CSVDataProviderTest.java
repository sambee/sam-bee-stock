package sam.bee.provider;

import org.junit.Test;
import sam.bee.porvider.CSVDataProvider;
import sam.bee.stock.loader.BasicTest;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static sam.bee.stock.Const.HISTORY;

/**
 * Created by Administrator on 2016/7/31.
 */
public class CSVDataProviderTest extends BasicTest {

    @Test
    public void test() throws Exception {
        String code = "600000";
        String name = "浦发银行";
        CSVDataProvider  p = new CSVDataProvider();
        List<Map<String,String>> list = p.getList(320, HISTORY, code + "-"+ name);

        Collections.sort(list, new Comparator<Map<String, String>>() {

            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                String code1 = o1.get("DATE");
                String code2 = o2.get("DATE");
                return code2.compareTo(code1);
            }

        });
        System.out.println("count: " + list.size());
        System.out.println("data: " + list.get(0));
    }
}
