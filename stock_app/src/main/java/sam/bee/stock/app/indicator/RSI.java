// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   RSI.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class RSI extends IndicatorBase {

    private final int m_iParam[] = {
        6, 12, 24
    };

    public RSI(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "RSI(" + m_iParam[0] + "," + m_iParam[1] + "," + m_iParam[2] + ")";
        super.m_strParamName = (new String[] {
            "RSI1", "RSI2", "RSI3"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        if(data == null || data.length == 0)
            return;
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = 0.0F;
        super.m_min = 10000F;
        for(int i = 0; i < 3; i++)
            GetValueMaxMin(super.m_data[i], m_iParam[i]);

        DrawCoordinate(g, 2);
        for(int i = 0; i < 3; i++)
            DrawLine(g, super.m_data[i], m_iParam[i], Application.rhColor.clIndicator[i]);

    }

    public void Calculate() {
        super.m_data = new float[3][super.m_kData.length];
        if(super.m_kData == null || super.m_kData.length == 0)
            return;
        for(int i = 0; i < 3; i++)
            GetRSI(m_iParam[i], super.m_data[i]);

    }

    private void GetRSI(int n, float rsi[]) {
        if(n > super.m_kData.length || n < 1)
            return;
        float up = 0.0F;
        float down = 0.0F;
        for(int i = 1; i < n; i++)
            if(super.m_kData[i].closePrice > super.m_kData[i - 1].closePrice)
                up += super.m_kData[i].closePrice - super.m_kData[i - 1].closePrice;
            else
                down += super.m_kData[i - 1].closePrice - super.m_kData[i].closePrice;

        if(up + down == 0.0F)
            rsi[n - 1] = 50F;
        else
            rsi[n - 1] = (up / (up + down)) * 100F;
        float predown;
        float preup = predown = 0.0F;
        for(int i = n; i < super.m_kData.length; i++) {
            up -= preup;
            down -= predown;
            if(super.m_kData[i].closePrice > super.m_kData[i - 1].closePrice)
                up += super.m_kData[i].closePrice - super.m_kData[i - 1].closePrice;
            else
                down += super.m_kData[i - 1].closePrice - super.m_kData[i].closePrice;
            if(up + down == 0.0F)
                rsi[i] = rsi[i - 1];
            else
                rsi[i] = (up / (up + down)) * 100F;
            preup = predown = 0.0F;
            if(super.m_kData[(i - n) + 1].closePrice > super.m_kData[i - n].closePrice)
                preup = super.m_kData[(i - n) + 1].closePrice - super.m_kData[i - n].closePrice;
            else
                predown = super.m_kData[i - n].closePrice - super.m_kData[(i - n) + 1].closePrice;
        }

    }
}
