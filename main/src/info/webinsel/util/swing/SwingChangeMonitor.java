package info.webinsel.util.swing;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import info.webinsel.util.ChangeMonitor;



public class SwingChangeMonitor extends ChangeMonitor 
    implements ChangeListener, DocumentListener, ItemListener {
	
	public void stateChanged(ChangeEvent e) {
		registerChange();
	}

	
	public void changedUpdate(DocumentEvent e) {
		registerChange();
	}

	
	public void insertUpdate(DocumentEvent e) {
		registerChange();
	}

	
	public void removeUpdate(DocumentEvent e) {
		registerChange();
	}


	public void itemStateChanged(ItemEvent e) {
		registerChange();
	}
}
