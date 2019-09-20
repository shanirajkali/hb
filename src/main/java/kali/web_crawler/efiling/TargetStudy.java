package kali.web_crawler.efiling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.http.impl.client.BasicCookieStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.jca.cci.RecordTypeNotSupportedException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import groovy.time.BaseDuration.From;


/*
 * System.out.println(elements.get(i).select("div div a").attr("href"));
			String moreDetailsLink = elements.get(i).select("div div a").attr("href");
			
			
			elements.get(i).select("td:eq(0) ul").remove();
			elements.get(i).select("td:eq(0) div").remove();
			elements.get(i).select("td:eq(0) b").remove();
			Element element= elements.get(i).select("td:eq(0)").get(0);
			
			System.out.println(element.toString().split("<br />")[1]);
 */

class Csv{
	String state;
	String city;
	String schoolName;
	String address;
	String board;
	String email;
	String website;
	String contact;
	String phone;
	String fax;
	public Csv() {
		
	}
	
	public Csv(String state, String city, String schoolName,String address,
			String board, String email, String website, String contact) {
				this.city= city;
				this.address= address;
				this.state = state;
				this.board= board;
				this.website= website;
				this.contact=contact;
				this.email=email;
				this.schoolName = schoolName;
		}
	
	String line(){
		return "\""+state+"\""+","
				+"\""+city+"\""+","
				+"\""+schoolName+"\""+","
				+"\""+address+"\""+","
				+"\""+board+"\""+","
				+"\""+email+"\""+","
				+"\""+website+"\""+","
				+"\""+contact+"\"\n";
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
}


public class TargetStudy {
	static Map<String,String> headers = new HashMap<>();	
	static List<Csv> csvLines = new ArrayList<>();
	public static final String recNo = "?recNo=";
	public static final String referer = "referer";
	
	static void parsePage(Document document, String link) throws UnirestException{
		System.out.println("ll: "+link);
		Elements elements = document.select("div[class=col-md-7] div[class=panel panel-custom]");
		Csv csv = new Csv();
		
		System.out.println("schools count: "+elements.size());
		for(int i = 0;i<elements.size()-1;i++){
			String moreDetailsLink = elements.get(i).select("div div a").attr("href");
			System.out.println(moreDetailsLink);
			headers.put(":path", getPathFromLink(moreDetailsLink));
			headers.put(referer, link);
			HttpResponse<String> response= Unirest
					.get(moreDetailsLink)
					.headers(headers)
					.asString();
			Document moreDetailsDoc = Jsoup.parse(response.getBody()); 
			System.out.println(moreDetailsLink);
			Elements trs = moreDetailsDoc.select("div[class=col-md-7 col-lg-7] tbody tr");
			System.out.println(trs.size());
			for(int j=0;j<trs.size();j++){
				if(trs.get(j).select("i[class=fa fa-home]")!=null ){
					System.out.println(trs.get(j).select("td").text());
				}else if (trs.get(j).select("i[class=fa fa-phone]")!=null) {
					System.out.println(trs.get(j).select("td").text());
				}else if (trs.get(j).select("i[class=fa fa-mobile]")!=null) {
					System.out.println(trs.get(j).select("td").text());
				}else if (trs.get(j).select("i[class=fa fa-globe]")!=null) {
					System.out.println(trs.get(j).select("td"));
				}else if (trs.get(j).select("i[class=fa fa-envelope-o]")!=null) {
					System.out.println(trs.get(j).select("td"));
				}
			}
		}	
	}
	
	public static String getPathFromLink(String link){
		String getPath = "";
		for(int i=3;i<link.split("/").length;i++){
			getPath =getPath+"/"+ link.split("/")[i];
		}
		return getPath;
	}
	
    public static void main(String[] args) throws UnirestException, IOException {
    	Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));
		 for(String log:loggers) { 
			    Logger logger = (Logger)LoggerFactory.getLogger(log);
			    logger.setLevel(Level.INFO);
			    logger.setAdditive(false);
			    }
		 
		headers.put(":authority", "targetstudy.com");
		headers.put(":method", "GET");
   	    headers.put(":path", "/school/senior-secondary-schools-in-india.html");
	    headers.put(":scheme", "https");	 
		headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		//headers.put("accept-encoding","gzip, deflate, br" );
		headers.put("accept-language", "en-US,en;q=0.9,es;q=0.8,hi;q=0.7,fil;q=0.6");
		headers.put("dnt", "1");
		headers.put("upgrade-insecure-requests", "1");
		headers.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36" );
		
		HttpResponse<String> response= Unirest
				.get("https://targetstudy.com/school/senior-secondary-schools-in-india.html")
				.headers(headers)
				.asString();
		Document document=Jsoup.connect("https://targetstudy.com/school/senior-secondary-schools-in-india.html").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36").timeout(800000).get();
		System.out.println(document);
		System.out.println(response.getStatus());
		List<String> statesLinks = new ArrayList<String>();
		
		for(Node e:Jsoup.parse(response.getBody()).getElementsByClass("c1Cols").get(0).childNode(0).childNodes()){
			String link = e.childNode(0).attr("href");
			String getPath = getPathFromLink(link);
			headers.put("referer", "https://targetstudy.com/school/senior-secondary-schools-in-india.html");
			System.out.println(getPath);
			headers.put(":path", getPath);
			
			response= Unirest
					.get(link)
					.headers(headers)
					.asString();
			
		//	Document document = Jsoup.parse(response.getBody());
		//	parsePage(document, link);
		//	System.out.println(response.getBody());
			
		}
		
		
		
	}

}
