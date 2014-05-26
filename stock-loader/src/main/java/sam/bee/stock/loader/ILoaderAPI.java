package sam.bee.stock.loader;

import java.util.List;
import java.util.Map;

public interface ILoaderAPI {

	/**
	 * 
	 * get all stock code and stock names.
	 * 
	 * @return
	 */
	List<Map<String, Object>> getAllStockList();
	
	
}
