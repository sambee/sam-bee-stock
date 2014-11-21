// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   CCI.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class CCI extends IndicatorBase {

    private final int m_iParam[] = {
        21
    };

    public CCI(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "CCI";
        super.m_strParamName = (new String[] {
            ""
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        GetValueMaxMin(super.m_data[0], m_iParam[0] - 1);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[0] - 1, Application.rhColor.clIndicator[0]);
    }

    public void Calculate() {
        super.m_data = new float[2][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        int n = m_iParam[0];
        if(n > super.m_kData.length || n < 2)
            return;
        super.m_data[0] = new float[super.m_kData.length];
        super.m_data[1] = new float[super.m_kData.length];
        float cci[] = super.m_data[0];
        float ma[] = super.m_data[1];
        double sum = 0.0D;
        for(int i = 0; i < n - 1; i++)
            sum += (super.m_kData[i].highPrice + super.m_kData[i].lowPrice + super.m_kData[i].closePrice) / 3F;

        float prec = 0.0F;
        for(int i = n - 1; i < super.m_kData.length; i++) {
            sum -= prec;
            sum += (super.m_kData[i].highPrice + super.m_kData[i].lowPrice + super.m_kData[i].closePrice) / 3F;
            ma[i] = (float)(sum / (double)n);
            prec = (super.m_kData[(i - n) + 1].highPrice + super.m_kData[(i - n) + 1].lowPrice + super.m_kData[(i - n) + 1].closePrice) / 3F;
        }

        cci[n - 2] = 0.0F;
        for(int i = n - 1; i < super.m_kData.length; i++) {
            sum = 0.0D;
            for(int j = (i - n) + 1; j <= i; j++)
                sum += Math.abs((super.m_kData[j].highPrice + super.m_kData[j].lowPrice + super.m_kData[j].closePrice) / 3F - ma[i]);

            if(sum == 0.0D)
                cci[i] = cci[i - 1];
            else
                cci[i] = (float)((double)((super.m_kData[i].highPrice + super.m_kData[i].lowPrice + super.m_kData[i].closePrice) / 3F - ma[i]) / ((0.014999999999999999D * sum) / (double)n));
        }

    }
}
