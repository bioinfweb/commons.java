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
package info.bioinfweb.commons.log;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Implementation of {@link ApplicationLogger} that stores all messages into a list.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class MessageListApplicationLogger extends AbstractApplicationLogger {
	private List<ApplicationLoggerMessage> list;
	private List<ApplicationLoggerMessage> unmodyfiableList;
	
	
	public MessageListApplicationLogger() {
		super();
		list = new ArrayList<ApplicationLoggerMessage>();
		unmodyfiableList = Collections.unmodifiableList(list);
	}
	

	public MessageListApplicationLogger(List<ApplicationLoggerMessage> list) {
		super();
		this.list = list;
		unmodyfiableList = Collections.unmodifiableList(list);
	}
	

	protected List<ApplicationLoggerMessage> getList() {
		return list;
	}


	public List<ApplicationLoggerMessage> getMessageList() {
		return unmodyfiableList;
	}


	@Override
	public void addMessage(ApplicationLoggerMessage message) {
		getList().add(message);
	}
	
	
	/**
	 * Adds all messages stored in this list to the other logger.
	 * <p>
	 * Note that the events of the specified logger may not be in chronological order
	 * anymore, depending on the time-stamps of the messages in this and in the other logger.
	 * 
	 * @param otherLogger the logger to write the messages to
	 */
	public void addListToLogger(ApplicationLogger otherLogger) {
		for (ApplicationLoggerMessage message : getMessageList()) {
			otherLogger.addMessage(message);
		}
	}
}
