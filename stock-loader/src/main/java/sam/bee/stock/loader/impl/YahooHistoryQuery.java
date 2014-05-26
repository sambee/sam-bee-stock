package sam.bee.stock.loader.impl;

import java.util.List;

public class YahooHistoryQuery extends BaseQuery implements ICommand {

	String code;
	IDataAdapter adapter;
	YahooHistoryLoader loader;
	
	public YahooHistoryQuery(String code) {
		this.code = code;
		this.adapter =  new YahooHistoryDataAdapter();
		this.loader = new YahooHistoryLoader();
	}
	
	public YahooHistoryQuery(YahooHistoryLoader loader, String code, IDataAdapter adapter) {
		this.code = code;
		this.adapter =  adapter;
		this.loader = loader;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object execute() throws Exception {		
		List<String> list = (List<String>)loader.get(code);		
		return adapter.parse(list);
	}
}