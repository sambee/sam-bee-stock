// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   VR.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class VR extends IndicatorBase {

    private final int m_iParam[] = {
        25, 5
    };

    public VR(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "VR";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "", "MA"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        GetValueMaxMin(super.m_data[0], m_iParam[0]);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[0], Application.rhColor.clIndicator[0]);
        DrawLine(g, super.m_data[1], (m_iParam[0] + m_iParam[1]) - 1, Application.rhColor.clIndicator[1]);
    }

    public void Calculate() {
        super.m_data = new float[2][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        super.m_data[0] = new float[super.m_kData.length];
        super.m_data[1] = new float[super.m_kData.length];
        int n1 = m_iParam[0];
        int n2 = m_iParam[1];
        float vr[] = super.m_data[0];
        float ma[] = super.m_data[1];
        float down;
        float middle;
        float up = down = middle = 0.0F;
        if(super.m_kData.length < n1)
            return;
        vr[n1 - 2] = 100F;
        for(int i = 1; i < n1; i++)
            if(super.m_kData[i].closePrice == super.m_kData[i - 1].closePrice)
                middle += super.m_kData[i].totalAmount;
            else
            if(super.m_kData[i].closePrice > super.m_kData[i - 1].closePrice)
                up += super.m_kData[i].totalAmount;
            else
                down += super.m_kData[i].totalAmount;

        if(down + middle / 2.0F == 0.0F)
            vr[n1 - 1] = vr[n1 - 2];
        else
            vr[n1 - 1] = (up + middle / 2.0F) / (down + middle / 2.0F);
        for(int i = n1; i < super.m_kData.length; i++) {
            if(super.m_kData[i].closePrice == super.m_kData[i - 1].closePrice)
                middle += super.m_kData[i].totalAmount;
            else
            if(super.m_kData[i].closePrice > super.m_kData[i - 1].closePrice)
                up += super.m_kData[i].totalAmount;
            else
                down += super.m_kData[i].totalAmount;
            if(down + middle / 2.0F == 0.0F)
                vr[i] = vr[i - 1];
            else
                vr[i] = ((up + middle / 2.0F) / (down + middle / 2.0F)) * 100F;
            if(super.m_kData[(i - n1) + 1].closePrice == super.m_kData[i - n1].closePrice)
                middle -= super.m_kData[(i - n1) + 1].totalAmount;
            else
            if(super.m_kData[(i - n1) + 1].closePrice > super.m_kData[i - n1].closePrice)
                up -= super.m_kData[(i - n1) + 1].totalAmount;
            else
                down -= super.m_kData[(i - n1) + 1].totalAmount;
        }

        IndicatorBase.Average(n1, super.m_kData.length, n2, vr, ma);
    }
}
