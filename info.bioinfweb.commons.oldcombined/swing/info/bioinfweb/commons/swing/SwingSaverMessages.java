/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.swing;


import java.util.*;



public class SwingSaverMessages {
	public static final String EN_ASK_TO_SAVE_TITLE = "Document has changed";
	public static final String EN_ASK_TO_SAVE_MESSAGE_BEGINNING = "Do you want to save changes in ";
	public static final String EN_ASK_TO_SAVE_MESSAGE_END = "?";
	public static final String EN_BUTTON_YES = "Yes";
	public static final String EN_BUTTON_NO = "No";
	public static final String EN_BUTTON_CANCEL = "Cancel";
	
	public static final String DE_ASK_TO_SAVE_TITLE = "Änderungen";
	public static final String DE_ASK_TO_SAVE_MESSAGE_BEGINNING = "Möchten Sie Änderungen an ";
	public static final String DE_ASK_TO_SAVE_MESSAGE_END = " speichern?";
	public static final String DE_BUTTON_YES = "Ja";
	public static final String DE_BUTTON_NO = "Nein";
	public static final String DE_BUTTON_CANCEL = "Abbrechen";
	
	
  private String askToSaveTitle;
  private String askToSaveMessageBeginnung;
  private String askToSaveMessageEnd;
  private String buttonYes;
  private String buttonNo;
  private String buttonCancel;
  
  
  public SwingSaverMessages() {
  	super();
  	setDefaultLocale();
  }
  
  
  public void setDefaultLocale() {
  	setLocale(Locale.getDefault());
  }
  
  
  public void setLocale(Locale locale) {
  	if (locale.getLanguage().equals(Locale.GERMAN.getLanguage())) {
  	  askToSaveTitle = DE_ASK_TO_SAVE_TITLE;
  	  askToSaveMessageBeginnung = DE_ASK_TO_SAVE_MESSAGE_BEGINNING;
  	  askToSaveMessageEnd = DE_ASK_TO_SAVE_MESSAGE_END;
  	  buttonYes = DE_BUTTON_YES;
  	  buttonNo = DE_BUTTON_NO;
  	  buttonCancel = DE_BUTTON_CANCEL;
  	}
  	else {  // Englisch för alle nicht unterstötzten Sprachen
  	  askToSaveTitle = EN_ASK_TO_SAVE_TITLE;
  	  askToSaveMessageBeginnung = EN_ASK_TO_SAVE_MESSAGE_BEGINNING;
  	  askToSaveMessageEnd = EN_ASK_TO_SAVE_MESSAGE_END;
  	  buttonYes = EN_BUTTON_YES;
  	  buttonNo = EN_BUTTON_NO;
  	  buttonCancel = EN_BUTTON_CANCEL;
  	}
  }
  
  
	public String getAskToSaveMessageBeginnung() {
		return askToSaveMessageBeginnung;
	}
	
	
	public void setAskToSaveMessageBeginnung(String askToSaveMessageBeginnung) {
		this.askToSaveMessageBeginnung = askToSaveMessageBeginnung;
	}
	
	
	public String getAskToSaveMessageEnd() {
		return askToSaveMessageEnd;
	}
	
	
	public void setAskToSaveMessageEnd(String askToSaveMessageEnd) {
		this.askToSaveMessageEnd = askToSaveMessageEnd;
	}
	
	
	public String getAskToSaveTitle() {
		return askToSaveTitle;
	}
	
	
	public void setAskToSaveTitle(String askToSaveTitle) {
		this.askToSaveTitle = askToSaveTitle;
	}
	
	
	public String getButtonCancel() {
		return buttonCancel;
	}
	
	
	public void setButtonCancel(String buttonCancel) {
		this.buttonCancel = buttonCancel;
	}
	
	
	public String getButtonNo() {
		return buttonNo;
	}
	
	
	public void setButtonNo(String buttonNo) {
		this.buttonNo = buttonNo;
	}
	
	
	public String getButtonYes() {
		return buttonYes;
	}
	
	
	public void setButtonYes(String buttonYes) {
		this.buttonYes = buttonYes;
	}
}
