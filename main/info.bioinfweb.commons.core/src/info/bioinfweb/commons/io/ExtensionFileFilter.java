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
package info.bioinfweb.commons.io;


import info.bioinfweb.commons.SystemUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * A file filter that both inherits from the Swing class {@link javax.swing.filechooser.FileFilter} and implements
 * both the Java I/O interfaces {@link FileFilter} and {@link FilenameFilter}. It implements functionality similar 
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
	
	/** The character separating an list of paths, e.g. {@code *.txt;*.*}. */
	public static final char PATH_LIST_SEPARATOR = '.';
	
	
	private String description;
	private List<String> extensionsList;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param description the description of this file type to be displayed e.g. in open dialogs
	 * @param addExtensionListToDescription Specify {@code true} here, if a list of valid extensions shall be appended
	 *        on the specified description in parentheses.
	 * @param extensions one and possibly additional alternative extensions accepted by this filter (All file extension 
	 *        should be specified without a leading {@code '.'}.)
	 * @throws NullPointerException if {@code description} or any specified extension are {@code null}
	 * @throws IllegalArgumentException if not at least one extension has been specified
	 */
	public ExtensionFileFilter(String description, boolean addExtensionListToDescription, 
			String... extensions) {
		
		this(description, addExtensionListToDescription, Arrays.asList(extensions));
	}
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param description the description of this file type to be displayed e.g. in open dialogs
	 * @param addExtensionListToDescription Specify {@code true} here, if a list of valid extensions shall be appended
	 *        on the specified description in parentheses.
	 * @param extensions one and possibly additional alternative extensions accepted by this filter (All file extension 
	 *        should be specified without a leading {@code '.'}.)
	 * @throws NullPointerException if {@code description} or any specified extension are {@code null}
	 * @throws IllegalArgumentException if not at least one extension has been specified
	 */
	public ExtensionFileFilter(String description, boolean addExtensionListToDescription, List<String> extensions) {
		super();
		if (extensions.size() < 1) {
			throw new IllegalArgumentException("At least one file extension needs to be specified.");
		}
		else {
			for (String extension : extensions) {
				if (extension == null) {
					throw new NullPointerException("A file extension must not be null.");
				}
			}
			
			extensionsList = Collections.unmodifiableList(extensions);
			this.description = processDescription(description, addExtensionListToDescription);
		}
	}
	
	
	/**
	 * Returns all extensions stored in this filter as a single string.
	 * <p>
	 * <b>Example:</b> If the extensions {@code jpeg}, {@code jpg} and {@code jpe} would be have been passed to the constructor
	 * of this class, the return value would be "{@code *.jpeg;*.jpg;*.jpe}". 
	 * 
	 * @return a string concatenating all extensions of this filter
	 */
	public String getExtensionsAsString() {
		StringBuilder result = new StringBuilder();
		Iterator<String> iterator = extensionsList.iterator();
		while (iterator.hasNext()) {
			result.append('*');
			result.append(EXTENSION_SEPARATOR);
			result.append(iterator.next());
			if (iterator.hasNext()) {
				result.append(PATH_LIST_SEPARATOR);
				result.append(' ');
			}
		}
		return result.toString();
	}
	
	
	private String processDescription(String description, boolean addExtensionListToDescription) {
		if (description == null) {
			throw new NullPointerException("The description must not be null.");
		}
		else {
			if (addExtensionListToDescription) {
				return description + " (" + getExtensionsAsString() + ")";
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
			for (String extension : extensionsList) {
				if (f.getName().endsWith(EXTENSION_SEPARATOR + extension)) {
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
		return extensionsList.get(0);
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
