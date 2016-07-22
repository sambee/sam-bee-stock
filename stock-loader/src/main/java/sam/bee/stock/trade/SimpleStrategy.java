package sam.bee.stock.trade;

import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

import static sam.bee.stock.trade.DataUtil.getClose;

/**
 * Created by Administrator on 2016/7/10.
 */
public class SimpleStrategy implements IStrategy{
    Logger logger = org.slf4j.LoggerFactory.getLogger(SimpleStrategy.class);
    Agent agent;
    Market market;
    String code = "600578";
    String stockname = "";
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    double p =0;
    int i=0;
    int BUY =1,SELL=2;
    int status = BUY;
    @Override
    public List<Decision>  execute(Object arg) throws Exception {
        String date = (String) arg;
        Map data = getMarket().getCurrentData(code);

        if(data!=null) {

            List<Map<String, String>> ml = getMarket().getHistory(code, date);

            Double[] close = getClose(ml);
            logger.debug(date + " " + p);
            if (ml != null) {
                //Suspension
                if(p!=close[close.length - 1]) {
                    p =  close[close.length - 1];
                    if(p<=4.28 && BUY==status) {
                        logger.debug(" ===========" + date + " buy " + p);
                        status = SELL;
                    }
                    else if(p>=4.39 && SELL ==status){
                        logger.debug(" ==========="  + date + " sell " + p);
                        status = BUY;
                    }
                }
            }
        }

        return null;
    }
}
