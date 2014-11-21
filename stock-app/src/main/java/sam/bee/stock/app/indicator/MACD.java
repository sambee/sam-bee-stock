// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   MACD.java

package sam.bee.stock.app.indicator;


import java.awt.*;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class MACD extends IndicatorBase {

    private final int m_iParam[] = {
        12, 26, 9
    };

    public MACD(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "MACD(" + m_iParam[0] + "," + m_iParam[1] + "," + m_iParam[2] + ")";
        super.m_strParamName = (new String[] {
            "DIF", "DEA", "MACD"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        if(data == null || data.length == 0) {
            return;
        } else {
            super.Paint(g, rc, data);
            Calculate();
            super.m_max = 0.0F;
            super.m_min = 10000F;
            GetValueMaxMin(super.m_data[0], m_iParam[1] - 1);
            GetValueMaxMin(super.m_data[1], (m_iParam[1] + m_iParam[2]) - 2);
            GetValueMaxMin(super.m_data[2], (m_iParam[1] + m_iParam[2]) - 2);
            DrawCoordinate(g, 2);
            DrawLine(g, super.m_data[0], m_iParam[1] - 1, Application.rhColor.clIndicator[0]);
            DrawLine(g, super.m_data[1], (m_iParam[1] + m_iParam[2]) - 2, Application.rhColor.clIndicator[1]);
            DrawVertLine(g, super.m_data[2], (m_iParam[1] + m_iParam[2]) - 2, Application.rhColor.clIncrease, Application.rhColor.clDecrease);
            return;
        }
    }

    public void Calculate() {
        super.m_data = new float[3][super.m_kData.length];
        for(int i = 0; i < 3; i++)
            if(m_iParam[i] > super.m_kData.length || m_iParam[i] < 1)
                return;

        float dif[] = super.m_data[0];
        float macd[] = super.m_data[1];
        float d_m[] = super.m_data[2];
        float di = 0.0F;
        float a = 0.0F;
        float b = 0.0F;
        float para[] = new float[3];
        float sum[] = new float[3];
        int n[] = new int[3];
        for(int i = 0; i < 3; i++) {
            n[i] = m_iParam[i];
            para[i] = 2.0F / (float)(n[i] + 1);
            sum[i] = 0.0F;
        }

        for(int i = 0; i < super.m_kData.length; i++) {
            di = super.m_kData[i].closePrice;
            if(i < n[0]) {
                sum[0] += di;
                a = i != n[0] - 1 ? 0.0F : sum[0] / (float)n[0];
            } else {
                a = (di - a) * para[0] + a;
            }
            if(i < n[1]) {
                sum[1] += di;
                b = i != n[1] - 1 ? 0.0F : sum[1] / (float)n[1];
            } else {
                b = (di - b) * para[1] + b;
            }
            dif[i] = i < n[0] - 1 || i < n[1] ? 0.0F : a - b;
            if(i < n[1] + n[2]) {
                sum[2] += dif[i];
                macd[i] = i != (n[1] + n[2]) - 1 ? 0.0F : sum[2] / (float)n[2];
            } else {
                macd[i] = (float)((double)(dif[i] - macd[i - 1]) * 0.20000000000000001D) + macd[i - 1];
            }
            d_m[i] = dif[i] - macd[i];
        }

    }

    private void DrawVertLine(Graphics g, float data[], int iFirst, Color color1, Color color2) {
        if(data == null)
            return;
        if(iFirst > super.m_kData.length)
            return;
        if(super.m_max - super.m_min <= 0.0F || super.m_rc.height - super.m_iTextH <= 0)
            return;
        int begin = super.m_pos.m_Begin > iFirst ? super.m_pos.m_Begin : iFirst;
        int end = super.m_pos.m_End;
        if(begin > end)
            return;
        float valuex = (float)super.m_rc.x + super.m_pos.m_Ratio / 2.0F + (float)(begin - super.m_pos.m_Begin) * super.m_pos.m_Ratio;
        float valuey = (super.m_max - super.m_min) / (float)(super.m_rc.height - super.m_iTextH);
        int zero = super.m_rc.y + super.m_iTextH + (int)((super.m_max - 0.0F) / valuey);
        g.setColor(color1);
        for(int i = begin; i <= end; i++) {
            if(data[i] > 0.0F)
                g.setColor(color1);
            else
                g.setColor(color2);
            g.drawLine((int)valuex, zero, (int)valuex, super.m_rc.y + super.m_iTextH + (int)((super.m_max - data[i]) / valuey));
            valuex += super.m_pos.m_Ratio;
        }

    }
}
