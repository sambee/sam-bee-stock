// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   BOLL.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            KLine, IndicatorBase, IndicatorPos

public class BOLL extends KLine {

    private final int m_iParam[] = {
        10
    };

    public BOLL(IndicatorPos pos, int iPrecision) {
        super(pos, 1, iPrecision);
        super.m_strIndicatorName = "BOLL";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "MID", "UPPER", "LOWER"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.m_kData = data;
        Calculate();
        super.Paint(g, rc, data);
        for(int i = 0; i < 3; i++)
            DrawLine(g, super.m_data[i], m_iParam[0] * 2 - 2, Application.rhColor.clIndicator[i]);

    }

    protected void GetMaxMin() {
        super.GetMaxMin();
        for(int i = 0; i < 3; i++)
            GetValueMaxMin(super.m_data[i], m_iParam[0] * 2 - 2);

    }

    public void Calculate() {
        super.m_data = new float[3][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        int n = m_iParam[0];
        if(n > super.m_kData.length || n < 1 || (n + n) - 2 >= super.m_kData.length)
            return;
        for(int i = 0; i < 3; i++)
            super.m_data[i] = new float[super.m_kData.length];

        float average[] = super.m_data[0];
        float up[] = super.m_data[1];
        float down[] = super.m_data[2];
        float sum = 0.0F;
        AverageClose(n, average);
        for(int i = n - 1; i < (n + n) - 2; i++) {
            float value = super.m_kData[i].closePrice - average[i];
            sum += value * value;
        }

        float prevalue = 0.0F;
        for(int i = (n + n) - 2; i < super.m_kData.length; i++) {
            sum -= prevalue;
            float value = super.m_kData[i].closePrice - average[i];
            sum += value * value;
            value = (float)Math.sqrt(sum / (float)n) * 1.805F;
            up[i] = average[i] + value;
            down[i] = average[i] - value;
            value = super.m_kData[(i - n) + 1].closePrice - average[(i - n) + 1];
            prevalue = value * value;
        }

    }
}
