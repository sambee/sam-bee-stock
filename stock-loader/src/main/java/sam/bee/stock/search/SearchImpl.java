package sam.bee.stock.search;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sam.bee.cache.Cache;
import sam.bee.cache.FileCache;
import sam.bee.cache.IStore;


public class SearchImpl implements ISearch {

	
	IStore cache;
	
	public SearchImpl(IStore cache){
		this.cache = cache;
	}
	
	public void saveAllToTab(String tab, List<Map<String, String>> stocks) throws Exception {
		cache.set(stocks, tab);
		
	}

	public void addToTab(String tab, Map<String, String> stock) throws Exception {
		List<Map<String, String>> list = getStockdsByTab(tab);
		list.add(stock);
		saveAllToTab(tab, list);
		
	}

	public void saveAllToTab(String tab, Map<String, String>[] stocks) throws Exception {
		saveAllToTab(tab, Arrays.asList(stocks));
		
	}

	public List<Map<String, String>> getStockdsByTab(String tab) throws Exception {	
		return cache.getList(tab);
	}

	public boolean deleteTab(String tab) throws Exception {
		return cache.cleanCache(tab);
		
	}

	public boolean delete(String tab, String key, String value)
			throws Exception {
		List<Map<String,String>> stocks = getStockdsByTab(tab);
		for(Map<String,String> m : stocks){
			if(value.equals(m.get(key))){
				stocks.remove(m);
				saveAllToTab(tab, stocks);
				return true;
			}
		}
		return false;
	}

	
}
