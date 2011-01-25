package info.bioinfweb.biojavax.bio.phylo.io.nexus;


import org.biojava.bio.seq.io.ParseException;
import org.biojavax.bio.phylo.io.nexus.NexusBlockParser;



/**
 * Parses a Nexus <code>SETS</code> block and fires events to a {@link SetsBlockListener}.
 * 
 * @author Ben St&ouml;ever
 */
public class SetsBlockParser extends NexusBlockParser.Abstract {
	public static final String CHAR_SET_COMMAND = "CharSet".toLowerCase();
	public static final String SETS_LIST_START = "=";
	public static final String START_END_SEPARATER = "-";
	
	
	private enum ParseStatus {
		OUTSIDE_CHAR_SET_COMMAND,
		CHAR_SET_COMMAND_SARTED,
		NAME_PARSED,
		SETS_LIST_STARTED;
	}
	
	
	private boolean atCommandStart = true;
	private ParseStatus status = ParseStatus.OUTSIDE_CHAR_SET_COMMAND;
	private String currentSetName = "";
	
	
	public SetsBlockParser(SetsBlockListener listener) {
		super(listener);
	}


	@Override
	public SetsBlockListener getBlockListener() {
		return (SetsBlockListener)super.getBlockListener();
	}


	@Override
	public void endTokenGroup() {
		super.endTokenGroup();
		resetStatus();
	}


	@Override
	public void parseToken(String token) throws ParseException {
		if (!token.trim().equals("")) {
			if (atCommandStart) {
				atCommandStart = false;
				if (token.toLowerCase().equals(CHAR_SET_COMMAND)) {
					status = ParseStatus.CHAR_SET_COMMAND_SARTED;
				}
			}
			else {
				switch (status) {
				  case CHAR_SET_COMMAND_SARTED:
				  	getBlockListener().addCharSet(token);
				  	currentSetName = token;
				  	status = ParseStatus.NAME_PARSED;
				  	break;
				  case NAME_PARSED:
				  	if (token.equals(SETS_LIST_START)) {
				  		status = ParseStatus.SETS_LIST_STARTED;
				  	}
				  	break;
				  case SETS_LIST_STARTED:
				  	String[] indices = token.split(START_END_SEPARATER);
				  	try {
				  		getBlockListener().addCharSetInterval(currentSetName, 
				  				Integer.parseInt(indices[0]), Integer.parseInt(indices[1]));
				  	}
				  	catch (NumberFormatException e) {
				  		System.err.println("WARNING: Interval '" + token + "' could not be parsed. Skipped.");
				  	}  // Eintrag wird �bersprungen  //TODO Ggf. sp�ter Fehlermeldung ausgeben.
				  	break;
				}
			}
		}
	}

	
	@Override
	protected void resetStatus() {
		atCommandStart = true;
		status = ParseStatus.OUTSIDE_CHAR_SET_COMMAND;
	}
}