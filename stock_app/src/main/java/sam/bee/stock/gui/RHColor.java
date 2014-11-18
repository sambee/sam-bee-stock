// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   RHColor.java

package sam.bee.stock.gui;

import java.awt.Color;

public class RHColor {

    static final int COLORSTYLE_CLASSICAL = 0;
    static final int COLORSTYLE_MODERN = 1;
    static final int COLORSTYLE_ELEGANCE = 2;
    static final int COLORSTYLE_SOFTNESS = 3;
    static final int COLORSTYLE_DIGNITY = 4;
    public Color clBackGround;
    public Color clIncrease;
    public Color clDecrease;
    public Color clEqual;
    public Color clGrid;
    public Color clMinLine;
    public Color clCursor;
    public Color clProductName;
    public Color clVolume;
    public Color clReserve;
    public Color clNumber;
    public Color clHighlight;
    public Color clItem;
    public Color clMultiQuote_TitleBack;
    public Color clKLineUp;
    public Color clKLineDown;
    public Color clKLineEqual;
    public Color clPolyLine;
    public Color clUSALine;
    public Color clIndicator[];

    public RHColor(int iStyle) {
        clIndicator = new Color[6];
        switch(iStyle) {
        case 1: // '\001'
            clBackGround = new Color(255, 255, 255);
            clIncrease = new Color(240, 0, 0);
            clDecrease = new Color(0, 128, 0);
            clEqual = new Color(68, 68, 68);
            clGrid = new Color(160, 160, 160);
            clMinLine = new Color(0, 0, 160);
            clCursor = new Color(255, 128, 0);
            clHighlight = new Color(112, 219, 255);
            clItem = new Color(0, 0, 128);
            clMultiQuote_TitleBack = new Color(192, 192, 192);
            clProductName = new Color(0, 0, 128);
            clVolume = new Color(0, 0, 192);
            clReserve = new Color(64, 128, 128);
            clNumber = new Color(0, 0, 128);
            clKLineUp = new Color(255, 0, 0);
            clKLineDown = new Color(0, 128, 0);
            clKLineEqual = new Color(128, 128, 128);
            clPolyLine = new Color(0, 255, 255);
            clUSALine = new Color(0, 255, 255);
            clIndicator[0] = new Color(0, 0, 64);
            clIndicator[1] = new Color(255, 0, 128);
            clIndicator[2] = new Color(255, 128, 0);
            clIndicator[3] = new Color(128, 0, 0);
            clIndicator[4] = new Color(255, 0, 255);
            clIndicator[5] = new Color(128, 128, 16);
            break;

        case 2: // '\002'
            clBackGround = new Color(0, 0, 128);
            clIncrease = new Color(255, 0, 0);
            clDecrease = new Color(0, 255, 255);
            clEqual = new Color(255, 255, 255);
            clGrid = new Color(128, 128, 128);
            clMinLine = new Color(255, 255, 255);
            clCursor = new Color(192, 192, 192);
            clHighlight = new Color(255, 255, 128);
            clItem = new Color(255, 255, 255);
            clMultiQuote_TitleBack = new Color(0, 0, 176);
            clProductName = new Color(255, 255, 0);
            clVolume = new Color(224, 224, 0);
            clReserve = new Color(64, 128, 128);
            clNumber = new Color(192, 192, 192);
            clKLineUp = new Color(255, 0, 0);
            clKLineDown = new Color(128, 255, 255);
            clKLineEqual = new Color(255, 255, 255);
            clPolyLine = new Color(0, 255, 255);
            clUSALine = new Color(0, 255, 255);
            clIndicator[0] = new Color(255, 255, 0);
            clIndicator[1] = new Color(255, 255, 255);
            clIndicator[2] = new Color(0, 0, 255);
            clIndicator[3] = new Color(255, 128, 64);
            clIndicator[4] = new Color(255, 0, 255);
            clIndicator[5] = new Color(128, 128, 16);
            break;

        case 3: // '\003'
            clBackGround = new Color(248, 248, 240);
            clIncrease = new Color(255, 0, 0);
            clDecrease = new Color(0, 160, 0);
            clEqual = new Color(0, 0, 0);
            clGrid = new Color(192, 192, 192);
            clMinLine = new Color(0, 0, 0);
            clCursor = new Color(64, 64, 64);
            clItem = new Color(0, 0, 0);
            clMultiQuote_TitleBack = new Color(232, 232, 224);
            clProductName = new Color(0, 0, 255);
            clHighlight = new Color(112, 219, 255);
            clReserve = new Color(64, 128, 128);
            clVolume = new Color(96, 96, 0);
            clNumber = new Color(64, 64, 64);
            clKLineUp = new Color(255, 0, 0);
            clKLineDown = new Color(0, 0, 255);
            clKLineEqual = new Color(128, 128, 128);
            clPolyLine = new Color(0, 255, 255);
            clUSALine = new Color(0, 255, 255);
            clIndicator[0] = new Color(64, 64, 64);
            clIndicator[1] = new Color(192, 0, 64);
            clIndicator[2] = new Color(32, 128, 32);
            clIndicator[3] = new Color(128, 0, 0);
            clIndicator[4] = new Color(255, 0, 255);
            clIndicator[5] = new Color(128, 128, 16);
            break;

        case 4: // '\004'
            clBackGround = new Color(245, 252, 253);
            clIncrease = new Color(255, 128, 128);
            clDecrease = new Color(0, 255, 0);
            clEqual = new Color(160, 160, 160);
            clGrid = new Color(128, 128, 128);
            clMinLine = new Color(160, 160, 160);
            clCursor = new Color(32, 32, 32);
            clHighlight = new Color(128, 128, 255);
            clItem = new Color(0, 0, 255);
            clMultiQuote_TitleBack = new Color(160, 240, 160);
            clProductName = new Color(64, 64, 255);
            clVolume = new Color(128, 128, 0);
            clReserve = new Color(64, 128, 128);
            clNumber = new Color(32, 32, 32);
            clKLineUp = new Color(255, 0, 0);
            clKLineDown = new Color(0, 255, 0);
            clKLineEqual = new Color(64, 64, 64);
            clPolyLine = new Color(0, 255, 255);
            clUSALine = new Color(0, 255, 255);
            clIndicator[0] = new Color(160, 160, 0);
            clIndicator[1] = new Color(175, 175, 175);
            clIndicator[2] = new Color(0, 0, 255);
            clIndicator[3] = new Color(255, 128, 64);
            clIndicator[4] = new Color(255, 0, 255);
            clIndicator[5] = new Color(128, 128, 16);
            break;

        case 0: // '\0'
        default:
            clBackGround = new Color(0, 0, 0);
            clIncrease = new Color(255, 0, 0);
            clDecrease = new Color(0, 255, 0);
            clEqual = new Color(255, 255, 255);
            clGrid = new Color(192, 0, 0);
            clMinLine = new Color(255, 255, 255);
            clCursor = new Color(192, 192, 192);
            clItem = new Color(0, 255, 255);
            clMultiQuote_TitleBack = new Color(128, 128, 128);
            clProductName = new Color(255, 255, 0);
            clHighlight = new Color(0, 0, 128);
            clVolume = new Color(255, 255, 0);
            clReserve = new Color(0, 255, 0);
            clNumber = new Color(192, 192, 192);
            clKLineUp = new Color(255, 0, 0);
            clKLineDown = new Color(0, 255, 255);
            clKLineEqual = new Color(255, 255, 255);
            clPolyLine = new Color(0, 255, 255);
            clUSALine = new Color(0, 255, 255);
            clIndicator[0] = new Color(255, 255, 255);
            clIndicator[1] = new Color(255, 255, 0);
            clIndicator[2] = new Color(255, 0, 255);
            clIndicator[3] = new Color(0, 255, 0);
            clIndicator[4] = new Color(255, 128, 0);
            clIndicator[5] = new Color(128, 128, 16);
            break;
        }
    }
}
