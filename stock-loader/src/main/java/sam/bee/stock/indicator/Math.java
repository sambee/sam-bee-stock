package sam.bee.stock.indicator;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/7.
 */
public class Math {

    /**
     * 过去N天最小
     * @param data
     * @param key
     * @param start
     * @param n
     * @return
     */
    public static double llv(List<Map<String,String>> data, String key, int start, int n){
        if(n>data.size()){
            n = data.size();
        }
        double min = Double.valueOf(data.get(start).get(key));
        double price;
        Map<String,String> m;
        for(int i=start; n>0 && i>=0; --n,i--){
            m = data.get(i);
            price = Double.valueOf(m.get(key));
            if(min>price){
                min = price;
            }

        }
        return min;
    }

    /**
     * 过去N天最大
     * @param data
     * @param key
     * @param start
     * @param n
     * @return
     */
    public static double hhv(List<Map<String,String>> data, String key, int start,  int n){
        if(n>data.size()){
            n = data.size();
        }
        double max =  Double.valueOf(data.get(start).get(key));
        double price;
        Map<String,String> m;
        for(int i=start; n>0 && i>=0; --n,i--){
            m = data.get(i);
            price = Double.valueOf(m.get(key));
            if(max<price){
                max = price;
            }
        }
        return max;
    }
    /**
     * SMA(X,N,M):X的N日移动平均,M为权重,如Y=(X*M+Y'*(N-M))/N
     * @param data
     * @param n
     * @return
     */
    public double sma(List<Double> data, int n){
        return 0.0;
    }

    public static double get(List<Map<String,String>> data, String key, int index){
        return Double.valueOf(data.get(index).get(key));
    }

    public static double getNumber(double number){
        DecimalFormat df = new DecimalFormat("#.####");
        double f=Double.valueOf(df.format(number));
        return f;
    }

    public static double getNumber2(double number){
        DecimalFormat df = new DecimalFormat("#.##");
        double f=Double.valueOf(df.format(number));
        return f;
    }
}
