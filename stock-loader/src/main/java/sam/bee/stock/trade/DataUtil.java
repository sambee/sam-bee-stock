package sam.bee.stock.trade;

import java.util.List;
import java.util.Map;

import static sam.bee.stock.Const.*;
import static sam.bee.stock.Const.DATE;
import static sam.bee.stock.Const.HIGH;

/**
 * Created by Administrator on 2016/7/10.
 */
public class DataUtil {

    protected  static  <T> T[] getDoubleData(Class<T> cls, List<Map<String, String>> dl , String field) throws Exception {
        T[] l =null;
        Map data;
        if(cls == Double.class){
           l  = (T[]) new Double[dl.size()];
        };
        if(cls == Long.class){
            l  = (T[]) new Long[dl.size()];
        };
        if(cls == String.class){
            l  = (T[]) new String[dl.size()];
        };

        for(int i=0;i<dl.size();i++){
            data = dl.get(i);
            Object o =  data.get(field);
            if(cls == Double.class) {
                l[i] = (T) Double.valueOf((String) o);
            }
            if(cls == Long.class) {
                l[i] = (T) Long.valueOf((String) o);
            }
            if(cls == String.class) {
                l[i] = (T) String.valueOf(o);
            }
        }
        return (T[])l;
    }
    public static Double[] getOpen(List<Map<String, String>> dl) throws Exception {
        return getDoubleData(Double.class, dl, OPEN );
    }



    public static  Double[] getClose(List<Map<String, String>> dl) throws Exception {
        return getDoubleData(Double.class, dl, CLOSE );
    }


    public  static Long[] getVolume(List<Map<String, String>> dl) throws Exception {
        return getDoubleData(Long.class, dl, VOLUME );
    }


    public Double[] getHigh(List<Map<String, String>> dl) throws Exception {
        return getDoubleData(Double.class, dl, HIGH );
    }


    public  static String[] getDate(List<Map<String, String>> dl) throws Exception {
        return getDoubleData(String.class, dl, DATE );
    }
}
