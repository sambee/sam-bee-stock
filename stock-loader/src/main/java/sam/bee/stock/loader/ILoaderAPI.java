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
	List<Map<String, String>> getAllStockInfoList() throws Exception;
	

	/**
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> getRealTimeStockList(String code) throws Exception;


	/**
	 *
	 * @param stockCode
	 * @return
	 * @throws Exception
     */
	List<Map<String, String>> getStockHistory(String stockCode) throws Exception;
}
