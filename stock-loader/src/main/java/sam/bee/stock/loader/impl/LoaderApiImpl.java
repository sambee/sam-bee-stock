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
		QQHttpLoader loader = new QQHttpLoader();
		List<String> actual = loader.get(stocks);
		QQRealTimeDataApapter adapter = new QQRealTimeDataApapter();
		return adapter.parse(actual);
	}

	@Override
	public List<Map<String, String>> getStockHistory(String stockCode) throws Exception {
		YahooHistoryLoader loader = new YahooHistoryLoader();
		List<String> list =loader.get(stockCode);
		return (List<Map<String, String>>) new YahooHistoryDataAdapter().parse(list);
	}

}
