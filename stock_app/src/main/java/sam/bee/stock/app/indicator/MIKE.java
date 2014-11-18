// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   MIKE.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            KLine, IndicatorBase, IndicatorPos

public class MIKE extends KLine {

    private final int m_iParam[] = {
        12
    };

    public MIKE(IndicatorPos pos, int iPrecision) {
        super(pos, 1, iPrecision);
        super.m_strIndicatorName = "MIKE";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "WR", "MR", "SR", "WS", "MS", "SS"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.m_kData = data;
        Calculate();
        super.Paint(g, rc, data);
        for(int i = 0; i < 6; i++)
            DrawLine(g, super.m_data[i], m_iParam[0], Application.rhColor.clIndicator[i / 1]);

    }

    protected void GetMaxMin() {
        super.GetMaxMin();
        for(int i = 0; i < 6; i++)
            GetValueMaxMin(super.m_data[i], m_iParam[0]);

    }

    public void Calculate() {
        super.m_data = new float[6][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        int n = m_iParam[0];
        if(n > super.m_kData.length || n < 1)
            return;
        for(int i = 0; i < 6; i++)
            super.m_data[i] = new float[super.m_kData.length];

        getN_DayLow(n, super.m_data[0]);
        getN_DayHigh(n, super.m_data[1]);
        for(int i = n - 1; i < super.m_kData.length; i++) {
            float close = super.m_kData[i].closePrice;
            float low = super.m_data[0][i];
            float high = super.m_data[1][i];
            float TYP = (close + high + low) / 3F;
            super.m_data[0][i] = TYP + (TYP - low);
            super.m_data[1][i] = TYP + (high - low);
            super.m_data[2][i] = 2.0F * high - low;
            super.m_data[3][i] = TYP - (high - TYP);
            super.m_data[4][i] = TYP - (high - low);
            super.m_data[5][i] = 2.0F * low - high;
        }

    }

    private void getN_DayLow(int iParam, float data[]) {
        if(super.m_kData == null || super.m_kData.length == 0)
            return;
        int n = iParam;
        if(n > super.m_kData.length || n < 1)
            return;
        double temp = 0.0D;
        for(int i = n - 1; i < super.m_kData.length; i++) {
            temp = super.m_kData[(i - n) + 1].lowPrice;
            for(int j = (i - n) + 2; j <= i; j++)
                if(temp > (double)super.m_kData[j].lowPrice)
                    temp = super.m_kData[j].lowPrice;

            data[i] = (float)temp;
        }

    }

    private void getN_DayHigh(int iParam, float data[]) {
        if(super.m_kData == null || super.m_kData.length == 0)
            return;
        int n = iParam;
        if(n > super.m_kData.length || n < 1)
            return;
        double temp = 0.0D;
        for(int i = n - 1; i < super.m_kData.length; i++) {
            temp = super.m_kData[(i - n) + 1].highPrice;
            for(int j = (i - n) + 2; j <= i; j++)
                if(temp < (double)super.m_kData[j].lowPrice)
                    temp = super.m_kData[j].lowPrice;

            data[i] = (float)temp;
        }

    }
}
