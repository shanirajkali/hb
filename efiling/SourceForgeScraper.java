package kali.web_crawlers.efiling;



import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import junit.framework.Assert;

public class SourceForgeScraper {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SourceForgeScraper sForgeScraper=new SourceForgeScraper();
	
		//sForgeScraper.homePage();
		sForgeScraper.homePage_Firefox();

	}
	
	@Test
	public void homePage() throws Exception {
	    try (final WebClient webClient = new WebClient()) {
	        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
	        Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());

	        final String pageAsXml = page.asXml();
	        Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

	        final String pageAsText = page.asText();
	        Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
	    }
	}
	
	@Test
	public void homePage_Firefox() throws Exception {
	    try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52)) {
	        final HtmlPage page = webClient.getPage("https://incometaxindiaefiling.gov.in/home");
	        Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
	    }
	}

}
