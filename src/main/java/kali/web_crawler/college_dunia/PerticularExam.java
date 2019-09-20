package kali.web_crawler.college_dunia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class PerticularExam {

	public static void main(String[] args) throws IOException, UnirestException, JSONException {
		
		String str1="192.168.0.222";
		String str2="255.255.255.1";
		String[] command1 = { "netsh", "interface", "ip", "set", "address",
		"name=", "Local Area Connection" ,"source=static", "addr=",str1,
		"mask=", str2};
		Process pp = java.lang.Runtime.getRuntime().exec(command1);
		
		File fileToWrite=new File("engineering__exams_and_colleges"+"_"+System.currentTimeMillis()+"_.csv");
		fileToWrite.createNewFile();
		FileWriter fileWriter = new FileWriter(fileToWrite,true);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		
		String csvTitle="Exam Name     ,"
				+"Application Form Date,"
				+ "Examination Date,"
				+"Result Announce Date,"
				+"Exam Medium,\n";
		bw.write(csvTitle);
		System.out.println("started at : "+System.currentTimeMillis());
		
		
		Document doc = Jsoup.connect("https://exams.collegedunia.com/engineering").timeout(30000).get();
		
		Elements examsList=doc.select("div[class=exam_list_view]");
		for(int i=0;i<examsList.size();i++){
			
			Element pDiv=examsList.get(i);
			String examMedium="\""+pDiv.select("div div[class=exam_details] div[class=exam_tags]").text()+"\"";
			System.out.println(examMedium);
			String examName="\""+pDiv.select("div div[class=exam_details] div[class=exam_name]").text()+"\"";
			System.out.println(examName);
			String applicationFormDate="";
			String examDate="";
			String resultDate="";
			if(pDiv.select("div[class=exam_dates_info] ul li span").size()>0){
				applicationFormDate="\""+pDiv.select("div[class=exam_dates_info] ul li span").first().text()+"\"";
				String applicationformName=pDiv.select("div[class=exam_dates_info] ul li h5").first().text();
				System.out.println(applicationFormDate+applicationformName);
				
				examDate="\""+pDiv.select("div[class=exam_dates_info] ul li[class=exam] span").text()+"\"";
				String examDateName=pDiv.select("div[class=exam_dates_info] ul li[class=exam] h5").text();
				System.out.println(examDate+examDateName);
				
				resultDate="\""+pDiv.select("div[class=exam_dates_info] ul li[class=result] span").text()+"\"";
				String resultName=pDiv.select("div[class=exam_dates_info] ul li[class=result] h5").text();
				System.out.println(resultDate+resultName);
			}
				
			
			bw.write(examName+","+applicationFormDate+","+examDate+","+resultDate+","+examMedium+"\n");
			
			
			String exam=pDiv.select("div div[class=exam_details] div[class=exam_name] a")
					.attr("href")
					.split("/")[3];
			
			String str=	Unirest.post("https://collegedunia.com/exams/entrance-exam")
					.header("Accept", "application/json")
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Referer", "https://exams.collegedunia.com/"+exam+"/top-colleges-in-india-accepting-"+exam)
					.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
					.header("Origin", "https://exams.collegedunia.com")
					.body("page=0&tab=top-colleges-in-india-accepting-"+exam).asJson().getBody().getObject().toString();
			
			
				JSONObject json = new JSONObject(str);
			    System.out.println(json.get("html").toString());
				
				System.out.println(str);
				 
				Document collegesDoc=Jsoup.parse(json.get("html").toString());
				System.out.println(collegesDoc.select("div[class=col-md-3 col-sm-6 col-xs-12 set_hight_participate_colleges]").size());
				Elements collegesList=collegesDoc.select("div[class=col-md-3 col-sm-6 col-xs-12 set_hight_participate_colleges]");
				
				
				int lodeMore=1;
				for(int i1=0;i1<collegesList.size();i1++){
					//fetch college data
					Element college=collegesList.get(i1);
					
					String collegeHeading = "\""+college.select("div[class=article_content] h3").text()+"\"";
					
					String collegeLocation = "\""+college.select("div[class=info]").text()+"\"";
					
					String collegeUniversity = "\""+college.select("div[class=info last]").text()+"\"";
					
					String likeAndRating = "\""+college.select("div[class=rating]").text()+"\"";
					
					String cutoffForGeneral= "\""+college.select("p[div=cutoff-link]").text()+"\"";
					
					String courseOffered = "\""+college.select("div[class=detail] h4").text()+"\"";
					
					String fees = "\""+college.select("div[class=detail] div[class=fees]").text()+"\"";
					
					System.out.println(collegeHeading+", "+collegeUniversity+ " , "+collegeLocation+", "+likeAndRating+", "+cutoffForGeneral+" ,"+courseOffered+" ,"+fees +"\n");
					String toWrite=examName+
							","+collegeHeading
							+","+collegeUniversity
							+ ","+collegeLocation
							+","+likeAndRating
							+","+cutoffForGeneral
							+","+courseOffered
							+","+fees+"\n" ;
					bw.write(toWrite);
					
					
					if(i1>=19){
					i1=0;	
					str=Unirest.post("https://collegedunia.com/exams/entrance-exam")
							.header("Accept", "application/json")
							.header("Accept-Encoding", "gzip, deflate, br")
							.header("Referer", "https://exams.collegedunia.com/"+exam+"/top-colleges-in-india-accepting-"+exam)
							.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
							.header("Origin", "https://exams.collegedunia.com")
							.body("page="+lodeMore+"&tab=top-colleges-in-india-accepting-"+exam).asJson().getBody().getObject().toString();
					lodeMore++;
					json = new JSONObject(str);
					collegesDoc=Jsoup.parse(json.get("html").toString());
					collegesList=collegesDoc.select("div[class=col-md-3 col-sm-6 col-xs-12 set_hight_participate_colleges]");
					System.out.println("loadMore");
					//break;
					}
					
				}
				
			bw.newLine();
		}
		//System.out.println(doc);
		bw.newLine();
		bw.flush();
		bw.close();
	}
}