package sam.bee.stock.loader.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sam.bee.stock.loader.BasicTest;
import sam.bee.stock.loader.ILoaderAPI;

public class GetRealTimeStockTest extends BasicTest {


	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		
		ILoaderAPI api = new LoaderApiImpl();
		String code = "";
		List<Map<String, String>> stockList = api.getRealTimeStockList(code);


	}

}
