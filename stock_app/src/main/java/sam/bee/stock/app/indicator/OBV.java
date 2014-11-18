// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   OBV.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class OBV extends IndicatorBase {

    private final int m_iParam[] = {
        12
    };

    public OBV(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "OBV";
        super.m_strParamName = (new String[] {
            "", "MA" + m_iParam[0]
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -1E+038F;
        super.m_min = 1E+038F;
        GetValueMaxMin(super.m_data[0], 0);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], 0, Application.rhColor.clIndicator[0]);
        DrawLine(g, super.m_data[1], m_iParam[0], Application.rhColor.clIndicator[1]);
    }

    public void Calculate() {
        super.m_data = new float[2][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        int n = m_iParam[0];
        super.m_data[0] = new float[super.m_kData.length];
        super.m_data[1] = new float[super.m_kData.length];
        float obv[] = super.m_data[0];
        obv[0] = 0.0F;
        for(int i = 1; i < super.m_kData.length; i++)
            if(super.m_kData[i].closePrice > super.m_kData[i - 1].closePrice)
                obv[i] = obv[i - 1] + (float)(super.m_kData[i].totalAmount / 1000L);
            else
            if(super.m_kData[i].closePrice < super.m_kData[i - 1].closePrice)
                obv[i] = obv[i - 1] - (float)(super.m_kData[i].totalAmount / 1000L);
            else
                obv[i] = obv[i - 1];

        IndicatorBase.Average(1, super.m_kData.length, n, obv, super.m_data[1]);
    }
}
