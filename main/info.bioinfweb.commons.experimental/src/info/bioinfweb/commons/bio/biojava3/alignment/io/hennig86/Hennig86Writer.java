/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.bio.biojava3.alignment.io.hennig86;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import info.bioinfweb.commons.SystemUtils;
import info.bioinfweb.commons.bio.biojava3.alignment.io.AbstractAlignmentWriter;
import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Writes an alignment to a Hennig86 file. (Note that no character set information will be written.)
 * 
 * Optionally a {@code nstates} command for <i>TNT</i> can be written depending on the constructor that is used.
 * 
 * @author Ben St&ouml;ver
 */
public class Hennig86Writer<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentWriter<S, C> {
	public static final String COMMAND_TERMINATOR = ";";
	public static final String NO_OF_STATES_COMMAND = "nstates";
	public static final String MATRIX_COMMAND = "xread";
	public static final String DATATYPE_DNA = "dna";
	public static final String DATATYPE_PROTEIN = "prot";
	public static final char WHITESPACE_REPLACEMENT = '_';

	
	private String noOfStates = null;
	private int maxTaxonNameLength = -1;
	
	
	public Hennig86Writer() {
		this(null);
	}


	public Hennig86Writer(String noOfStates) {
		super();
		getNameMap().getParameters().getReplacements().put("\\s", Character.toString(WHITESPACE_REPLACEMENT));
		this.noOfStates = noOfStates;
	}


	public String getNoOfStates() {
		return noOfStates;
	}


	public void setNoOfStates(String noOfStates) {
		this.noOfStates = noOfStates;
	}


	public int getMaxTaxonNameLength() {
		return maxTaxonNameLength;
	}


	public void setMaxTaxonNameLength(int maxTaxonNameLength) {
		this.maxTaxonNameLength = maxTaxonNameLength;
	}


	public Hennig86Writer(int noOfStates) {
		super();
		this.noOfStates = Integer.toString(noOfStates);
	}


	@Override
	public void write(Alignment<S, C> alignment, OutputStream stream)	throws Exception {
		write(alignment, stream, null);
	}
	
	
	public void write(Alignment<S, C> alignment, File file, String title)	throws Exception {
		OutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		try {
			write(alignment, stream, title);
		}
		finally {
			stream.close();
		}
	}
	
	
	public void write(Alignment<S, C> alignment, OutputStream stream, String title)	throws Exception {
		getNameMap().clear();
		BufferedOutputStream out = new BufferedOutputStream(stream);
		try {
			final byte[] LINE_SEPARATOR = SystemUtils.LINE_SEPARATOR.getBytes();
			
			if (noOfStates != null) {
				out.write((NO_OF_STATES_COMMAND + " " + noOfStates + COMMAND_TERMINATOR).getBytes());
				out.write(LINE_SEPARATOR);
			}
			
			out.write(MATRIX_COMMAND.getBytes());
      out.write(LINE_SEPARATOR);
      if (title != null) {
      	out.write(("'" + title + "'").getBytes());
        out.write(LINE_SEPARATOR);
      }
      out.write((Integer.toString(alignment.maxSequenceLength()) + " " + Integer.toString(alignment.size())).getBytes());
      out.write(LINE_SEPARATOR);
			
			Iterator<String> iterator = alignment.nameIterator();
			while (iterator.hasNext()) {
				String name = iterator.next();
				String modifiedName = getNameMap().addName(name);
	      out.write((modifiedName + " ").getBytes());
        out.write(alignment.getSequence(name).getSequenceAsString().getBytes());
        out.write(LINE_SEPARATOR);
			}
      out.write(";".getBytes());
		}
		finally {
			out.close();
		}
	}
}
