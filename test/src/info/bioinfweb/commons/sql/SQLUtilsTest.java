package info.bioinfweb.commons.sql;


import java.util.EnumSet;

import org.junit.* ;


import static org.junit.Assert.* ;



public class SQLUtilsTest {
	private static enum TestEnum {
		A, B_1, B_2;
	}
	
	
  @Test
  public void test_readEnumSet() {
  	EnumSet<TestEnum> set = SQLUtils.readEnumSet("A,B_1", TestEnum.class);
  	assertTrue(set.contains(TestEnum.A));
  	assertTrue(set.contains(TestEnum.B_1));
  	assertFalse(set.contains(TestEnum.B_2));

  	set = SQLUtils.readEnumSet("A, B_1", TestEnum.class);
  	assertTrue(set.contains(TestEnum.A));
  	assertTrue(set.contains(TestEnum.B_1));
  	assertFalse(set.contains(TestEnum.B_2));
  	
  	set = SQLUtils.readEnumSet("", TestEnum.class);
  	assertTrue(set.isEmpty());
  	
  	assertNull(SQLUtils.readEnumSet(null, TestEnum.class));
  }
}
