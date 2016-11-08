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
package info.bioinfweb.commons.bio.biojava3.core.sequence.io;


import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.io.DNASequenceCreator;
import org.biojava3.core.sequence.io.FastaReader;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.sequence.io.GenericFastaHeaderParser;



/**
 * Tool class that provides additional functionality to read FASTA files, that {@link FastaReaderHelper}
 * does not offer.
 * 
 * @author Ben St&ouml;ver
 */
public class FastaReaderTools {
	/**
	 * Reads an alignment contained in a FASTA file. In contrast to 
	 * {@link FastaReaderHelper#readFastaDNASequence(InputStream)} the {@link AlignmentAmbiguityNucleotideCompoundSet}
	 * is used here, so that alignments containing gaps and ambiguity codes can also be processed.
	 * 
	 * @param file - the file to read the data from
	 * @return a map with the sequence names as keys and the sequences as values
	 * @throws IOException
	 */
	public static Map<String, DNASequence> readDNAAlignment(File file) throws IOException {
		return readDNAAlignment(new FileInputStream(file));
	}
	
	
	/**
	 * Reads an alignment contained in a FASTA file. In contrast to 
	 * {@link FastaReaderHelper#readFastaDNASequence(InputStream)} the {@link AlignmentAmbiguityNucleotideCompoundSet}
	 * is used here, so that alignments containing gaps and ambiguity codes can also be processed.
	 * 
	 * @param stream - the stream to read the data from
	 * @return a map with the sequence names as keys and the sequences as values
	 * @throws IOException
	 */
	public static Map<String, DNASequence> readDNAAlignment(InputStream stream) throws IOException {
  	FastaReader<DNASequence, NucleotideCompound> fastaReader = 
  	    new FastaReader<DNASequence, NucleotideCompound>(
    	  		new BufferedInputStream(stream),
    	  		new GenericFastaHeaderParser<DNASequence, NucleotideCompound>(),
            new DNASequenceCreator(new AlignmentAmbiguityNucleotideCompoundSet())); 
		return fastaReader.process();
	}
}
