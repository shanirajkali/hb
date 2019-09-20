package kali.web_crawlers.extension_info_fetching;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class is fetching data from "https://file-extension.com"
 *
 */
public class FetchingDataFromSecondUrl extends Thread {
	private String extension;
	private AboutExtension aboutExtension;
	public FetchingDataFromSecondUrl(String extension, AboutExtension aboutExtension) {
		this.extension = extension;
		this.aboutExtension = aboutExtension;
	}
	public void run(){
		try{
			Document doc = Jsoup.connect("http://filext.com/file-extension/"+extension).timeout(30000).get();
			
			//fetching associate application and then set in aboutExtension
			Elements associateApplications = doc.select("div#extended-info ul li");
			StringBuilder text = new StringBuilder();
			if(associateApplications.size()>0){
				for(Element application : associateApplications){
					text.append("\t"+application.select("span:eq(0)").text()+"\n");
				}
			}else{
				text.append("Not Available");
			}
			aboutExtension.setApplications(text.toString());
			
			//fetching mime type and then set in aboutExtension
			if(doc.select("div#extended-info div").size()>=3 && doc.select("div#extended-info div").get(2).text().contains("Mime type")){
				Element mimeType = doc.select("div#extended-info div").get(2);
				aboutExtension.setMimeType(mimeType.textNodes().get(0).toString());
			}else{
				aboutExtension.setMimeType("Not Available");
			}
			
			
        }catch(IOException e){
        	//System.err.println("data fetching error for extension " +extension+ "from https://file-extension.com");
        }catch(ArrayIndexOutOfBoundsException e){
			
		}
	}
}