package info.bioinfweb.biojava3.core.sequence.io;


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
