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


import java.io.File;



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
	 */
	public ContentExtensionFileFilter(String description, String defaultExtension, boolean addExtensionListToDescription,
					TestStrategy testStrategy, String... extensions) {
		
	  super(description, defaultExtension, addExtensionListToDescription, extensions);
	  this.testStrategy = testStrategy;
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
	 */
	public ContentExtensionFileFilter(String description, String defaultExtension, boolean addExtensionListToDescription,
					String... extensions) {
		
		this(description, defaultExtension, addExtensionListToDescription, TestStrategy.BOTH, extensions);
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
			throw new NullPointerException("The test strategy must not be null-.");
		}
		else {
			this.testStrategy = testStrategy;
		}
	}
	
	
	/**
	 * Inherited classes must implement this method by testing the content of the specified file.
	 * <p>
	 * It is also the responsibility of the implementing class to handle exceptions that may occur while processing the file 
	 * and decide whether files producing exceptions should be accepted or not. (Usually such files will not be accepted,
	 * but this API does not prescribe this behavior.)
	 * 
	 * @param file the file to be tested
	 * @return {@code true} if the specified file is accepted or {@code false} otherwise
	 */
	protected abstract boolean acceptContent(File file);


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
