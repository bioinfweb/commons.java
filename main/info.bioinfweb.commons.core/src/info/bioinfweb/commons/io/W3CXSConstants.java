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
package info.bioinfweb.commons.io;


import javax.xml.XMLConstants;
import javax.xml.namespace.QName;



/**
 * Provides constants for <a href="https://www.w3.org/TR/xmlschema11-2/#built-in-datatypes">built-in XSD data types</a>.
 * 
 * @author Ben St&ouml;ver
 * @author Sarah Wiechers
 * @since 2.0.0
 */
public interface W3CXSConstants {
	public static final QName DATA_TYPE_BOOLEAN = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "boolean");
	public static final QName DATA_TYPE_STRING = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "string");
	public static final QName DATA_TYPE_NORMALIZED_STRING = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "normalizedString");
	public static final QName DATA_TYPE_TOKEN = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "token");

	public static final QName DATA_TYPE_INTEGER = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "integer");
	public static final QName DATA_TYPE_INT = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "int");
	public static final QName DATA_TYPE_LONG = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "long");
	public static final QName DATA_TYPE_SHORT = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "short");
	public static final QName DATA_TYPE_BYTE = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "byte");
	public static final QName DATA_TYPE_UNSIGNED_BYTE = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "unsignedByte");
	public static final QName DATA_TYPE_UNSIGNED_SHORT = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "unsignedShort");
	public static final QName DATA_TYPE_UNSIGNED_INT = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "unsignedInt");
	public static final QName DATA_TYPE_UNSIGNED_LONG = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "unsignedLong");
	public static final QName DATA_TYPE_NON_POSITIVE_INTEGER = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "nonPositiveInteger");  // {...,-2,-1, 0}
	public static final QName DATA_TYPE_NEGATIVE_INTEGER = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "negativeInteger");  // {...,-2,-1}
	public static final QName DATA_TYPE_NON_NEGATIVE_INTEGER = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "nonNegativeInteger ");  // {0,1,2,...}
	public static final QName DATA_TYPE_POSITIVE_INTEGER = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "positiveInteger");  // {1,2,...}
	 
	public static final QName DATA_TYPE_DECIMAL = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "decimal");
	public static final QName DATA_TYPE_FLOAT = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "float");
	public static final QName DATA_TYPE_DOUBLE = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "double");
	
	public static final QName DATA_TYPE_BASE_64_BINARY = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "base64Binary");
	public static final QName DATA_TYPE_HEX_BINARY = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "hexBinary");

	public static final QName DATA_TYPE_QNAME = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "QName");
	public static final QName DATA_TYPE_ANY_URI = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "anyURI");

	public static final QName DATA_TYPE_DURATION = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "duration");
	public static final QName DATA_TYPE_DATE_TIME = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "dateTime");
	public static final QName DATA_TYPE_TIME = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "time");
	public static final QName DATA_TYPE_DATE = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "date");
	public static final QName DATA_TYPE_G_YEAR_MONTH = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gYearMonth");
	public static final QName DATA_TYPE_G_YEAR = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gYear");
	public static final QName DATA_TYPE_G_MONTH_DAY= new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gMonthDay");
	public static final QName DATA_TYPE_G_DAY = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gDay");
	public static final QName DATA_TYPE_G_MONTH = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gMonth");
}
