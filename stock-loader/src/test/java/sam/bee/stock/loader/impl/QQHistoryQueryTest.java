package sam.bee.stock.loader.impl;

import org.junit.Test;
import sam.bee.stock.loader.BasicTest;
import sam.bee.stock.trade.Market;

import java.util.List;
import java.util.Map;

import static sam.bee.stock.Const.HISTORY;
import static sam.bee.stock.Const.STOCK_NAME;

/**
 * Created by Administrator on 2016/7/21.
 */
public class QQHistoryQueryTest extends BasicTest {

    @Test
    public void test() throws Exception {
        String code = "600103";
        Market m = new Market();
        Map data = m.getStockInfo(code);
        String name = (String) data.get(STOCK_NAME);
        List l = (List) new QQHistoryLoader(code).execute();


        getDataProvider().setList(l, HISTORY, code+"-"+name + ".csv");
        System.out.println(l);
    }
}
