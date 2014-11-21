// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   TRIX.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class TRIX extends IndicatorBase {

    private final int m_iParam[] = {
        12, 9
    };

    public TRIX(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "TRIX";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "", ""
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        GetValueMaxMin(super.m_data[0], m_iParam[0] * 3 - 3);
        GetValueMaxMin(super.m_data[1], ((m_iParam[0] * 3 - 3) + m_iParam[1]) - 1);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[0] * 3 - 3, Application.rhColor.clIndicator[0]);
        DrawLine(g, super.m_data[1], ((m_iParam[0] * 3 - 3) + m_iParam[1]) - 1, Application.rhColor.clIndicator[1]);
    }

    public void Calculate() {
        super.m_data = new float[2][];
        if(super.m_kData == null || super.m_kData.length <= 0) {
            return;
        } else {
            super.m_data[0] = new float[super.m_kData.length];
            super.m_data[1] = new float[super.m_kData.length];
            GetEXPMA(m_iParam[0], super.m_data[0]);
            IndicatorBase.Average(m_iParam[0] - 1, super.m_kData.length, m_iParam[0], super.m_data[0], super.m_data[1]);
            IndicatorBase.Average(m_iParam[0] * 2 - 2, super.m_kData.length, m_iParam[0], super.m_data[1], super.m_data[0]);
            IndicatorBase.Average(m_iParam[0] * 3 - 3, super.m_kData.length, m_iParam[1], super.m_data[0], super.m_data[1]);
            return;
        }
    }

    private void GetEXPMA(int n, float expma[]) {
        float xs = 2.0F / (float)(n + 1);
        expma[0] = super.m_kData[0].closePrice;
        for(int i = 1; i < super.m_kData.length; i++)
            expma[i] = (super.m_kData[i].closePrice - expma[i - 1]) * xs + expma[i - 1];

    }
}
