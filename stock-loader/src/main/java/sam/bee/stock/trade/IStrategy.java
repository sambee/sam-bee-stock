package sam.bee.stock.trade;

import java.util.Map;
import java.util.Observable;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/10.
 */
public interface IStrategy {

    void setMarket(Market market);
    void setAgent(Agent agent);
    Descition execute(Object content) throws Exception;
}
