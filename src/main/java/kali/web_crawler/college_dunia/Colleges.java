package kali.web_crawler.college_dunia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Colleges {
	
	public static void writeDetailsByCourseUrl(String courseUrl) throws IOException{
		File fileToWrite=new File(courseUrl+"_"+System.currentTimeMillis()+"_.csv");
		fileToWrite.createNewFile();
		FileWriter fileWriter = new FileWriter(fileToWrite,true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		
		System.out.println("started at : "+System.currentTimeMillis());
		Document doc = Jsoup.connect("https://collegedunia.com/"+courseUrl+"?ajax=1&college_type=0&page=100").timeout(30000).get();
		String header="College                         			 					,"
						+"Rating               			,"
						+"Location									,"
						+"Affiliate 					,"
						+"Average Fee 								,"
						+"Exam Accepted								,"
						+"Review									\n";
		
		bufferedWriter.write(header);
		for(int i=1;i<100;i++){
			Document ajax =Jsoup.connect("https://collegedunia.com/"+courseUrl+"?ajax=1&college_type=0&page="+i).get();
			System.out.println(ajax.toString());
			if(!ajax.select("body").text().equals("false")){
				System.out.println(i);
				Elements body=ajax.getElementsByClass("col-sm-4 automate_clientimg_snippet  ");
				for(int j=0;j<body.size();j++){
					//top block
					String collegeNameAddress="\""+body.get(j).select("div div div div[class=clg-name-address]").text()+"\"";
					System.out.println(collegeNameAddress);
					String collegeRating="\""+body.get(j).select("div div div a[class=data-icon pull-right margin-right-0] span:eq(1)").text()+"\"";
					System.out.println(collegeRating);
					
					//bottom block
					String locationBadge ="\""+body.get(j).select("div div div[class=bottom-block] div span[class=location-badge] span:eq(0)").text()+"\"";
					System.out.println(locationBadge);
					String affiliate="\""+body.get(j).select("div div div[class=bottom-block] div span[class=location-badge] span:eq(1)").text()+"\"";
					System.out.println(affiliate);
					
					Elements clgFeeReviewTitle=body.get(j).select("div div div[class=bottom-block] ul[class=clg-fee-review tile_details_div] li");
					System.out.println(clgFeeReviewTitle.size()+"     college fee review");
					String averageFee="";
					String examAccepted="";
					String review="";
					
					if(clgFeeReviewTitle.size()>=1){
						averageFee="\""+body.get(j).select("div div div[class=bottom-block] ul[class=clg-fee-review tile_details_div] li:eq(0)").text()+"\"";
						System.out.println(averageFee);
					}
					
					if(clgFeeReviewTitle.size()>1){
						for(int k=1;k<3;k++){
							String text=body.get(j).select("div div div[class=bottom-block] ul[class=clg-fee-review tile_details_div] li:eq("+k+") span:eq(1)").text();
							System.out.println("text "+text+"  "+k);
							if(text.equals("Exam Accepted")){
								
								String link=body.get(j).select("div div div[class=bottom-block] ul[class=clg-fee-review tile_details_div] li:eq("+k+") a").attr("href");
								System.out.println(link);
								Document examDoc=Jsoup.connect(link).timeout(30000).get();
								
								examAccepted="\""+examDoc.select("div[class=content main_wrapper] h1").text()+"\"";
							}
							else{
							System.out.println(examAccepted);
							review="\""+body.get(j).select("div div div[class=bottom-block] ul[class=clg-fee-review tile_details_div] li:eq("+k+")").text()+"\"";
							}
						}
					}
					
					System.out.println(review);
					
					String finalResult=collegeNameAddress+","
							+collegeRating+","
							+locationBadge+","
							+affiliate+","
							+averageFee+","
							+examAccepted+","
							+review+"\n";
					
					bufferedWriter.write(finalResult);
					
				}
			}
			
		}
		//System.out.println(doc.toString());
		
		
		bufferedWriter.newLine();
		bufferedWriter.flush();
		bufferedWriter.close();
		
	}

	public static void main(String[] args) throws IOException {
		
		writeDetailsByCourseUrl("bcom-colleges");
		
	}

}
