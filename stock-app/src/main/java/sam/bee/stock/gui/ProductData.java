// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   ProductData.java

package sam.bee.stock.gui;

import java.util.*;
import java.util.Arrays;

import sam.bee.stock.vo.ProductDataVO;


// Referenced classes of package gnnt.MEBS.HQApplet:
//            KLineData

public class ProductData {

    public String sCode;
    public ProductDataVO realData;
    public Vector vMinLine;
    Vector vBill;
    KLineData dayKLine[];
    KLineData min5KLine[];

    @Override
    public String toString() {
        return "ProductData{" +
                "sCode='" + sCode + '\'' +
                ", realData=" + realData +
                ", vMinLine=" + vMinLine +
                ", vBill=" + vBill +
                ", dayKLine=" + Arrays.toString(dayKLine) +
                ", min5KLine=" + Arrays.toString(min5KLine) +
                '}';
    }
}
