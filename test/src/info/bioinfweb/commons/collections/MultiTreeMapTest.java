package info.bioinfweb.commons.collections;


import org.junit.* ;


import static org.junit.Assert.* ;



public class MultiTreeMapTest {
	public static void main(String[] args) {
  	MultiTreeMap<Integer, String> map = new MultiTreeMap<Integer, String>();
  	map.put(1, "1.1");
  	map.put(1, "1.2");
  	map.put(4, "4.1");
  	System.out.println(map.get(1));
  	System.out.println(map.get(4));
  	System.out.println(map.ceilingEntry(2));
  	System.out.println(map.floorEntry(2));
  	
	}
}
