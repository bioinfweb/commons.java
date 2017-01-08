/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.bio.biojava3.alignment.io.mega;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import info.bioinfweb.commons.bio.biojava3.alignment.io.AbstractAlignmentWriter;
import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Writes an alignment to a MEGA file. (Note that no character set information will be written.)
 * 
 * @author Ben St&ouml;ver
 */
public class MEGAWriter<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentWriter<S, C> {
	public static final String DATATYPE_DNA = "DNA";
	public static final String DATATYPE_PROTEIN = "Protein";
	public static final String DEFAULT_TITLE = "Alignment generated with bioinfweb AlignmentIO";
	public static final char DEFAULT_GAP_CHAR = '-';
	public static final int LINE_LENGTH = 80;
	public static final byte[] LINE_SEPARATOR = "\r\n".getBytes();  // Always use windows separator because there are only windows versions of MEGA.
	
	
	private String datatype;
	private char gapCharacter;
	
	
	public MEGAWriter(String datatype, char gapCharacter) {
		super();
		getNameMap().getParameters().getReplacements().put("\\s", Character.toString(WHITESPACE_REPLACEMENT));
		this.datatype = datatype;
		this.gapCharacter = gapCharacter;
	}


	public MEGAWriter(String datatype) {
		this(datatype, DEFAULT_GAP_CHAR);
	}


	@Override
	public void write(Alignment<S, C> alignment, OutputStream stream)	throws Exception {
		write(alignment, stream, DEFAULT_TITLE);
	}
	
	
	public void write(Alignment<S, C> alignment, File file, String title)	throws Exception {
		OutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		try {
			write(alignment, stream, DEFAULT_TITLE);
		}
		finally {
			stream.close();
		}
	}
	
	
	public void write(Alignment<S, C> alignment, OutputStream stream, String title)	throws Exception {
		getNameMap().clear();
		BufferedOutputStream out = new BufferedOutputStream(stream);
		try {
			out.write("#mega".getBytes());
      out.write(LINE_SEPARATOR);
			out.write(("!Title " + title + ";").getBytes());
      out.write(LINE_SEPARATOR);
			out.write(("!Format DataType=" + datatype + " indel=" + gapCharacter + ";").getBytes());
      out.write(LINE_SEPARATOR);
      out.write(LINE_SEPARATOR);
			
			Iterator<String> iterator = alignment.nameIterator();
			while (iterator.hasNext()) {
				String name = iterator.next();
				String modifiedName = getNameMap().addName(name);
	      out.write('#');
	      out.write(modifiedName.getBytes());
	      out.write(LINE_SEPARATOR);
	
	      int compoundCount = 0;
	      String seq = alignment.getSequence(name).getSequenceAsString();
	      for (int i = 0; i < seq.length(); i++) {
	          out.write(seq.charAt(i));
	          compoundCount++;
	          if (compoundCount == LINE_LENGTH) {
	              out.write(LINE_SEPARATOR);
	              compoundCount = 0;
	          }
	      }
	      if ((seq.length() % LINE_LENGTH) != 0) {
	          out.write(LINE_SEPARATOR);
	      }
			}
		}
		finally {
			out.close();
		}
	}
}
