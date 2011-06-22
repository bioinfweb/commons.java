package info.webinsel.util;


import org.junit.* ;


import static org.junit.Assert.* ;



public class StringUtilsTest {
  @Test
  public void test_firstCharToLowerCase() {
  	assertEquals("content", StringUtils.firstCharToLowerCase("Content"));
  }
  
  
  @Test
  public void test_firstCharToUpperCase() {
  	assertEquals("Content", StringUtils.firstCharToUpperCase("content"));
  }
  
  
  @Test
  public void test_convertCamelCase() {
  	assertEquals("an-example-keyword", StringUtils.convertCamelCase("anExampleKeyword", "-"));
  }
}
