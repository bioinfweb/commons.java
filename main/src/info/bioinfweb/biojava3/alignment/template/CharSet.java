package info.bioinfweb.biojava3.alignment.template;



public interface CharSet {
  public void addInterval(int firstPos, int lastPos);
  
  public void movePositions(int start);
  
  public boolean contains(int pos);
  
  
}
