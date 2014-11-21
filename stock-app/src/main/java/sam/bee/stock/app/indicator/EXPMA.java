// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   EXPMA.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            KLine, IndicatorBase, IndicatorPos

public class EXPMA extends KLine {

    private final int m_iParam[] = {
        5, 20, 50
    };

    public EXPMA(IndicatorPos pos, int iPrecision) {
        super(pos, 0, iPrecision);
        super.m_strIndicatorName = "EXPMA";
        super.m_strParamName = new String[m_iParam.length];
        for(int i = 0; i < m_iParam.length; i++)
            super.m_strParamName[i] = "MA" + m_iParam[i];

    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        if(data == null || data.length == 0)
            return;
        super.m_kData = data;
        Calculate();
        super.Paint(g, rc, data);
        for(int i = 0; i < m_iParam.length; i++)
            DrawLine(g, super.m_data[i], 0, Application.rhColor.clIndicator[i]);

    }

    public void Calculate() {
        super.m_data = new float[m_iParam.length][];
        for(int i = 0; i < m_iParam.length; i++) {
            super.m_data[i] = new float[super.m_kData.length];
            GetEXPMA(m_iParam[i], super.m_data[i]);
        }

    }

    private void GetEXPMA(int n, float expma[]) {
        float xs = 2.0F / (float)(n + 1);
        expma[0] = super.m_kData[0].closePrice;
        for(int i = 1; i < super.m_kData.length; i++)
            expma[i] = (super.m_kData[i].closePrice - expma[i - 1]) * xs + expma[i - 1];

    }

    protected void GetMaxMin() {
        super.GetMaxMin();
        for(int i = 0; i < m_iParam.length; i++)
            GetValueMaxMin(super.m_data[i], m_iParam[i] - 1);

    }
}
