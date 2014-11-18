// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   WVAD.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class WVAD extends IndicatorBase {

    private final int m_iParam[] = {
        6
    };

    public WVAD(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "WVAD";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            ""
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -1E+038F;
        super.m_min = 1E+038F;
        GetValueMaxMin(super.m_data[0], m_iParam[0]);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[0], Application.rhColor.clIndicator[0]);
    }

    public void Calculate() {
        super.m_data = new float[1][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        int n = m_iParam[0];
        if(n > super.m_kData.length || n < 1)
            return;
        super.m_data[0] = new float[super.m_kData.length];
        float wvad[] = super.m_data[0];
        for(int i = 0; i < super.m_kData.length; i++) {
            float value = super.m_kData[i].highPrice - super.m_kData[i].lowPrice;
            if(value > 0.0F)
                wvad[i] = (((super.m_kData[i].closePrice - super.m_kData[i].openPrice) / value) * (float)super.m_kData[i].totalAmount) / 1000F;
            else
                wvad[i] = (float)super.m_kData[i].totalAmount / 1000F;
        }

        IndicatorBase.Average(0, super.m_kData.length, n, super.m_data[0], super.m_data[0]);
        for(int i = 0; i < super.m_kData.length; i++)
            super.m_data[0][i] *= n;

    }
}
