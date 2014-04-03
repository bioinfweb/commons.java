package info.webinsel.wikihelp.client;


import info.bioinfweb.commons.swing.OkCancelApplyHelpDialog;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;



public abstract class OkCancelApplyWikiHelpDialog extends OkCancelApplyHelpDialog {
	public static final int UNDEFINED_HELP_CODE = -1;
	
	
	private WikiHelp wikiHelp = null;
	private int helpCode = UNDEFINED_HELP_CODE;
	
	
	public OkCancelApplyWikiHelpDialog(WikiHelp wikiHelp) {
		super();
		this.wikiHelp = wikiHelp;
	}


	public OkCancelApplyWikiHelpDialog(Frame owner, WikiHelp wikiHelp) {
		super(owner);
		this.wikiHelp = wikiHelp;
	}


	public OkCancelApplyWikiHelpDialog(Frame owner, boolean modal, WikiHelp wikiHelp) {
		super(owner, modal);
		this.wikiHelp = wikiHelp;
	}
	
	
	public OkCancelApplyWikiHelpDialog(Dialog owner, boolean modal, WikiHelp wikiHelp) {
		super(owner, modal);
		this.wikiHelp = wikiHelp;
	}


	public OkCancelApplyWikiHelpDialog(Frame owner, boolean modal, boolean closeOnEnter, WikiHelp wikiHelp) {
		super(owner, modal, closeOnEnter);
		this.wikiHelp = wikiHelp;
	}
	
	
	public OkCancelApplyWikiHelpDialog(Dialog owner, boolean modal, boolean closeOnEnter, WikiHelp wikiHelp) {
		super(owner, modal, closeOnEnter);
		this.wikiHelp = wikiHelp;
	}


	public OkCancelApplyWikiHelpDialog(Dialog owner, WikiHelp wikiHelp) {
		super(owner);
		this.wikiHelp = wikiHelp;
	}


	public OkCancelApplyWikiHelpDialog(Window owner, WikiHelp wikiHelp) {
		super(owner);
		this.wikiHelp = wikiHelp;
	}


	public int getHelpCode() {
		return helpCode;
	}


	public void setHelpCode(int helpCode) {
		this.helpCode = helpCode;
	}


	public WikiHelp getWikiHelp() {
		return wikiHelp;
	}


	@Override
	protected void help() {
		if (helpCode == UNDEFINED_HELP_CODE) {
			wikiHelp.displayContents();
		}
		else {
			wikiHelp.displayTopic(helpCode);
			
		}
	}
}
