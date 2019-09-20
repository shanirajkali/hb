package kali.web_crawlers.topuniversities_com;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Temp {

	private final static String baseUrl="https://www.topuniversities.com";

	public static void main(String[] args) throws IOException {
		
		System.out.println("started");
		Document currentUrlDoc=Jsoup.connect("https://www.topuniversities.com/university-rankings/university-subject-rankings/2018/life-sciences-medicine").timeout(6424242).get();
		FileWriter fw=new FileWriter("topUNI.html");
		BufferedWriter bWriter=new BufferedWriter(fw);
		bWriter.write(currentUrlDoc.html());
		bWriter.flush();
		bWriter.close();
		///currentUrlDoc.outputSettings().charset("ISO-8859-1");
		//university title
		System.out.println(currentUrlDoc.getElementsByClass("title_info").select("h1").text());
	

	}

}
