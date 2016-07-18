package sam.bee.stock.loader.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseLoader {
	
	protected abstract List<String> get(Object... params) throws Exception; 
	
	protected List<String> getResponse(String request) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		BufferedReader br = null;
		URL url = new URL(request);
		List<String> list = new ArrayList<String>(0);
		try {
			conn =(HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			in = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, "GBK"));
			String tmp = null;

			while (null != (tmp = br.readLine())) {
				list.add(tmp);
			}

		}
		finally {
			if(br!=null) {
				try {
					br.close();
				}catch (Exception e){}
			}
			if(in!=null){
				try{
					in.close();
				}catch (Exception e){}
			}
			if(conn!=null){
				try{
					conn.getInputStream().close();
				}catch (Exception e){}
			}
		}
		return list;
	}
}
