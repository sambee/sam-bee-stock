// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   W_R.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class W_R extends IndicatorBase {

    private final int m_iParam[] = {
        14, 6
    };

    public W_R(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "W%R";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = new String[m_iParam.length];
        for(int i = 0; i < m_iParam.length; i++)
            super.m_strParamName[i] = "WR" + (i + 1);

        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -1E+038F;
        super.m_min = 1E+038F;
        for(int i = 0; i < m_iParam.length; i++)
            GetValueMaxMin(super.m_data[i], m_iParam[i]);

        DrawCoordinate(g, 2);
        for(int i = 0; i < m_iParam.length; i++)
            DrawLine(g, super.m_data[i], m_iParam[i], Application.rhColor.clIndicator[i]);

    }

    public void Calculate() {
        super.m_data = new float[m_iParam.length][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        for(int i = 0; i < m_iParam.length; i++) {
            super.m_data[i] = new float[super.m_kData.length];
            GetW_R(m_iParam[i], super.m_data[i]);
        }

    }

    private void GetW_R(int n, float wms[]) {
        if(n > super.m_kData.length || n < 1)
            return;
        for(int i = n - 1; i < super.m_kData.length; i++) {
            float maxhigh = super.m_kData[i].highPrice;
            float minlow = super.m_kData[i].lowPrice;
            for(int j = i - 1; j > i - n; j--) {
                maxhigh = Math.max(maxhigh, super.m_kData[j].highPrice);
                minlow = Math.min(minlow, super.m_kData[j].lowPrice);
            }

            if(maxhigh - minlow == 0.0F) {
                if(i - 1 == 0)
                    wms[i] = -50F;
                else
                    wms[i] = wms[i - 1];
            } else {
                wms[i] = (-(maxhigh - super.m_kData[i].closePrice) / (maxhigh - minlow)) * 100F;
            }
        }

    }
}
