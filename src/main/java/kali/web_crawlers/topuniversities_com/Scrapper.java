package kali.web_crawlers.topuniversities_com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import kali.web_crawlers.extension_info_fetching.ExtensionInformation;
import kali.web_crawlers.repository.UniversityRepository;
import kali.web_crawlers.service.UniversityService;

public class Scrapper {
	
	private static long id=0;
	private final static String baseUrl="https://www.topuniversities.com";
	private final static String urlSeparator="/";
	private final static String countryDataUrl="https://www.topuniversities.com/universities/country";
	
	/*
	 * to fetch all values in location by getting the option element by Id "pro-search_api_aggregation_2" https://www.topuniversities.com/universities
	 */
	public static HashMap<String, String> getListOfCountryInOption() throws IOException{
		HashMap<String, String> countryNameAndUrlValue = new HashMap<String, String>();
		String currentUrl=Scrapper.baseUrl+Scrapper.urlSeparator+"universities";
		Document currentUrlDoc=Jsoup.connect(currentUrl).timeout(3424242).get();
		currentUrlDoc.getElementById("pro-search_api_aggregation_2");
		Element countryListSelect=currentUrlDoc.getElementById("pro-search_api_aggregation_2");
	//	System.out.println(baseUrlDoc.getElementById("pro-search_api_aggregation_2"));
		Elements allCountryListOptions=countryListSelect.select("option");
		System.out.println(allCountryListOptions.size());
		for(int i=0;i<allCountryListOptions.size();i++){
			String countryNameOptionValue=allCountryListOptions.get(i).attr("value");
			System.out.println(countryNameOptionValue);
			String countryNameOptionText=allCountryListOptions.get(i).text();
			countryNameAndUrlValue.put(countryNameOptionValue, countryNameOptionText);
			System.out.println(countryNameOptionText);
		}
		
		return countryNameAndUrlValue;
	}
	
	public static University getParticularUniversityData(Element universityElement){
		University university = new University();
		String universityName=universityElement.select("a h2").text();
		String universityNodeUrl=universityElement.select("a").attr("href");
		String logoLiveUrl=universityElement.select("a div div img").attr("src");
		university.setName(universityName);
		university.setUniversityNodeUrl(universityNodeUrl);
		university.setLogoLiveUrl(logoLiveUrl);
		return university;
	}
	
	public static ArrayList<University> particularCountryData(String countryName, String countryValue) throws IOException{
		ArrayList<University> universities=new ArrayList<University>(300);
 		String currentUrl=Scrapper.countryDataUrl+Scrapper.urlSeparator+countryValue;
		Document universitiesPageDoc=Jsoup.connect(currentUrl).timeout(8336739).get();
		Element ulOfUniversities=universitiesPageDoc.getElementById("universities-search");
		if(ulOfUniversities != null){
			Elements all_li_ofUniversities=ulOfUniversities.select("li");
			if (all_li_ofUniversities!=null) {
				for(int i=0;i<all_li_ofUniversities.size();i++){
					Element particular_li=all_li_ofUniversities.get(i);
					University university=getParticularUniversityData(particular_li);
					university.setCountryName(countryName);
					universities.add(university);
					System.out.println(university.toString());
				}	
			}
		}
		return universities;
	}
	
	public static void  downloadImage(String imageURL, String outputDir) throws IOException {
		
		File file = new File("E:\\shani rajkali\\CODE\\web_crawlers\\topuniversities_university_image\\firstFile.csv");
		FileReader fileReader=new FileReader(file);
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		
		BufferedWriter bWriter=new BufferedWriter(new FileWriter("E:\\shani rajkali\\CODE\\web_crawlers\\topuniversities_university_image\\image_data.csv"));
		String line=bufferedReader.readLine();
		while (line!=null) {
			try {
			String string=	line.split(",")[1];			
			Response response= Jsoup.connect(string).ignoreContentType(true).execute();
			String imageNameString= +System.currentTimeMillis()+".jpg";
			String outputFolder= "topuniversities_university_image\\images\\"+imageNameString;
			bWriter.write(line.split(",")[0]+","+imageNameString+"\n");
			FileOutputStream out= (new FileOutputStream(new java.io.File(outputFolder)));
			out.write(response.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
			out.flush();
			out.close();
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("OUt oF BouND");
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("io ex");
			}
			
			line=bufferedReader.readLine();
		}
		bWriter.flush();
		bWriter.close();
	}
	
	public static  void addUniversitiesDataToFile(HashMap<String,String> countries) throws IOException{
		StringBuffer data = new StringBuffer();
		Set<String> keys = countries.keySet();
		Iterator<String> keysItrator= keys.iterator();
		
    	File firstFileToWrite=new File("firstFile"+"_"+System.currentTimeMillis()+"_.csv");
    	FileWriter firstFileWriter = new FileWriter(firstFileToWrite,true);
		BufferedWriter firstBufferedWriter = new BufferedWriter(firstFileWriter);
    	File secondFileToWrite=new File("secondFile"+"_"+System.currentTimeMillis()+"_.csv");
    	FileWriter secondFileWriter = new FileWriter(secondFileToWrite,true);
		BufferedWriter secondBufferedWriter = new BufferedWriter(secondFileWriter);
		firstFileToWrite.createNewFile();
		secondFileToWrite.createNewFile();
		
		while (keysItrator.hasNext()) {
			String nextKey= keysItrator.next();
			String countryValue=countries.get(nextKey);
			//data.append(University.getCSVHeader()+"\n");
			System.out.println(nextKey);
			ArrayList<University> universities=particularCountryData(countryValue, nextKey);
			for(int i=0;i<universities.size();i++){
				if(universities.get(i)!=null)
					System.out.println(universities.get(i).toString()+" hgjhg");
				
				University universityData=universities.get(i);
				universityData.setId(++id);
				ParticularUniversityScrap r = new ParticularUniversityScrap(universityData,firstBufferedWriter,secondBufferedWriter);
	    	   r.run();
				//executor.execute(r);
				System.out.println(universities.get(i).getUniversityDataInCSVFormate());
			}
		}
		
		firstBufferedWriter.flush();
		firstBufferedWriter.close();
		secondBufferedWriter.flush();
		secondBufferedWriter.close();
		
	}
	
	public static ArrayList<UniversitySubjects> getAllSubjects(String pageLink){
		ArrayList<UniversitySubjects> universitySubjects = new ArrayList<UniversitySubjects>();
		try {
			Document currentUrlDoc=Jsoup.connect(Scrapper.baseUrl+Scrapper.urlSeparator).timeout(3424242).get();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return universitySubjects;
	}
	public static void main(String[] args)  throws IOException {
	/*	HashMap<String, String> countries= new HashMap<String, String>();
		countries.put("india", "India");
		addUniversitiesDataToFile(getListOfCountryInOption());
	*/	downloadImage("https://www.topuniversities.com/sites/default/files/styles/logo_90x90/public/tokyo-institute-of-technology_613_medium_1.jpg", "jhgh");
		}
}
