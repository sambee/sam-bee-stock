// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   MA.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            KLine, IndicatorBase, IndicatorPos

public class MA extends KLine {

    private final int m_iParam[] = {
        5, 10, 20, 30, 60
    };

    public MA(IndicatorPos pos, int iLineType, int iPrecision) {
        super(pos, iLineType, iPrecision);
        super.m_strIndicatorName = "MA";
        super.m_strParamName = new String[m_iParam.length];
        for(int i = 0; i < m_iParam.length; i++)
            super.m_strParamName[i] = "MA" + m_iParam[i];

    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.m_kData = data;
        Calculate();
        super.Paint(g, rc, data);
        for(int i = 0; i < m_iParam.length; i++)
            DrawLine(g, super.m_data[i], m_iParam[i] - 1, Application.rhColor.clIndicator[i]);

    }

    public void Calculate() {
        super.m_data = new float[m_iParam.length][];
        if(super.m_kData == null || super.m_kData.length == 0)
            return;
        for(int i = 0; i < m_iParam.length; i++) {
            super.m_data[i] = new float[super.m_kData.length];
            AverageClose(m_iParam[i], super.m_data[i]);
        }

    }

    protected void GetMaxMin() {
        super.GetMaxMin();
    }
}
