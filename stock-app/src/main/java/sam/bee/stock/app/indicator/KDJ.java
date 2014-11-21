// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   KDJ.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class KDJ extends IndicatorBase {

    private final int m_iParam[] = {
        9, 3, 3
    };

    public KDJ(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "KDJ(" + m_iParam[0] + "," + m_iParam[1] + "," + m_iParam[2] + ")";
        super.m_strParamName = (new String[] {
            "K", "D", "J"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        if(data == null || data.length == 0)
            return;
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = 0.0F;
        super.m_min = 10000F;
        for(int i = 0; i < 3; i++)
            GetValueMaxMin(super.m_data[i], m_iParam[i]);

        DrawCoordinate(g, 2);
        for(int i = 0; i < m_iParam.length; i++)
            DrawLine(g, super.m_data[i], m_iParam[i], Application.rhColor.clIndicator[i]);

    }

    public void Calculate() {
        super.m_data = new float[3][super.m_kData.length];
        int n1 = m_iParam[0];
        int n2 = m_iParam[1];
        int n3 = m_iParam[2];
        if(super.m_kData == null || n1 > super.m_kData.length || n1 < 1)
            return;
        float kvalue[] = super.m_data[0];
        float dvalue[] = super.m_data[1];
        float jvalue[] = super.m_data[2];
        n2 = n2 > 0 ? n2 : 3;
        n3 = n3 > 0 ? n3 : 3;
        float maxhigh = super.m_kData[n1 - 1].highPrice;
        float minlow = super.m_kData[n1 - 1].lowPrice;
        for(int j = n1 - 1; j >= 0; j--) {
            if(maxhigh < super.m_kData[j].highPrice)
                maxhigh = super.m_kData[j].highPrice;
            if(minlow < super.m_kData[j].lowPrice)
                minlow = super.m_kData[j].lowPrice;
        }

        float rsv;
        if(maxhigh <= minlow)
            rsv = 50F;
        else
            rsv = ((super.m_kData[n1 - 1].closePrice - minlow) / (maxhigh - minlow)) * 100F;
        float prersv;
        kvalue[n1 - 1] = dvalue[n1 - 1] = jvalue[n1 - 1] = prersv = rsv;
        for(int i = 0; i < n1; i++) {
            kvalue[i] = 0.0F;
            dvalue[i] = 0.0F;
            jvalue[i] = 0.0F;
        }

        for(int i = n1; i < super.m_kData.length; i++) {
            maxhigh = super.m_kData[i].highPrice;
            minlow = super.m_kData[i].lowPrice;
            for(int j = i - 1; j > i - n1; j--) {
                if(maxhigh < super.m_kData[j].highPrice)
                    maxhigh = super.m_kData[j].highPrice;
                if(minlow > super.m_kData[j].lowPrice)
                    minlow = super.m_kData[j].lowPrice;
            }

            if(maxhigh <= minlow) {
                rsv = prersv;
            } else {
                prersv = rsv;
                rsv = ((super.m_kData[i].closePrice - minlow) / (maxhigh - minlow)) * 100F;
            }
            kvalue[i] = (kvalue[i - 1] * (float)(n2 - 1)) / (float)n2 + rsv / (float)n2;
            dvalue[i] = kvalue[i] / (float)n3 + (dvalue[i - 1] * (float)(n3 - 1)) / (float)n3;
            jvalue[i] = 3F * kvalue[i] - 2.0F * dvalue[i];
        }

    }
}
