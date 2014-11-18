package com.sam.stock.trade;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Agent {

  private LinkedList<Order> ordersQueue = new LinkedList<Order>();
//  ExecutorService executorService = Executors.newCachedThreadPool();
  ExecutorService executorService = Executors.newFixedThreadPool(10);
  int getHold(){
	  return ordersQueue.size();
  }
  void placeOrder(Order order) {
        ordersQueue.addLast(order);	        
       // new Thread(new Handler()).start();
        executorService.execute(new Handler());   
  }    
  


  class Handler implements Runnable {
	@Override
	public void run() {
		if(ordersQueue.size()>0){
			ordersQueue.remove(0).execute();
		}		
	}
  }
}
