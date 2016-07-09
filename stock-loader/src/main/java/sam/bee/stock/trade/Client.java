package sam.bee.stock.trade;

public class Client {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	 public static void main(String[] args) throws Exception {
		 Market market = new Market();

		 Agent agent = new Agent();
		market.addAgent(agent);
		 for(int i=0;i<180; i++){
			 String date = market.next();
			 System.out.println(date);
		 }

	        System.exit(0);
	  }

}
