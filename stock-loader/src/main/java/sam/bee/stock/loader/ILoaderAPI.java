package sam.bee.stock.loader;

import java.util.List;
import java.util.Map;

public interface ILoaderAPI {

	/**
	 * 
	 * @return
	 */
	List<Map<String, Object>> getAllStockList();
	
	
}
