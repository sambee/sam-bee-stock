package sam.bee.stock.loader.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import sam.bee.stock.loader.ILoaderAPI;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class LoaderApiImpl implements ILoaderAPI{

	@Override
	public List<Map<String, String>> getShangHaiStockList() throws IOException {
		return new GetAllShangHaiStockList().list();
	}

	@Override
	public List<Map<String, String>> getShenZhenStockList() throws IOException {
		return new GetAllShenZhenStockList().list();
	}

	@Override
	public List<Map<String, String>> getRealTimeStockList(List<String> stocks)
			throws Exception {
		QQRealTimQuery query = new QQRealTimQuery(stocks);
		return (List<Map<String, String>>) query.execute();
	}

	@Override
	public List<Map<String, String>> getStockHistory(String stockCode) throws Exception {
		QQHistoryQuery qqHistoryQuery = new QQHistoryQuery(stockCode);
		return (List<Map<String, String>>) qqHistoryQuery.execute();
	}

}
