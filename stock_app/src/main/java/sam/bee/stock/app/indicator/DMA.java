// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   DMA.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class DMA extends IndicatorBase {

    private final int m_iParam[] = {
        10, 50
    };

    public DMA(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "DMA";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "DMA", "AMA"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        GetValueMaxMin(super.m_data[0], m_iParam[1] - 1);
        GetValueMaxMin(super.m_data[1], (m_iParam[0] + m_iParam[1]) - 2);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[1] - 1, Application.rhColor.clIndicator[0]);
        DrawLine(g, super.m_data[1], (m_iParam[0] + m_iParam[1]) - 2, Application.rhColor.clIndicator[1]);
    }

    public void Calculate() {
        super.m_data = new float[2][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        int n1 = m_iParam[0];
        int n2 = m_iParam[1];
        if(super.m_kData.length < Math.max(n1, n2))
            return;
        for(int i = 0; i < 2; i++)
            super.m_data[i] = new float[super.m_kData.length];

        float dma[] = super.m_data[0];
        float ama[] = super.m_data[1];
        AverageClose(m_iParam[0], dma);
        AverageClose(m_iParam[1], ama);
        for(int i = m_iParam[1] - 1; i < super.m_kData.length; i++)
            dma[i] -= ama[i];

        IndicatorBase.Average(m_iParam[1] - 1, super.m_kData.length, m_iParam[0], dma, ama);
    }
}
