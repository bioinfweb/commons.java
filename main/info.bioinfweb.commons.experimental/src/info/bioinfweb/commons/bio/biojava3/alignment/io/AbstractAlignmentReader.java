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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.biojava3.core.sequence.io.template.SequenceCreatorInterface;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



public abstract class AbstractAlignmentReader<S extends Sequence<C>, C extends Compound> 
    implements AlignmentReader<S, C> {
	
	private SequenceCreatorInterface<C> sequenceCreator;
	
	
	public AbstractAlignmentReader(SequenceCreatorInterface<C> sequenceCreator) {
		super();
		this.sequenceCreator = sequenceCreator;
	}


	protected SequenceCreatorInterface<C> getSequenceCreator() {
		return sequenceCreator;
	}


	@Override
	public Alignment<S, C> read(File file) throws Exception {
		InputStream stream = new BufferedInputStream(new FileInputStream(file));
		Alignment<S, C> result = null; 
		try {
			result = read(stream);
		}
		finally {
			stream.close();
		}
		return result;
	}
}
