package info.bioinfweb.commons.log;


import javax.swing.JTextArea;



/**
 * This implementation of {@link ApplicationLogger} appends all messages to a {@link JTextArea}.
 * 
 * @author Ben St&ouml;ver
 */
public class JTextAreaApplicationLogger extends AbstractApplicationLogger {
  private JTextArea textArea = null;
  
  
	public JTextAreaApplicationLogger(JTextArea textArea) {
		super();
		this.textArea = textArea;
	}


	public JTextArea getTextArea() {
		return textArea;
	}


	@Override
	public void addMessage(ApplicationLoggerMessage message) {
		getTextArea().append(message.toString() + System.lineSeparator());
		getTextArea().setCaretPosition(getTextArea().getDocument().getLength());
	}
}
