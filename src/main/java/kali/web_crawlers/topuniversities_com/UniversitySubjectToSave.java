package kali.web_crawlers.topuniversities_com;


public class UniversitySubjectToSave{
	private long id;
	private long fkey;
	private String grad;
	private String SubjectHeading;
	private String subjectString;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFkey() {
		return fkey;
	}
	public void setFkey(long fkey) {
		this.fkey = fkey;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public String getSubjectHeading() {
		return SubjectHeading;
	}
	public void setSubjectHeading(String subjectHeading) {
		SubjectHeading = subjectHeading;
	}
	public String getSubjectString() {
		return subjectString;
	}
	public void setSubjectString(String subjectString) {
		this.subjectString = subjectString;
	}
	
	public String getCSVHeader(){
		return "id,fkey,grad,SubjectHeading,subjectString";
	}
	
	public String getUniversityDataInCSVFormate() {
		return "\""+getId()+"\","
				+"\""+getFkey()+"\","
				+"\""+getGrad()+"\","
				+"\""+getSubjectHeading()+"\","
				+"\""+getSubjectString()+"\"";
	}
	
	
}