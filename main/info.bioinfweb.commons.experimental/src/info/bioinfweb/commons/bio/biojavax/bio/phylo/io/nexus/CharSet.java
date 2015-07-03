package info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus;


import info.bioinfweb.commons.collections.NonOverlappingIntervalList;



/**
 * Stores a label of a part of a sequence (e.g. read from the Nexus <code>CHARSET</code> 
 * command.)
 * 
 * @author Ben St&ouml;ver
 */
public class CharSet extends NonOverlappingIntervalList {
	private String name;
	
	
	public CharSet(String name) {
		super();
		this.name = name;
	}


	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
}
