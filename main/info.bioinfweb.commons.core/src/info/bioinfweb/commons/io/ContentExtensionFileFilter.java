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


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



/**
 * An abstract file filter that allows to test the content of a file to determine whether it is accepted by this filter.
 * The way a file is tested is determined by {@link #getTestStrategy()} and can be changed during runtime.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public abstract class ContentExtensionFileFilter extends ExtensionFileFilter {
	/**
	 * Determines the way instances of {@link ContentExtensionFileFilter} determine which files are valid.
	 * 
	 * @author Ben St&ouml;ver
	 * @since 2.0.0
	 */
	public static enum TestStrategy {
		/** 
		 * Files will only be filtered by their extension.
		 * <p>
		 * This option does not allow to distinguish between different file types, that may have the same extension, but is a lot 
		 * faster then the alternatives, since files do not have to be opened. This is especially true of folder with many files
		 * or folder on slow (network) resources shall be tested.
		 */
		EXTENSION,
		
		/** 
		 * Files will only be tested by their content. 
		 * <p>
		 * This option allow to distinguish file types by the file content and does not require them to have unique extensions. 
		 * Note that this option can be slow if many files are contained in a folder or a folder is located on a slow resource, 
		 * since each file needs to be opened.
		 */
		CONTENT,
		
		/** 
		 * Files will first be selected by their extension and those will additionally be tested by their content.
		 * <p>
		 * This option is a compromise between the other two. It is faster than {@link #CONTENT} since only files with
		 * an according extension need to be opened and still allows to determine between file types, that may share an 
		 * extension. It should always be preferred over {@link #CONTENT} if files can be expected to have one of the defined
		 * extensions.
		 */
		BOTH;
	}
	
	
	private TestStrategy testStrategy;
	private boolean acceptFilesWithExceptions;
	
	
	/**
	 * Creates a new instance of this class.
	 * <p>
	 * All file extension parameters should be specified without a leading {@code '.'}.
	 * 
	 * @param description the description of this file type to be displayed e.g. in open dialogs
	 * @param defaultExtension the default extension of this file format
	 * @param addExtensionListToDescription Specify {@code true} here, if a list of valid extensions shall be appended
	 *        on the specified description in parentheses.
	 * @param testStrategy the strategy to be used to accept a file or not
	 * @param extensions optional alternative extensions can be specified here
	 * @throws NullPointerException if {@code description} or any specified extension are {@code null}
 	 * @throws IllegalArgumentException if not at least one extension has been specified
 	 */
	public ContentExtensionFileFilter(String description, boolean addExtensionListToDescription,
					TestStrategy testStrategy, boolean acceptFilesWithExceptions, String... extensions) {
		
	  super(description, addExtensionListToDescription, extensions);
	  this.testStrategy = testStrategy;
	  this.acceptFilesWithExceptions = acceptFilesWithExceptions;
  }


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
	 * @throws IllegalArgumentException if not at least one extension has been specified
	 */
	public ContentExtensionFileFilter(String description, boolean addExtensionListToDescription,
					String... extensions) {
		
		this(description, addExtensionListToDescription, TestStrategy.BOTH, false, extensions);
	}
	
	
	/**
	 * The strategy that is currently used to determine whether a file shall be accepted by this filter or not.
	 * 
	 * @return the current test strategy
	 */
	public TestStrategy getTestStrategy() {
		return testStrategy;
	}


	/**
	 * Specifies a new test strategy to determine whether a file shall be accepted by this filter or not.
	 * 
	 * @param testStrategy the test strategy to be used from now on
	 * @throws if {@code null} is provided as the new test strategy
	 */
	public void setTestStrategy(TestStrategy testStrategy) {
		if (testStrategy == null) {
			throw new NullPointerException("The test strategy must not be null.");
		}
		else {
			this.testStrategy = testStrategy;
		}
	}
	
	
	/**
	 * Determines whether this filter accepts files where {@link IOException}s were thrown while they were tested
	 * with {@link #acceptContent(BufferedInputStream)}. 
	 * 
	 * @return {@code true} if such files shall be accepted or {@code false} otherwise
	 */
	public boolean isAcceptFilesWithExceptions() {
		return acceptFilesWithExceptions;
	}


	/**
	 * Specifies whether this filter accepts files where {@link IOException}s were thrown while they were tested
	 * with {@link #acceptContent(BufferedInputStream)}. 
	 * 
	 * @param acceptFilesWithExceptions Specify {@code true} here, if such files shall be accepted or {@code false} otherwise
	 */
	public void setAcceptFilesWithExceptions(boolean acceptFilesWithExceptions) {
		this.acceptFilesWithExceptions = acceptFilesWithExceptions;
	}


	/**
	 * Tests the contents of the specified file. It calls {@link #acceptContent(BufferedInputStream)} internally, if the specified
	 * file is not a directory.
	 * <p>
	 * Inherited classes can overwrite this method, if additional checks shall be performed on the file. In some cases it may
	 * be useful to check in here, if the file extension is unique or not before calling 
	 * 
	 * @param file the file to be tested
	 * @return {@code true} if the specified file is accepted or {@code false} otherwise
	 */
	protected boolean acceptContent(File file) {
		if (file.isDirectory()) {
			return true;
		}
		else if (!file.exists()) {
			return true;  //TODO Does this make sense for save dialogs?
		}
		else {
			try {
				boolean result;
				FileInputStream stream = new FileInputStream(file);
				try {
					result = acceptContent(stream);
				}
				finally {
					stream.close();
				}
				return result;
			}
			catch (Exception e) {
				return isAcceptFilesWithExceptions();
			}
		}
	}
	
	
	/**
	 * Inherited classes must implement this method to test the content of a file. Note that buffering this stream (e.g. using
	 * an instance of {@link BufferedInputStream} way significantly speed up testing, which may be relevant in folders containing
	 * many files or folders on slow (network) resources.
	 * 
	 * @param stream the input stream providing the content of the file to be tested
	 * @return {@code true} if the specified content is accepted or {@code false} otherwise
	 */
	protected abstract boolean acceptContent(FileInputStream stream) throws Exception;
	
	
	@Override
  public boolean accept(File f) {
		switch (getTestStrategy()) {
			case EXTENSION:
				return super.accept(f);
			case CONTENT:
				return acceptContent(f);
			case BOTH:
				return super.accept(f) && acceptContent(f);
			default:
				throw new InternalError("Unsupported test strategy " + getTestStrategy() + ".");
		}
	}
}
