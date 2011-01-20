package info.webinsel.util;


import org.junit.* ;


import static org.junit.Assert.* ;



public class Math2Test {
	@Test
  public void test_sum1ToN() {
		assertEquals(0, Math2.sum1ToN(0));
		assertEquals(10, Math2.sum1ToN(4));
		assertEquals(15, Math2.sum1ToN(5));
		assertEquals(55, Math2.sum1ToN(10));
	}

	
	@Test(expected=ArithmeticException.class)
  public void test_sum1ToN_exception() {
		Math2.sum1ToN(-1);
	}
}
