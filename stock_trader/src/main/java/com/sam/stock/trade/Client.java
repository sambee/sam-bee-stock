package com.sam.stock.trade;

public class Client {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	 public static void main(String[] args) throws InterruptedException {
			 
		 Agent agent = new Agent();
		 
		 for(int i=1; i<=10000; i++){
		        StockTrade stock = new StockTrade(new Stock("Stock"+i, i, i));
		        BuyStockOrder bsc = new BuyStockOrder (stock);
		        SellStockOrder ssc = new SellStockOrder (stock);
		        

		        agent.placeOrder(bsc); // Buy Shares
		        agent.placeOrder(ssc); // Sell Shares
		    }

	        while(agent.getHold()>0){
	        	Thread.sleep(5000);
	        }
	        System.exit(0);
	    }

}
