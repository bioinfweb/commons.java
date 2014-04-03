package info.bioinfweb.commons.io;


import java.util.*;
import javax.swing.filechooser.*;



public abstract class InternationalFileFilter extends FileFilter {
	protected String description = "";
		
	
	public void setDefaultLocale() {
		setLocale(Locale.getDefault());
	}
	
	
	public abstract void setLocale(Locale locale);

	
	@Override
	public String getDescription() {
		return description;
	}
}
