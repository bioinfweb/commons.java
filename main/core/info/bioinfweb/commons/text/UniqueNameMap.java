/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.text;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;



public class UniqueNameMap {
	private Map<String, String> nameMap = new TreeMap<String, String>();
  private UniqueNameMapParameters parameters;
  
  
	public UniqueNameMap(UniqueNameMapParameters parameters) {
		super();
		this.parameters = parameters;
	}


  public UniqueNameMapParameters getParameters() {
		return parameters;
	}


	protected Map<String, String> getMap() {
  	return nameMap;
  }
  
  
  /**
   * Adds an original name as the key and its processed version as the values to the map. 
   * 
   * @param name - the original name (stored as the key in the new map entry)
   * @return the processed name (stored as the value in the new map entry)
   */
	public String addName(String name) {
		UniqueNameMapParameters params = getParameters();
  	String result = name;
  	if (params.hasReplacements()) {
  		Iterator<String> iterator = params.getReplacements().keySet().iterator();
  		while (iterator.hasNext()) {
  			String keyPattern = iterator.next();
  			result = name.replaceAll(keyPattern, params.getReplacements().get(keyPattern));
  		}
  	}
  	
  	if (!params.isUnlimitedLength()) {
  		if (result.length() > params.getMaxNameLength()) {
	      result = result.substring(0, params.getMaxNameLength());
	    } 
	    else if ((result.length() < params.getMaxNameLength()) && params.isFillUp()) {
	      StringBuffer buffer = new StringBuffer(params.getMaxNameLength());
	      buffer.append(result);
	      while (buffer.length() < params.getMaxNameLength()) {
	        buffer.append(" ");
	      }
	      result = buffer.toString();
	    }
  	}
    
  	if (!name.equals(result)) {
	    int index = 1;
	    while (getMap().containsKey(result)) {
	    	String indexStr = "" + index;
	    	result = result.substring(0, params.getMaxNameLength() - indexStr.length()) + indexStr;
	    	index++;
	    }
	    getMap().put(result, name);
  	}
    
    return result;
  }
	
	
	public void addNames(Collection<String> names) {
		Iterator<String> iterator = names.iterator();
		while (iterator.hasNext()) {
			addName(iterator.next());
		}
	}
	
	
	public String getProcessedName(String originalName) {
		return nameMap.get(originalName);
	}


	public void clear() {
		nameMap.clear();
	}


	public boolean containsKey(Object key) {
		return nameMap.containsKey(key);
	}


	public boolean containsValue(Object value) {
		return nameMap.containsValue(value);
	}


	public Set<Entry<String, String>> entrySet() {
		return nameMap.entrySet();
	}


	public boolean isEmpty() {
		return nameMap.isEmpty();
	}


	public Set<String> keySet() {
		return nameMap.keySet();
	}


	public String remove(Object key) {
		return nameMap.remove(key);
	}


	public int size() {
		return nameMap.size();
	}


	public Collection<String> values() {
		return nameMap.values();
	}


	/**
   * Writes the contents of the current name map in text format as tab separated values. Every line
   * contains one name pair, where the first column contains the processed name and the second column 
   * the original name.
   * 
   * @param stream - the stream to write the table to
   */
  public void writeNameMap(OutputStream stream) {
  	PrintWriter writer = new PrintWriter(stream);
  	Iterator<String> iterator = nameMap.keySet().iterator();
  	while (iterator.hasNext()) {
  		String processedName = iterator.next();
  		writer.println(processedName + "\t" + nameMap.get(processedName));
  	}
  	writer.flush();
  }
  
  
  /**
   * Writes the contents of the current name map in text format as tab separated values. Every line
   * contains one name pair, where the first column contains the processed name and the second column 
   * the original name.
   * 
   * @param file - the file to write the table to
   * @throws FileNotFoundException - if the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
   * @throws SecurityException - if a security manager exists and its checkWrite method denies write access to the file.
   */
  public void writeNameMap(File file) throws FileNotFoundException, IOException {
  	BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file)); 
  	try {
  		writeNameMap(stream);
  	}
  	finally {
  		stream.close();
  	}
  }
}
