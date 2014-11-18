package sam.bee.stock.gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;



@SuppressWarnings("rawtypes")
class AboutDialog extends Dialog {

    public static final String VER = "";
    private static final long serialVersionUID = 0x6d97c7b19fc60a97L;
    
	private Vector vString;
    public String strCmd;
    private java.awt.List m_list;
    private TextArea m_text;
    private Application m_applet;

    public AboutDialog(Frame f, Application applet) {
        super(f, applet.getShowString("About"), true);
        vString = new Vector();
        m_list = new java.awt.List();
        m_text = new TextArea("", 2, 2, 3);
        m_applet = applet;
        try {
            jbInit();
        }
        catch(Exception e) {
            if(Application.bDebug){
                e.printStackTrace();
            }
        }
    }

    private void jbInit() throws Exception {
        m_list.setForeground(Color.blue);
        m_list.addMouseListener(new MouseAdapter() {        

            public void mouseClicked(MouseEvent e) {
                m_list_mouseClicked(e);
            }
        
        });
        m_list.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent keyevent) {
            }
        });
        
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                closeThisDialog();
            }
        });
        
        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent keyevent) {
            }
        });
        
        
        m_text.setBackground(Color.white);
        m_text.setForeground(Color.blue);
        m_text.setEditable(false);
        m_text.append(m_applet.getShowString("AppletName"));
        m_text.append("\n" + m_applet.getShowString("Version"));
        m_text.append("\n\n" + m_applet.getShowString("Company"));
        add(m_text);
        setResizable(false);
    }

    void closeThisDialog() {
    	setVisible(false);
    }

    void m_list_mouseClicked(MouseEvent e) {
        if(e.getModifiers() != 4 && e.getClickCount() > 1) {
            strCmd = "";
            int iSel = m_list.getSelectedIndex();
            if(iSel >= 0 && iSel < vString.size())
                strCmd = (String)vString.elementAt(iSel);
            closeThisDialog();
        }
    }
}
