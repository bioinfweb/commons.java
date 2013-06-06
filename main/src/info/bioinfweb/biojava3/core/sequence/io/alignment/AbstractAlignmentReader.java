package info.bioinfweb.biojava3.core.sequence.io.alignment;

import info.bioinfweb.biojava3.core.sequence.template.Alignment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;



public abstract class AbstractAlignmentReader implements AlignmentReader {
	@Override
	public Alignment read(File file) throws Exception {
		return read(new BufferedInputStream(new FileInputStream(file)));
	}
}
