package sam.bee.stock.loader.impl;

import org.junit.Test;
import sam.bee.cache.FileCache;
import sam.bee.cache.ICache;
import sam.bee.stock.loader.BaseTest;
import sam.bee.stock.loader.ILoaderAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GetStockNameAndFileCacheTest extends BaseTest{

	@Test
	public void test() throws Exception {
		log.info(":::::::::: GetStockNameAndFileCacheTest");
		ILoaderAPI api = new LoaderApiImpl();
		

		ICache cache = new FileCache("build/stocks/realtime",1800000);
		
		log.info("Get shang hai stock list.");
		List<Map<String,String>> list =  api.getShangHaiStockList();
		cache.set(list, "shuanghai");

		List<Map<String,String>> actual = cache.getList("shuanghai");
		validate(list, actual);
		
		log.info("Get sheng zhen stock list.");
		list =  api.getShenZhenStockList();
		cache.set(list, "shengzhen");
		
		actual = cache.getList("shengzhen");
		validate(list, actual);
		
		log.info("------------- DONE ---------");
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
	
//	private AccessDatabaseCache getDatabase(File dbFile) throws Exception{
////		if(dbFile.exists()){
////			dbFile.delete();
////		}
//
//		//create access file.
//		return new AccessDatabaseCache(dbFile);
//	}
	

}
