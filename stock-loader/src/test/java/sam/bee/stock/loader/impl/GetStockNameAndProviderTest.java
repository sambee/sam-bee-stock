package sam.bee.stock.loader.impl;

import org.junit.Test;
import sam.bee.porvider.CSVDataProvider;
import sam.bee.stock.loader.BasicTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GetStockNameAndProviderTest extends BasicTest {

	@Test
	public void test() throws Exception {
		logger.info(":::::::::: GetStockNameAndFileCacheTest");



		CSVDataProvider cache = new CSVDataProvider("target");

		logger.info("Get shang hai stock list.");
		List<Map<String,String>> list =  new GetAllStockInfoList().load();



		logger.info("------------- DONE ---------");
	}
	
	void validate(List<Map<String,String>> expected, List<Map<String,String>> actual){
		assertEquals(expected.size(), actual.size());		
		assertEquals(expected.hashCode(), actual.hashCode());
		
		for(int i=0;i<actual.size(); i++){
			assertEquals(actual.get(i), expected.get(i));
		}
	}
	
	
	private List<String> getStockCode(List<Map<String, Object>> stocks, int start, int limit){
		List<String> list = new ArrayList<String>(stocks.size());
		int end = start+limit;
		for(int i=start; i<end && (i<stocks.size()); i++){
			Map<String, Object> obj = stocks.get(i);			
			list.add((String)obj.get("STOCK_CODE"));
			//log.info("INDEX:" + i);
		}
		return list;
	}
	
}
