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
package info.bioinfweb.commons.bio.biojava3.alignment.io.fasta;


import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;

import info.bioinfweb.commons.bio.biojava3.alignment.io.AbstractAlignmentWriter;
import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;
import info.bioinfweb.commons.text.UniqueNameMap;



/**
 * Writes an alignment to a FASTA file. (Note that no character set information will be written because the 
 * FASTA format does not support this.)
 * 
 * @author Ben St&ouml;ver
 */
public class FastaWriter<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentWriter<S, C> {
	public static final int LINE_LENGTH = 80;
	
	
	public FastaWriter() {
		super();
	}


	public FastaWriter(UniqueNameMap nameMap) {
		super(nameMap);
	}


	@Override
	public void write(Alignment<S, C> alignment, OutputStream stream)
			throws Exception {
		
		BufferedOutputStream out = new BufferedOutputStream(stream);
		try {
			Iterator<String> iterator = alignment.nameIterator();
			while (iterator.hasNext()) {
				String name = iterator.next();
	      out.write('>');
	      out.write(name.getBytes());
	      final byte[] SEPARATOR = System.getProperty("line.separator").getBytes();
	      out.write(SEPARATOR);
	
	      int compoundCount = 0;
	      String seq = alignment.getSequence(name).getSequenceAsString();
	      for (int i = 0; i < seq.length(); i++) {
	          out.write(seq.charAt(i));
	          compoundCount++;
	          if (compoundCount == LINE_LENGTH) {
	              out.write(SEPARATOR);
	              compoundCount = 0;
	          }
	      }
	      if ((seq.length() % LINE_LENGTH) != 0) {
	          out.write(SEPARATOR);
	      }
			}
		}
		finally {
			out.close();
		}
	}
}
