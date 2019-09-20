package kali.web_crawlers.topuniversities_com;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class University{
	private String name;
	private String url;
	private String logoLocalUrlOrName;
	private String logoLiveUrl;
	private String bgImageLocalUrlOrName;
	private String bgImageLiveUrl;
	private String universityNodeUrl;
	private String status;
	private String researchOutput;
	private String totalStudents;
	private String academicFacultyStaff;
	private String universityOriginalWebsite;
	private String countryName;
	private String globalQsRanking;
	private String internationalStudents;
	private String dataFecthed;
	
	public String getDataFecthed() {
		return dataFecthed;
	}
	public void setDataFecthed(String dataFecthed) {
		this.dataFecthed = dataFecthed;
	}
	public String getInternationalStudents() {
		return internationalStudents;
	}
	public void setInternationalStudents(String internationalStudents) {
		this.internationalStudents = internationalStudents;
	}

	
	
	public String getGlobalQsRanking() {
		return globalQsRanking;
	}
	public void setGlobalQsRanking(String globalQsRanking) {
		this.globalQsRanking = globalQsRanking;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUniversityOriginalWebsite() {
		return universityOriginalWebsite;
	}
	public void setUniversityOriginalWebsite(String universityOriginalWebsite) {
		this.universityOriginalWebsite = universityOriginalWebsite;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getWebsite() {
		return universityOriginalWebsite;
	}
	public void setWebsite(String universityOriginalWebsite) {
		this.universityOriginalWebsite = universityOriginalWebsite;
	}
	public String getUniversityNodeUrl() {
		return universityNodeUrl;
	}
	public void setUniversityNodeUrl(String universityNodeUrl) {
		this.universityNodeUrl = universityNodeUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResearchOutput() {
		return researchOutput;
	}
	public void setResearchOutput(String researchOutput) {
		this.researchOutput = researchOutput;
	}
	public String getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(String totalStudents) {
		this.totalStudents = totalStudents;
	}
	public String getAcademicFacultyStaff() {
		return academicFacultyStaff;
	}
	public void setAcademicFacultyStaff(String academicFacultyStaff) {
		this.academicFacultyStaff = academicFacultyStaff;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLogoLocalUrlOrName() {
		return logoLocalUrlOrName;
	}
	public void setLogoLocalUrlOrName(String logoLocalUrlOrName) {
		this.logoLocalUrlOrName = logoLocalUrlOrName;
	}
	public String getLogoLiveUrl() {
		return logoLiveUrl;
	}
	public void setLogoLiveUrl(String logoLiveUrl) {
		this.logoLiveUrl = logoLiveUrl;
	}
	public String getBgImageLocalUrlOrName() {
		return bgImageLocalUrlOrName;
	}
	public void setBgImageLocalUrlOrName(String bgImageLocalUrlOrName) {
		this.bgImageLocalUrlOrName = bgImageLocalUrlOrName;
	}
	public String getBgImageLiveUrl() {
		return bgImageLiveUrl;
	}
	public void setBgImageLiveUrl(String bgImageLiveUrl) {
		this.bgImageLiveUrl = bgImageLiveUrl;
	}
	public String toString() {
		return "Name: "+name+
				" URL: "+url+
				" logoLocalUrlOrName: "+logoLocalUrlOrName+
				" logoLiveUrl: "+logoLiveUrl+
				" bgImageLocalUrlOrName: "+bgImageLocalUrlOrName+
				" bgImageLiveUrl: "+bgImageLiveUrl+
				" universityNodeUrl: "+universityNodeUrl+
				" status: "+status+
				" researchOutput: "+researchOutput+
				" totalStudents: "+totalStudents+
				" academicFacultyStaff: "+academicFacultyStaff+
				" universityOriginalWebsite: "+universityOriginalWebsite+
				" country: "+countryName;
	}
	
	public static String getCSVHeader(){
		return "Id,"
				+ "Country,"
				+ "University Name,"
				+ "logo local Location,"
				+ "Logo live URL,"
				+ "bg image local,"
				+ "bg image live URL,"
				+ "University node URL,"
				+ "University Url,"
				+ "Status,"
				+ "Research Output,"
				+ "Total Students,"
				+ "Academic Faculty Staff,"
				+ "International Students,"
				+ "Data Fetched";
	}
	
	public String getUniversityDataInCSVFormate() {
		return "\""+getId()+"\","
				+"\""+getCountryName()+"\","
				+"\""+getName()+"\","
				+"\""+getLogoLocalUrlOrName()+"\","
				+"\""+getLogoLiveUrl()+"\","
				+"\""+getBgImageLocalUrlOrName()+"\","
				+"\""+getBgImageLiveUrl()+"\","
				+"\""+getUniversityNodeUrl()+"\","
				+"\""+getUniversityOriginalWebsite()+"\","
				+"\""+getStatus()+"\","
				+"\""+getResearchOutput()+"\","
				+"\""+getTotalStudents()+"\","
				+"\""+getAcademicFacultyStaff()+"\","
				+"\""+getInternationalStudents()+"\","
				+"\""+getDataFecthed()+"\",";
	}
}
