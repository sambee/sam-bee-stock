package sam.bee.stock.trade;

import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Client {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	 public static void main(String[] args) throws Exception {

		 File f = new File(System.getProperty("user.home") + File.separator + ".stocks" + File.separator + "log4j.properties");
		 File f2 = new File(System.getProperty("user.home") + File.separator + ".stocks" + File.separator + "stocks.properties");
		 if(f.exists()) {
			 Properties p = new Properties();
			 p.load(new InputStreamReader(new FileInputStream(f), "UTF-8"));
			 PropertyConfigurator.configure(p);
		 }
		 org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Client.class);
		 long startTime =System.currentTimeMillis();
		 logger.info("==== Start ====");
		 logger.info("load " + (f.exists()?"success ":"fail ") + f.getAbsolutePath());

		 IStrategy strategy = null;
		 if(f2.exists()){
			 Properties p = new Properties();
			 p.load(new InputStreamReader(new FileInputStream(f2), "UTF-8"));
			 String s = (String) p.get("stock.strategy");
			 if(s!=null && s.length()>0){				 ;
				strategy = (IStrategy) Class.forName(s).newInstance();
			 }
		 }

		 if(strategy ==null) {
  			 strategy = new SimpleStrategy();
		 }
		 logger.debug("==== load strategy:" + strategy.getClass().getName());
		 Market market = new Market();
		 Agent agent = new Agent();
		 strategy.setMarket(market);
		 strategy.setAgent(agent);
		 agent.setStrategy(strategy);
		 market.addAgent(agent);
		 for(int i=0;i<180; i++){
			 market.next();
		 }
		 long end =System.currentTimeMillis();

		 logger.info("==== End ==== Used:" +(end-startTime) );
	     System.exit(0);
	  }

}
