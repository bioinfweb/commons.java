package info.bioinfweb.biojava3.alignment.io;


import info.bioinfweb.biojava3.alignment.template.Alignment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;



public abstract class AbstractAlignmentWriter implements AlignmentWriter {
	@Override
	public void write(Alignment alignment, File file) throws Exception {
		write(alignment, new BufferedOutputStream(new FileOutputStream(file)));
	}
}
