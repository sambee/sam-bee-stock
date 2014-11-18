// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   ROC.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class ROC extends IndicatorBase {

    private final int m_iParam[] = {
        12, 6
    };

    public ROC(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "ROC";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "", "ROCMA"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        GetValueMaxMin(super.m_data[0], m_iParam[0] + 1);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[1] + 1, Application.rhColor.clIndicator[0]);
        DrawLine(g, super.m_data[1], m_iParam[0] + m_iParam[1], Application.rhColor.clIndicator[1]);
    }

    public void Calculate() {
        super.m_data = new float[2][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        int n = m_iParam[0];
        int m = m_iParam[1];
        if(super.m_kData.length < n || n < 1)
            return;
        super.m_data[0] = new float[super.m_kData.length];
        super.m_data[1] = new float[super.m_kData.length];
        float roc[] = super.m_data[0];
        roc[n - 1] = 0.0F;
        for(int i = n; i < super.m_kData.length; i++)
            if(super.m_kData[i - n].closePrice == 0.0F)
                roc[i] = roc[i - 1];
            else
                roc[i] = (super.m_kData[i].closePrice / super.m_kData[i - n].closePrice - 1.0F) * 100F;

        IndicatorBase.Average(1, super.m_kData.length, m, roc, super.m_data[1]);
    }
}
