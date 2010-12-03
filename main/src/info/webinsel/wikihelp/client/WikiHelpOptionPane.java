package info.webinsel.wikihelp.client;


import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;



/**
 * This class offers the same <code>showMessageDialog</code>-methods as <code>JOptionPane</code> but
 * additionally show a help button which is linked to a wiki help topic.
 * 
 * @author Ben St&ouml;ver
 */
public class WikiHelpOptionPane {
  public static void showMessageDialog(Component parentComponent, Object message, WikiHelp wikiHelp, 
  		int helpTopic) {
  	
  	showMessageDialog(parentComponent, message, "Message", JOptionPane.INFORMATION_MESSAGE, null, 
  			wikiHelp, helpTopic);
  }
  
  
  public static void showMessageDialog(Component parentComponent, Object message, String title, 
  		int messageType, WikiHelp wikiHelp, int helpTopic) {
  	
  	showMessageDialog(parentComponent, message, title, messageType, null, wikiHelp, helpTopic);
  }
  
  
  public static void showMessageDialog(Component parentComponent, Object message, String title, 
  		int messageType, Icon icon, WikiHelp wikiHelp, int helpTopic) {
  	
		String[] options = new String[2];
		options[0] = "OK";
		options[1] = "Help";
		if (JOptionPane.showOptionDialog(parentComponent, message, title, JOptionPane.OK_CANCEL_OPTION, 
				messageType, icon, options, options[0]) == 1) {
			
			wikiHelp.displayTopic(helpTopic);
		}
  }
}
