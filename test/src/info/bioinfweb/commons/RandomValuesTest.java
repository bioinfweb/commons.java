package info.bioinfweb.commons;


import java.util.ArrayList;
import java.util.List;

import org.junit.* ;


import static org.junit.Assert.* ;



public class RandomValuesTest {
	@Test
	public void test_listSwap() {
		List<Integer> list = new ArrayList<Integer>(10);
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		RandomValues.listSwap(list, 10);
		
		int changeCount = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != i) {
				changeCount++;
			}
		}
		assertTrue("Changes: " + changeCount + " (Note that this test might ramdomly fail in very few cases.)", changeCount > 0);
		
//		System.out.print("listSwap: ");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print(list.get(i) + " ");
//		}
//		System.out.println();
	}
}
