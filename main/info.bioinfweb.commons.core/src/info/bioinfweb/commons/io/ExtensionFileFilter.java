/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2016  Ben Stöver
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
package info.bioinfweb.commons.io;


import info.bioinfweb.commons.SystemUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * A file filter that both inherits from the Swing class {@link javax.swing.filechooser.FileFilter} and implements
 * both the Java IO interfaces {@link FileFilter} and {@link FilenameFilter}. It implements functionality similar 
 * to that of {@link FileNameExtensionFilter} but offers more features and allows extending this class, which is 
 * prohibited by {@link FileNameExtensionFilter}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class ExtensionFileFilter extends javax.swing.filechooser.FileFilter implements FileFilter, FilenameFilter {
	//TODO Inherit XML specific class that also tests e.g. XML namespace declarations.
	
	/** The character separating an extension from the file name. */
	public static final char EXTENSION_SEPARATOR = '.';
	
	
	private String description;
	private String[] extensions;
	private List<String> extensionsList;
	
	
	/**
	 * Creates a new instance of this class.
	 * <p>
	 * All file extension parameters should be specified without a leading {@code '.'}.
	 * 
	 * @param description the description of this file type to be displayed e.g. in open dialogs
	 * @param defaultExtension the default extension of this file format
	 * @param addExtensionListToDescription Specify {@code true} here, if a list of valid extensions shall be appended
	 *        on the specified description in parentheses.
	 * @param extensions optional alternative extensions can be specified here
	 * @throws NullPointerException if {@code description} or any specified extension are {@code null}
	 */
	public ExtensionFileFilter(String description, String defaultExtension, boolean addExtensionListToDescription, 
			String... extensions) {
		
		super();
		if (defaultExtension == null) {
			throw new NullPointerException("The default extension must not be null.");
		}
		this.extensions = new String[extensions.length + 1];
		this.extensions[0] = defaultExtension;
		for (int i = 0; i < extensions.length; i++) {
			if (extensions[i] == null) {
				throw new NullPointerException("An extension must not be null.");
			}
			else {
				this.extensions[i + 1] = extensions[i];
			}
		}
		extensionsList = Collections.unmodifiableList(Arrays.asList(extensions));

		this.description = processDescription(description, addExtensionListToDescription);
	}
	
	
	private String processDescription(String description, boolean addExtensionListToDescription) {
		if (description == null) {
			throw new NullPointerException("The description must not be null.");
		}
		else {
			if (addExtensionListToDescription) {
				StringBuilder result = new StringBuilder();
				result.append(description);
				result.append(" (");
				for (int i = 0; i < extensions.length; i++) {
					result.append('*');
					result.append(EXTENSION_SEPARATOR);
					result.append(extensions[i]);
					result.append(';');
					if (i < extensions.length - 1) {
						result.append(' ');
					}
				}
				result.append(')');
				return result.toString();
			}
			else {
				return description;
			}
		}
	}


	/**
	 * This default implementation accepts all files that are directories or that end with one of the defined extensions.
	 * <p>
	 * Inherited classes may add additional functionality to this method by overwriting, if the specified file 
	 * extensions are ambiguous. 
	 * 
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 * @see FileFilter#accept(File)
	 */
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		else {
			for (int i = 0; i < extensions.length; i++) {
				if (f.getName().endsWith(extensions[i])) {
					return true;
				}
			}
			return false;
		}
	}

	
	/**
	 * Creates a file from {@code dir} and {@code name} and delegates to {@link #accept(File)}.
	 * 
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String name) {
		return accept(new File(dir.getAbsolutePath() + SystemUtils.FILE_SEPARATOR + name));
	}


	@Override
	public String getDescription() {
		return description;
	}

	
	/**
	 * Returns the default file extension of this filter.
	 * 
	 * @return the default file extension not including the leading {@code '.'}
	 */
	public String getDefaultExtension() {
		return extensions[0];
	}
	
	
	/**
	 * Returns a list of all extensions accepted by this filter. The first element of that list is equal to the return
	 * value of {@link #getDefaultExtension()}.
	 * 
	 * @return an unmodifiable list of alternative file extensions
	 */
	public List<String> getExtensions() {
		return extensionsList;
	}
}
