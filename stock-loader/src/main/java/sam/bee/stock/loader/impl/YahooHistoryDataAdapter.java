package sam.bee.stock.loader.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sam.bee.stock.loader.IDataAdapter;

public class YahooHistoryDataAdapter implements IDataAdapter {

	@Override
	public Object parse(List<?> list) {
		List<Map<String, String>> ret = new ArrayList<Map<String,String>>();
		for(Object obj : list){
			String tmp = (String)obj;
			
		}
		return ret;
	}

}
