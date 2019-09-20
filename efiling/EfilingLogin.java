package kali.web_crawlers.efiling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.apache.xerces.impl.xpath.XPath.LocationPath;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EfilingLogin {

	public static void main(String[] args) throws Exception {
		Unirest.setDefaultHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0");
		
		String baseUrl="https://incometaxindiaefiling.gov.in";
		
		Scanner scanner=new Scanner(System.in);
		
		FileWriter fileWriter = new FileWriter(new File("sessions.txt"),true);
		BufferedReader reader=new BufferedReader(new FileReader("sessions.txt"));
		String currentLine="";
		String nextLine="nusdflll";
		while (true) {
			nextLine=reader.readLine();
			System.out.println(nextLine);
			if (nextLine==null) {
				break;
			}
			currentLine=nextLine;
		}
		
		
		
		
		System.out.println(currentLine);
		currentLine ="";
		
		Map<String, String> headers =new HashMap<String, String>();
		headers.put("GET", "/e-Filing/UserLogin/LoginHome.html?lang=eng HTTP/1.1");
		headers.put("Host", "incometaxindiaefiling.gov.in");
		headers.put("Connection", "keep-alive");
		headers.put("Cache-Control", "max-age=0");
		headers.put("Upgrade-Insecure-Requests", "1");
	//	headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers.put("DNT", "1");
		headers.put("Referer", "https://incometaxindiaefiling.gov.in/home");
		headers.put("Accept-Encoding", "gzip, deflate, sdch, br");
		headers.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
	//	headers.put("Cookie", currentLine);
		
		HttpResponse<String> string2= Unirest.get("https://incometaxindiaefiling.gov.in/e-Filing/UserLogin/LoginHome.html?lang=eng")
				.headers(headers)
				.asString();
		System.out.println(string2.getHeaders());
		Object firstCookieObject = string2.getHeaders().get("Set-Cookie");
		String firstCookie = firstCookieObject.toString().substring(1, firstCookieObject.toString().length()-1);
		currentLine =firstCookie;
		System.out.println("cookie "+currentLine);
		System.out.println("---------------------------------------------------------");
	
		
		Document loginPageDoc= Jsoup.parse(string2.getBody().toString());
		String requestId=loginPageDoc.getElementById("Login_requestId").val();
		
		String captchaImgSrc=loginPageDoc.getElementById("captchaImg").attr("src");
		headers.put("Cookie", currentLine);
		Response response= Jsoup.connect("https://incometaxindiaefiling.gov.in"+captchaImgSrc)
				.headers(headers)
				.ignoreContentType(true).execute();
		System.out.println("https://incometaxindiaefiling.gov.in"+captchaImgSrc);
		String outputFolder= "/home/shanirajkali/Desktop/captcha/"+System.currentTimeMillis();
		FileOutputStream out= (new FileOutputStream(new java.io.File(outputFolder)));
		out.write(response.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
		out.close();														
		
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		Object cookie=string2.getHeaders().get("Set-Cookie");
		if (cookie!=null) {
			System.out.println(cookie);
			currentLine=cookie.toString().substring(1, cookie.toString().length()-1);
			bufferedWriter.write(currentLine);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
		}
		
		Map<String, String> loginHeaders = new HashMap<String, String>();
		loginHeaders.put("POST", "/e-Filing/UserLogin/Login.html HTTP/1.1");
		loginHeaders.put("Host", "incometaxindiaefiling.gov.in");
		loginHeaders.put("Connection", "keep-alive");
		loginHeaders.put("Cache-Control", "max-age=0");
		loginHeaders.put("Upgrade-Insecure-Requests", "1");
		loginHeaders.put("Origin", "https://incometaxindiaefiling.gov.in");
	//	loginHeaders.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36");
		loginHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		loginHeaders.put("DNT", "1");
		loginHeaders.put("Content-Type", "application/x-www-form-urlencoded");
		loginHeaders.put("Referer", "https://incometaxindiaefiling.gov.in/home");
		loginHeaders.put("Accept-Encoding", "gzip, deflate, sdch, br");
		loginHeaders.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
		loginHeaders.put("Cookie", currentLine);
		
		String captchaCode=scanner.nextLine();
		System.out.println("entered captcha code: "+captchaCode);
		
		String loginDetails="hindi=&requestId="
							+requestId+"&nextPage=&userName=CZZPK6947C&userPan=&password=%401KALIhpdlink&dob=04%2F10%2F1994&rsaToken=&requestId="
							+requestId+"&captchaCode="+captchaCode;
		System.out.println(Integer.toString(loginDetails.length()));
	//	loginHeaders.put("Content-Length", Integer.toString(loginDetails.length()));
		
		HttpResponse<String> loggedInDetails=Unirest
				.post("https://incometaxindiaefiling.gov.in/e-Filing/UserLogin/Login.html")
				.headers(loginHeaders)
				.body(loginDetails)
				.asString();
		
		System.out.println(loggedInDetails.getHeaders());
		System.out.println("---------------------------------------------------------");
	//	System.out.println(loggedInDetails.getBody());
		System.out.println("---------------------------------------------------------");
		System.out.println(loggedInDetails.getStatus());
		
		// request to login page
		Object loginResponseCookie=loggedInDetails.getHeaders().get("Set-Cookie");
		if (loginResponseCookie!=null) {
			System.out.println(loginResponseCookie);
			currentLine=loginResponseCookie.toString().substring(1, cookie.toString().length()-1);
			bufferedWriter.write(currentLine);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
		}
		String location = loggedInDetails.getHeaders().get("Location").toString();
		location = location.substring(1, location.length()-1);
		
		Map<String, String> loginFormHeader = new HashMap<String, String>();
		loginFormHeader.put("Referer", "https://incometaxindiaefiling.gov.in/e-Filing/UserLogin/LoginHome.html");
		loginFormHeader.put("GET",location+" HTTP/1.1");
		loginFormHeader.put("Host", "incometaxindiaefiling.gov.in");
		loginFormHeader.put("Connection", "keep-alive");
		loginFormHeader.put("Cache-Control", "max-age=0");
		loginFormHeader.put("Upgrade-Insecure-Requests", "1");
		loginFormHeader.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		loginFormHeader.put("DNT", "1");
		loginFormHeader.put("Accept-Encoding", "gzip, deflate, sdch, br");
		loginFormHeader.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
		loginFormHeader.put("Cookie", currentLine);
		
		HttpResponse<String> loggedInFormHome=Unirest.get(baseUrl+location).headers(loginFormHeader).asString();
		System.out.println("-----------------------------LoggedIn form home--------------------------------");
		System.out.println(loggedInFormHome.getBody());
		Document loggedInFormHomeDoc = Jsoup.parse(loggedInFormHome.getBody());
		Element menuBar=loggedInFormHomeDoc.select("ul[class=menu] li").get(2);
		String incomeTaxReturnPageLink = menuBar.select("div[class=submenu] dl dt a").attr("href");
		String[] loctionParts = location.split("/");
		for(int n=0;n<loctionParts.length;n++){
			System.out.println(loctionParts[n] +"  "+ n);
		}
		if (true) {
			System.out.println(loggedInFormHome.getHeaders());
			String forceLoginUrl = "/e-Filing/UserLogin/ForcedLogin.html";
			loginFormHeader.put("Referer", baseUrl+location);
			loginFormHeader.put("GET",forceLoginUrl+" HTTP/1.1");
			loggedInFormHome=Unirest.get(baseUrl+forceLoginUrl).headers(loginFormHeader).asString();
	//		System.out.println(loggedInFormHome.getBody());
			System.out.println(loggedInFormHome.getHeaders());
//			String forceLoginHomeLocation = loggedInFormHome.getHeaders().get("Location").toString();
//			forceLoginHomeLocation = forceLoginHomeLocation.substring(1, forceLoginHomeLocation.length()-1);
//			loginFormHeader.put("Referer", baseUrl+location);
//			loginFormHeader.put("GET",forceLoginHomeLocation+" HTTP/1.1");
//			loggedInFormHome=Unirest.get(baseUrl+forceLoginHomeLocation).headers(loginFormHeader).asString();
//			System.out.println("in force loggedin page");
		}
		
		System.out.println(loggedInFormHome.getBody());
		loggedInFormHomeDoc = Jsoup.parse(loggedInFormHome.getBody());
		menuBar=loggedInFormHomeDoc.select("ul[class=menu] li").get(2);
		
		System.out.println(menuBar);
		 incomeTaxReturnPageLink = menuBar.select("div[class=submenu] dl dt a").attr("href");
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println(incomeTaxReturnPageLink);
		Map<String, String> incomeTaxReturnPageHeader = new HashMap<String , String>();
		incomeTaxReturnPageHeader.put("Referer", baseUrl+location);
		incomeTaxReturnPageHeader.put("GET",incomeTaxReturnPageLink+" HTTP/1.1");
		incomeTaxReturnPageHeader.put("Host", "incometaxindiaefiling.gov.in");
		incomeTaxReturnPageHeader.put("Connection", "keep-alive");
	//	loginFormHeader.put("Cache-Control", "max-age=0");
		incomeTaxReturnPageHeader.put("Upgrade-Insecure-Requests", "1");
	//	loginFormHeader.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36");
		incomeTaxReturnPageHeader.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		incomeTaxReturnPageHeader.put("DNT", "1");
		incomeTaxReturnPageHeader.put("Accept-Encoding", "gzip, deflate, sdch, br");
		incomeTaxReturnPageHeader.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
		incomeTaxReturnPageHeader.put("Cookie",currentLine);
		
		System.out.println("-----------------------------------incometax return page doc-------------------");
		System.out.println("Req: "+baseUrl+incomeTaxReturnPageLink);
		HttpResponse<String> incomeTaxReturnPageResponse=Unirest.get(baseUrl+incomeTaxReturnPageLink).headers(incomeTaxReturnPageHeader).asString();
		Document incomeTaxReturnPageDoc = Jsoup.parse(incomeTaxReturnPageResponse.getBody());
		System.out.println(incomeTaxReturnPageDoc);
		System.out.println(incomeTaxReturnPageResponse.getHeaders());
	
		System.out.println("-----------------------------------request for xml upload-----------------------");
		Map<String, String> requestForXmlUpload = new HashMap<String, String >();
		requestForXmlUpload.put("Referer", baseUrl+incomeTaxReturnPageLink);
		requestForXmlUpload.put("POST","/e-Filing/MyAccount/UploadReturnWithEverify.html HTTP/1.1");
		requestForXmlUpload.put("Host", "incometaxindiaefiling.gov.in");
		requestForXmlUpload.put("Connection", "keep-alive");
	//	loginFormHeader.put("Cache-Control", "max-age=0");
		requestForXmlUpload.put("Origin", "https://incometaxindiaefiling.gov.in");
		requestForXmlUpload.put("Upgrade-Insecure-Requests", "1");
	//	loginFormHeader.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36");
		requestForXmlUpload.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		requestForXmlUpload.put("DNT", "1");
		requestForXmlUpload.put("Content-Type", "application/x-www-form-urlencoded");
		requestForXmlUpload.put("Accept-Encoding", "gzip, deflate, sdch, br");
		requestForXmlUpload.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
		requestForXmlUpload.put("Cookie",currentLine);
		String requestForUploadForm= incomeTaxReturnPageDoc.getElementById("UploadReturnWithEverify_ID").val();
	//	System.out.println(requestForUploadForm);
		String requestForXmlUploadFormData= "ID="
				+requestForUploadForm
				+"&noRegDscFlag=Y&retDtlsBean.panNo=CZZPK6947C&retDtlsBean.asstYear=2017&retDtlsBean.formName=ITR-1&retDtlsBean.filingUs1192b=-1&retDtlsBean.filingType=-1&uploadType=U&__checkbox_retDtlsBean.flagDetails.section44AB=true&__checkbox_retDtlsBean.sec92EFlag=true&evcOption=ITRV&isHindiCheck=No";
		HttpResponse<String> requestForXmlUploadForm = Unirest.post("https://incometaxindiaefiling.gov.in/e-Filing/MyAccount/UploadReturnWithEverify.html")
							.headers(requestForXmlUpload)
							.body(requestForXmlUploadFormData).asString();
		System.out.println("-----------------------------------request for xml upload-----------------------");
//		System.out.println(requestForXmlUploadForm.getBody());
//		System.out.println(requestForXmlUploadForm.getStatus());
//		System.out.println(requestForXmlUploadForm.getHeaders());
		Document uploadXmlFormDoc = Jsoup.parse(requestForXmlUploadForm.getBody());
		
		
		System.out.println("-----------------------------------upload xml file------------------------------");
		Map<String, String > uploadXmlHeader= new HashMap<String,String>();
		uploadXmlHeader.put("Referer", "https://incometaxindiaefiling.gov.in/e-Filing/MyAccount/UploadReturnWithEverify.html");
		uploadXmlHeader.put("POST","/e-Filing/MyAccount/UploadReturnWithOptedEVerification.html HTTP/1.1");
		uploadXmlHeader.put("Host", "incometaxindiaefiling.gov.in");
		uploadXmlHeader.put("Connection", "keep-alive");
		uploadXmlHeader.put("Cache-Control", "max-age=0");
		uploadXmlHeader.put("Origin", "https://incometaxindiaefiling.gov.in");
		uploadXmlHeader.put("Upgrade-Insecure-Requests", "1");
		uploadXmlHeader.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		uploadXmlHeader.put("DNT", "1");
		uploadXmlHeader.put("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
		uploadXmlHeader.put("Accept-Encoding", "gzip, deflate, sdch, br");
		uploadXmlHeader.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
		uploadXmlHeader.put("Cookie",currentLine);
		
		UploadXml.uploadXml(uploadXmlFormDoc, currentLine);
			
		scanner.close();
		reader.close();
	}

}
