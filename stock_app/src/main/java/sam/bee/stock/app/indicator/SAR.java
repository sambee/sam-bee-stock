// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   SAR.java

package sam.bee.stock.app.indicator;


import java.awt.*;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            KLine, IndicatorBase, IndicatorPos

public class SAR extends KLine {

    private final int m_iParam[] = {
        5
    };
    private final int SAR_UP = 0;
    private final int SAR_DOWN = 1;
    private final int SAR_CUP = 16;
    private final int SAR_CDOWN = 17;

    public SAR(IndicatorPos pos, int iPrecision) {
        super(pos, 0, iPrecision);
        super.m_strIndicatorName = "SAR(" + m_iParam[0] + ")";
        super.m_strParamName = (new String[] {
            ""
        });
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.m_kData = data;
        Calculate();
        super.Paint(g, rc, data);
        Color color[] = {
            Application.rhColor.clIncrease, Application.rhColor.clDecrease, Application.rhColor.clEqual
        };
        DrawSAR(g, m_iParam[0] - 1, super.m_data[0], super.m_data[1], color);
    }

    public void Calculate() {
        int n = m_iParam[0];
        super.m_data = new float[2][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        if(n > super.m_kData.length || n < 3)
            return;
        for(int i = 0; i < 2; i++)
            super.m_data[i] = new float[super.m_kData.length];

        float sar[] = super.m_data[0];
        float sign[] = super.m_data[1];
        float xs = 0.02F;
        if(super.m_kData[n - 1].closePrice < super.m_kData[n - 2].closePrice) {
            if(super.m_kData[n - 2].closePrice <= super.m_kData[n - 3].closePrice)
                sign[n - 1] = 1.0F;
            else
                sign[n - 1] = 17F;
        } else
        if(super.m_kData[n - 1].closePrice > super.m_kData[n - 2].closePrice) {
            if(super.m_kData[n - 2].closePrice >= super.m_kData[n - 3].closePrice)
                sign[n - 1] = 0.0F;
            else
                sign[n - 1] = 16F;
        } else
        if(super.m_kData[n - 2].closePrice < super.m_kData[n - 3].closePrice)
            sign[n - 1] = 1.0F;
        else
        if(super.m_kData[n - 2].closePrice > super.m_kData[n - 3].closePrice)
            sign[n - 1] = 0.0F;
        else
            sign[n - 1] = 16F;
        if(sign[n - 1] == 1.0F || sign[n - 1] == 17F) {
            sar[n - 1] = -1E+036F;
            for(int j = n - 1; j >= 0; j--)
                sar[n - 1] = Math.max(sar[n - 1], super.m_kData[j].highPrice);

        } else {
            sar[n - 1] = 1E+036F;
            for(int j = n - 1; j >= 0; j--)
                sar[n - 1] = Math.min(sar[n - 1], super.m_kData[j].lowPrice);

        }
        for(int i = n; i < super.m_kData.length; i++)
            if(sign[i - 1] == 0.0F || sign[i - 1] == 16F) {
                if(super.m_kData[i].closePrice < sar[i - 1]) {
                    sar[i] = -1E+036F;
                    for(int j = i; j > i - n; j--)
                        sar[i] = Math.max(sar[i], super.m_kData[j].highPrice);

                    sign[i] = 17F;
                    xs = 0.02F;
                } else {
                    sar[i] = sar[i - 1] + xs * (super.m_kData[i - 1].highPrice - sar[i - 1]);
                    xs = xs >= 0.2F ? xs : xs + 0.02F;
                    sign[i] = 0.0F;
                }
            } else
            if(super.m_kData[i].closePrice > sar[i - 1]) {
                sar[i] = 1E+036F;
                for(int j = i; j > i - n; j--)
                    sar[i] = Math.min(sar[i], super.m_kData[j].lowPrice);

                sign[i] = 16F;
                xs = 0.02F;
            } else {
                sar[i] = sar[i - 1] + xs * (super.m_kData[i - 1].lowPrice - sar[i - 1]);
                xs = xs >= 0.2F ? xs : xs + 0.02F;
                sign[i] = 1.0F;
            }

    }

    protected void GetMaxMin() {
        super.GetMaxMin();
        GetValueMaxMin(super.m_data[0], m_iParam[0]);
        if(super.m_rc.height > super.m_iTextH) {
            float temp = super.m_max - super.m_min;
            super.m_max += ((super.m_pos.m_Ratio / 2.0F) * temp) / (float)(super.m_rc.height - super.m_iTextH);
            super.m_min -= ((super.m_pos.m_Ratio / 2.0F) * temp) / (float)(super.m_rc.height - super.m_iTextH);
        }
    }

    private void DrawSAR(Graphics g, int iBegin, float data[], float sign[], Color color[]) {
        if(data == null || sign == null)
            return;
        Rectangle rect = new Rectangle(super.m_rc.x, super.m_rc.y + super.m_iTextH, super.m_rc.width, super.m_rc.height - super.m_iTextH);
        if(super.m_max - super.m_min == 0.0F || rect.height <= 0)
            return;
        int begin = super.m_pos.m_Begin >= iBegin ? super.m_pos.m_Begin : iBegin;
        float valuex = (float)rect.x + (float)(begin - super.m_pos.m_Begin) * super.m_pos.m_Ratio;
        float valuey = (super.m_max - super.m_min) / (float)rect.height;
        for(int i = begin; i <= super.m_pos.m_End; i++) {
            float value = (float)rect.y + (super.m_max - data[i]) / valuey;
            if(sign[i] == 1.0F)
                g.setColor(color[1]);
            else
            if(sign[i] == 0.0F)
                g.setColor(color[0]);
            else
                g.setColor(color[2]);
            int x1 = (int)valuex;
            int y1 = (int)(value - super.m_pos.m_Ratio / 2.0F);
            int x2 = (int)(valuex + super.m_pos.m_Ratio);
            int y2 = (int)(value + super.m_pos.m_Ratio / 2.0F);
            g.drawArc(x1, y1, x2 - x1, y2 - y1, 0, 360);
            valuex += super.m_pos.m_Ratio;
        }

    }
}
