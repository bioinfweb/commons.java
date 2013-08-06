package info.webinsel.util.log;


import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;



/**
 * This implementation of {@link ApplicationLogger} appends all messages to a {@link Document}. It 
 * can be used to print log messages to swing text components (e.g. {@link JEditorPane}).
 * 
 * @author Ben St&ouml;ver
 */
public class SwingDocumentApplicationLogger extends AbstractApplicationLogger {
  private Document document = null;
  
  
	public SwingDocumentApplicationLogger(Document document) {
		super();
		this.document = document;
	}


	public Document getDocument() {
		return document;
	}


	@Override
	public void addMessage(ApplicationLoggerMessage message) {
		try {
			getDocument().insertString(getDocument().getLength(), message.toString() + System.lineSeparator(), null);
		}
		catch (BadLocationException e) {
			e.printStackTrace();
			throw new InternalError("The following unexpected exception occurred: \"" + e.toString() + "\"");
		}
	}
}
