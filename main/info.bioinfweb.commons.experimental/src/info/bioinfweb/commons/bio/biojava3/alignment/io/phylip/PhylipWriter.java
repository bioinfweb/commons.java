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
package info.bioinfweb.commons.bio.biojava3.alignment.io.phylip;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import info.bioinfweb.commons.bio.biojava3.alignment.io.AbstractAlignmentWriter;
import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;
import info.bioinfweb.commons.text.UniqueNameMap;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojavax.bio.phylo.io.phylip.PHYLIPFileFormat;



/**
 * Writes an alignment to a file in PHYLIP format.
 * (This class is based on the implementation of the BioJava 1.8 class {@link PHYLIPFileFormat} written by
 * Richard Holland, Tobias Thierer and Jim Balhoff, who provided the source code under LGPL.) 
 * 
 * @author Ben St&ouml;ver
 */
public class PhylipWriter<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentWriter<S, C> {
  public static final int DEFAULT_MAX_NAME_LENGTH = 10;
  
  
  public PhylipWriter() {
		this(true, DEFAULT_MAX_NAME_LENGTH);
	}


  public PhylipWriter(boolean replaceWhiteSpace) {
		this(replaceWhiteSpace, DEFAULT_MAX_NAME_LENGTH);
	}


	public PhylipWriter(boolean replaceWhiteSpace, int maxNameLength) {
		super();
		getNameMap().getParameters().setMaxNameLength(maxNameLength);
		if (replaceWhiteSpace) {
			getNameMap().getParameters().getReplacements().put("\\s", Character.toString(WHITESPACE_REPLACEMENT));
		}
		getNameMap().getParameters().setFillUp(true);
	}

	
	/**
   * Note that certain parameter sets for the specified name map might lead to an invalid output of this class. 
   * 
   * @param nameMap
   */
  public PhylipWriter(UniqueNameMap nameMap) {
		super(nameMap);
	}


	/**
   * Writes the specified alignment in sequential Phylip format.
   * 
   * @see info.bioinfweb.commons.bio.biojava3.alignment.io.AlignmentWriter#write(info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment, java.io.OutputStream)
   */
  @Override
	public void write(Alignment<S, C> alignment, OutputStream stream) throws Exception {
    getNameMap().clear();
    PrintWriter writer = new PrintWriter(stream);
    writer.println(" " + alignment.size() + " " + alignment.maxSequenceLength());
    Iterator<String> iterator = alignment.nameIterator();
    while (iterator.hasNext()) {
    	String name = iterator.next();
    	writer.println(getNameMap().addName(name) + " " + alignment.getSequence(name).getSequenceAsString());
    }
    writer.flush();		
	}
}
