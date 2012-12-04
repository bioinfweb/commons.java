package info.webinsel.util.collections;

import java.util.Comparator;



public interface SequenceIntervalPositionAdapter<T> extends Comparator<T> {
  public int getFirstPos(T o);
  
  public int getLastPos(T o);

  public void setFirstPos(T o, int newFirstPos);
  
  public void setLastPos(T o, int newLastPos);
}
