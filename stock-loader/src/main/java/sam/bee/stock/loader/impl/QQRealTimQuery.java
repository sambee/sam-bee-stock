package sam.bee.stock.loader.impl;

import java.util.List;

public class QQRealTimQuery extends BaseQuery implements ICommand{


	private List<String> stockCodeList;
	private IDataAdapter adapter;
	QQHttpLoader loader;
	
	public QQRealTimQuery(List<String> stockCodeList)
	{
		this.stockCodeList = stockCodeList;
		this.adapter =  new QQRealTimeDataApapter();
		this.loader = new QQHttpLoader();
	}
	
	public QQRealTimQuery(QQHttpLoader loader, List<String> stockCodeList, IDataAdapter adapter)
	{
		this.stockCodeList = stockCodeList;
		this.adapter =  adapter;
		this.loader = loader;
	}
	
	
	@SuppressWarnings("unchecked")
	public Object execute() throws Exception
	{
		List<String> list = loader.get(stockCodeList);		
		return adapter.parse(list);
	}
	

	

}
