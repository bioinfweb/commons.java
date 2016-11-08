/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.io.streammessage;


import java.io.DataInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;



/**
 * Abstract factory class that provides an {@link EnumMap} to generate and read the according 
 * {@link Message} object when a message is send over the stream.
 * 
 * @author Ben St&ouml;ver
 * 
 * @param <T> - the message type
 */
public abstract class MessageReader<T extends Enum<T>> {
	private Class<T> typeClass = null;
	
	
	private Map<T, Class<? extends Message<T>>> messages = 
		  new HashMap<T, Class<? extends Message<T>>>();
	
	
	public MessageReader(Class<T> typeClass) {
		super();
		this.typeClass = typeClass;
		fillList();
	}
	
	
	protected Map<T, Class<? extends Message<T>>> getMessages() {
		return messages;
	}


	protected abstract void fillList();
	
	
	public Message read(DataInputStream in) throws IOException {
		try {
			Message message = messages.get(typeClass.getEnumConstants()[in.readInt()]).newInstance();
	  	message.read(in);
			return message;
		}
		catch (IllegalAccessException e) {
			throw new Error(e);  // Passiert wohl nicht
		}
		catch (InstantiationException e) {
			throw new Error(e);  // Passiert wohl nicht
		}
	}
}
