package sam.bee.stock.trade;

import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public interface IStrategy {

    public final static int BUY =1;
    public final static int SELL =2;
    void setMarket(Market market);
    void setAgent(Agent agent);
    List<Decision> execute(Object content) throws Exception;
}
