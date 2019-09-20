package kali.web_crawler.college_dunia;

public class XMLHttpRequest {
	
	void onreadystatechange(){
		
	}
	
	String responseText;
	String urlname;
	String methodName;
	boolean ajaxType;
	int status ;
	
	void open(String m, String u, boolean a){
		methodName =m;
		urlname = u;
		ajaxType = a;
	}
	
	String send(){
		//call url
		return "responsemilega";
	}
}
