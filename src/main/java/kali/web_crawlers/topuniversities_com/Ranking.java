package kali.web_crawlers.topuniversities_com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;


class UniversityRanking{
	private String rank;
	private String country;
	private String universityName;
	private String subject;
	private String universityTopUniversityNamedUrl;
	
	
	public String getUniversityTopUniversityNamedUrl() {
		return universityTopUniversityNamedUrl;
	}
	public void setUniversityTopUniversityNamedUrl(String universityTopUniversityNamedUrl) {
		this.universityTopUniversityNamedUrl = universityTopUniversityNamedUrl;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	
	@Override
	public String toString(){
		return getUniversityName()+" "+getRank()+" "+ getCountry()+" "+getSubject();
	}
	
	public String getDataInCSVFormat(){
		return "\""+getUniversityName()+"\","
		+"\""+getCountry()+"\","
		+"\""+getRank()+"\","
		+"\""+getSubject()+"\"";
	}
}

public class Ranking {
	
	static int listSize=0;
	
	static public void  scrapHtmlFile() throws IOException {
		File file=new File("E:\\shani rajkali\\CODE\\web_crawlers\\topuniversities\\university_ranking_html\\");
		BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("Ranking.csv"));
	
		for (int i = 0; i <file.listFiles().length; i++) {
			try {
				BufferedReader bReader=new BufferedReader(new FileReader(file.listFiles()[i]));
				Document document= Jsoup.parse(bReader.readLine(),"",Parser.xmlParser());
				Elements elements=document.getElementsByTag("tr");
				for (int j = 0; j < elements.size(); j++) {
					Element element=elements.get(j);
					String rank=element.select("span").text();
					String universityName=element.select("a[class=title]").text();
					String country=element.select("img[class=flag]").attr("data-original-title");
					String universityTopUniversityNamedUrl=element.select("div[class=td-wrap] a[class=more]").attr("href");
					//System.out.println(universityTopUniversityNamedUrl);
					UniversityRanking uRanking=new UniversityRanking();
					uRanking.setCountry(country);
					uRanking.setRank(rank);
					uRanking.setUniversityName(universityName);
					uRanking.setSubject(file.listFiles()[i].getName());
					String splt="^^^^^^";
					bufferedWriter.write(rank+splt+universityName+splt+country+splt+file.listFiles()[i].getName()+splt+universityTopUniversityNamedUrl+"\n");
					System.out.print(rank+splt+universityName+splt+country+splt+file.listFiles()[i].getName()+splt+universityTopUniversityNamedUrl+"\n");
					//System.out.println(uRanking.getDataInCSVFormat());
					listSize++;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bufferedWriter.flush();bufferedWriter.close();
		System.out.println(listSize);
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader bReader=new BufferedReader(new FileReader("E:\\shani rajkali\\CODE\\web_crawlers\\Ranking.csv"));
		String lineString=bReader.readLine();
		while(lineString!=null){	
			String[] lineSplit=lineString.split(Pattern.quote("^^^^^^")); 
			try {
				System.out.println(lineSplit[0]+" "+lineSplit[1]+" "+lineSplit[2]+" "+lineSplit[3]+" "+lineSplit[4]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			lineString=bReader.readLine();
		}
	//scrapHtmlFile();
	}

}
