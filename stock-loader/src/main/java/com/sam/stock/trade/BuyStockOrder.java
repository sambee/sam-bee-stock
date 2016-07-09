package com.sam.stock.trade;

public class BuyStockOrder implements Order {

    private StockTrade stock;
    
    public BuyStockOrder ( StockTrade st) {
        stock = st;
    }
    public void execute() {
    	try {
			Thread.sleep((int)(java.lang.Math.random()*10)*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        stock.buy();
    }

}
