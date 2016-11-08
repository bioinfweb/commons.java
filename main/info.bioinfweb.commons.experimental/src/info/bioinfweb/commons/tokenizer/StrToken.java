/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.tokenizer;



public class StrToken {
  private int id = -1;
  private String content = "";
  private int pos = -1;
  
  
	public StrToken() {
		super();
	}


	public StrToken(int id, String content, int pos) {
		super();
		this.id = id;
		this.content = content;
		this.pos = pos;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getID() {
		return id;
	}


	public void setID(int id) {
		this.id = id;
	}



	public int getPos() {
		return pos;
	}


	public void setPos(int pos) {
		this.pos = pos;
	}
}
