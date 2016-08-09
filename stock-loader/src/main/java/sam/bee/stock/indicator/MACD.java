package sam.bee.stock.indicator;

import java.text.DecimalFormat;
import java.util.*;

import static sam.bee.stock.indicator.Math.getNumber;

/**
 * Created by Administrator on 2016/7/10.
 */
public class MACD {

    /**
     * Calculate EMA,
     *
     * @param list
     *            :Price list to calculate，the first at head, the last at tail.
     * @return
     */
    public static final Double expma(final List<Double> list, final int number) {
        // 开始计算EMA值，
        Double k = 2.0 / (number + 1.0);// 计算出序数
        Double ema = list.get(0);// 第一天ema等于当天收盘价
        for (int i = 1; i < list.size(); i++) {
            // 第二天以后，当天收盘 收盘价乘以系数再加上昨天EMA乘以系数-1
            ema = list.get(i) * k + ema * (1 - k);
        }
        return ema;
    }

    /**
     * calculate MACD values
     *
     * @param list
     *            :Price list to calculate，the first at head, the last at tail.
     * @param shortPeriod
     *            :the short period value.
     * @param longPeriod
     *            :the long period value.
     * @param midPeriod
     *            :the mid period value.
     * @return
     */
    public static final HashMap<String, Double> macd(final List<Double> list, final int shortPeriod, final int longPeriod, int midPeriod) {
        HashMap<String, Double> macdData = new HashMap<String, Double>();
        List<Double> diffList = new ArrayList<Double>();
        Double shortEMA = 0.0;
        Double longEMA = 0.0;
        Double dif = 0.0;
        Double dea = 0.0;

        for (int i = list.size() - 1; i >= 0; i--) {
            List<Double> sublist = list.subList(0, list.size() - i);
            shortEMA = expma(sublist, shortPeriod);
            longEMA = expma(sublist, longPeriod);
            dif = shortEMA - longEMA;
            diffList.add(dif);
        }
        dea = expma(diffList, midPeriod);
        macdData.put("DIF", getNumber(dif));
        macdData.put("DEA", getNumber(dea));
        macdData.put("MACD",getNumber ((dif - dea) * 2));
        return macdData;
    }

    private static int length = 36;
    private static double multiplier;




    public static boolean cross(List<Map> macd){
        if(macd.size()<2){
            return false;
        }
        Map last1 = macd.get(macd.size()-2);
        Map last2 = macd.get(macd.size()-1);
        double y1 = (Double) last1.get("DEA");
        double x1 =1;
        double y2 =  (Double)last2.get("DEA");
        double x2 =2;
        double y3 = (Double) last1.get("DIF");
        double x3 =1;
        double y4 =  (Double)last2.get("DIF");
        double x4 =2;

        double vx1 = x1 -x3, vy1 = y1-y3;
        double vx2=  x2 -x3, vy2 = y2-y3;
        double vx3=  x4 -x3, vy3 = y4-y3;
        double v=crossMul(vx1, vy1, vx3,vy3) * crossMul(vx2,vy2, vx3,vy3);

        double vvx1=x3-x1,  vvy1=y3-y1;
        double vvx2=x4-x1,  vvy2=y4-y1;
        double vvx3=x2-x1,  vvy3=y2-y1;
        return v<=0 && (crossMul(vvx1, vvy1,vvx3,vvy3)* crossMul(vvx2,vvy2,vvx3,vvy3))<=0;
    }

    //向量的叉乘公式
    public static double crossMul(double x0, double y0, double x1, double y1){
        return x0*y1-x1*y0;
    }

    public static void main(String args[]){
        List<Map> list = new ArrayList<Map>();
        Map A = new HashMap();
        A.put("DEA", 0.01);
        A.put("DIF", 0.03);
        list.add(A);
        Map B = new HashMap();
        B.put("DEA", 0.0);
        B.put("DIF", -0.03);
        list.add(B);

        System.out.println(cross(list));
    }

    public static final  double ema(double[] prices, int number){

        int first =0;
        int last = prices.length;
        if(prices.length>(number*2+1)) {
            length = number;
            first = last - 2 * number + 1;
        }
        multiplier = 2. / (number + 1.);
        double ema = 0;

        for (int index = 1; index < last; index++) {
            ema = prices[index-1] +((prices[index]-prices[index-1]) * multiplier);
        }
        return getNumber(ema);
    }

//http://blog.sina.com.cn/s/blog_5938eb510100fz89.html
//    对理工检测：
//            20091218日：
//    新股上市，DIFF=0, DEA=0, MACD=0，收盘价55.01
//            20091219日：
//    收盘价53.7
//    EMA（12）=55.01+(53.7-55.01)×2/13=54.8085
//    EMA（26）=55.01+(53.7-55.01)×2/27=54.913
//    DIFF=EMA（12）- EMA（26）= 54.8085 - 54.913 = -0.1045  （-0.104？）
//    DEA=0+(-0.1045)X2/10=-0.0209
//    BAR=2*((-0.1045)-(-0.0209))=-0.1672

//    对法因数控：
//            20080905日：
//    新股上市，DIFF=0, DEA=0, MACD=0，收盘价12.34
//            20080908日：
//    收盘价11.11
//    EMA（12）=12.34+(11.11-12.34)×2/13=12.1508
//    EMA（26）=12.34+(11.11-12.34)×2/27=12.2489
//    DIFF=EMA（12）- EMA（26）= 12.1508 - 12.2489 = -0.0981
//    DEA=0+(-0.0981)X2/10=-0.01962
//    BAR=2*((-0.0981)-(-0.01962))=-0.15696


//    DIF:EMA(CLOSE,SHORT)-EMA(CLOSE,LONG);
//    DEA:EMA(DIF,MID);
//    MACD:(DIF-DEA)*2,COLORSTICK;
    public static  void  test(String[] args) {
        double[] d1 = new double[]{55.01};
        double[] d2 = new double[]{55.01, 53.7};

        double ema1_12 = ema(d1, 12);
        System.out.println("1 day ema12 " + ema1_12);

        double ema1_26 = ema(d1, 26);
        System.out.println("1 day ema26 " +  ema1_26);

        double dif1 = 0 -(ema1_12 -ema1_26) ;
        double dea1 = dif1;
        System.out.println("1 day ema26 " +  ema1_26);

        double ema2_12 = ema(d2, 12);
        System.out.println("2 day ema12 " + ema2_12);

        double ema2_26 = ema(d2, 26);
        System.out.println("2 day ema26 " +  ema2_26);

        double dif2 =  getNumber(ema(d2, 12) - ema(d2, 26));
        System.out.println("2 day dif " +  dif2);

        double dea2 =  ema(new double[]{0., dif2}, 9);
        System.out.println("2 day ema " +  dea2);

        double macd =  (dif2-dea2)*2;
        System.out.println(String.format("2 day macd (dif2=%f - dea2=%f)*2=%f" , dif2, dea2, macd));

        List<Double> ld = new ArrayList<Double>();
        ld.add(new Double(d2[0]));
        ld.add(new Double(d2[1]));
        HashMap<String, Double>  macdMap = macd(ld, 12, 26, 9);

        System.out.println("DIF="+getNumber(macdMap.get("DIF")));
        System.out.println("DEA="+getNumber(macdMap.get("DEA")));
        System.out.println("MACD="+getNumber(macdMap.get("MACD")));
    }
}
