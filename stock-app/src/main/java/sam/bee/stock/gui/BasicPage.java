// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Page_Main.java

package sam.bee.stock.gui;

import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

// Referenced classes of package gnnt.MEBS.HQApplet:
//            MenuListener, HQApplet, CodeTable

abstract class BasicPage extends MenuListener {

    boolean stopFlag;
    protected Rectangle m_rc;
    Thread timerThread;

    public BasicPage(Rectangle _rc, Application applet) {
        stopFlag = false;
        timerThread = new  Thread() {

            public void run() {
                try {
                    while(!stopFlag)  {
                        Thread.sleep(5000L);
                        if(!stopFlag){
                            askForDataOnTimer();
                        }
                    }
                }
                catch(InterruptedException e) {
                    if(Application.bDebug){
                        e.printStackTrace();
                    }
                }
            }
        };
        super.application = applet;
        m_rc = _rc;
        if(super.application.mainGraph != null)
            super.application.mainGraph.stopFlag = true;
        timerThread.start();
    }

    abstract void paint(Graphics g);

    abstract boolean keyPressed(KeyEvent keyevent);

    void askForDataOnTimer() {
    }

    boolean mouseLeftClicked(int x, int y) {
        return false;
    }

    boolean mouseLeftDblClicked(int x, int y) {
        return false;
    }

    boolean mouseDragged(int x, int y) {
        return false;
    }

    boolean mouseMoved(int x, int y) {
        return false;
    }

    abstract void processMenuEvent(PopupMenu popupmenu, int i, int j);

    protected void processCommonMenuEvent(PopupMenu popupMenu, ActionListener listener) {
        MenuItem menuHistory = new MenuItem(super.application.getShowString("History") + "  F7");
        menuHistory.setActionCommand("page_history");
        menuHistory.addActionListener(listener);
        popupMenu.add(menuHistory);
        MenuItem menuAbout = new MenuItem(super.application.getShowString("About") + " ...");
        menuAbout.setActionCommand("about");
        menuAbout.addActionListener(listener);
        popupMenu.addSeparator();
        popupMenu.add(menuAbout);
        int iCount = 0;
        Menu menuSub = new Menu(super.application.getShowString("ClassIndex"));
        for(int i = 0; i < super.application.m_codeList.size(); i++) {
            String code = (String)super.application.m_codeList.elementAt(i);
            CodeTable codeTable = (CodeTable)super.application.m_ProductByHttp.get(code);
            if(codeTable.status == 2 || codeTable.status == 3) {
                MenuItem menuIndex;
                if(codeTable.status == 3)
                    menuIndex = new MenuItem(codeTable.sName + "  F3");
                else
                    menuIndex = new MenuItem(codeTable.sName);
                menuIndex.setActionCommand("INDEX_" + code);
                menuIndex.addActionListener(listener);
                if(codeTable.status == 3)
                    popupMenu.insert(menuIndex, 0);
                else
                    menuSub.add(menuIndex);
                iCount++;
            }
        }

        if(iCount > 0)
            popupMenu.insertSeparator(1);
        if(menuSub.getItemCount() > 0)
            popupMenu.insert(menuSub, 0);
        Menu MenuSeriesSub = new Menu(super.application.getShowString("SeriesPrice"));
        for(int i = 0; i < super.application.m_codeList.size(); i++) {
            String code = (String)super.application.m_codeList.elementAt(i);
            CodeTable codeTable = (CodeTable)super.application.m_ProductByHttp.get(code);
            if(codeTable.status == 4) {
                MenuItem menuSeries = new MenuItem(codeTable.sName);
                menuSeries.setActionCommand("SERIES_" + code);
                menuSeries.addActionListener(listener);
                MenuSeriesSub.add(menuSeries);
            }
        }

        if(MenuSeriesSub.getItemCount() > 0) {
            popupMenu.insert(MenuSeriesSub, 0);
            popupMenu.insertSeparator(1);
        }
    }
}
