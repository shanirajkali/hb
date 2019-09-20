package kali.web_crawlers.topuniversities_com;

import java.util.ArrayList;
import java.util.HashMap;

class Grad{
	private String gradName;
	private ArrayList<SubjectHeading> subjectHeadings;
	public String getGradName() {
		return gradName;
	}
	public void setGradName(String gradName) {
		this.gradName = gradName;
	}
	public ArrayList<SubjectHeading> getSubjectHeadings() {
		return subjectHeadings;
	}
	public void setSubjectHeadings(ArrayList<SubjectHeading> subjectHeadings) {
		this.subjectHeadings = subjectHeadings;
	}
}

class SubjectHeading{
	private String suvjectHeadingName;
	private ArrayList<String> subjects;
	
	public String getSuvjectHeadingName() {
		return suvjectHeadingName;
	}
	public void setSuvjectHeadingName(String suvjectHeadingName) {
		this.suvjectHeadingName = suvjectHeadingName;
	}
	public ArrayList<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(ArrayList<String> subjects) {
		this.subjects = subjects;
	}	
}



public class UniversitySubjects {
	private ArrayList<Grad> grads;

	public ArrayList<Grad> getGrads() {
		return grads;
	}

	public void setGrads(ArrayList<Grad> grads) {
		this.grads = grads;
	}
	
}


