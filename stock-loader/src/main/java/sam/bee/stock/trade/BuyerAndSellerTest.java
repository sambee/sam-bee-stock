package sam.bee.stock.trade;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuyerAndSellerTest {

    public static void main(String[] args) throws InterruptedException {
    	
    	/** ���⽻���� */
        MyAgent agent = new MyAgent();
   
       int pThreadSize = 5;
       ExecutorService pool = Executors.newFixedThreadPool(pThreadSize);
       
       for(int i=0;i<pThreadSize;i++){           
           pool.execute(new Buy(agent, "���" + i));
           pool.execute(new Sell(agent, "����" +i));           
     
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
    
    /** ���꼴ֹ */
    public void close(){ 
    	isClosed = true;
    	synchronized (this){
    		notifyAll();	
    	}
    	
    }
    
   
    public synchronized void sell(MyOrder order) {
        System.out.println(" ++ ���۹�Ʊǰ Index��" + index);

        while(index >= orders.length) {
        	if(isClosed){
        		System.out.println("������������ʱ���ѹرգ��޷����н��ף��˶������ϡ�:" + order);
        		return;
        	}
            System.out.println(" >>>>>> ��Ҫ���۵Ĺ�Ʊ̫����:(����ͣ����");
            try {
            	this.wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }   
        }
       
        orders[index] = order;
       // System.out.println("���۹�Ʊ��" + order);        
        index++;
        notifyAll();
    }
     
    public synchronized MyOrder buy() {
       // System.out.println(" -- ����ǰ��Ʊ��Ϊ��" + index );

        while(index <= 0) {
        	if(isClosed){
        		System.out.println("������������ʱ���ѹرգ��޷����н��ף��޷�����");
        		return null;
        	}
            System.out.println("  >>>>>> ��û�й�Ʊ��������-_-!!����ͣ����");
            try {
                this.wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        } 

        index--;
        //System.out.println("�����Ʊ��" + orders[index]);
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
        	MyOrder order = new MyOrder("��" + i + "ֻ��Ʊ");
        	agent.sell(order);
            //System.out.println("[Sell]�� " + order);
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
            System.out.println(name + "������:" +agent.buy());
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }        
       
    }
}