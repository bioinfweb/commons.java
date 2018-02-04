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
package info.bioinfweb.commons.collections.bitstorage;

import info.bioinfweb.commons.collections.bitstorage.exception.BitIndexOutOfBoundException;



/**
 * Interface that allows to read and write primitive data types from and to a field of bits.
 * Implementing classes can be used to store huge amounts of data into the RAM without each
 * element using more bits than necessary.
 * 
 * @author Ben St&ouml;ver
 */
public interface BitField {
	//TODO Document the rest
  /**
   * Inserts the specified number of bits at the specified position of the bit field and moves all bits
   * with indices {@code >= start} to the right. The new bit area is not necessarily initialized.
   * 
   * @param start - the index where the first bit shall be inserted
   * @param numberOfBits - the number of bits to be inserted
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public void insertBits(long start, long numberOfBits) throws BitIndexOutOfBoundException;

  /**
   * Removes the specified number of bits from the specified position of the bit field and moves all bits
   * with indices {@code >= start + numberOfBits} to the left.
   * 
   * @param start - the index where the first bit shall be inserted
   * @param numberOfBits - the number of bits to be inserted
   */
  public void removeBits(long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  /**
   * Copies the specified number of bits from one position in the bit field to another.
   * 
   * @param sourceStart - the index of the first bit to be copied
   * @param numberOfBits - the number of bits to be copied
   * @param destStart - the index where the left most bit of the copied area shall be written to
   * @throws BitIndexOutOfBoundException if {@code sourceStart} or {@code sourceStart} are lower than 0 or
   *         {@code sourceStart + numberOfBits} or {@code sourceStart + numberOfBits} are greater than 
   *         {@link #length()} or if the source and the destination area overlap
   */
  public void copyBits(long sourceStart, long numberOfBits, long destStart) throws BitIndexOutOfBoundException;
  
  /**
   * Moves the specified number of bits from one position in the bit field to another. This method acts similar
   * to {@link #copyBits(long, long, long)} with the only difference that the source and the destination area
   * are allowed to overlap.
   * 
   * @param sourceStart - the index of the first bit to be copied
   * @param numberOfBits - the number of bits to be copied
   * @param destStart - the index where the left most bit of the moved area shall be written to
   * @throws BitIndexOutOfBoundException if {@code sourceStart} or {@code sourceStart} are lower than 0 or
   *         {@code sourceStart + numberOfBits} or {@code sourceStart + numberOfBits} are greater than 
   *         {@link #length()}
   */
  public void moveBits(long sourceStart, long numberOfBits, long destStart) throws BitIndexOutOfBoundException;

  /**
   * Returns the current length of the bit field.
   * 
   * @return the length of the field in bits
   */
  public long length();
  
  /**
   * Reads a signed integer with the specified length and returns it as a {@code byte} value.
   * 
   * @param start - the index of the bit in the bit field that shall be interpreted as the beginning of
   *        the integer value
   * @param numberOfBits - the number of bits to be read (The first left most bit determines the 
   *        algebraic sign.)
   * @return the read bits interpreted as an signed integer
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public byte readByte(long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  /**
   * Reads an unsigned integer with the specified length and returns it as a {@code byte} value.
   * 
   * @param start - the index of the bit in the bit field that shall be interpreted as the beginning of
   *        the integer value
   * @param numberOfBits - the number of bits to be read
   * @return the read bits interpreted as an unsigned integer
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public byte readUnsignedByte(long start, long numberOfBits) throws BitIndexOutOfBoundException;

  /**
   * 
   * 
   * @param value
   * @param start
   * @param numberOfBits
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public void writeByte(byte value, long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  public void writeUnsignedByte(byte value, long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  
  /**
   * Reads a signed integer with the specified length and returns it as a {@code short} value.
   * 
   * @param start - the index of the bit in the bit field that shall be interpreted as the beginning of
   *        the integer value
   * @param numberOfBits - the number of bits to be read (The first left most bit determines the 
   *        algebraic sign.)
   * @return the read bits interpreted as an signed integer
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public short readShort(long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  /**
   * Reads an unsigned integer with the specified length and returns it as a {@code short} value.
   * 
   * @param start - the index of the bit in the bit field that shall be interpreted as the beginning of
   *        the integer value
   * @param numberOfBits - the number of bits to be read
   * @return the read bits interpreted as an unsigned integer
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public short readUnsignedShort(long start, long numberOfBits) throws BitIndexOutOfBoundException;

  public void writeShort(short value, long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  public void writeUnsignedShort(short value, long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  
  /**
   * Reads a signed integer with the specified length and returns it as a {@code int} value.
   * 
   * @param start - the index of the bit in the bit field that shall be interpreted as the beginning of
   *        the integer value
   * @param numberOfBits - the number of bits to be read (The first left most bit determines the 
   *        algebraic sign.)
   * @return the read bits interpreted as an signed integer
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public int readInt(long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  /**
   * Reads an unsigned integer with the specified length and returns it as a {@code int} value.
   * 
   * @param start - the index of the bit in the bit field that shall be interpreted as the beginning of
   *        the integer value
   * @param numberOfBits - the number of bits to be read
   * @return the read bits interpreted as an unsigned integer
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public int readUnsignedInt(long start, long numberOfBits) throws BitIndexOutOfBoundException;

  public void writeInt(int value, long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  public void writeUnsignedInt(int value, long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  
  /**
   * Reads a signed integer with the specified length and returns it as a {@code long} value.
   * 
   * @param start - the index of the bit in the bit field that shall be interpreted as the beginning of
   *        the integer value
   * @param numberOfBits - the number of bits to be read (The first left most bit determines the 
   *        algebraic sign.)
   * @return the read bits interpreted as an signed integer
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public long readLong(long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  /**
   * Reads an unsigned integer with the specified length and returns it as a {@code long} value.
   * 
   * @param start - the index of the bit in the bit field that shall be interpreted as the beginning of
   *        the integer value
   * @param numberOfBits - the number of bits to be read
   * @return the read bits interpreted as an unsigned integer
   * @throws BitIndexOutOfBoundException if {@code start} is lower than 0 or {@code start + numberOfBits} 
   *         is greater than {@link #length()}
   */
  public long readUnsignedLong(long start, long numberOfBits) throws BitIndexOutOfBoundException;

  public void writeLong(long value, long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  public void writeUnsignedLong(long value, long start, long numberOfBits) throws BitIndexOutOfBoundException;
  
  
  /**
   * Interprets the bit at the specified position as a boolean value.
   * 
   * @param position - the position of the bit to be read
   * @return {@code true} if the specified bit is set, {@code false} otherwise
   * @throws BitIndexOutOfBoundException if {@code position} is lower than 0 or greater or equal to 
   *         {@link #length()}
   */
  public boolean readBoolean(long position) throws BitIndexOutOfBoundException;

  /**
   * Sets the bit at the specified position according the specified boolean value.
   * 
   * @param value - Determine whether the bit shall be set to 0 or 1
   * @param position - the index of the bit in the field
   * @throws BitIndexOutOfBoundException if {@code position} is lower than 0 or greater or equal to 
   *         {@link #length()}
   */
  public void writeBoolean(boolean value, long position) throws BitIndexOutOfBoundException;
}
