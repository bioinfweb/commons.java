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
package info.bioinfweb.commons.bio.biojava3.alignment.io;


import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;
import info.bioinfweb.commons.text.UniqueNameMap;
import info.bioinfweb.commons.text.UniqueNameMapParameters;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



public abstract class AbstractAlignmentWriter<S extends Sequence<C>, C extends Compound> 
    implements AlignmentWriter<S, C> {
	
  public static final char WHITESPACE_REPLACEMENT = '_'; 

  
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
