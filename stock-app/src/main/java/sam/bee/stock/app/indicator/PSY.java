// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   PSY.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class PSY extends IndicatorBase {

    private final int m_iParam[] = {
        12, 24
    };

    public PSY(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "PSY";
        super.m_strParamName = new String[m_iParam.length];
        for(int i = 0; i < m_iParam.length; i++)
            super.m_strParamName[i] = "PSY" + m_iParam[i];

        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        for(int i = 0; i < m_iParam.length; i++)
            GetValueMaxMin(super.m_data[i], m_iParam[i]);

        DrawCoordinate(g, 2);
        for(int i = 0; i < m_iParam.length; i++)
            DrawLine(g, super.m_data[i], m_iParam[i], Application.rhColor.clIndicator[i]);

    }

    public void Calculate() {
        super.m_data = new float[m_iParam.length][];
        if(super.m_kData == null || super.m_kData.length == 0)
            return;
        for(int i = 0; i < m_iParam.length; i++)
            if(m_iParam[i] <= super.m_kData.length && m_iParam[i] > 0) {
                super.m_data[i] = new float[super.m_kData.length];
                GetPSY(m_iParam[i], super.m_data[i]);
            }

    }

    private void GetPSY(int n, float psy[]) {
        if(psy == null)
            return;
        double sum = 0.0D;
        for(int i = 1; i < n; i++)
            if(super.m_kData[i].closePrice > super.m_kData[i - 1].closePrice)
                sum++;

        for(int i = n; i < super.m_kData.length; i++) {
            if(super.m_kData[i].closePrice > super.m_kData[i - 1].closePrice)
                sum++;
            psy[i] = (float)((sum / (double)n) * 100D);
            int j = (i - n) + 1;
            if(super.m_kData[j].closePrice > super.m_kData[j - 1].closePrice)
                sum--;
        }

    }
}
