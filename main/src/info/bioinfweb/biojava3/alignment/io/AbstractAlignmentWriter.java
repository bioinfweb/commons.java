package info.bioinfweb.biojava3.alignment.io;


import info.bioinfweb.biojava3.alignment.template.Alignment;
import info.webinsel.util.text.UniqueNameMap;
import info.webinsel.util.text.UniqueNameMapParameters;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



public abstract class AbstractAlignmentWriter<S extends Sequence<C>, C extends Compound> 
    implements AlignmentWriter<S, C> {
	
  public static char WHITESPACE_REPLACEMENT = '_'; 

  
	private UniqueNameMap nameMap;
	
	
	public AbstractAlignmentWriter(UniqueNameMap nameMap) {
		super();
		this.nameMap = nameMap;
	}


	public AbstractAlignmentWriter() {
		this(new UniqueNameMap(new UniqueNameMapParameters()));
	}


	@Override
	public UniqueNameMap getNameMap() {
		return nameMap;
	}


	@Override
	public void write(Alignment<S, C> alignment, File file) throws Exception {
		OutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		try {
			write(alignment, stream);
		}
		finally {
			stream.close();
		}
	}
}
