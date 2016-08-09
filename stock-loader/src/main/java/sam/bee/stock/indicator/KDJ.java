package sam.bee.stock.indicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static sam.bee.stock.Const.*;
import static sam.bee.stock.indicator.Math.*;

/**
 * Created by Administrator on 2016/8/7.
 */
public class KDJ {
    private ArrayList<Double> Ks;
    private ArrayList<Double> Ds;
    private ArrayList<Double> Js;
    private ArrayList<Double> RSVS;

    private int N = 9;
    private int M1 = 3;
    private int M2 = 3;

//    RSV:=(CLOSE-LLV(LOW,N))/(HHV(HIGH,N)-LLV(LOW,N))*100;
//    K:SMA(RSV,M1,1);
//    D:SMA(K,M2,1);
//    J:3*K-2*D;
//    RSV赋值:(收盘价-N日内最低价的最低值)/(N日内最高价的最高值-N日内最低价的最低值)*100
//    输出K:RSV的M1日[1日权重]移动平均
//    输出D:K的M2日[1日权重]移动平均
//    输出J:3*K-2*D

    //SMA(X,N,M):X的N日移动平均,M为权重,如Y=(X*M+Y'*(N-M))/N

    public KDJ(List<Map<String, String>> data) {
        Ks = new ArrayList<>();
        Ds = new ArrayList<>();
        Js = new ArrayList<>();
        RSVS = new ArrayList<>();
        if (data != null && data.size() > 0) {

            double RSV ;
            double k ;
            double d ;
            double j ;
            double minPrice ;
            double maxPrice ;
            double close ;
            for (int i = 0; i < data.size(); i++) {
                minPrice = llv(data, LOW, i, N);
                maxPrice = hhv(data, HIGH, i, N);
                close = get(data, CLOSE, i);
                if (i == 0) {
                    RSV = (close - minPrice) / (maxPrice - minPrice) * 100;
                    k = getNumber((1 * RSV + (M1 - 1) * 0) / 1);
                    d = (1 * k + (M2 - 1) * 0) / 1;
                    j = 3 * k - 2 * d;

                } else {
                    RSV = (close - minPrice) / (maxPrice - minPrice) * 100;
                    if(!Double.isNaN(RSV)){
                        k = getNumber((1 * RSV + (M1-1) * Ks.get(i - 1)) / M1);
                        d = (1 * k + (M2 - 1) * Ds.get(i - 1)) / M2;
                        j = 3 * k - 2 * d;
                    }
                    else{
                        k = 100;
                        d = 100;
                        j = 100;
                    }

                }

                if (k < 0) k = 0;
                if (k > 100) k = 100;
                if (d < 0) d = 0;
                if (d > 100) d = 100;
                if (j < 0) j = 0;
                if (j > 100) j = 100;

                RSVS.add(RSV);
                Ks.add(k);
                Ds.add(d);
                Js.add(j);
            }
        }

    }

    public ArrayList<Double> getK() {
        return Ks;
    }

    public ArrayList<Double> getD() {
        return Ds;
    }

    public ArrayList<Double> getJ() {
        return Js;
    }

}
