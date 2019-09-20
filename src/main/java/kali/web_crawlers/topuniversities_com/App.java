package kali.web_crawlers.topuniversities_com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * @Author Shani RajKali
 * @Since 24/Jan/2017
 */
public class App {
	
	public static StringBuffer fetchFinalData(String subjectUrl,String countryName,String lavel,String course) throws IOException{
		
		StringBuffer completeSubjectUniversityDetailReadyCsv=new StringBuffer();
		
		
		
	/*
	 * fetching data for particular domain/subject
	 * "https://www.topuniversities.com/universities/country/india/level/undergrad/subject/computer-science-information-systems";
	 *  	
	 */
		Document subjectDoc=Jsoup.connect(subjectUrl).timeout(8336739).get();
		
	/*
	 * there is ul in subjectDoc that have the list of universities and their QS World University Ranking.
	 * sometimes there QS World Ranking is not available in ranking div so we making condition for the particular div
	 * that div has child or not.
	 */
		Element ulOfUniversities=subjectDoc.getElementById("universities-search");
		if(ulOfUniversities==null){
			return completeSubjectUniversityDetailReadyCsv;
		}
		Elements all_li_ofUniversities=ulOfUniversities.select("li");
		if(all_li_ofUniversities==null){
			return completeSubjectUniversityDetailReadyCsv;
		}
	//	System.out.println(universitis.select("li"));
	//	System.out.println(all_li_ofUniversities.size());
		
		for(int i=0;i<all_li_ofUniversities.size();i++){
			
			Element particular_li=all_li_ofUniversities.get(i);
		//	System.out.println(uni.select("a h2"));
			String universityName=particular_li.select("a h2").text();
			ArrayList<Element> paticular_li_children=particular_li.children();
			System.out.println("size of a:  "+paticular_li_children.size());
		//	System.out.println(uni.select("a div").get(2).text());
			String universityRankingCouldBeAvailable="";
			if(paticular_li_children.size()!=0){
				universityRankingCouldBeAvailable=particular_li.select("a div").get(2).text();
				}
			
			completeSubjectUniversityDetailReadyCsv.append(
												countryName
												+","+lavel
												+","+course
												+","+universityName
												+","+universityRankingCouldBeAvailable
												+"\n"
												);
		}	
		
		
		
		return completeSubjectUniversityDetailReadyCsv;
		
	}
	
	public static void main() throws IOException {
		
		System.out.println("Started At: "+System.currentTimeMillis());
		
		StringBuffer result=new StringBuffer();
		result.append("Country Name,"+"Course Lavel,"+"University Name,"+"Course,"+"Ranking"+"\n");
		
		String baseUrl="https://www.topuniversities.com/universities";
		Document baseUrlDoc=Jsoup.connect(baseUrl).timeout(3424242).get();
		
		
		baseUrlDoc.getElementById("pro-search_api_aggregation_2");
		Element countryListSelect=baseUrlDoc.getElementById("pro-search_api_aggregation_2");
	//	System.out.println(baseUrlDoc.getElementById("pro-search_api_aggregation_2"));
		Elements allCountryListOptions=countryListSelect.select("option");
	//	System.out.println(allCountryListOptions.size());
		
		
	aa:	for(int i=0;i<allCountryListOptions.size();i++){
			
			StringBuffer particularCountryData =new StringBuffer();
			particularCountryData.append("Country Name,"+"Course Lavel,"+"University Name,"+"Course,"+"Ranking"+"\n");
			
			String countryNameOptionValue=allCountryListOptions.get(i).attr("value");
		//	System.out.println(countryNameOptionValue);
			String countryNameOptionText=allCountryListOptions.get(i).text();
		//	System.out.println(countryNameOptionText);
			
			String countryUrl="/country/"+countryNameOptionValue;
			Document countryDoc=Jsoup.connect(baseUrl+countryUrl).timeout(434323542).get();
			
			//level like postgraduate/undergraduate in dropdown
			Element levelSelect=countryDoc.getElementById("edit-study-level");
			Elements levelOptions=levelSelect.select("option");
			
			//condition because first two element of levelOption is not useful 
			if(levelOptions.size()>3){
				
				for(int j=2;j<levelOptions.size();j++){
					
					String levelValue=levelOptions.get(j).attr("value");
				//	System.out.println(levelValue);
					String levelText=levelOptions.get(j).text();
					
					Document subjectDoc=Jsoup.connect(baseUrl+"/country/"+allCountryListOptions.get(i).attr("value")+levelValue).timeout(23343233).get();
				//	System.out.println(baseUrl+"/country/"+allCountryListOptions.get(i).attr("value")+levelValue);
					Element subjectSelect=subjectDoc.getElementById("pro-search_api_aggregation_4");
					if(subjectSelect!=null){
						
						System.out.println(subjectSelect);
						Elements subjectOptions=subjectSelect.select("option");
//						System.out.println(subjectOptions.size());
//						System.out.println(subjectOptions);
						for(int k=0;k<subjectOptions.size();k++){
						
							Document finalDetailDoc=Jsoup.connect(baseUrl+"/country/"
														+allCountryListOptions.get(i).attr("value")
														+levelValue+"/subject/"
														+subjectOptions.get(k).attr("value")).timeout(342424233).get();
//							System.out.println(subjectOptions.get(k).attr("value"));
//							System.out.println(baseUrl+"/country/"
//									+allCountryListOptions.get(i).attr("value")
//									+levelValue+"/subject/"
//									+subjectOptions.get(k).attr("value"));
							
													
						StringBuffer completeSubjectUniversityDetailReadyCsv = fetchFinalData(baseUrl+"/country/"
																					+allCountryListOptions.get(i).attr("value")
																					+levelValue+"/subject/"
																					+subjectOptions.get(k).attr("value")
																					,countryNameOptionText
																					,levelText
																					,subjectOptions.get(k).text());
						
							result.append(completeSubjectUniversityDetailReadyCsv);
							particularCountryData.append(completeSubjectUniversityDetailReadyCsv.toString());
							System.out.println(result.toString());
						}
						
					}
					System.out.println("out from loop");					
				}
			}
			else{
				System.out.println("||?////\"\"|?|?/|\"?||");
				break aa;
				}
			File countryNameFile=new File("particular_country_"+System.currentTimeMillis()+"_.csv");
			if(!countryNameFile.exists())	countryNameFile.createNewFile();
			FileWriter fileWriter = new FileWriter(countryNameFile,true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(particularCountryData.toString());
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
			
		}
		
		File countryNameFile=new File("topuniversities_"+System.currentTimeMillis()+"_.csv");
		if(!countryNameFile.exists())	countryNameFile.createNewFile();
		FileWriter fileWriter = new FileWriter(countryNameFile,true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(result.toString());
		bufferedWriter.newLine();
		bufferedWriter.flush();
		bufferedWriter.close();
		
	}
}
