package sam.bee.stock.loader.impl;

import org.junit.Test;
import sam.bee.porvider.CSVDataProvider;
import sam.bee.stock.loader.BasicTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static sam.bee.stock.Const.CODE;

public class GetShangHaiAndShenZhenStockListTest extends BasicTest {


	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		

		List<Map<String,String>> all = new ArrayList<Map<String,String>>();
		List<Map<String,String>> list =  new GetAllStockInfoList().load();

		for(Map<String,String> stock : list){
			stock.put("STOCK_TYPE", "ss");
			all.add(stock);
		}

		CSVDataProvider provider = new CSVDataProvider();

		provider.setList(all, CODE, "all.csv");

		List<Map<String,String>> lm   = provider.getList(CODE, "all.csv");
		System.out.println(lm);
	}
}
