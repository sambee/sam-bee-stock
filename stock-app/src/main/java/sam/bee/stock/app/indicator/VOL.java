// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   VOL.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class VOL extends IndicatorBase {

    private final int m_iParam[] = {
        5, 10
    };

    public VOL(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "VOL";
        super.m_iPrecision = 0;
        super.m_strParamName = new String[m_iParam.length];
        for(int i = 0; i < m_iParam.length; i++)
            super.m_strParamName[i] = "MA" + m_iParam[i];

    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        GetMaxMin();
        for(int i = 0; i < m_iParam.length; i++)
            GetValueMaxMin(super.m_data[i], m_iParam[i] - 1);

        DrawCoordinate(g, 0);
        DrawVolume(g);
        for(int i = 0; i < m_iParam.length; i++)
            DrawLine(g, super.m_data[i], m_iParam[i] - 1, Application.rhColor.clIndicator[i]);

    }

    public void Calculate() {
        super.m_data = new float[m_iParam.length][];
        for(int i = 0; i < m_iParam.length; i++)
            AverageVolume(i);

        super.m_data = new float[m_iParam.length][];
        for(int i = 0; i < m_iParam.length; i++)
            AverageVolume(i);

    }

    private void GetMaxMin() {
        super.m_max = 0.0F;
        super.m_min = 0.0F;
        for(int i = super.m_pos.m_Begin; i <= super.m_pos.m_End; i++)
            if((float)super.m_kData[i].totalAmount > super.m_max)
                super.m_max = super.m_kData[i].totalAmount;

    }

    private void DrawVolume(Graphics g) {
        if(super.m_max - super.m_min == 0.0F || super.m_rc.height - super.m_iTextH <= 0)
            return;
        int width = super.m_pos.m_Ratio >= 3F ? (int)((super.m_pos.m_Ratio + 1.0F) / 3F) : 0;
        if(width % 2 == 0 && width > 0)
            width--;
        float valuex = (float)super.m_rc.x + super.m_pos.m_Ratio / 2.0F;
        float valuey = (super.m_max - super.m_min) / (float)(super.m_rc.height - super.m_iTextH);
        for(int i = super.m_pos.m_Begin; i <= super.m_pos.m_End; i++) {
            int value = super.m_rc.y + super.m_iTextH + (int)((super.m_max - (float)super.m_kData[i].totalAmount) / valuey);
            if(super.m_kData[i].openPrice > super.m_kData[i].closePrice) {
                g.setColor(Application.rhColor.clKLineDown);
                g.fillRect((int)valuex - width, value, 2 * width + 1, (super.m_rc.y + super.m_rc.height) - value - 1);
            } else
            if(super.m_kData[i].openPrice < super.m_kData[i].closePrice) {
                g.setColor(Application.rhColor.clKLineUp);
                g.drawRect((int)valuex - width, value, 2 * width, (super.m_rc.y + super.m_rc.height) - value - 1);
            } else {
                g.setColor(Application.rhColor.clKLineEqual);
                g.drawRect((int)valuex - width, value, 2 * width, (super.m_rc.y + super.m_rc.height) - value - 1);
            }
            valuex += super.m_pos.m_Ratio;
        }

    }

    private void AverageVolume(int iIndex) {
        if(super.m_kData == null || super.m_kData.length == 0)
            return;
        int n = m_iParam[iIndex];
        if(n > super.m_kData.length || n < 1)
            return;
        super.m_data[iIndex] = new float[super.m_kData.length];
        float data[] = super.m_data[iIndex];
        float prevolume = 0.0F;
        double sum = 0.0D;
        for(int i = 0; i < n - 1; i++)
            sum += super.m_kData[i].totalAmount;

        for(int i = n - 1; i < super.m_kData.length; i++) {
            sum -= prevolume;
            sum += super.m_kData[i].totalAmount;
            data[i] = (float)(sum / (double)n);
            prevolume = super.m_kData[(i - n) + 1].totalAmount;
        }

    }
}
