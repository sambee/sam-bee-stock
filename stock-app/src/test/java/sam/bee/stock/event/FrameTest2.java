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
import java.lang.Character.UnicodeBlock;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class FrameTest2 {

	public static void main(String args[]) throws HeadlessException, UnsupportedEncodingException{
		
	//	System.out.println(System.getProperty("file.encoding"));
		//System.setProperty("file.encoding", "gbk");
		JFrame frame = new JFrame(){
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
		
		Menu menu = new Menu(convertToUnicode("测试"));
		///MenuItem item0 = new JMenuItem("aaa");
		
		//menu.add(item0);
		MenuBar mb = new MenuBar();
		mb.add(menu);
		frame.setMenuBar(mb);
		
		 JPanel p1 = new JPanel();
		 p1.setLayout(new BorderLayout());
		 
		TextField tf = new TextField();			
		p1.add(tf, BorderLayout.SOUTH);
		 
		TextField tf2 = new TextField();
		p1.add(tf2);
			
		frame.add(p1);
		frame.setVisible(true);
		
	}
	
	static String convertToUnicode(String inStr){
//		  char[] myBuffer = inStr.toCharArray();
//	        
//	        StringBuffer sb = new StringBuffer();
//	        for (int i = 0; i < inStr.length(); i++) {
//	         UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
//	            if(ub == UnicodeBlock.BASIC_LATIN){
//	             //英文及数字等
//	             sb.append(myBuffer[i]);
//	            }else if(ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS){
//	             //全角半角字符
//	             int j = (int) myBuffer[i] - 65248;
//	             sb.append((char)j);
//	            }else{
//	             //汉字
//	             short s = (short) myBuffer[i];
//	                String hexS = Integer.toHexString(s);
//	                String unicode = "\\u"+hexS;
//	             sb.append(unicode.toLowerCase());
//	            }
//	        }
//	        return sb.toString();
//		
		return inStr;
	}
}
