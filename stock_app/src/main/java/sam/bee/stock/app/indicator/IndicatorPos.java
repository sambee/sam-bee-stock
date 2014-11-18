// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:43:34 

//source File Name:   IndicatorPos.java

package sam.bee.stock.app.indicator;


public class IndicatorPos {

    public int m_VirtualRatio;
    public float m_Ratio;
    public int m_ScreenCount;
    public int m_EndPos;
    public int m_MaxPos;
    public int m_End;
    public int m_Begin;

    public IndicatorPos() {
        m_VirtualRatio = 15;
        m_EndPos = 0;
        m_End = 0;
        m_Begin = 0;
    }

    public void GetScreen(int width, int kDataLen) {
        m_Ratio = m_VirtualRatio > 10 ? m_VirtualRatio - 10 : 1.0F / (float)(12 - m_VirtualRatio);
        if(m_EndPos < 0)
            m_EndPos = 0;
        if(m_EndPos >= kDataLen)
            m_EndPos = kDataLen - 1;
        m_ScreenCount = (int)((float)width / m_Ratio);
        if(m_ScreenCount < 0)
            m_ScreenCount = 0;
        if(kDataLen > m_ScreenCount) {
            m_MaxPos = kDataLen - m_ScreenCount;
            m_End = kDataLen - m_EndPos - 1;
            m_Begin = (m_End + 1) - m_ScreenCount;
            if(m_Begin < 0) {
                m_Begin = 0;
                m_End = m_ScreenCount - 1;
                m_EndPos = kDataLen - m_ScreenCount;
            }
        } else {
            m_Begin = m_MaxPos = m_EndPos = 0;
            m_End = kDataLen - 1;
        }
    }
}
