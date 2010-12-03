package info.webinsel.util.servlet.urltree.sitemap;


import java.util.Vector;

import info.webinsel.util.servlet.urltree.URLTreeElement;



public class SitemapEntry extends URLTreeElement {
	private SitemapEntry parent = null;
	private Vector<SitemapEntry> subentries = new Vector<SitemapEntry>();

	
	public SitemapEntry getParent() {
		return parent;
	}


	public void setParent(SitemapEntry parent) {
		this.parent = parent;
	}
	
	
	public Vector<SitemapEntry> getSubentries() {
		return subentries;
	}
	
	
	public String getPath(String lang) {
		String result = getUrlParts().get(lang);
		if (getParent() != null) {
			SitemapEntry pos = getParent();
			while (pos.getParent() != null) {
				result = pos.getUrlParts().get(lang) + "/" + result;
				pos = pos.getParent();
			}
		}
		return result;
	}
}
