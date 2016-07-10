package sam.bee.stock.trade;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static sam.bee.stock.trade.DataUtil.getClose;

/**
 * Created by Administrator on 2016/7/10.
 */
public class SimpleStrategy implements IStrategy{

    Agent agent;
    Market market;
    String code = "600578";
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
    @Override
    public Descition execute(Object arg) throws Exception {
        String date = (String) arg;
        Map data = getMarket().getCurrentData(code);

        if(data!=null) {

            List<Map<String, String>> ml = getMarket().getHistory(code, date);

            Double[] close = getClose(ml);
            if (ml != null) {
                //Suspension
                if(p!=close[close.length - 1]) {
                    p =  close[close.length - 1];
                    System.out.println(date + " " + close[close.length - 1]);
                }
            }
        }

        return null;
    }
}
