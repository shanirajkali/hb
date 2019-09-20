package kali.web_crawler.college_dunia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.VoiceStatus;
import javax.sound.sampled.LineListener;
import javax.swing.plaf.synth.SynthScrollBarUI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration;
import org.springframework.restdocs.hypermedia.Link;

	class  facultyCsv{
	String id;
	String designation;
	String name;
	String qualification;
	public facultyCsv() {
		// TODO Auto-generated constructor stub
	}
	public facultyCsv(String id,String designation,String name,String qualification) {
		this.id =id;this.designation= designation;this.name=name;this.qualification=qualification;
	}
	
	String toCSV(){
		return "\""+id+"\""+","
				+"\""+designation+"\""+","
				+"\""+name+"\""+","
				+"\""+qualification+"\"";
	}
}
	class feesCsv{
		String id;
		String course;
		String fees;
		
		public feesCsv(String id,String course,String fees){
			this.id= id;this.course= course;this.fees = fees;
		}
		
		String toCSV(){
			return "\""+id+"\""+","
					+"\""+course+"\""+","
					+"\""+fees+"\"";
		}
	}
	
	class infoCsv{
		String id;
		String name;
		String address;
		String established;
		String affiliatedTo;
		String type;
		String accredited;
		String staff;
		String coursesOffered;
		
		public infoCsv(String id,String name,String address,String established,String affiliatedTo,String type,String accredited,String staff,String coursesOffered){
			this.id= id;
			this.name = name;
			this.address = address;
			this.established = established;
			this.affiliatedTo= affiliatedTo;
			this.type = type;
			this.accredited = accredited;
			this.staff = staff;
			this.coursesOffered =coursesOffered;
		}
		
		String toCSV(){
			return "\""+id+"\""+","
					+"\""+name+"\""+","
					+"\""+address+"\""+","
					+"\""+established+"\""+","
					+"\""+affiliatedTo+"\""+","
					+"\""+type+"\""+","
					+"\""+accredited+"\""+","
					+"\""+staff+"\"\n"+","
					+"\""+coursesOffered+"\"\n";
		}
	}

public class CollegeDunia {

	public static final String baseUrl = "https://collegedunia.com/";
	public static final String courseFeeLinkParticularCollege = "/courses-fees";
	public static final String addmissionLinkParticularCollege = "/admission";
	public static final String facultyLinkParticularCollege = "/faculty";
	
	
	
	
	static public List<String> getAllLinksOfParticularColleges(String courseName) throws IOException{
		List<String> links = new ArrayList<>(1500);
		File fileToWrite=new File(courseName+"_"+System.currentTimeMillis()+"_.txt");
		fileToWrite.createNewFile();
		FileWriter fileWriter = new FileWriter(fileToWrite,true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		String url = baseUrl + courseName;
		Document document = Jsoup.connect(url).get();
		Elements elements = document.getElementsByClass("col-sm-4");
		for(Element e:elements){
			System.out.println(e.getElementsByClass("clg-name-address").get(0).select("a").attr("href"));
			links.add(e.getElementsByClass("clg-name-address").get(0).select("a").attr("href"));
			bufferedWriter.write(e.getElementsByClass("clg-name-address").get(0).select("a").attr("href"));
			
			bufferedWriter.newLine();
			bufferedWriter.flush();
	
		}
		int i=1;
		while (!document.toString().equals("false")|| i>398) {
			document = Jsoup.connect(url+"?ajax=1&college_type=0&page="+i).get();
			i++;
			elements = document.getElementsByClass("col-sm-4");
			for(Element e:elements){
				System.out.println(e.getElementsByClass("clg-name-address").get(0).select("a").attr("href"));
				links.add(e.getElementsByClass("clg-name-address").get(0).select("a").attr("href"));
				bufferedWriter.write(e.getElementsByClass("clg-name-address").get(0).select("a").attr("href"));
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
			System.out.println(i);
		}
		bufferedWriter.flush();
		bufferedWriter.close();
		return links;
	}
	
	static public List<String> getLinksOfAllCollegesFromFile(String filePath) throws IOException{ 
		FileReader fr  = new FileReader(new File("bcom-colleges_1534879190821_.txt"));
		BufferedReader bufferedReader = new BufferedReader(fr);
		String line = bufferedReader.readLine();
		List<String> links = new ArrayList<>(4000);
		while (line !=null) {
			line = bufferedReader.readLine();
			links.add(line);
			//System.out.println(line);
			
		}
		return links;
	}
	
	static public void fees(String link, String id,BufferedWriter bw) throws IOException{
		String mainClassName = "single_course";
		Document document = Jsoup.connect(link+courseFeeLinkParticularCollege).get();
		
		for(Element e:document.getElementsByClass(mainClassName)){
			
			System.out.println(e.getElementsByClass("course_name").text()+" "+e.select("span[class=fees]").text());
			String course = e.getElementsByClass("course_name").text();
			String fees = e.select("span[class=fees]").text();
			bw.write(new feesCsv(id, course, fees).toCSV());
			bw.newLine();
			bw.flush();
		}
	}
	
	static public void info(String link, String id,BufferedWriter bw) throws IOException{
		Document document = Jsoup.connect(link).get();
		Elements elements = document.getElementsByTag("td");
		
		String name = document.getElementById("page_h1").text();
		String address = document.select("div[class=extra_info] span").get(0).text();
		String established="";
		String affiliatedTo="";
		String type="";
		String accredited="";
		String staff="";
		String coursesOffered="";
		
		System.out.println(name);
		System.out.println(address);
		for(int i=0;i<elements.size()-1;i++){
			if(elements.get(i).select("p").text().equals("Established")){
				established = elements.get(i+1).select("p").text();
				System.out.println(elements.get(i).select("p").text()+" "+established);
			}else if (elements.get(i).select("p").text().equals("Affiliated To")) {
				affiliatedTo = elements.get(i+1).select("p").text();
				System.out.println(elements.get(i).select("p").text()+" "+affiliatedTo);
			}else if (elements.get(i).select("p").text().equals("Type of University")) {
				type = elements.get(i+1).select("p").text();
				System.out.println(elements.get(i).select("p").text()+" "+type);
			}else if (elements.get(i).select("p").text().equals("Accredited By")) {
				accredited = elements.get(i+1).select("p").text();
				System.out.println(elements.get(i).select("p").text()+" "+accredited);
			}else if (elements.get(i).select("p").text().equals("Teaching Staff")) {
				staff = elements.get(i+1).select("p").text();
				System.out.println(elements.get(i).select("p").text()+" "+staff);
			}else if (elements.get(i).select("p").text().equals("Total Number of courses offered")) {
				coursesOffered = "";
				for(int j=i+1;j<elements.size();j++){
					coursesOffered += elements.get(j).select("p").text();
				}
				//System.out.println(elements.get(i).select("p").text()+" "+elements.get(i+1).select("p").text()+" "+elements.get(i+2).select("p").text());
			}
			
		}
		
		bw.write(new infoCsv(id, name, address, established, affiliatedTo, type, accredited, staff, coursesOffered).toCSV());
		bw.newLine();
		bw.flush();
	}
	

	
	static public void faculty(String link, String id,BufferedWriter bw) throws IOException{
		
		Document document = Jsoup.connect(link+facultyLinkParticularCollege).get();
		System.out.println(document.getElementsByClass("faculty_sub").get(0).text()+" "+document.getElementsByClass("faculty_name").get(0).text());
		String designation =document.getElementsByClass("faculty_sub").get(0).text();
		String name = document.getElementsByClass("faculty_name").get(0).text();
		String qualification ="";
		bw.write(new facultyCsv(id, designation, name, qualification).toCSV());
		bw.newLine();
		bw.flush();
		for(Element e:document.getElementsByClass("faculty_info")){
			
			name =e.getElementsByClass("other_faculty_name").text();
			designation = e.getElementsByClass("other_faculty_dept").text();
			qualification = e.getElementsByClass("other_faculty_experties").select("span").text();
			System.out.println(name+" "+designation+" "+qualification);
			bw.write(new facultyCsv(id, designation, name, qualification).toCSV());
			bw.newLine();
			bw.flush();
		}
	}
	public static void main(String[] args) throws IOException {
		String courseName =	"bcom-colleges";
	
		// getAllLinksOfParticularColleges(courseName);
//		for(String s:getLinksOfAllCollegesFromFile("")){
//			System.out.println(s);
//			Document document = Jsoup.connect(s).get();
//			System.out.println(document);
//			System.exit(0);
//		}
		List<String> links = getLinksOfAllCollegesFromFile("");
		
		File fileToWrite=new File(courseName+"_"+"faculty"+System.currentTimeMillis()+"_.csv");
		fileToWrite.createNewFile();
		FileWriter fileWriter = new FileWriter(fileToWrite,true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		File fileToWriteinfo=new File(courseName+"_"+"info"+System.currentTimeMillis()+"_.csv");
		fileToWriteinfo.createNewFile();
		FileWriter fileWriterinfo = new FileWriter(fileToWriteinfo,true);
		BufferedWriter bufferedWriterinfo = new BufferedWriter(fileWriterinfo);
		
		File fileToWritefees=new File(courseName+"_"+"fees"+System.currentTimeMillis()+"_.csv");
		fileToWritefees.createNewFile();
		FileWriter fileWriterfees = new FileWriter(fileToWritefees,true);
		BufferedWriter bufferedWriterfees = new BufferedWriter(fileWriterfees);
		
		String s= "https://collegedunia.com/college/2595-loyola-college-chennai";//+courseFeeLinkParticularCollege;
		//info(s);
		//	fees(s);
		int i =0;
		for(String link:links){
			i++;
			if(i<933) continue;
			try {
				faculty(link, link.split("/")[4].split("-")[0],bufferedWriter);	
				info(link, link.split("/")[4].split("-")[0],bufferedWriterinfo);
				fees(link, link.split("/")[4].split("-")[0],bufferedWriterfees);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
		}
		
//		for(String link:links){
//				
//			
//		}
//		
//		for(String link:links){
//				
//			
//		}
		
	}
	
	

}
