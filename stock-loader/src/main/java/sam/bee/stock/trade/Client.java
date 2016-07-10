package sam.bee.stock.trade;

public class Client {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	 public static void main(String[] args) throws Exception {
		 Market market = new Market();
		 SimpleStrategy simpleStrategy =  new SimpleStrategy();
		 simpleStrategy.setMarket(market);
		 Agent agent = new Agent();
		 agent.setStrategy(simpleStrategy);
		 market.addAgent(agent);
		 for(int i=0;i<180; i++){
			 market.next();
		 }

	     System.exit(0);
	  }

}
