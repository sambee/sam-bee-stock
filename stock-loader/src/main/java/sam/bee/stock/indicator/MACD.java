package sam.bee.stock.indicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class MACD {

//    新股上市首日，其DIFF,DEA以及MACD都为0，因为当日不存在前一日，无法做迭代。而计算新股上市第二日的EMA时，前一日的EMA需要用收盘价（而非0）来计算。
//    另外，需要注意，计算过程小数点后四舍五入保留4位小数，最后显示的时候四舍五入保留3位小数。
//    具体计算公式及例子如下：
//    EMA（12）= 前一日EMA（12）×11/13＋今日收盘价×2/13
//    EMA（26）= 前一日EMA（26）×25/27＋今日收盘价×2/27
//    DIFF=今日EMA（12）- 今日EMA（26）
//    DEA（MACD）= 前一日DEA×8/10＋今日DIF×2/10
//    BAR=2×(DIFF－DEA)
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

//同花顺 算法
//    DIFF : EMA(CLOSE,SHORT) - EMA(CLOSE,LONG);
//    DEA  : EMA(DIFF,M);
//    MACD : 2*(DIFF-DEA);
//    Zero : 0;

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
        macdData.put("DIF", dif);
        macdData.put("DEA", dea);
        macdData.put("MACD", (dif - dea) * 2);
        return macdData;
    }
}
