package com.sam.stock.trade;

import java.util.Map;

public interface TradeAPI {

	Map<String,?> buyStock(String loginToken, String stockCode, int unit, double price);
	
	Map<String,?> sellStock(String loginToken, String stockCode, int unit, double price);
	
	void addAmount(double money);
	
	void takeAmount(double money);
	
	Map<String,?> getAccountInfo(String loginToken);
	
	String login(String userId, String password);
	
	String regsiter(Map<String,?> attr);
	
	String updateProfile(String token, Map<String,?> attr);
	
	String changePassword(String tokenId, String newPassword);
	
	String forgotPassword(String email);
	
	String logoff(String token);
	
	Map<String,?> getStockInfo(String stockCode);
	
	
	
}
