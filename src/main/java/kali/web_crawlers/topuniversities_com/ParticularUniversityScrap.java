package kali.web_crawlers.topuniversities_com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class ParticularUniversityScrap  {

	static long id=0;
	private University university;
	private File fileToStoreData;
	private BufferedWriter bufferedWriterFirstFile;
	private BufferedWriter bufferedWriterNextFile;
	private final String baseUrl="https://www.topuniversities.com";
	public ParticularUniversityScrap(University university, BufferedWriter bufferedWriterFirstFile, BufferedWriter bufferedWriterNextFile){
		this.university=university;
		this.bufferedWriterFirstFile=bufferedWriterFirstFile;
		this.bufferedWriterNextFile=bufferedWriterNextFile;
	}
	
	public static void main(String[] args) {
		University university=new University();
		university.setUniversityNodeUrl("/node/297196");
		//ParticularUniversityScrap particularUniversityScrap = new ParticularUniversityScrap(university, new File(""));
		//particularUniversityScrap.run();
	}
	
	public void storeNextPageData(University university, UniversitySubjects universitySubjects, BufferedWriter bufferedWriterFirstFile, BufferedWriter bufferedWriterNextFile) throws IOException  {
		bufferedWriterFirstFile.write(university.getUniversityDataInCSVFormate());
		bufferedWriterFirstFile.newLine();
		if(universitySubjects==null){
			return;
		}
		ArrayList<Grad> grads=universitySubjects.getGrads();
		for(int i=0;i<grads.size();i++){
			String gradName=grads.get(i).getGradName();
			ArrayList<SubjectHeading> subjectHeadings = grads.get(i).getSubjectHeadings();
			for(int j=0;j<subjectHeadings.size();j++){
				String subjectHeadingName=subjectHeadings.get(j).getSuvjectHeadingName();
				ArrayList<String> subjects=subjectHeadings.get(j).getSubjects();
				for (int k = 0; k < subjects.size(); k++) {
					String subject= subjects.get(k);
					UniversitySubjectToSave universitySubjectToSave=new UniversitySubjectToSave();
					universitySubjectToSave.setId(++id);
					universitySubjectToSave.setFkey(university.getId());
					universitySubjectToSave.setGrad(gradName);
					universitySubjectToSave.setSubjectHeading(subjectHeadingName);
					universitySubjectToSave.setSubjectString(subject);
					bufferedWriterNextFile.write(universitySubjectToSave.getUniversityDataInCSVFormate());
					bufferedWriterNextFile.newLine();
				}
			}
		}
		
	}
	
	public Grad getGradData(Element gradElement) {
		Grad gradData=new Grad();
		Element subjects= gradElement.select("div[class=view-content]").first();
		
		ArrayList<SubjectHeading> subjectHeadingsData=new ArrayList<SubjectHeading>();
		
		for (int i = 0; i < subjects.childNodeSize()/2; i++) {
			System.out.println("---");
			//System.out.print(subjects.child(i));
			Element subjectHieadingElement=subjects.child(i);
			
			SubjectHeading particularSubjectHeading=new SubjectHeading();
			//subject heading
			System.out.println(subjectHieadingElement.select("div[class=views-field views-field-field-dept-name]").text());
			particularSubjectHeading.setSuvjectHeadingName(subjectHieadingElement.select("div[class=views-field views-field-field-dept-name]").text());
			
			ArrayList<String> subjectsListData=new ArrayList<String>();
			ArrayList<Element> subjectsList=subjectHieadingElement.select("div[class=item-list] li");
			for (int j = 0; j < subjectsList.size(); j++) {
				//Subjects
				System.out.println(subjectsList.get(j).text());
				subjectsListData.add(subjectsList.get(j).text());	
			}
			particularSubjectHeading.setSubjects(subjectsListData);
			subjectHeadingsData.add(particularSubjectHeading);
		}
		gradData.setSubjectHeadings(subjectHeadingsData);
		return gradData;
	}
	
	public void run() {
		if(!this.university.getUniversityNodeUrl().equals("")){
			try {
				System.out.println("started");
				Document currentUrlDoc=Jsoup.connect(baseUrl+this.university.getUniversityNodeUrl()).timeout(6424242).get();
				currentUrlDoc.outputSettings().charset("ISO-8859-1");
				//university title
				System.out.println(currentUrlDoc.getElementsByClass("title_info").select("h1").text());
				this.university.setName(currentUrlDoc.getElementsByClass("title_info").select("h1").text());
				
				//university website
				System.out.println(currentUrlDoc.getElementsByClass("actions").select("a[class=btn btn-trans]").attr("href"));
				this.university.setUniversityOriginalWebsite(currentUrlDoc.getElementsByClass("actions").select("a[class=btn btn-trans]").attr("href"));
				//university status, research output, total students, staff
				ArrayList<Element> topDetails= currentUrlDoc.getElementsByClass("key pull-left");
				System.out.println(topDetails.size());
				
				for(int i=0;i<topDetails.size();i++){
					//System.out.println(topDetails.get(i).select("div label").text());
					String label=topDetails.get(i).select("div label").text();
					if(label.equals("QS Global World Ranking")){
						System.out.println(label+": "+topDetails.get(i).select("div[class=val]").text());
						this.university.setGlobalQsRanking(topDetails.get(i).select("div[class=val]").text());
					}else if (label.equals("Status")) {
						System.out.println(label+": "+topDetails.get(i).select("div[class=val]").text());
						this.university.setStatus(topDetails.get(i).select("div[class=val]").text());
					}else if (label.equals("Research Output")) {
						System.out.println(label+": "+topDetails.get(i).select("div[class=val]").text());
						this.university.setResearchOutput(topDetails.get(i).select("div[class=val]").text());
					}else if (label.equals("Total Students")) {
						System.out.println(label+": "+topDetails.get(i).select("div[class=val]").text());
						this.university.setTotalStudents(topDetails.get(i).select("div[class=val]").text());
					}else if (label.equals("Academic Faculty Staff")) {
						System.out.println(label+": "+topDetails.get(i).select("div[class=val]").text());
						this.university.setAcademicFacultyStaff(topDetails.get(i).select("div[class=val]").text());
					}else if (label.equals("International Students")) {
						System.out.println(label+": "+topDetails.get(i).select("div[class=val]").text());
						this.university.setInternationalStudents(topDetails.get(i).select("div[class=val]").text());
					}	
				}
				
				Element postGrad=currentUrlDoc.getElementsByClass("uni_post_depts").first();
				Element underGrad=currentUrlDoc.getElementsByClass("uni_under_depts").first();
				ArrayList<Grad> gradsData= new ArrayList<Grad>(); 
				Grad underGradData=getGradData(underGrad);
				underGradData.setGradName("Undergraduate Faculties");
				Grad postGradData=getGradData(postGrad);
				postGradData.setGradName("Postgraduate Faculties");
				gradsData.add(underGradData);
				gradsData.add(postGradData);
				UniversitySubjects universitySubjects=new UniversitySubjects();
				universitySubjects.setGrads(gradsData);
				this.university.setDataFecthed("1");
				storeNextPageData(this.university, universitySubjects, bufferedWriterFirstFile, bufferedWriterNextFile);
			} catch (Exception e) {
				System.err.println("ERROR in fetching "+university.getUniversityNodeUrl());
				try {
					storeNextPageData(this.university, null, bufferedWriterFirstFile, bufferedWriterNextFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.university.setDataFecthed("0");
				e.printStackTrace();
			}
		}
	}
}
