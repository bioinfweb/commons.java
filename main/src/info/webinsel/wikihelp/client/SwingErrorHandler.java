package info.webinsel.wikihelp.client;


import javax.swing.JOptionPane;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;



public class SwingErrorHandler implements WikiHelpErrorHandler {
	private String wikiURL = null;
	
	
	public SwingErrorHandler(String wikiURL) {
		super();
		this.wikiURL = wikiURL;
	}


	public void initFail(BrowserLaunchingInitializingException e) {
		JOptionPane.showMessageDialog(null, "An error occured when trying to launch your browser. You can acces the help system at \"" + wikiURL + "\"", "Launching error", JOptionPane.ERROR_MESSAGE);
	}

	
	public void unsupportedOperatingSystem(UnsupportedOperatingSystemException e) {
		JOptionPane.showMessageDialog(null, "Your operating system is not supported. A browser could not be launched.\nYou can acces the help system at \"" + wikiURL + "\"", "Unsupported operating system.", JOptionPane.ERROR_MESSAGE);
	}
}
