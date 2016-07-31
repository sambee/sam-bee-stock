package sam.bee.stock.loader.impl;

import org.junit.Test;
import sam.bee.porvider.CSVDataProvider;
import sam.bee.stock.loader.BasicTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static sam.bee.stock.Const.ALL_STOCK_INFO;
import static sam.bee.stock.Const.CODE;

public class GetAllStockInfoListTest extends BasicTest {


	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		List<Map<String,String>> list =  new GetAllStockInfoList().load();

		CSVDataProvider provider = new CSVDataProvider();

		provider.setList(list, CODE, ALL_STOCK_INFO);

		List<Map<String,String>> lm   = provider.getList(CODE, ALL_STOCK_INFO);
		System.out.println(lm);
	}
}
