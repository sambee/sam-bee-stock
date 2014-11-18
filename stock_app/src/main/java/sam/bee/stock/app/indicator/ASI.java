// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   ASI.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class ASI extends IndicatorBase {

    public ASI(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "ASI";
        super.m_strParamName = (new String[] {
            ""
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -1E+038F;
        super.m_min = 1E+038F;
        GetValueMaxMin(super.m_data[0], 1);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], 0, Application.rhColor.clIndicator[0]);
    }

    public void Calculate() {
        super.m_data = new float[1][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        super.m_data[0] = new float[super.m_kData.length];
        float asi[] = super.m_data[0];
        asi[0] = 0.0F;
        float si = 0.0F;
        for(int i = 1; i < super.m_kData.length; i++) {
            float a = Math.abs(super.m_kData[i].highPrice - super.m_kData[i - 1].closePrice);
            float b = Math.abs(super.m_kData[i].lowPrice - super.m_kData[i - 1].closePrice);
            float c = Math.abs(super.m_kData[i].highPrice - super.m_kData[i - 1].lowPrice);
            float d = Math.abs(super.m_kData[i - 1].closePrice - super.m_kData[i - 1].openPrice);
            float e = super.m_kData[i].closePrice - super.m_kData[i - 1].closePrice;
            float f = super.m_kData[i].closePrice - super.m_kData[i].openPrice;
            float g = super.m_kData[i - 1].closePrice - super.m_kData[i - 1].openPrice;
            float x = e + f / 2.0F + g;
            float r = 0.0F;
            if(a >= b && a >= c)
                r = a + b / 2.0F + d / 4F;
            if(b >= a && b >= c)
                r = b + a / 2.0F + d / 4F;
            if(c >= a && c >= b)
                r = c + d / 4F;
            float k = Math.max(a, b);
            if(k != 0.0F)
                si = (((50F * x) / r) * k) / 3F;
            asi[i] = asi[i - 1] + si;
        }

    }
}
