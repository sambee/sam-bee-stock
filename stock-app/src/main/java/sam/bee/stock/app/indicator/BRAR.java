// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   BRAR.java

package sam.bee.stock.app.indicator;


import java.awt.Graphics;
import java.awt.Rectangle;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.KLineData;

// Referenced classes of package gnnt.MEBS.HQApplet.Indicator:
//            IndicatorBase, IndicatorPos

public class BRAR extends IndicatorBase {

    private final int m_iParam[] = {
        26
    };

    public BRAR(IndicatorPos pos, int Precision) {
        super(pos, Precision);
        super.m_strIndicatorName = "BRAR";
        super.m_strIndicatorName += "(";
        for(int i = 0; i < m_iParam.length; i++) {
            if(i > 0)
                super.m_strIndicatorName += ",";
            super.m_strIndicatorName += m_iParam[i];
        }

        super.m_strIndicatorName += ")";
        super.m_strParamName = (new String[] {
            "AR", "BR"
        });
        super.m_iPrecision = 2;
    }

    public void Paint(Graphics g, Rectangle rc, KLineData data[]) {
        super.Paint(g, rc, data);
        Calculate();
        super.m_max = -10000F;
        super.m_min = 10000F;
        GetValueMaxMin(super.m_data[0], m_iParam[0] + 1);
        GetValueMaxMin(super.m_data[1], m_iParam[0] + 1);
        DrawCoordinate(g, 2);
        DrawLine(g, super.m_data[0], m_iParam[0] + 1, Application.rhColor.clIndicator[0]);
        DrawLine(g, super.m_data[1], m_iParam[0] + 1, Application.rhColor.clIndicator[1]);
    }

    public void Calculate() {
        super.m_data = new float[2][];
        if(super.m_kData == null || super.m_kData.length <= 0) {
            return;
        } else {
            super.m_data[0] = new float[super.m_kData.length];
            super.m_data[1] = new float[super.m_kData.length];
            GetAR(m_iParam[0], super.m_data[0]);
            GetBR(m_iParam[0], super.m_data[1]);
            return;
        }
    }

    private void GetAR(int n, float ar[]) {
        if(super.m_kData.length < n)
            return;
        float downsum;
        float upsum = downsum = 0.0F;
        for(int i = 1; i < n; i++) {
            upsum += super.m_kData[i].highPrice - super.m_kData[i].openPrice;
            downsum += super.m_kData[i].openPrice - super.m_kData[i].lowPrice;
        }

        float prear = 0.0F;
        for(int i = n; i < super.m_kData.length; i++) {
            upsum += super.m_kData[i].highPrice - super.m_kData[i].openPrice;
            downsum += super.m_kData[i].openPrice - super.m_kData[i].lowPrice;
            ar[i] = prear;
            if(downsum != 0.0F)
                ar[i] = (upsum / downsum) * 100F;
            prear = ar[i];
            int j = (i - n) + 1;
            upsum -= super.m_kData[j].highPrice - super.m_kData[j].openPrice;
            downsum -= super.m_kData[j].openPrice - super.m_kData[j].lowPrice;
        }

    }

    private void GetBR(int n, float br[]) {
        if(super.m_kData.length < n)
            return;
        float downsum;
        float upsum = downsum = 0.0F;
        for(int i = 1; i < n; i++) {
            float value = super.m_kData[i].highPrice - super.m_kData[i - 1].closePrice;
            upsum += value > 0.0F ? value : 0.0F;
            value = super.m_kData[i - 1].closePrice - super.m_kData[i].lowPrice;
            downsum += value > 0.0F ? value : 0.0F;
        }

        float prebr = 0.0F;
        for(int i = n; i < super.m_kData.length; i++) {
            float value = super.m_kData[i].highPrice - super.m_kData[i - 1].closePrice;
            upsum += value > 0.0F ? value : 0.0F;
            value = super.m_kData[i - 1].closePrice - super.m_kData[i].lowPrice;
            downsum += value > 0.0F ? value : 0.0F;
            br[i] = prebr;
            if(downsum != 0.0F)
                br[i] = (upsum / downsum) * 100F;
            prebr = br[i];
            int j = (i - n) + 1;
            value = super.m_kData[j].highPrice - super.m_kData[j - 1].closePrice;
            upsum -= value > 0.0F ? value : 0.0F;
            value = super.m_kData[j - 1].closePrice - super.m_kData[j].lowPrice;
            downsum -= value > 0.0F ? value : 0.0F;
        }

    }
}
