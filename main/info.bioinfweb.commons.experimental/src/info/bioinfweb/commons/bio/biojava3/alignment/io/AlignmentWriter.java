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
package info.bioinfweb.commons.bio.biojava3.alignment.io;


import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;
import info.bioinfweb.commons.text.UniqueNameMap;

import java.io.File;
import java.io.OutputStream;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



public interface AlignmentWriter<S extends Sequence<C>, C extends Compound> {
  public void write(Alignment<S, C> alignment, OutputStream stream) throws Exception;

  public void write(Alignment<S, C> alignment, File file) throws Exception;
  
  public UniqueNameMap getNameMap();
}
