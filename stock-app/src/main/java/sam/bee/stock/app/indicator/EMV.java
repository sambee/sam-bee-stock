// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   EMV.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class EMV extends IndicatorBase {

    private final int m_iParam[] = {
        14, 9
    };

    public EMV(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "EMV";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "", "EMVMA"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        if(data == null || data.length == 0)
            return;
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        GetValueMaxMin(super.m_data[0], m_iParam[0]);
        if(m_iParam[1] > 0 && m_iParam[1] <= super.m_kData.length)
            GetValueMaxMin(super.m_data[1], (m_iParam[0] + m_iParam[1]) - 1);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[0], Application.rhColor.clIndicator[0]);
        if(m_iParam[1] > 0 && m_iParam[1] <= super.m_kData.length)
            DrawLine(g, super.m_data[1], (m_iParam[0] + m_iParam[1]) - 1, Application.rhColor.clIndicator[1]);
    }

    public void Calculate() {
        super.m_data = new float[2][];
        int n1 = m_iParam[0];
        int n2 = m_iParam[1];
        if(super.m_kData == null || n1 > super.m_kData.length || n1 < 1)
            return;
        super.m_data[0] = new float[super.m_kData.length];
        super.m_data[1] = new float[super.m_kData.length];
        float emv[] = super.m_data[0];
        float ma[] = super.m_data[1];
        emv[n1 - 1] = 0.0F;
        for(int i = n1; i < super.m_kData.length; i++) {
            emv[i] = 0.0F;
            if(super.m_kData[i].totalAmount > 0L)
                emv[i] = (((super.m_kData[i].highPrice + super.m_kData[i].lowPrice) - super.m_kData[i - n1].highPrice - super.m_kData[i - n1].lowPrice) / 2.0F) * (super.m_kData[i].highPrice - super.m_kData[i].lowPrice);
        }

        if(n2 <= super.m_kData.length && n2 > 0)
            IndicatorBase.Average(n1, super.m_kData.length, n2, emv, ma);
    }
}
