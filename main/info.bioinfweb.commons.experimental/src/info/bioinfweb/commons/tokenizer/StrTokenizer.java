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
package info.bioinfweb.commons.tokenizer;


import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StrTokenizer {
  private Vector<String> regExps = new Vector<String>();
  

	public boolean add(String regExp) {
		return regExps.add(regExp);
	}
	
	
	private Pattern getPattern() {
		if (regExps.size() > 0) {
			String bigExp = "([";
			for (int i = 0; i < regExps.size() - 1; i++) {
				bigExp += regExps.get(i) + "|";
			}
			bigExp += regExps.get(regExps.size() - 1) + "])";
			return Pattern.compile(bigExp);
		}
		else {
			return null;
		}
	}


	public StrToken[] parse(String content) {
		if (regExps.size() > 0) {
			Matcher fullMatcher = getPattern().matcher(content);
			Vector<StrToken> tokens = new Vector<StrToken>();
			while (fullMatcher.find()) {
				tokens.add(new StrToken(0, fullMatcher.group(1), fullMatcher.start(1)));
			}
			
			Pattern[] patterns = new Pattern[regExps.size()];
			for (int i = 0; i < patterns.length; i++) {
				patterns[i] = Pattern.compile(regExps.get(i));
			}

			for (int i = 0; i < tokens.size(); i++) {
				int pos = 0;
				while (!patterns[pos].matcher(tokens.get(i).getContent()).matches()) {
					pos++;
				}
				tokens.get(i).setID(pos);
			}
			
			return tokens.toArray(new StrToken[tokens.size()]);
		}
		else {
			return null;
		}
	}
}
