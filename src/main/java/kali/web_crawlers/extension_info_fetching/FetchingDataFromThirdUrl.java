package kali.web_crawlers.extension_info_fetching;


import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class fetching data from "https://pc.net"
 *
 */
public class FetchingDataFromThirdUrl extends Thread {
	private String extension;
	private AboutExtension aboutExtension;
	public FetchingDataFromThirdUrl(String extension, AboutExtension aboutExtension) {
		this.extension = extension;
		this.aboutExtension = aboutExtension;
	}
	public void run(){
		try{
			Document doc = Jsoup.connect("https://pc.net/extensions/file/"+extension).timeout(30000).get();
			
	        //fetching file type 
	        Elements elementsForFileType = doc.select("table.display tr:eq(0) th:eq(1)");
	        ArrayList<String> fileTypes = new ArrayList<String>();
	        if(elementsForFileType.size()>0){
	        	for(Element fileType : elementsForFileType){
	        		fileTypes.add(fileType.text());
	        	}
	        }
	        
	        //fetching usage
	        Elements elementsForUsage = doc.select("table.display tbody tr:eq(2) td:eq(1)");
	        StringBuilder usage = new StringBuilder();
	        if(elementsForUsage.size()>0 && elementsForFileType.size()>0){
	        	int index = 0;
	        	for(Element usageElement : elementsForUsage){
	        		usage.append("\n\t(File Type: "+fileTypes.get(index)+") :: "+usageElement.text());
	        		index++;
	        	}
	        }else{
	        	usage.append("Not Available");
	        }
	        
	        //fetching description
	        Elements elementsOfDescription = doc.select("table.display tbody tr:eq(4) td:eq(1)");
	        StringBuilder description = new StringBuilder();
	        if(elementsOfDescription.size()>0 && elementsForFileType.size()>0){
	        	int index = 0;
	        	for(Element descriptionElement : elementsOfDescription){
	        		description.append("\n\t(File Type: "+fileTypes.get(index)+") :: "+descriptionElement.text());
	        		index++;
	        	}
	        }else{
	        	description.append("Not Available");
	        }
	        
	        //setting values of usage and description in aboutExtension
	        aboutExtension.setUsage(usage.toString());
	        aboutExtension.setDescription(description.toString());
		}catch(IOException e){
			//System.err.println("data fetching error for extension " +extension+ "from https://pc.net");
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
	}
}
