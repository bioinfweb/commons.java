package info.bioinfweb.biojavax.bio.phylo.io.nexus;


import java.util.Vector;



/**
 * Stores a label of a part of a sequence (e.g. read from the Nexus <code>CHARSET</code> 
 * command.)
 * 
 * @author Ben St&ouml;ver
 */
public class CharSet {
	public class Interval {
		private int start;
		private int end;
		
		
		public Interval(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}


		public int getEnd() {
			return end;
		}


		public void setEnd(int end) {
			this.end = end;
		}


		public int getStart() {
			return start;
		}


		public void setStart(int start) {
			this.start = start;
		}
	}
	
	
	private String name;
	private Vector<Interval> intervals = new Vector<Interval>();
	
	
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
	
	
	/**
	 * Adds a sequence interval to this character set. 
	 * @param start - the first position of the new interval
	 * @param end - the first position after the new interval
	 */
	public void addInterval(int start, int end) {
		intervals.add(new Interval(start, end));
		//TODO Es wäre auch denkbar Intervalle auf Überlappung zu prüfen und Einträge der Liste entsprechend zu verändern. (getIntervals() müsste dann wegfallen.)
	}
	
	
	public Vector<Interval> getIntervals() {
		return intervals;
	}


	public boolean containsPosition(int pos) {
		for (int i = 0; i < intervals.size(); i++) {
			if ((pos >= intervals.get(i).start) && (pos < intervals.get(i).end)) {
				return true;
			}
		}
		return false;
	}
}
