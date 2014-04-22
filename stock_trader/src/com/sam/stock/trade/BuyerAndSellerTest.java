package com.sam.stock.trade;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuyerAndSellerTest {

    public static void main(String[] args) throws InterruptedException {
    	
    	/** 虚拟交易所 */
        MyAgent agent = new MyAgent();
   
       int pThreadSize = 5;
       ExecutorService pool = Executors.newFixedThreadPool(pThreadSize);
       
       for(int i=0;i<pThreadSize;i++){           
           pool.execute(new Buy(agent, "买家" + i));
           pool.execute(new Sell(agent, "卖家" +i));           
     
       }

       
       ExecutorService pool3 = Executors.newFixedThreadPool(1);
   	   pool3.execute(new ExchangeTimer(agent));         
       pool.shutdown();       
       pool3.shutdown();
      // System.exit(0);
        
    }
}


 
class MyOrder  {
	
	
     private String producer;
     MyOrder(String producer) {
         this.producer = producer;
     }
      
     public String toString() {
         return  "   " + producer;
     }
}
 
class MyAgent {
	
    private int index = 0;
    
    MyOrder[] orders = new MyOrder[6];
     
    boolean isClosed = false;
    
    /** 买完即止 */
    public void close(){ 
    	isClosed = true;
    	synchronized (this){
    		notifyAll();	
    	}
    	
    }
    
   
    public synchronized void sell(MyOrder order) {
        System.out.println(" ++ 出售股票前 Index：" + index);

        while(index >= orders.length) {
        	if(isClosed){
        		System.out.println("【交易所交易时间已关闭，无法进行交易，此订单作废】:" + order);
        		return;
        	}
            System.out.println(" >>>>>> 需要出售的股票太多了:(，暂停处理！");
            try {
            	this.wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }   
        }
       
        orders[index] = order;
       // System.out.println("出售股票：" + order);        
        index++;
        notifyAll();
    }
     
    public synchronized MyOrder buy() {
       // System.out.println(" -- 购买前股票数为：" + index );

        while(index <= 0) {
        	if(isClosed){
        		System.out.println("【交易所交易时间已关闭，无法进行交易，无法购买】");
        		return null;
        	}
            System.out.println("  >>>>>> 已没有股票可以卖了-_-!!，暂停购买！");
            try {
                this.wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        } 

        index--;
        //System.out.println("购买股票：" + orders[index]);
        notifyAll();
        return orders[index];
    }
}

class ExchangeTimer implements Runnable {
	
	MyAgent agent;
	public ExchangeTimer(MyAgent agent){
		this.agent = agent;		
	}
	
	 public void run() {
		 long current = System.currentTimeMillis();
		 while(System.currentTimeMillis()-current<5000){
			 try {
				Thread.sleep(1000);
				System.out.println(System.currentTimeMillis()-current);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
		 agent.close();
	 }
}
 
class Sell implements Runnable {
    private MyAgent agent;
     
    public Sell(MyAgent agent, String name) {
        this.agent = agent;
    }
     
    public void run() {
        for(int i=0; i<100; i++) {
        	MyOrder order = new MyOrder("第" + i + "只股票");
        	agent.sell(order);
            //System.out.println("[Sell]： " + order);
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
          
    }
}
 
class Buy implements Runnable {
    private MyAgent agent;
     String name;
    public Buy(MyAgent agent, String name) {
        this.agent = agent;
        this.name = name;
    }
     
    public void run() {
        for(int i=0; i<100; i++) {
            System.out.println(name + "购买了:" +agent.buy());
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }        
       
    }
}