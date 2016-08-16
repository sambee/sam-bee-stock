package sam.bee.stock.loader.impl;

import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/31.
 */
public class YahooRealTimeLoaderTest {

    @Test
    public void test() throws Exception {

        List<Map<String,String>> data = new YahooRealTimeLoader().execute("600397", "sh");
        System.out.println(data);

    }
}
