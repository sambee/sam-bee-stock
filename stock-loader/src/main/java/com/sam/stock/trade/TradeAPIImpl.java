package com.sam.stock.trade;

import java.util.HashMap;
import java.util.Map;

public class TradeAPIImpl {
	


	Map<String,?> buyStock(final String loginToken, final String stockCode, final int unit, final double price){
		Map params = new HashMap(){{
			put("", loginToken);
		}};
		
	    return params;
	};
	
	Map<String,?> sellStock(final String loginToken, final String stockCode, final int unit, final double price){
		Map params = new HashMap(){{
			put("", loginToken);
		}};

		return params;
	};

	
	
}
