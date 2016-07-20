package sam.bee.stock.loader.impl;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllShenZhenStockList {

	public List<Map<String,String>> list() throws IOException{
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Document doc = Jsoup.connect("http://bbs.10jqka.com.cn/codelist.html").get();
//      Document doc = Jsoup.parse(input, "UTF-8", "");	
		Elements  els = doc.select("div.bbsilst_wei3");
		
		assert(els.size()==3);
		Elements shenzhen = els.get(1).select("a");
		Map<String,String> map;
		
		for(int i=0; i<shenzhen.size(); i++){
			String line = shenzhen.get(i).text().trim();
			if(line!=null && line.length()>0){

				String STOCK_CODE = line.substring(line.lastIndexOf(" ")+1);
				String STOCK_NAME = line.substring(0,line.lastIndexOf(" "));
				map = new LinkedHashMap();
				map.put("STOCK_NAME", STOCK_NAME);
				map.put("STOCK_CODE", STOCK_CODE);
				list.add(map);
			}
		}
		
		return list;
	}
}
