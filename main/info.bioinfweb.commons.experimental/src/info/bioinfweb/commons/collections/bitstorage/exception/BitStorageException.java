/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.collections.bitstorage.exception;


import info.bioinfweb.commons.collections.bitstorage.BitField;



/**
 * Exceptions thrown by an implementation of {@link BitField} should implement this interface.
 * 
 * @author Ben St&ouml;ver
 */
public interface BitStorageException {
  /**
   * Returns the bit field where the exception occurred.
   * 
   * @return the instance throwing this exception
   */
  public BitField getSource();	
}
