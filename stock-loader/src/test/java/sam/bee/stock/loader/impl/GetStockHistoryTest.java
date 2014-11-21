package sam.bee.stock.loader.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import sam.bee.stock.loader.BaseTest;
import sam.bee.stock.loader.ILoaderAPI;
import sam.bee.cache.FileCache;
import sam.bee.cache.ICache;
import java.util.concurrent.CountDownLatch;
public class GetStockHistoryTest extends BaseTest{

	
	final ICache cache = new FileCache("../stocks",1800000);
	final String HISTORY = "history";
	final String EXCLUDE = "exclude";
	final String CODE = "code";
	@Test
	public void test() throws Exception {
		//CountDownLatch doneSignal = new CountDownLatch(100);
		

		int POOL_SIZE = 1;
		int cpuNums = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService =Executors.newFixedThreadPool(cpuNums * POOL_SIZE);
		
		
		ILoaderAPI api = new LoaderApiImpl();
		List<Map<String,String>> stockList = cache.getList(CODE, "shanghai");
		if(stockList==null){
			stockList =  api.getShangHaiStockList();
			cache.set(stockList, CODE,"shanghai");
		}
		
		//test shuanghai
		for(Map<String,String> m : stockList){
			String stockCode = m.get("STOCK_CODE");
			String stockName = m.get("STOCK_NAME");
			if(!cache.exist(HISTORY, stockCode) && !cache.existIgnoreTime(EXCLUDE, stockCode)){				
				executorService.execute(new Task(stockCode,stockName,  cache,api));
				
			}
			else{
				log.info("Shang Hai Ignore stock:" +  stockCode + " "+  stockName);
			}
			
		}
		
		//test shengzhen
		stockList = cache.getList(CODE, "shengzhen");
		if(stockList==null){
			stockList =  api.getShenZhenStockList();
			cache.set(stockList, CODE,"shengzhen");
		}
		
		for(Map<String,String> m : stockList){
			String stockCode = m.get("STOCK_CODE");
			String stockName = m.get("STOCK_NAME");
			if(!cache.exist(HISTORY, stockCode) && !cache.existIgnoreTime(EXCLUDE, stockCode)){				
				executorService.execute(new Task(stockCode,stockName, cache,api));
				
			}
			else{
				log.info("Ignore stock:" +  stockCode + " "+  stockName);
			}
		}
		
		executorService.shutdown();
		while(!executorService.awaitTermination(1, TimeUnit.SECONDS)){
			System.out.println("----------- Wait ---------" +  ((ThreadPoolExecutor)executorService).getCompletedTaskCount());
		}

		
		System.out.println("----------- Done ---------2");
		
	}
	
	class Task implements Runnable{
		
		String stockCode;
		ICache cache;
		ILoaderAPI api;
		String name;
		
		public Task(String stockCode, String name, ICache cache, ILoaderAPI api){
			this.stockCode = stockCode;
			this.cache = cache;
			this.api = api;	
			this.name = name;
			
		}
		
		@Override
		public void run() {
			List<Map<String, String>> list;
			try {
				//log.info("Load stock:" +  stockCode);
				list = (List<Map<String,String>>)api.getStockHistory(stockCode);
				Collections.sort(list, new  Comparator<Map<String,String>>(){

					@Override
					public int compare(Map<String, String> o1, Map<String, String> o2) {
						String code1 = o1.get("DATE");
						String code2 = o2.get("DATE");
						return code1.compareTo(code2);
					}
					
				});
				log.info("Loaded:" + stockCode + " " +name);
				cache.set(list, HISTORY, stockCode);

				
				
			} catch (Exception e) {	
				try {
					cache.set(e.getMessage(), EXCLUDE, stockCode);
				} catch (Exception e1) {
					log.error("",e);
				}
				log.error("",e);
			}
			
			
		}
		
	}

}
