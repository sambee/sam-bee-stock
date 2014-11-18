// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   IndicatorBase.java

package sam.bee.stock.app.indicator;


import java.awt.*;

import sam.bee.stock.gui.Common;
import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorPos

public abstract class IndicatorBase {

    String m_strIndicatorName;
    protected Rectangle m_rc;
    protected KLineData m_kData[];
    protected IndicatorPos m_pos;
    protected float m_max;
    protected float m_min;
    protected int m_iTextH;
    protected float m_data[][];
    protected String m_strParamName[];
    protected int m_iPrecision;
    
    public static final String INDICATOR_NAME[][] = {
        {
            "ASI", "累计振荡指标"
        }, {
            "BIAS", "乖离率"
        }, {
            "BRAR", "BRAR能量指标"
        }, {
            "BOLL", "布林线"
        }, {
            "CCI", "顺势指标"
        }, {
            "CR", "CR能量指标"
        }, {
            "DMA", "平均线差"
        }, {
            "DMI", "趋向指标"
        }, {
            "EMV", "简易波动指标"
        }, {
            "EXPMA", "指数平均数"
        }, {
            "KDJ", "随机指标"
        }, {
            "MACD", "平滑异同移动平均线"
        }, {
            "MIKE", "麦克指标"
        }, {
            "OBV", "能量潮"
        }, {
            "PSY", "心理线"
        }, {
            "ROC", "变动速率"
        }, {
            "RSI", "相对强弱指标"
        }, {
            "SAR", "抛物线指标"
        }, {
            "TRIX", "三重指数平均"
        }, {
            "VR", "成交量变异率"
        }, {
            "W%R", "威廉指标"
        }, {
            "WVAD", "威廉变异离散量"
        }, {
            "ORDER", "订货量"
        }
    };

    public IndicatorBase(IndicatorPos pos, int iPrecision) {
        m_iTextH = 12;
        m_pos = pos;
        m_iPrecision = iPrecision;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        m_rc = rc;
        m_kData = data;
    }

    public abstract void Calculate();

    public void DrawTitle(Graphics g, int iIndex) {
        g.setFont(new Font("宋体", 0, 12));
        FontMetrics fm = g.getFontMetrics();
        g.clearRect(m_rc.x + 1, m_rc.y + 1, m_rc.width - 1, m_iTextH - 1);
        int x = m_rc.x + 1;
        int y = m_rc.y + fm.getAscent();
        g.setColor(Application.rhColor.clItem);
        g.drawString(m_strIndicatorName, x, y);
        x += fm.stringWidth(m_strIndicatorName) + m_iTextH;
        if(m_data[0] == null || m_data[0].length == 0)
            return;
        if(iIndex >= m_data[0].length)
            iIndex = m_data[0].length - 1;
        for(int i = 0; i < m_strParamName.length; i++) {
            if(m_data[i] == null)
                continue;
            String str = Common.FloatToString(m_data[i][iIndex], m_iPrecision);
            if(m_strParamName[i].length() > 0)
                str = m_strParamName[i] + ":" + str;
            g.setColor(Application.rhColor.clIndicator[i]);
            if(x + fm.stringWidth(str) > m_rc.x + m_rc.width)
                break;
            g.drawString(str, x, y);
            x += fm.stringWidth(str) + m_iTextH;
        }

    }

    public void DrawCursor(Graphics g1, int i) {
    }

    final void DrawCoordinate(Graphics g, int precision) {
        if(m_max <= m_min)
            return;
        g.setFont(new Font("宋体", 0, 12));
        FontMetrics fm = g.getFontMetrics();
        m_iTextH = fm.getHeight();
        int top = m_rc.y + fm.getHeight();
        if(top >= m_rc.y + m_rc.height)
            return;
        float unit = 100F;
        switch(precision) {
        case 2: // '\002'
            unit = 0.1F;
            break;

        case 3: // '\003'
            unit = 0.01F;
            break;

        default:
            unit = 10F;
            break;
        }
        float minUnit = unit;
        int step[] = {
            2, 5, 2
        };
        int height = (int)((unit * (float)m_rc.height) / (m_max - m_min));
        for(int i = 0; height < fm.getHeight() * 2; i = ++i % 3) {
            unit *= step[i];
            if(i == 1)
                unit /= 2.0F;
            height = (int)((unit * (float)m_rc.height) / (m_max - m_min));
        }

        float max = (float)(int)(m_max / minUnit / (unit / minUnit)) * unit;
        for(float value = max; value <= m_max && value >= m_min;) {
            int y = (int)((float)top + ((m_max - value) * (float)(m_rc.height - fm.getHeight())) / (m_max - m_min));
            if(y < top + fm.getHeight() || y > m_rc.y + m_rc.height) {
                value -= unit;
            } else {
                g.setColor(Application.rhColor.clGrid);
                g.drawLine(m_rc.x - 3, y, m_rc.x, y);
                Common.DrawDotLine(g, m_rc.x, y, m_rc.x + m_rc.width, y);
                String text = Common.FloatToString(value, precision);
                int xx = m_rc.x - 3 - fm.stringWidth(text);
                int yy = (y - fm.getHeight() / 2) + fm.getAscent();
                g.setColor(Application.rhColor.clNumber);
                g.drawString(text, xx, yy);
                value -= unit;
            }
        }

    }

    final void GetValueMaxMin(float data[], int iFirst) {
        if(data == null)
            return;
        int begin = m_pos.m_Begin > iFirst ? m_pos.m_Begin : iFirst;
        int end = m_pos.m_End;
        for(int i = begin; i <= end; i++) {
            if(i >= data.length)
                break;
            if(data[i] > m_max)
                m_max = data[i];
            if(data[i] < m_min)
                m_min = data[i];
        }

    }

    final void DrawLine(Graphics g, float data[], int iFirst, Color color) {
        if(data == null)
            return;
        if(iFirst > m_kData.length)
            return;
        if(m_max - m_min <= 0.0F || m_rc.height - m_iTextH <= 0)
            return;
        int begin = m_pos.m_Begin > iFirst ? m_pos.m_Begin : iFirst;
        int end = m_pos.m_End;
        if(begin > end)
            return;
        float valuex = (float)m_rc.x + m_pos.m_Ratio / 2.0F + (float)(begin - m_pos.m_Begin) * m_pos.m_Ratio;
        float valuey = (m_max - m_min) / (float)(m_rc.height - m_iTextH);
        g.setColor(color);
        for(int i = begin + 1; i <= end; i++) {
            if(i >= data.length)
                break;
            if(m_max >= data[i - 1] && data[i - 1] >= m_min && m_max >= data[i] && data[i] >= m_min)
                g.drawLine((int)valuex, m_rc.y + m_iTextH + (int)((m_max - data[i - 1]) / valuey), (int)(valuex + m_pos.m_Ratio), m_rc.y + m_iTextH + (int)((m_max - data[i]) / valuey));
            valuex += m_pos.m_Ratio;
        }

    }

    protected void AverageClose(int iParam, float data[]) {
        if(m_kData == null || m_kData.length == 0)
            return;
        int n = iParam;
        if(n > m_kData.length || n < 1)
            return;
        float preClose = 0.0F;
        double sum = 0.0D;
        for(int i = 0; i < n - 1; i++)
            sum += m_kData[i].closePrice;

        for(int i = n - 1; i < m_kData.length; i++) {
            sum -= preClose;
            sum += m_kData[i].closePrice;
            data[i] = (float)(sum / (double)n);
            preClose = m_kData[(i - n) + 1].closePrice;
        }

    }

    protected void AverageHigh(int iParam, float data[]) {
        if(m_kData == null || m_kData.length == 0)
            return;
        int n = iParam;
        if(n > m_kData.length || n < 1)
            return;
        float preHigh = 0.0F;
        double sum = 0.0D;
        for(int i = 0; i < n - 1; i++)
            sum += m_kData[i].highPrice;

        for(int i = n - 1; i < m_kData.length; i++) {
            sum -= preHigh;
            sum += m_kData[i].highPrice;
            data[i] = (float)(sum / (double)n);
            preHigh = m_kData[(i - n) + 1].highPrice;
        }

    }

    protected void AverageLow(int iParam, float data[]) {
        if(m_kData == null || m_kData.length == 0)
            return;
        int n = iParam;
        if(n > m_kData.length || n < 1)
            return;
        float preLow = 0.0F;
        double sum = 0.0D;
        for(int i = 0; i < n - 1; i++)
            sum += m_kData[i].lowPrice;

        for(int i = n - 1; i < m_kData.length; i++) {
            sum -= preLow;
            sum += m_kData[i].lowPrice;
            data[i] = (float)(sum / (double)n);
            preLow = m_kData[(i - n) + 1].lowPrice;
        }

    }

    static void Average(int begin, int iCount, int n, float source[], float destination[]) {
        if(source == null || destination == null)
            return;
        if(n > iCount - begin || n < 1)
            return;
        float prevalue = 0.0F;
        double sum = 0.0D;
        for(int i = iCount - 1; i > iCount - n; i--)
            sum += source[i];

        for(int i = iCount - 1; i >= (begin + n) - 1; i--) {
            sum -= prevalue;
            sum += source[(i - n) + 1];
            prevalue = source[i];
            destination[i] = (float)(sum / (double)n);
        }

    }

}
