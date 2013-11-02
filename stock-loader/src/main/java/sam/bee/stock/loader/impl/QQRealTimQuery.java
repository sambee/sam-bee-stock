package sam.bee.stock.loader.impl;

import java.util.List;

import sam.bee.stock.loader.ICommand;
import sam.bee.stock.loader.IDataAdapter;
import sam.bee.stock.loader.ILoader;

public class QQRealTimQuery extends BaseQuery implements ICommand{


	private List<String> stockCodeList;
	private IDataAdapter adapter;
	ILoader loader;
	
	public QQRealTimQuery(List<String> stockCodeList)
	{
		this.stockCodeList = stockCodeList;
		this.adapter =  new QQRealTimeDataApapter();
		this.loader = new QQHttpLoader();
	}
	
	public QQRealTimQuery(ILoader loader, List<String> stockCodeList, IDataAdapter adapter)
	{
		this.stockCodeList = stockCodeList;
		this.adapter =  adapter;
		this.loader = loader;
	}
	
	
	@SuppressWarnings("unchecked")
	public Object execute() throws Exception
	{
		List<String> list = (List<String>)loader.load(stockCodeList);		
		return adapter.parse(list);
	}
	

	

}
