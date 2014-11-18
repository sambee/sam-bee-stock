// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   KLine.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class KLine extends IndicatorBase {

    public static final int LineType_K = 0;
    public static final int LineType_USA = 1;
    public static final int LineType_POLY = 2;
    int m_iLineType;

    public KLine(IndicatorPos pos, int iLineType, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "KLine";
        m_iLineType = iLineType;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        GetMaxMin();
        DrawCoordinate(g, super.m_iPrecision);
        switch(m_iLineType) {
        case 2: // '\002'
            DrawPolyLine(g);
            break;

        case 1: // '\001'
            drawUSA(g);
            break;

        default:
            DrawKLine(g);
            break;
        }
    }

    public void Calculate() {
    }

    protected void GetMaxMin() {
        if(super.m_pos.m_Begin > super.m_pos.m_End) {
            super.m_max = 0.0F;
            super.m_min = 0.0F;
            return;
        }
        super.m_max = 0.0F;
        super.m_min = 1E+008F;
        for(int i = super.m_pos.m_Begin; i <= super.m_pos.m_End; i++)
            if(m_iLineType == 0 || m_iLineType == 1) {
                if(super.m_kData[i].highPrice > 0.0F) {
                    if(super.m_kData[i].highPrice > super.m_max)
                        super.m_max = super.m_kData[i].highPrice;
                    if(super.m_kData[i].lowPrice < super.m_min)
                        super.m_min = super.m_kData[i].lowPrice;
                }
            } else
            if(super.m_kData[i].closePrice > 0.0F) {
                if(super.m_kData[i].closePrice > super.m_max)
                    super.m_max = super.m_kData[i].closePrice;
                if(super.m_kData[i].closePrice < super.m_min)
                    super.m_min = super.m_kData[i].closePrice;
            }

    }

    private void DrawKLine(Graphics g) {
        int begin = super.m_pos.m_Begin;
        int end = super.m_pos.m_End;
        if(super.m_max - super.m_min == 0.0F || super.m_rc.height - super.m_iTextH <= 0)
            return;
        int width = super.m_pos.m_Ratio >= 3F ? (int)((super.m_pos.m_Ratio + 1.0F) / 3F) : 0;
        if(width % 2 == 0 && width > 0)
            width--;
        float valuex = (float)super.m_rc.x + super.m_pos.m_Ratio / 2.0F;
        float valuey = (super.m_max - super.m_min) / (float)(super.m_rc.height - super.m_iTextH);
        for(int i = begin; i <= end; i++) {
            int open = super.m_rc.y + super.m_iTextH + (int)((super.m_max - super.m_kData[i].openPrice) / valuey);
            int high = super.m_rc.y + super.m_iTextH + (int)((super.m_max - super.m_kData[i].highPrice) / valuey);
            int low = super.m_rc.y + super.m_iTextH + (int)((super.m_max - super.m_kData[i].lowPrice) / valuey);
            int close = super.m_rc.y + super.m_iTextH + (int)((super.m_max - super.m_kData[i].closePrice) / valuey);
            if(super.m_kData[i].openPrice == super.m_kData[i].closePrice) {
                g.setColor(Application.rhColor.clKLineEqual);
                g.drawLine((int)valuex - width, open, (int)valuex + width, close);
                g.drawLine((int)valuex, high, (int)valuex, low);
            } else
            if(super.m_kData[i].openPrice > super.m_kData[i].closePrice) {
                g.setColor(Application.rhColor.clKLineDown);
                g.drawLine((int)valuex, high, (int)valuex, low);
                g.fillRect((int)valuex - width, open, 2 * width + 1, close - open);
            } else {
                g.setColor(Application.rhColor.clKLineUp);
                g.drawLine((int)valuex, high, (int)valuex, close);
                g.drawLine((int)valuex, open, (int)valuex, low);
                g.drawRect((int)valuex - width, close, 2 * width, open - close);
            }
            valuex += super.m_pos.m_Ratio;
        }

    }

    private void drawUSA(Graphics g) {
        int begin = super.m_pos.m_Begin;
        int end = super.m_pos.m_End;
        if(super.m_max - super.m_min == 0.0F || super.m_rc.height - super.m_iTextH <= 0)
            return;
        int width = super.m_pos.m_Ratio >= 3F ? (int)((super.m_pos.m_Ratio + 1.0F) / 3F) : 0;
        if(width % 2 == 0 && width > 0)
            width--;
        float valuex = (float)super.m_rc.x + super.m_pos.m_Ratio / 2.0F;
        float valuey = (super.m_max - super.m_min) / (float)(super.m_rc.height - super.m_iTextH);
        for(int i = begin; i <= end; i++) {
            int open = super.m_rc.y + super.m_iTextH + (int)((super.m_max - super.m_kData[i].openPrice) / valuey);
            int high = super.m_rc.y + super.m_iTextH + (int)((super.m_max - super.m_kData[i].highPrice) / valuey);
            int low = super.m_rc.y + super.m_iTextH + (int)((super.m_max - super.m_kData[i].lowPrice) / valuey);
            int close = super.m_rc.y + super.m_iTextH + (int)((super.m_max - super.m_kData[i].closePrice) / valuey);
            g.setColor(Application.rhColor.clUSALine);
            g.drawLine((int)valuex, high, (int)valuex, low);
            g.drawLine((int)valuex - width, open, (int)valuex, open);
            g.drawLine((int)valuex + width + 1, close, (int)valuex, close);
            valuex += super.m_pos.m_Ratio;
        }

    }

    private void DrawPolyLine(Graphics g) {
        int begin = super.m_pos.m_Begin;
        int end = super.m_pos.m_End;
        if(super.m_max - super.m_min == 0.0F || super.m_rc.height - super.m_iTextH <= 0)
            return;
        int width = super.m_pos.m_Ratio >= 3F ? (int)((super.m_pos.m_Ratio + 1.0F) / 3F) : 0;
        if(width % 2 == 0 && width > 0)
            width--;
        float valuex = (float)super.m_rc.x + super.m_pos.m_Ratio / 2.0F;
        float valuey = (super.m_max - super.m_min) / (float)(super.m_rc.height - super.m_iTextH);
        g.setColor(Application.rhColor.clPolyLine);
        int oldx = -1;
        int oldy = -1;
        for(int i = begin; i <= end; i++) {
            int close = super.m_rc.y + super.m_iTextH + (int)((super.m_max - super.m_kData[i].closePrice) / valuey);
            if(oldx != -1 && oldy != -1) {
                g.drawLine(oldx, oldy, (int)valuex, close);
                if((float)(oldy - close) > valuex - (float)oldx) {
                    g.drawLine(oldx - 1, oldy, (int)valuex - 1, close);
                    g.drawLine(oldx + 1, oldy, (int)valuex + 1, close);
                } else
                if((float)(close - oldy) > valuex - (float)oldx) {
                    g.drawLine(oldx - 1, oldy, (int)valuex - 1, close);
                    g.drawLine(oldx + 1, oldy, (int)valuex + 1, close);
                } else {
                    g.drawLine(oldx, oldy - 1, (int)valuex, close - 1);
                    g.drawLine(oldx, oldy + 1, (int)valuex, close + 1);
                }
            }
            oldx = (int)valuex;
            oldy = close;
            valuex += super.m_pos.m_Ratio;
        }

    }

    public void DrawCursor(Graphics g, int iPos) {
        int iIndex = super.m_pos.m_Begin + iPos;
        int y = (int)((float)(super.m_rc.y + super.m_iTextH) + ((super.m_max - super.m_kData[iIndex].closePrice) * (float)(super.m_rc.height - super.m_iTextH)) / (super.m_max - super.m_min));
        g.drawLine(super.m_rc.x, y, super.m_rc.x + super.m_rc.width, y);
    }
}
