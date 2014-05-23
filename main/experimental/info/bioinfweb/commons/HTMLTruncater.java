package info.bioinfweb.commons;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Truncates HTML formatted text after a specific number of characters (not including characters that are part
 * of an HTML tag) while preserving a valid HTML structure.<br />
 * The initial code came from 
 * <a href="http://stackoverflow.com/questions/2496372/html-truncator-in-java">http://stackoverflow.com/questions/2496372/html-truncator-in-java</a>
 * and was posted by 
 * <a href="http://stackoverflow.com/users/1045305/scott-brady">http://stackoverflow.com/users/1045305/scott-brady</a>.
 *  
 * @author <a href="http://stackoverflow.com/users/1045305/scott-brady">Scott Brady</a>, Ben St&ouml;ver
 */
public class HTMLTruncater {
	/**
	 * This pattern creates tokens, where each line starts with the tag.
	 * For example, "One, <b>Two</b>, Three" produces the following:
	 *     One,
	 *     <b>Two
	 *     </b>, Three
	 */
	public static final Pattern TAG_PATTERN = Pattern.compile("(<.+?>)?([^<>]*)");

	/**
	 * Checks for an empty tag, for example img, br, etc.
	 */
	public static final Pattern EMPTY_TAG_PATTERN = Pattern.compile("^<\\s*(img|br|input|hr|area|base|basefont|col|frame|isindex|link|meta|param).*>$");

	/**
	 * Checks for closing tags, allowing leading and ending space inside the brackets
	 */
	public static final Pattern CLOSING_TAG_PATTERN = Pattern.compile("^<\\s*/\\s*([a-zA-Z]+)\\s*>$");

	/**
	 * Checks for opening tags, allowing leading and ending space inside the brackets
	 */
	public static final Pattern OPENING_TAG_PATTERN = Pattern.compile("^<\\s*([a-zA-Z]+).*?>$");

	/**
	 * Find &nbsp; &gt; ...
	 */
	public static final Pattern ENTITY_PATTERN = Pattern.compile("(&[0-9a-z]{2,8};|&#[0-9]{1,7};|[0-9a-f]{1,6};)");


	public static String truncateHTML(String text, int length, String suffix) {
		// if the plain text is shorter than the maximum length, return the whole text
		if (text.replaceAll("<.*?>", "").length() <= length) {
			return text;
		}
		String result = "";
		boolean trimmed = false;
		if (suffix == null) {
			suffix = "...";
		}

		// splits all html-tags to scanable lines
		Matcher tagMatcher =  TAG_PATTERN.matcher(text);

		int totalLength = suffix.length();
		List<String> openTags = new ArrayList<String>();

		boolean proposingChop = false;
		while (tagMatcher.find()) {
			String tagText = tagMatcher.group(1);
			String plainText = tagMatcher.group(2);

			if (proposingChop &&
					tagText != null && tagText.length() != 0 &&
					plainText != null && plainText.length() != 0) {

				trimmed = true;
				break;
			}

			// if there is any html-tag in this line, handle it and add it (uncounted) to the output
			if (tagText != null && tagText.length() > 0) {
				boolean foundMatch = false;

				// if it's an "empty element" with or without xhtml-conform closing slash
				Matcher matcher = EMPTY_TAG_PATTERN.matcher(tagText);
				if (matcher.find()) {
					foundMatch = true;
					// do nothing
				}

				// closing tag?
				if (!foundMatch) {
					matcher = CLOSING_TAG_PATTERN.matcher(tagText);
					if (matcher.find()) {
						foundMatch = true;
						// delete tag from openTags list
						String tagName = matcher.group(1);
						openTags.remove(tagName.toLowerCase());
					}
				}

				// opening tag?
				if (!foundMatch) {
					matcher = OPENING_TAG_PATTERN.matcher(tagText);
					if (matcher.find()) {
						// add tag to the beginning of openTags list
						String tagName = matcher.group(1);
						openTags.add(0, tagName.toLowerCase());
					}
				}

				// add html-tag to result
				result += tagText;
			}

			// calculate the length of the plain text part of the line; handle entities (e.g. &nbsp;) as one character
			int contentLength = plainText.replaceAll("&[0-9a-z]{2,8};|&#[0-9]{1,7};|[0-9a-f]{1,6};", " ").length();
			if (totalLength + contentLength > length) {
				// the number of characters which are left
				int numCharsRemaining = length - totalLength;
				int entitiesLength = 0;
				Matcher entityMatcher = ENTITY_PATTERN.matcher(plainText);
				while (entityMatcher.find()) {
					String entity = entityMatcher.group(1);
					if (numCharsRemaining > 0) {
						numCharsRemaining--;
						entitiesLength += entity.length();
					} else {
						// no more characters left
						break;
					}
				}

				// keep us from chopping words in half
				int proposedChopPosition = numCharsRemaining + entitiesLength;
				int endOfWordPosition = plainText.indexOf(" ", proposedChopPosition-1);
				if (endOfWordPosition == -1) {
					endOfWordPosition = plainText.length();
				}
				int endOfWordOffset = endOfWordPosition - proposedChopPosition;
				if (endOfWordOffset > 6) { // chop the word if it's extra long
					endOfWordOffset = 0;
				}

				proposedChopPosition = numCharsRemaining + entitiesLength + endOfWordOffset;
				if (plainText.length() >= proposedChopPosition) {
					result += plainText.substring(0, proposedChopPosition);
					proposingChop = true;
					if (proposedChopPosition < plainText.length()) {
						trimmed = true;
						break; // maximum length is reached, so get off the loop
					}
				} else {
					result += plainText;
				}
			} else {
				result += plainText;
				totalLength += contentLength;
			}
			// if the maximum length is reached, get off the loop
			if(totalLength >= length) {
				trimmed = true;
				break;
			}
		}

		for (String openTag : openTags) {
			result += "</" + openTag + ">";
		}
		if (trimmed) {
			result += suffix;
		}
		return result;
	}
	
	
	public static String truncateHTML(String text, int length) {
		return truncateHTML(text, length, null);
	}

	
	private static void test(int length) {
		String truncated = truncateHTML("<p>This is a <a href='/Link/To/1'>text</a> that contains two <a href='/Link/To/2'>links</a>.</p>", length, null); 
		System.out.println(truncated + " (" + length + ", " + truncated.length() + ")");
	}
	
	
	public static void main(String[] args) {
		test(10);  //TODO Test mit 10 hätte "a" mitnehmen müssen. 15 und 20 funktionieren. => Quellcode überarbeiten. 
		test(15);
		test(20);
	}
}
