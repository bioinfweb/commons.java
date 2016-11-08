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
package info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus;


import org.biojavax.bio.phylo.io.nexus.NexusFileBuilder;



/**
 * Builds a Nexus file including information from the <code>SETS block</code> by listening to 
 * events.
 * 
 * @author Ben St&ouml;ver
 */
public class CharSetNexusFileBuilder extends NexusFileBuilder {
	public CharSetNexusFileBuilder() {
		super();
		
		setBlockParser(SetsBlock.SETS_BLOCK, new SetsBlockParser(new SetsBlockBuilder()));
	}
}
