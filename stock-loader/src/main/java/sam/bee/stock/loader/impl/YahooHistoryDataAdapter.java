package sam.bee.stock.loader.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YahooHistoryDataAdapter implements IDataAdapter {

	@Override
	public Object parse(List<?> list) {
		List<Map<String, String>> ret = new ArrayList<Map<String,String>>();
		
		String[] columns = ((String)list.remove(0)).split(",");
		for(int i=0; i< columns.length; i++){
			columns[i] = columns[i].toUpperCase().replaceAll(" ", "_");
		}
		for(Object obj : list){
			String tmp = (String)obj;
			String[] stocks = tmp.split(",");
			Map map = new HashMap();
			for(int i=0; i< columns.length; i++){
				map.put(columns[i], stocks[i]);
			}
			
			
			ret.add(map);
		}
		return ret;
	}

}
