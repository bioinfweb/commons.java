package info.bioinfweb.commons.bio.alignment.pairwise;


import static org.junit.Assert.*;


import org.junit.Test;



/**
 * Test class for {@link NeedlemanWunschAligner}.
 * 
 * @author Ben St&ouml;ver
 */
public class NeedlemanWunschAlignerTest {
  @Test
  public void test_align() {
  	CharSequence[] result = new NeedlemanWunschAligner().align("ATCGATTATTATTATTACGTTGAC", "ATCGATTATTATTACGTTGAC");
  	assertEquals("ATCGATTATTATTATTACGTTGAC", result[0].toString());
  	assertEquals("ATCG---ATTATTATTACGTTGAC", result[1].toString());
  }
}
