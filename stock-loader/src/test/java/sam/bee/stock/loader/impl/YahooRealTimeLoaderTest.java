package sam.bee.stock.loader.impl;

import org.junit.Test;
import sam.bee.stock.loader.StockLoaderFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/31.
 */
public class YahooRealTimeLoaderTest {

    @Test
    public void test() throws Exception {

        List<Map<String,String>> data = StockLoaderFactory.getInst().getHistory("600397");
        System.out.println(data);

    }
}
