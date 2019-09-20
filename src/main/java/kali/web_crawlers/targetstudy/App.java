package kali.web_crawlers.targetstudy;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mashape.unirest.http.Unirest;

public class App {
	
	public static List<String> statesLink() throws IOException{
		
		List<String> statesLink = new ArrayList<>();
		
		System.out.println(Unirest.get("https://targetstudy.com/").getHeaders());
//		Document document = Jsoup.parse();
//		System.out.println(document.getElementsByClass("c1Cols").get(4).child(0).childNodeSize());
		return statesLink;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Unirest.setDefaultHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		
		statesLink();
	}

}
	