package com.sam.stock.trade;

public class StockTrade {

	Stock sk;
	public StockTrade(Stock sk){
		this.sk = sk;
	}
	
    public void buy() {
        System.out.println("You want to buy stocks:" + sk.getName());
    }
    public void sell() {
        System.out.println("You want to sell stocks " + sk.getName());
    }

}
