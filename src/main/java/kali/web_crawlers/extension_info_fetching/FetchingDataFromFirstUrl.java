package kali.web_crawlers.extension_info_fetching;


import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * this class will fetch data from "https://fileinfo.com" 
 *
 */
public class FetchingDataFromFirstUrl extends Thread {
	private String extension;
	private AboutExtension aboutExtension;
	public FetchingDataFromFirstUrl(String extension,AboutExtension aboutExtension) {
		this.extension = extension;
		this.aboutExtension = aboutExtension;
	}
	public void run(){
		try{
			Document doc = Jsoup.connect("https://fileinfo.com/extension/"+extension).timeout(30000).get();
			
	        //fetching file type
	        Elements elementsForfileType = doc.select("section[class=ext]");
	        ArrayList<String> fileTypes = new ArrayList<String>();
	        if(elementsForfileType.size()>1){
		        for(Element fileType : elementsForfileType){
		        	String stringFileType= fileType.child(0).text();
		        	fileTypes.add(stringFileType.substring(11, stringFileType.length()));
		        }
	        }else{
	        	for(Element fileType : elementsForfileType){
			       	String stringFileType= fileType.child(0).text();
			       	fileTypes.add(stringFileType.substring(9, stringFileType.length()));
	            } 	 
	        }
	        
	        StringBuilder allFileType = new StringBuilder();
	        if(fileTypes.size()==0){
	        	allFileType.append("Not Available");
	        }else{
		        for(int i = 0;i<fileTypes.size();i++){
		        	allFileType.append("\n\t"+fileTypes.get(i));
		        }
	        }
	        
	        //fetching category 
	        Elements elementsOfCategory = doc.select("table.headerInfo tbody tr:eq(2) td:eq(1) a");
	        StringBuilder categories = new StringBuilder();
	        if(elementsOfCategory.size()>0 && fileTypes.size()>0){
		        int index = 0 ;
		        for(Element category : elementsOfCategory){
		        	categories.append("\n\t(file type : "+fileTypes.get(index)+") :: "+category.text());
		        	index++;
		        }
	        }else{
	        	categories.append("Not Available");
	        }
	        
	        //fetching format
	        Elements elementsOfformat = doc.select("table.headerInfo tbody tr:eq(3) td:eq(1) > a");
	        StringBuilder formats = new StringBuilder();
	        if(elementsOfformat.size()>0 && fileTypes.size()>0){
	        	int index = 0;
	        	for(Element format : elementsOfformat){
	        		formats.append("\n\t(file type : "+fileTypes.get(index)+") :: "+format.text());
	        		index++;
	        	}
	        }else{
	        	formats.append("Not Available");
	        }
	        
	        //setting values of file, category and format in aboutExtension
	        aboutExtension.setFileTpe(allFileType.toString());
	        aboutExtension.setCategory(categories.toString());
	        aboutExtension.setFormat(formats.toString());
		}catch(IOException e){
			//System.err.println("data fetching error for extension " +extension+ "from https://fileinfo.com");
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
	}
}
