package info.webinsel.util.swing;


import java.awt.Desktop;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.text.html.HTMLDocument;



/**
 * This components modifies a {@link JEditorPane} in a way that is looks exactly like a JLabel and provides a
 * {@link HyperlinkListener} that opens the systems default browser is the user clicks on any links. 
 * 
 * @author Ben St&ouml;ver
 */
public class JHTMLLabel extends JEditorPane {
	private final JHTMLLabel THIS = this;
	
	
	public final HyperlinkListener HYPERLINK_LISTENER = 		
				  new javax.swing.event.HyperlinkListener() {
							public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent e) {
								if (e.getEventType().equals(EventType.ACTIVATED)) {
									try {
										Desktop.getDesktop().browse(e.getURL().toURI());
									}
									catch (Exception ex) {
										JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(THIS), 
												"An error occurred when trying open the selected link.", 
												"Navigation failed,", JOptionPane.ERROR_MESSAGE);
									}
								}
							}
						};


						
	public JHTMLLabel() {
	  super();
		setBackground(SystemColor.menu);
		setContentType("text/html");
		Font font = UIManager.getFont("Label.font");
    String bodyRule = "body { font-family: " + font.getFamily() + "; " +
            "font-size: " + font.getSize() + "pt; }";
    ((HTMLDocument)getDocument()).getStyleSheet().addRule(bodyRule);
    
		setEditable(false);
		setBorder(null);
		addHyperlinkListener(HYPERLINK_LISTENER);
  }
	
	
	@Override
  public void setText(String t) {
	  super.setText(t);
		setCaretPosition(0);
  }


	public void setHTMLContent(String content) {
		setText("<html><body>" + content + "</body></html>");
	}
}
