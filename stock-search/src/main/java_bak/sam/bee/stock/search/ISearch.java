package sam.bee.stock.search;

import java.util.List;
import java.util.Map;

public interface ISearch {

	void saveAllToTab(String tab, List<Map<String,String>> stocks) throws Exception;
	
	void addToTab(String tab, Map<String,String> stock) throws Exception;
	
	void saveAllToTab(String tab, Map<String,String>[] stocks) throws Exception;
	
	List<Map<String,String>> getStockdsByTab(String tab) throws Exception;
	
	boolean deleteTab(String tab) throws Exception;
	
	boolean delete(String tab, String key, String value) throws Exception;
}