package info.bioinfweb.biojava3.core.sequence.template;


import info.webinsel.util.collections.SequenceIntervalList;

import java.util.Map;

import org.biojava3.alignment.template.Profile;



/**
 * Classes implementing this interface represent an aligned set of sequences. Additionally the alignment
 * can be annotated by character sets as defined in the Nexus format.
 * 
 * Although this interface is based on BioJava 3, it differs from {@link Profile} since alignment gaps
 * are treated the same way as nucleotides here. In the future there might be an alternative version 
 * extending {@link Profile}.
 * 
 * @author Ben St&ouml;ver
 */
public class Alignment {
  //private Map<String, SequenceIntervalList<E>>
}
