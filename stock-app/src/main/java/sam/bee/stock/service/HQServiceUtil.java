// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:45:08 

//source File Name:   HQServiceUtil.java

package sam.bee.stock.service;

import java.util.Calendar;
import java.util.Date;

public class HQServiceUtil {

    public HQServiceUtil() {
    }

    public static Date getDate(int date, int time) {
        Calendar cal = Calendar.getInstance();
        cal.set(14, 0);
        int year = date / 10000;
        int month = (date - year * 10000) / 100;
        int dateInt = date - year * 10000 - month * 100;
        int hour = time / 10000;
        int minue = (time - hour * 10000) / 100;
        int second = time - hour * 10000 - minue * 100;
        cal.set(year, month - 1, dateInt, hour, minue, second);
        return cal.getTime();
    }

    public static int dateToHHmmss(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(11) * 10000 + cal.get(12) * 100 + cal.get(13);
    }
}
