package kali.web_crawler.college_dunia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

	public static void main(String[] args) throws IOException {
		
		Document doc = Jsoup.connect("https://exams.collegedunia.com/").timeout(30000).get();
		System.out.println(doc.toString());
		FileWriter fileWriter = new FileWriter(new File("exams_collegedunia_com.html"),true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(doc.toString());
		bufferedWriter.newLine();
		bufferedWriter.flush();
		bufferedWriter.close();
		
		 Elements divsOfDomains = doc.select("div[class=col-sm-6 col-xs-6 exam_categories_container]");
		 //System.out.println(divsOfDomains.get(0).toString());
		 StringBuffer finalResult = new StringBuffer();
		 
		 finalResult.append("Domain,"
				 +"Name of Exam,"
				 +"University,"
				 +"Ways to Attempt,"
				 +"Application Coming Up,"
				 +"Examination Date,"
				 +"Result Announce Date,"
				 +"Some Useful Links"+"\n");
		 
		 for(int i=0;i<divsOfDomains.size();i++){
			 
			 Element div=divsOfDomains.get(i);
			 String link=div.attr("onclick").split("\"")[1];
			// System.out.println(link);
			 String testName=link.split("/")[3];
			// finalResult.append(testName.toUpperCase()+"\n");
		//	 
			// System.out.println(finalResult.toString());
			 Document perticularPage=Jsoup.connect(link).get();
			 Elements perticularLinkData=perticularPage.select("div[class=exam_list_view]");
			
			 for(int j=0;j<perticularLinkData.size();j++){
				 
				 Element perticularLink=perticularLinkData.get(j);
				 Elements waysToAttempt=perticularLink.select("div.exam_list_view div.exam_info_wrapper div:eq(1) div:eq(0) ul li");
				 String waysToAttemptStr;
				 
				 if(waysToAttempt.size()>1){
					 String temp=perticularLink.select("div.exam_list_view div.exam_info_wrapper div:eq(1) div:eq(0) ul li:eq(0)").text();
					 waysToAttemptStr=temp.substring(0, temp.length()-1)+" "+perticularLink.select("div.exam_list_view div.exam_info_wrapper div:eq(1) div:eq(0) ul li:eq(1)").text();
				 }
				 else{
					 waysToAttemptStr=perticularLink.select("div.exam_list_view div.exam_info_wrapper div:eq(1) div:eq(0) ul li:eq(0)").text();
				 }
				 
				// System.out.println(waysToAttemptStr);
				// System.out.println(waysToAttempt.size());
				 Elements div2ExamName=perticularLink.select("div.exam_list_view div.exam_info_wrapper div:eq(1) div:eq(1)");
				 String div2ExamNameStr0=perticularLink.select("div.exam_list_view div.exam_info_wrapper div:eq(1) div:eq(1) h1").text();
				 String div2ExamNameStr=perticularLink.select("div.exam_list_view div.exam_info_wrapper div:eq(1) div:eq(1) span").text();
				 String[] ExamNameSplit=div2ExamNameStr.split(",");
				 div2ExamNameStr="";
				 
				 for(int k=0;k<ExamNameSplit.length;k++){
					 div2ExamNameStr+=ExamNameSplit[k]+" ";
				 }
				 
				 // System.out.println(div2ExamNameStr);
				 Elements davDates=perticularLink.select("div.exam_list_view div.exam_dates_info ul");
				 String formDateExpected=perticularLink.select("div.exam_list_view div.exam_dates_info ul li:eq(0) span").text();
				 String examinationDateExpected=perticularLink.select("div.exam_list_view div.exam_dates_info ul li:eq(1) span").text();
				 String rusultAnounceDateExpected=perticularLink.select("div.exam_list_view div.exam_dates_info ul li:eq(2) span").text();
				 String usefulSource=perticularLink.select("div.exam_list_view div.exam_dates_info ul li:eq(0) h5 a").attr("href");
				
				 String finalDetail=testName.toUpperCase()
						 +","+div2ExamNameStr0
						 +","+div2ExamNameStr
						 +","+waysToAttemptStr+","
						 +formDateExpected+","
						 +examinationDateExpected
						 +","+rusultAnounceDateExpected
						 +","+usefulSource+"\n";
				 
				 finalResult.append(finalDetail);
				 
				 System.out.println(usefulSource);
				 System.out.println(formDateExpected);
				 System.out.println(examinationDateExpected);
				 System.out.println(rusultAnounceDateExpected);
				 System.out.println(finalDetail);
			 }
			 
			// finalResult.append("");
			
		 }
		 
		 
		 System.out.println(finalResult.toString());
		 String outputFile = "output_"+System.currentTimeMillis()+".csv";
		 File fileToStore = new File(outputFile);
		 fileToStore.createNewFile();
		 FileWriter fileWriterOutput = new FileWriter(fileToStore,true);
		 BufferedWriter bufferedWriterOutput = new BufferedWriter(fileWriterOutput);
		 bufferedWriterOutput.write(finalResult.toString());
		 bufferedWriterOutput.newLine();
		 bufferedWriterOutput.flush();
		 bufferedWriterOutput.close();

	}
}