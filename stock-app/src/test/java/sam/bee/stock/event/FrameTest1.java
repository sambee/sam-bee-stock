package sam.bee.stock.event;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;

public class FrameTest1 {

	public static void main(String args[]) throws HeadlessException, UnsupportedEncodingException{
		Frame frame = new Frame(){
			private static final long serialVersionUID = 0x5ee6fe2cdea8105dL;
				protected void processWindowEvent(WindowEvent e) {
				super.processWindowEvent(e);
				if (e.getID() == 201) {
					
					System.exit(0);
				}
			}

			public synchronized void setTitle(String title) {
				super.setTitle(title);
				enableEvents(AWTEvent.WINDOW_EVENT_MASK);
			}
		};
		
		frame.setTitle("Applet Frame");
		
		frame.setSize(800, 600);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width - frame.getSize().width) / 2,
				(d.height - frame.getSize().height) / 2);
		
		Menu menu = new Menu(new String(new byte[]{-78, -30, -54, -44}, "unicode"));
		MenuItem item0 = new MenuItem("aaa");
		
		menu.add(item0);
		MenuBar mb = new MenuBar();
		mb.add(menu);
		frame.setMenuBar(mb);
		
		 Panel p1 = new Panel();
		 p1.setLayout(new BorderLayout());
		 
		TextField tf = new TextField();			
		p1.add(tf, BorderLayout.SOUTH);
		 
		TextField tf2 = new TextField();
		p1.add(tf2);
			
		frame.add(p1);
		frame.setVisible(true);
		
	}
}
