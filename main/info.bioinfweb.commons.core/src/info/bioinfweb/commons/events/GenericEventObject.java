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
package info.bioinfweb.commons.events;


import java.util.EventObject;



/**
 * An event state object that returns its source as a generic type. Can be used as an alternative superclass to
 * {@link EventObject} if a certain return type for {@link #getSource()} is needed.
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 *
 * @param <S> the type of source
 */
public class GenericEventObject<S> extends EventObject {
	public GenericEventObject(S source) {
		super(source);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public S getSource() {
		return (S)super.getSource();
	}
}
