/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus;


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
				  		int firstPos = Integer.parseInt(indices[0]);
				  		int lastPos = firstPos;
				  		if (indices.length > 1) {
				  			lastPos = Integer.parseInt(indices[1]);
				  		}
				  		getBlockListener().addCharSetInterval(currentSetName, 
				  				firstPos, lastPos);
				  	}
				  	catch (NumberFormatException e) {
				  		System.err.println("WARNING: Interval '" + token + "' could not be parsed. Skipped.");
				  	}  // Eintrag wird übersprungen  //TODO Ggf. später Fehlermeldung ausgeben.
				  	break;
				case OUTSIDE_CHAR_SET_COMMAND:
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
