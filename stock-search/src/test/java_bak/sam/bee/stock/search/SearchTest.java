package sam.bee.stock.search;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sam.bee.cache.FileCache;
import sam.bee.cache.IStore;



public class SearchTest {

	final String HISTORY = "history";
	final String EXCLUDE = "exclude";
	final String CODE = "code";
	final String TAB = "TAB1";
	protected static final Logger log = LoggerFactory.getLogger(SearchTest.class);
	
	@Test
	public void test() throws Exception{

	
		final IStore cache = new FileCache("../stock-data",-1);
		ISearch search = new SearchImpl(cache);
		List<Map<String,String>> stockList = cache.getList(CODE, "shanghai");
		
		search.deleteTab(TAB);
		List<Map<String,String>> list  = search.getStockdsByTab(TAB);
		assertNull(list);
		assertNotNull(stockList);
		assertThat(stockList.size(), greaterThan(0));
		
		search.saveAllToTab(TAB, stockList);
		list  = search.getStockdsByTab(TAB);
		assertThat(list.size(), equalTo(stockList.size()));
		 
		Map stock = new HashMap(){{
			put("TEST1", "TEST1");
		}};	
		
		//test remove one item.
		search.addToTab(TAB, stock);
		List<Map<String,String>> list2  = search.getStockdsByTab(TAB);
		assertThat(list2.size(),  equalTo(stockList.size()+1));
		
		boolean isDelete = search.delete(TAB, "TEST1", "TEST1");
		assertTrue(isDelete);
		
		list2  = search.getStockdsByTab(TAB);
		assertThat(list2.size(),  equalTo(stockList.size()));
		
		search.deleteTab(TAB);
		List<Map<String,String>> list3  = search.getStockdsByTab(TAB);
		assertNull(list3);
		
		log.info("=========== TEST ============");
	}
	
}
