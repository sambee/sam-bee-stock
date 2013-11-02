package sam.bee.stock.loader.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import sam.bee.stock.loader.ILoader;

public abstract class BaseLoader implements ILoader{
	
	protected List<String> getResponse(String request) throws IOException {
		URL url = new URL(request);
		URLConnection conn = url.openConnection();
		
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"));
		String tmp = null;
		List<String> list = new ArrayList<String>(0);
		while (null != (tmp = br.readLine()))
		{		
			list.add(tmp);
		}
		br.close();
		in.close();
		return list;
	}
}
