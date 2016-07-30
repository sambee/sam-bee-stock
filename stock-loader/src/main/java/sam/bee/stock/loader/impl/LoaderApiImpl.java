package sam.bee.stock.loader.impl;

import java.util.List;
import java.util.Map;

import sam.bee.stock.loader.ILoaderAPI;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class LoaderApiImpl implements ILoaderAPI{


	@Override
	public List<Map<String, String>> getAllStockInfoList() throws Exception {
		return new GetAllStockInfoList().load();
	}

	@Override
	public List<Map<String, String>> getRealTimeStockList(String code)
			throws Exception {
		QQRealTimLoader query = new QQRealTimLoader(code);
		return (List<Map<String, String>>) query.execute();
	}

	@Override
	public List<Map<String, String>> getStockHistory(String stockCode) throws Exception {
		QQHistoryLoader qqHistoryQuery = new QQHistoryLoader(stockCode);
		return (List<Map<String, String>>) qqHistoryQuery.execute();
	}

}
