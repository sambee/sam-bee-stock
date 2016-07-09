package sam.bee.stock.trade;

import java.util.Observable;
import java.util.Observer;

public class Agent implements Observer{


    @Override
    public void update(Observable o, Object arg) {
        Market market = (Market)o;
        System.out.println("------------------- " + arg);
    }
}
