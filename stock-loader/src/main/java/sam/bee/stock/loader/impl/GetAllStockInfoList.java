package sam.bee.stock.loader.impl;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllStockInfoList {


	public List<Map<String,String>> load() throws IOException {
		Document doc = Jsoup.connect("http://quote.eastmoney.com/stocklist.html").get();
		Vector<Map<String,String>> v = new Vector<>();
		parseSS(v, doc);
		parseSZ(v, doc);
		return v;
	}


	private List<Map<String,String>> parseSS(Vector<Map<String,String>> v, Document doc) throws IOException{

//      Document doc = Jsoup.parse(input, "UTF-8", "");
		Elements  els = doc.select("div[id=quotesearch]");


		Elements shuanghai = els.first().select("ul li");
		Map<String,String> map;
		
		for(int i=0; i<shuanghai.size(); i++){
			String line = shuanghai.get(i).text();
			if(line!=null && line.length()>0){
				int pos = line.lastIndexOf("(");
				//String[] str =line.split(" ");
				map = new LinkedHashMap<>();
				map.put("STOCK_CODE",line.substring(pos+1, line.length()-1).trim());
				map.put("STOCK_NAME",line.substring(0, pos).trim());
				map.put("STOCK_TYPE","ss");
				v.add(map);
			}
		}
		
		return v;
	}



	private List<Map<String,String>> parseSZ(Vector<Map<String,String>> v, Document doc) throws IOException{
//      Document doc = Jsoup.parse(input, "UTF-8", "");
		Elements  els = doc.select("div[id=quotesearch]");

		Elements shuanghai = els.first().select("ul li");
		Map<String,String> map;

		for(int i=0; i<shuanghai.size(); i++){
			String line = shuanghai.get(i).text().trim();
			if(line!=null && line.length()>0){
				int pos = line.lastIndexOf("(");
				map = new LinkedHashMap();
				map.put("STOCK_CODE",line.substring(pos+1, line.length()-1).trim());
				map.put("STOCK_NAME",line.substring(0, pos).trim());
				map.put("STOCK_TYPE","sz");
				v.add(map);
			}
		}

		return v;
	}
}
