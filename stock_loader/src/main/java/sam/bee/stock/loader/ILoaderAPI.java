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
	List<Map<String, String>> getShangHaiStockList() throws Exception;
	
	/**
	 * 
	 * get all stock code and stock names.
	 * 
	 * @return
	 */
	List<Map<String, String>> getShenZhenStockList() throws Exception;
	
	/**
	 * 
	 * @param stocks
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> getRealTimeStockList(List<String> stocks) throws Exception;
	
	
	List<Map<String, String>> getStockHistory(String stockCode) throws Exception;
}
