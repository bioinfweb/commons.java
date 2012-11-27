package info.webinsel.util.collections;



/**
 * Classes that represent an interval of a sequence should implement this interface. Classes that do so can be used
 * with {@link SequenceIntervalList}.
 * 
 * @author Ben St&ouml;ver
 */
public interface SequenceInterval extends Comparable<SequenceInterval> {
  public int getFirstPos();

  public int getLastPos();
}
