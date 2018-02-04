/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.bio.biojava3.core.sequence.io;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import org.biojava3.core.sequence.template.Sequence;



/**
 * Writes a map which contains sequence names as keys and sequences as values to a FASTA file.
 * 
 * @author Ben St&ouml;ver
 */
public class MapFastaWriter<S extends Sequence<?>> {
	private int lineLength = 80;
	private String lineSeparator = System.getProperty("line.separator");
	
	
  public int getLineLength() {
		return lineLength;
	}


	public void setLineLength(int lineLength) {
		this.lineLength = lineLength;
	}


	public String getLineSeparator() {
		return lineSeparator;
	}


	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}


	public void write(File file, Map<String, S> map) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);
		try {
			write(stream, map);	
		}
		finally {
			stream.close();
		}
	}
	
	
	public void write(OutputStream stream, Map<String, S> map) throws IOException {
  	Iterator<String> iterator = map.keySet().iterator();
  	while (iterator.hasNext()) {
  		String key = iterator.next();
  		stream.write('>');
  		stream.write(key.getBytes());
  		stream.write(getLineSeparator().getBytes());

      String seq = map.get(key).getSequenceAsString();
      int compoundCount = 0;
      for (int i = 0; i < seq.length(); i++) {
      	stream.write(seq.charAt(i));
        compoundCount++;
        if (compoundCount == getLineLength()) {
        	stream.write(getLineSeparator().getBytes());
          compoundCount = 0;
        }
      }
      if ((map.get(key).getLength() % getLineLength()) != 0) {
      	stream.write(getLineSeparator().getBytes());
      }
    }
  }
}
