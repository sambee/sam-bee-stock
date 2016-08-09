package sam.bee.stock.indicator;

import org.junit.Test;
import sam.bee.porvider.CSVDataProvider;
import sam.bee.stock.loader.BasicTest;
import sam.bee.stock.loader.impl.QQHistoryLoader;
import sam.bee.stock.loader.impl.YahooHistoryLoader;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static sam.bee.stock.Const.DATE;
import static sam.bee.stock.Const.HISTORY;
import static sam.bee.stock.indicator.Math.getNumber2;

/**
 * Created by Administrator on 2016/8/7.
 */
public class KDJTest extends BasicTest {

    @Test
    public void test() throws Exception {
        String code = "601198";
        String name = "东兴证券";
        List<Map<String, String>>  mapList = new QQHistoryLoader().execute(code);

        Collections.sort(mapList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                String code1 = o1.get("DATE");
                String code2 = o2.get("DATE");
                return code1.compareTo(code2);
            }
        });
        System.out.println(code +"-" + name);
//        List<Map<String, String>>  mapList = new CSVDataProvider().getList( HISTORY, code+"-"+name);
        new CSVDataProvider().setList(mapList, HISTORY, code+"-"+name);
        KDJ  kdj = new KDJ(mapList);

        System.out.println("Now:" + mapList.get(mapList.size()-1).get(DATE));
        System.out.println("K:" + getNumber2(kdj.getK().get(kdj.getK().size()-1)));
        System.out.println("D:" + getNumber2(kdj.getD().get(kdj.getD().size()-1)));
        System.out.println("J:" + getNumber2(kdj.getJ().get(kdj.getJ().size()-1)));
    }

}
