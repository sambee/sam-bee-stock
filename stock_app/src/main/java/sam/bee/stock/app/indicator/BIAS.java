// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   BIAS.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class BIAS extends IndicatorBase {

    private final int m_iParam[] = {
        6, 12
    };

    public BIAS(IndicatorPos pos, int iPrecision) {
        super(pos, iPrecision);
        super.m_strIndicatorName = "BIAS";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = new String[m_iParam.length];
        for(int i = 0; i < m_iParam.length; i++)
            super.m_strParamName[i] = "BIAS" + (i + 1);

        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        for(int i = 0; i < m_iParam.length; i++)
            GetValueMaxMin(super.m_data[i], m_iParam[i] - 1);

        DrawCoordinate(g, 2);
        for(int i = 0; i < m_iParam.length; i++)
            DrawLine(g, super.m_data[i], m_iParam[i] - 1, Application.rhColor.clIndicator[i]);

    }

    public void Calculate() {
        super.m_data = new float[m_iParam.length][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        for(int i = 0; i < 2; i++)
            if(m_iParam[i] <= super.m_kData.length && m_iParam[i] > 0) {
                super.m_data[i] = new float[super.m_kData.length];
                GetBIAS(m_iParam[i], super.m_data[i]);
            }

    }

    private void GetBIAS(int n, float bias[]) {
        if(bias == null)
            return;
        AverageClose(n, bias);
        bias[n - 2] = 0.0F;
        for(int i = n - 1; i < super.m_kData.length; i++)
            if(bias[i] != 0.0F)
                bias[i] = ((super.m_kData[i].closePrice - bias[i]) / bias[i]) * 100F;
            else
                bias[i] = bias[i - 1];

    }
}
