package info.webinsel.util;


import org.junit.* ;


import static org.junit.Assert.* ;



public class StringUtilsTest {
  @Test
  public void test_firstCharToLowerCase() {
  	assertEquals("content", StringUtils.firstCharToLowerCase("Content"));
  	assertEquals("", StringUtils.firstCharToLowerCase(""));
  	assertEquals("c", StringUtils.firstCharToLowerCase("C"));
  }
  
  
  @Test
  public void test_firstCharToUpperCase() {
  	assertEquals("Content", StringUtils.firstCharToUpperCase("content"));
  	assertEquals("", StringUtils.firstCharToUpperCase(""));
  	assertEquals("C", StringUtils.firstCharToUpperCase("c"));
  }
  
  
  @Test
  public void test_convertCamelCase() {
  	assertEquals("an-example-keyword", StringUtils.convertCamelCase("anExampleKeyword", "-"));
  	assertEquals("", StringUtils.convertCamelCase("", "-"));
  	assertEquals("a", StringUtils.convertCamelCase("A", "-"));
  }
}
