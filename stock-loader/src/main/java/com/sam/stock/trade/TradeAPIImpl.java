package com.sam.stock.trade;

import java.util.HashMap;
import java.util.Map;

import com.sam.stock.db.DBUtils;

public class TradeAPIImpl {
	
	DBUtils db = null;

	Map<String,?> buyStock(final String loginToken, final String stockCode, final int unit, final double price){
		Map params = new HashMap(){{
			put("", loginToken);
		}};
		
		return db.save("", params);
	};
	
	Map<String,?> sellStock(final String loginToken, final String stockCode, final int unit, final double price){
		Map params = new HashMap(){{
			put("", loginToken);
		}};
		
		return db.save("", params);
	};
	
	void addAmount(final String loginToken, final double money){
		
	};
	
	void takeAmount(final String loginToken, final double money){
		
	};
	
	Map<String,?> getAccountInfo(final String loginToken){
		Map params = new HashMap(){{
			put("", loginToken);
		}};
		return db.save("", params);
	};
	
	String login(String userId, String password){
		
	};
	
	String regsiter(Map<String,?> attr){
		
	};
	
	String updateProfile(String token, Map<String,?> attr){
		
	};
	
	String changePassword(String tokenId, String newPassword){
		
	};
	
	String forgotPassword(String email){
		
	};
	
	String logoff(String token){
		
	};
	
	Map<String,?> getStockInfo(String stockCode){
		
	};
	
	
	
}
