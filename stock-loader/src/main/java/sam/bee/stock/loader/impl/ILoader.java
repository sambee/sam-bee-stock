package sam.bee.stock.loader.impl;


import java.util.List;
import java.util.Map;

public interface ILoader {
	
	List<Map<String,String>> execute(String code, String type)throws Exception;

}
