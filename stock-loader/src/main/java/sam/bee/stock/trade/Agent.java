package sam.bee.stock.trade;

import java.util.Observable;
import java.util.Observer;

public class Agent implements Observer{

    IStrategy strategy;
    public void setStrategy(IStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public void update(Observable o, Object arg) {
        Market market = (Market)o;
        String date = (String)arg;
        if(strategy!=null){
            try {
                strategy.execute(arg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
