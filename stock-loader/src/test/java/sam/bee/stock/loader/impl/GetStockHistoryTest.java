package sam.bee.stock.loader.impl;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import sam.bee.stock.loader.BaseTest;
import sam.bee.stock.loader.ILoaderAPI;
import sam.bee.porvider.FileDataProvider;
import sam.bee.porvider.IDataProvider;

import static sam.bee.stock.Const.*;

public class GetStockHistoryTest extends BaseTest{


	private  final static  IDataProvider provider = new FileDataProvider(System.getProperty("user.home") + "/.stocks");


	@Test
	public void test() throws Exception {
		logger.info("==== test get story history ====");
		//CountDownLatch doneSignal = new CountDownLatch(100);
		

		int POOL_SIZE = 1;
		int cpuNums = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService =Executors.newFixedThreadPool(cpuNums * POOL_SIZE);
		
		
		ILoaderAPI loader = new LoaderApiImpl();
		List<Map<String,String>> stockList = provider.getList(CODE, SHUANG_HAI);
		if(stockList==null){
			stockList =  loader.getShangHaiStockList();
			provider.set(stockList, CODE, SHUANG_HAI);
		}

		List<Task> tasks = new ArrayList<Task>();
		//load shuanghai stock history data.
		for(Map<String,String> m : stockList){
			String stockCode = m.get("STOCK_CODE");
			String stockName = m.get("STOCK_NAME");
			tasks.add(new Task(stockCode,stockName, provider,loader));
		}
		

		stockList = provider.getList(CODE, SHENG_ZHEN);
		if(stockList==null){
			stockList =  loader.getShenZhenStockList();
			provider.set(stockList, CODE,SHENG_ZHEN);
		}

		//load shengzhen  stock history data.
		for(Map<String,String> m : stockList){
			String stockCode = m.get("STOCK_CODE");
			String stockName = m.get("STOCK_NAME");
			tasks.add(new Task(stockCode,stockName, provider,loader));
		}


		for(Task t : tasks){

			String code = t.code;
			String name = t.name;
			if(!provider.exist(HISTORY,  getName(code, name) ) &&  !provider.exist(EXCLUDE, getName(code,name))){
				logger.info("load task:" + getName(code, name));
				executorService.execute(t);

			}
			else{
				logger.info("Ignore stock:" +  getName(code, name));
			}
		}
		
		executorService.shutdown();

		while(!executorService.awaitTermination(1, TimeUnit.SECONDS)){
			logger.info(  "Load stock history done:" +  ((ThreadPoolExecutor)executorService).getCompletedTaskCount() + "/"  + ((ThreadPoolExecutor)executorService).getTaskCount());
		}

		
		logger.info("----------- Done ---------2");
		
	}
	
	class Task implements Runnable{

		public String code;
		IDataProvider cache;
		ILoaderAPI loader;
		public String name;



		public Task(String code, String name, IDataProvider cache, ILoaderAPI loader){
			this.code = code;
			this.cache = cache;
			this.loader = loader;
			this.name = name;
			
		}
		
		@Override
		public void run() {
			List<Map<String, String>> list;
			try {
				//log.info("Load stock:" +  stockCode);
				list = (List<Map<String,String>>)loader.getStockHistory(code);
				Collections.sort(list, new  Comparator<Map<String,String>>(){

					@Override
					public int compare(Map<String, String> o1, Map<String, String> o2) {
						String code1 = o1.get("DATE");
						String code2 = o2.get("DATE");
						return code1.compareTo(code2);
					}
					
				});
				logger.info("Loaded:" + getName(code, name));
				cache.set(list, HISTORY,  getName(code, name));

				
			} catch (Exception e) {	
				try {
					cache.set(e.getMessage(), EXCLUDE, getName(code,name));
				} catch (Exception e1) {
					logger.error("",e);
				}
				logger.error("",e);
			}
			
			
		}
		
	}

	private String getName(String code, String name){
		return (code+"-" + name ).replaceAll("\\*", "");
	}



}
