// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   DMI.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class DMI extends IndicatorBase {

    private final int m_iParam[] = {
        7, 6, 5
    };

    public DMI(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "DMI";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "+DI", "-DI", "ADX", "ADXR"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        GetValueMaxMin(super.m_data[0], m_iParam[0]);
        GetValueMaxMin(super.m_data[1], m_iParam[0]);
        GetValueMaxMin(super.m_data[2], (m_iParam[0] + m_iParam[1]) - 1);
        GetValueMaxMin(super.m_data[3], (m_iParam[0] + m_iParam[1] + m_iParam[2]) - 1);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[0], Application.rhColor.clIndicator[0]);
        DrawLine(g, super.m_data[1], m_iParam[0], Application.rhColor.clIndicator[1]);
        DrawLine(g, super.m_data[2], (m_iParam[0] + m_iParam[1]) - 1, Application.rhColor.clIndicator[2]);
        DrawLine(g, super.m_data[3], (m_iParam[0] + m_iParam[1] + m_iParam[2]) - 1, Application.rhColor.clIndicator[3]);
    }

    public void Calculate() {
        super.m_data = new float[5][];
        if(super.m_kData == null || super.m_kData.length <= 0)
            return;
        int n1 = m_iParam[0];
        int n2 = m_iParam[1];
        int n3 = m_iParam[2];
        for(int i = 0; i < 5; i++)
            super.m_data[i] = new float[super.m_kData.length];

        float zDI[] = super.m_data[0];
        float fDI[] = super.m_data[1];
        float TR[] = super.m_data[2];
        float ADX[] = super.m_data[2];
        float ADXR[] = super.m_data[3];
        float zDM[] = super.m_data[3];
        float fDM[] = super.m_data[4];
        float DX[] = super.m_data[4];
        if(super.m_kData.length < n1)
            return;
        float a;
        float b;
        float c;
        for(int i = 1; i < super.m_kData.length; i++) {
            a = Math.abs(super.m_kData[i].highPrice - super.m_kData[i].lowPrice);
            b = Math.abs(super.m_kData[i].highPrice - super.m_kData[i - 1].closePrice);
            c = Math.abs(super.m_kData[i].lowPrice - super.m_kData[i - 1].closePrice);
            TR[i] = Math.max(a, Math.max(b, c));
            a = super.m_kData[i].highPrice - super.m_kData[i - 1].highPrice;
            b = super.m_kData[i - 1].lowPrice - super.m_kData[i].lowPrice;
            a = a > 0.0F ? a : 0.0F;
            b = b > 0.0F ? b : 0.0F;
            zDM[i] = 0.0F;
            fDM[i] = 0.0F;
            if(a > b)
                zDM[i] = a;
            else
            if(a < b)
                fDM[i] = b;
        }

        a = b = c = 0.0F;
        for(int i = 1; i < n1; i++) {
            a += TR[i];
            b += zDM[i];
            c += fDM[i];
        }

        float prefDI;
        float prezDI = prefDI = 0.0F;
        for(int i = n1; i < super.m_kData.length; i++) {
            a += TR[i];
            b += zDM[i];
            c += fDM[i];
            zDI[i] = prezDI;
            fDI[i] = prefDI;
            if(a != 0.0F) {
                zDI[i] = (b / a) * 100F;
                fDI[i] = (c / a) * 100F;
            }
            prezDI = zDI[i];
            prefDI = fDI[i];
            int j = (i - n1) + 1;
            a -= TR[j];
            b -= zDM[j];
            c -= fDM[j];
        }

        for(int i = n1; i < super.m_kData.length; i++)
            if(zDI[i] + fDI[i] != 0.0F)
                DX[i] = (Math.abs(zDI[i] - fDI[i]) / Math.abs(zDI[i] + fDI[i])) * 100F;
            else
                DX[i] = 0.0F;

        IndicatorBase.Average(n1, super.m_kData.length, n2, DX, ADX);
        for(int i = (n1 + n2 + n3) - 1; i < super.m_kData.length; i++)
            ADXR[i] = (ADX[i] + ADX[i - n3]) / 2.0F;

    }
}
