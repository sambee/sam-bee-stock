package sam.bee.stock.loader.impl;

import java.util.List;

import sam.bee.stock.loader.ICommand;
import sam.bee.stock.loader.IDataAdapter;
import sam.bee.stock.loader.ILoader;

public class YahooHistoryQuery extends BaseQuery implements ICommand {

	String code;
	IDataAdapter adapter;
	ILoader loader;
	
	public YahooHistoryQuery(String code) {
		this.code = code;
		this.adapter =  new YahooHistoryDataAdapter();
		this.loader = new YahooHttpLoader();
	}
	
	public YahooHistoryQuery(ILoader loader, String code, IDataAdapter adapter) {
		this.code = code;
		this.adapter =  adapter;
		this.loader = loader;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object execute() throws Exception {		
		List<String> list = (List<String>)loader.load(code);		
		return adapter.parse(list);
	}
}