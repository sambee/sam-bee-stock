// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Page_Main.java

package sam.bee.stock.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Referenced classes of package gnnt.MEBS.HQApplet:
//            HQApplet

class MenuListener
    implements ActionListener {

    Application application;

    MenuListener() {
    }

    public void actionPerformed(ActionEvent e) {
        application.userCommand(e.getActionCommand());
        application.repaint();
    }
}
