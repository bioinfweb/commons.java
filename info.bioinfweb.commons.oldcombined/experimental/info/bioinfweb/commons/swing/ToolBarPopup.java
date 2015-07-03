package info.bioinfweb.commons.swing;


import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import javax.swing.JDialog;



public class ToolBarPopup extends JDialog {
	private Component origin;
	
	
	public ToolBarPopup(Component origin) {
		super();
		this.origin = origin;
		init();
	}

	
	public ToolBarPopup(Component origin, Dialog owner) {
		super(owner);
		this.origin = origin;
		init();
	}

	
	public ToolBarPopup(Component origin, Frame owner) {
		super(owner);
		this.origin = origin;
		init();
	}

	
	public ToolBarPopup(Component origin, Window owner) {
		super(owner);
		this.origin = origin;
		init();
	}
	
	
	private void init() {
		setUndecorated(true);
	}
	
	
	private void position() {
		Point originLocation = origin.getLocationOnScreen();
		setLocation((int)Math.round(originLocation.getX()), (int)Math.round(originLocation.getY() + origin.getHeight()));
	}


	@Override
	public void setVisible(boolean flag) {
		if (flag) {
			position();
		}
		super.setVisible(flag);
	}


	@Override
	@Deprecated
	public void show() {
		position();
		super.show();
	}


	@Override
	@Deprecated
	public void show(boolean flag) {
		if (flag) {
			position();
		}
		super.show(flag);
	}
}