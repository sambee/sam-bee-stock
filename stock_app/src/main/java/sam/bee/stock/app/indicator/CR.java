// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   CR.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class CR extends IndicatorBase {

    private final int m_iParam[] = {
        26, 10, 20, 40
    };

    public CR(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "CR";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "CR", "a", "b", "c"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        GetValueMaxMin(super.m_data[0], m_iParam[0]);
        GetValueMaxMin(super.m_data[1], m_iParam[0] + m_iParam[1]);
        GetValueMaxMin(super.m_data[2], m_iParam[0] + m_iParam[2]);
        GetValueMaxMin(super.m_data[3], m_iParam[0] + m_iParam[3]);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[0], Application.rhColor.clIndicator[0]);
        DrawLine(g, super.m_data[1], m_iParam[0] + m_iParam[1], Application.rhColor.clIndicator[1]);
        DrawLine(g, super.m_data[2], m_iParam[0] + m_iParam[2], Application.rhColor.clIndicator[2]);
        DrawLine(g, super.m_data[3], m_iParam[0] + m_iParam[3], Application.rhColor.clIndicator[3]);
    }

    public void Calculate() {
        super.m_data = new float[4][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        int n = m_iParam[0];
        for(int i = 0; i < 4; i++)
            super.m_data[i] = new float[super.m_kData.length];

        GetCR(n, super.m_data[0]);
        IndicatorBase.Average(m_iParam[0], super.m_kData.length, m_iParam[1], super.m_data[0], super.m_data[1]);
        IndicatorBase.Average(m_iParam[0], super.m_kData.length, m_iParam[2], super.m_data[0], super.m_data[2]);
        IndicatorBase.Average(m_iParam[0], super.m_kData.length, m_iParam[3], super.m_data[0], super.m_data[3]);
    }

    private void GetCR(int n, float cr[]) {
        if(super.m_kData.length < n)
            return;
        float downsum;
        float upsum = downsum = 0.0F;
        for(int i = 1; i < n; i++) {
            float value = (super.m_kData[i - 1].highPrice + super.m_kData[i - 1].lowPrice) / 2.0F;
            upsum += super.m_kData[i].highPrice - value <= 0.0F ? 0.0F : super.m_kData[i].highPrice - value;
            downsum += value - super.m_kData[i].lowPrice <= 0.0F ? 0.0F : value - super.m_kData[i].lowPrice;
        }

        float precr = 0.0F;
        for(int i = n; i < super.m_kData.length; i++) {
            float value = (super.m_kData[i - 1].highPrice + super.m_kData[i - 1].lowPrice) / 2.0F;
            upsum += super.m_kData[i].highPrice - value <= 0.0F ? 0.0F : super.m_kData[i].highPrice - value;
            downsum += value - super.m_kData[i].lowPrice <= 0.0F ? 0.0F : value - super.m_kData[i].lowPrice;
            cr[i] = precr;
            if(downsum != 0.0F)
                cr[i] = (upsum / downsum) * 100F;
            precr = cr[i];
            int j = (i - n) + 1;
            value = (super.m_kData[j - 1].highPrice + super.m_kData[j - 1].lowPrice) / 2.0F;
            upsum -= super.m_kData[j].highPrice - value <= 0.0F ? 0.0F : super.m_kData[j].highPrice - value;
            downsum -= value - super.m_kData[j].lowPrice <= 0.0F ? 0.0F : value - super.m_kData[j].lowPrice;
        }

    }
}
