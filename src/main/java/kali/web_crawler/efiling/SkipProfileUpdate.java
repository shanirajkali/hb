package kali.web_crawler.efiling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.tomcat.util.log.SystemLogHandler;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

/*
 * @Author Shani rajkali
 * @Since Monday, 22 July, 2018, 18HRS
 * @Updated 
 * 
 * in this class some methods has those own sequence no. we must to call those methods according to sequence no.
 */
public class SkipProfileUpdate {
	
	//all constants(header keys)
	private static final String referer = "Referer";
	private static final String setCookie = "Set-Cookie";
	private static final String cookie = "Cookie";
	private static final String dnt = "DNT";
	private static final String host = "Host";
	private static final String allowCOR = "Access-Control-Allow-Origin";
	private static final String contentType = "Content-Type";
	private static final String xurl = "application/x-www-form-urlencoded";
	private static final String location = "Location";
	private static final String cacheControl = "Cache-Control";
	private static final String maxAge0 = "max-age=0";
	private static final String pragma = "pragma";
	private static final String get = "GET";
	private static final String http_1_1 =" HTTP/1.1";
	private static final String post = "POST";
	//private static final 
	//all cookies
	public static String ak_bmsc = "";
	public static String bm_mi = "";
	public static String jsess = "";
	public static String bm_sv = "";
	public static String refererUrl = "";
	
	private static final String baseUrl = "https://portal.incometaxindiaefiling.gov.in";
	static final String homeUrl="https://www.incometaxindiaefiling.gov.in/home";
	static final String loginHomeUrl = "https://portal.incometaxindiaefiling.gov.in/e-Filing/UserLogin/LoginHome.html?lang=eng";
	private static String loginUrl = "/e-Filing/UserLogin/Login.html";
	
	static BasicCookieStore basicCookieStore = new BasicCookieStore();
	static Map<String, String> headers = new HashMap<>();
	
	
	
	
	private static String parseHeader(String notParse){
		return notParse.substring(1,notParse.length()-1);
	}
	
	public static void setAllCookies(List<String> cookies){
		if(cookies == null) return;
		for(String c:cookies){
			String[] cStrings = c.split(";");
			if (cStrings[0].startsWith("ak_bmsc")) {
				ak_bmsc = cStrings[0];
			}else if (cStrings[0].startsWith("bm_mi")) {
				bm_mi = cStrings[0];
			}else if (cStrings[0].startsWith("JSESSION")) {
				
				jsess = cStrings[0];
				System.out.println(jsess);
			}else if (cStrings[0].startsWith("bm_sv")) {
				bm_sv = cStrings[0];
			}
		}
		
		headers.put(cookie, getCookie());
	}
	
	public static String getCookie(){
		String j = jsess.equals("")?"":jsess+"; ";
		String bm =bm_sv.equals("")?"":bm_sv+"; ";
		String ak = ak_bmsc.equals("")?"":ak_bmsc+"; ";
		String bmm = bm_mi.equals("")?"":bm_mi;
		return	j+bm+ak+bmm;
	}
	
	public SkipProfileUpdate() {
		Unirest.setDefaultHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0");		
		Unirest.setHttpClient(org.apache.http.impl.client.HttpClients.custom()
                .setDefaultCookieStore(basicCookieStore)
                .build());
	//	headers.put("scheme", "https");
		headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
	//	headers.put("accept-encoding", "gzip, deflate, br");
		headers.put("accept--language", "en-US,en;q=0.9,es;q=0.8,hi;q=0.7,fil;q=0.6");
		headers.put("cache-control", "no-cache");
		headers.put("Connection", "keep-alive");
		headers.put(dnt, "1");
		headers.put(referer, "https://www.google.co.in/");
		headers.put("upgrade-insecure-requests", "1");
		headers.put("pragma", "no-cache");
		
		
		
		Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));
		 for(String log:loggers) { 
			    Logger logger = (Logger)LoggerFactory.getLogger(log);
			    logger.setLevel(Level.INFO);
			    logger.setAdditive(false);
			    }
	}
	
	
	private static HttpResponse<String> firstReq() throws UnirestException{
		HttpResponse<String> resHeaders= Unirest.get(homeUrl).headers(headers).asString();
		
		return resHeaders;
	}
	
	private static HttpResponse<String> login(HttpResponse<String> prevRes) throws UnirestException, IOException {
		
		headers.put(referer, homeUrl);			
		HttpResponse<String> res= Unirest.get(loginHomeUrl).headers(headers).asString();
		setAllCookies(res.getHeaders().get(setCookie));
		
		Document document = Jsoup.parse(res.getBody());
		String captchaImgSrc =document.getElementById("captchaImg").attr("src");
		String requestId=document.getElementById("Login_requestId").val();
		
		String currAllowedOrigin = prevRes.getHeaders().get(allowCOR).get(0);
		
		Response response= Jsoup.connect(currAllowedOrigin+captchaImgSrc)
				.referrer(loginHomeUrl)
				.header("dnt", headers.get(dnt))
				.header(cookie, headers.get(cookie))
				.ignoreContentType(true).execute();
		String outputFolder= "D:\\shani rajkali\\CODE\\web_crawlers\\captcha\\"+System.currentTimeMillis()+".png";
		FileOutputStream out= (new FileOutputStream(new java.io.File(outputFolder)));
		out.write(response.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
		out.close();
		
		
		
			
		Map<String, String> loginHeaders = new HashMap<String, String>();
	//	loginHeaders.put("POST", "/e-Filing/UserLogin/Login.html HTTP/1.1");
	//	loginHeaders.put("Host", "incometaxindiaefiling.gov.in");
	//	loginHeaders.put("Connection", "keep-alive");
	//	loginHeaders.put("Cache-Control", "max-age=0");
	//	loginHeaders.put("Upgrade-Insecure-Requests", "1");
	//	loginHeaders.put("Origin", "https://incometaxindiaefiling.gov.in");
	//	loginHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	//	loginHeaders.put("DNT", "1");
	//	loginHeaders.put("Content-Type", "application/x-www-form-urlencoded");
	//	loginHeaders.put("Referer", "https://incometaxindiaefiling.gov.in/home");
	//	loginHeaders.put("Accept-Encoding", "gzip, deflate, sdch, br");
	//	loginHeaders.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
		
		
		Scanner scanner=new Scanner(System.in);
		String captchaCode=scanner.nextLine();
		String loginDetails="hindi=N&requestId="+requestId+"&nextPage=&userName=CZZPK6947C&userPan=&password=%241KALIhpdlink&rsaToken=&requestId="+requestId+"&captchaCode="+captchaCode;
		System.out.println(loginDetails);
	//	loginHeaders.put("Content-Length", Integer.toString(loginDetails.length()));
	
		headers.put(referer,currAllowedOrigin+loginUrl);
		headers.put("Host", "portal.incometaxindiaefiling.gov.in");
		headers.put(contentType, xurl);
		System.out.println(headers);
		System.out.println(currAllowedOrigin+loginUrl);
		HttpResponse<String> loggedInDetails=Unirest
				.post(currAllowedOrigin+"/e-Filing/UserLogin/Login.html")
				.headers(headers)
				.body(loginDetails)
				.asString();
		
		
		
		return loggedInDetails;
		
	}
	
//	public static HttpResponse<String> skipUpdateProfile(HttpResponse<String> prevRes){
//		String profileHome = prevRes.getHeaders().getFirst("Location");
//		setAllCookies(prevRes.getHeaders().get(setCookie));
//		//MyAccountHome
//		if(profileHome.contains("")){AN
//			
//		}
//	}
	
 public static	HttpResponse<String> homeAfterLogin(HttpResponse<String> prevRes) throws UnirestException{
		setAllCookies(prevRes.getHeaders().get(setCookie));
		String homeLoc = prevRes.getHeaders().get(location).get(0);
		if(homeLoc.contains("Forcekbkjhbkjhd")){
			
		}else {
			String refererNextRequest = baseUrl+homeLoc;
			headers.remove(contentType);
			
			headers.put(cacheControl, maxAge0);
			headers.remove(pragma);
			headers.remove("cache-control");
			headers.put(referer, "https://portal.incometaxindiaefiling.gov.in/e-Filing/UserLogin/LoginHome.html");
			headers.put(host, "portal.incometaxindiaefiling.gov.in");
			headers.put(get, homeLoc+http_1_1);
			System.out.println(headers);
			
			HttpResponse<String> loggedInDetails=Unirest
					.get(baseUrl+homeLoc)
					.headers(headers)
					.asString();
			System.out.println(headers);
			headers.put(referer, refererNextRequest);
			return loggedInDetails;
		}
		return null;
	}

 public static	HttpResponse<String> viewITRReturnsNavBar(HttpResponse<String> prevRes) throws UnirestException{
		setAllCookies(prevRes.getHeaders().get(setCookie));
		 
		 Document document = Jsoup.parse(prevRes.getBody());
			Elements elements = document.getElementsByClass("submenu");
			Element	element= elements.get(0);
			String filedReturnsLink =  element.getElementsByTag("a").get(1).attr("href");
			System.out.println(filedReturnsLink);
			
			headers.put(get, filedReturnsLink+http_1_1);
			
			HttpResponse<String> response=Unirest
					.get(baseUrl+filedReturnsLink)
					.headers(headers)
					.asString();
			
			setAllCookies(response.getHeaders().get(setCookie));
			System.out.println(response.getBody());
			
			document = Jsoup.parse(response.getBody());
			
			
			String formId = "ViewReturnsFormsPage";
			String requestIdId = "requestId";
			String reqType = "reqType=ITR";
			String formFeildId2=  "ViewReturnsFormsPage_userRoleId";
			String formFeildId3 = "ViewReturnsFormsPage_userType";
			String formFeildId4 = "userId";
			
			String actionUrl = document.getElementById(formId).attr("action");
			String requestParameter1 = document.getElementById(requestIdId).attr("name")+"="+document.getElementById(requestIdId).attr("value");
			String requestParameter2 = document.getElementById(formFeildId2).attr("name")+"="+document.getElementById(formFeildId2).attr("value");
			String requestParameter3 = document.getElementById(formFeildId3).attr("name")+"="+document.getElementById(formFeildId3).attr("value");
			String requestParameter4 = document.getElementById(formFeildId4).attr("name")+"="+document.getElementById(formFeildId4).attr("value");
			String requestParameter5 = "reqType=ITR";
			String reqUrl = "dropkey=ViewMyReturnsITR"+"&"+requestParameter1+"&"
							+requestParameter2+"&"+requestParameter3+"&"+requestParameter4+"&"+requestParameter5;
			System.out.println(reqUrl);
			
			headers.put(referer, baseUrl+filedReturnsLink);
			headers.remove(get);
			headers.put(post, actionUrl+http_1_1);
			headers.put(contentType, xurl);
		
			response=Unirest
					.post(baseUrl+actionUrl)
					.headers(headers)
					.body(reqUrl)
					.asString();
			System.out.println(response.getHeaders());
			System.out.println("readreqest");
			System.out.println(headers);
			headers.put(referer, reqUrl);
			refererUrl = baseUrl+response.getHeaders().get(location).get(0);
			
			response = Unirest
					.get(baseUrl+response.getHeaders().get(location).get(0))
					.headers(headers)
					.asString();
			headers.put(referer, refererUrl);
			
			//System.out.println(response.getBody());
			System.out.println("readreqest");
			System.out.println(response.getHeaders());
			return response;
	}

 public static	HttpResponse<String> clickAckNoPageAndGotoDownloadPdfPag(HttpResponse<String> prevRes) throws UnirestException, IOException{
	
	 
	 headers.remove(get);
	 headers.put(contentType, xurl);
	 Document	document = Jsoup.parse(prevRes.getBody());
	 String id = document.getElementById("sessionId").attr("value");
	 Elements trs =  document.getElementById("ViewMyReturnsITR").select("table:eq(2) tbody tr");
	 System.out.println(trs);
	 for(int i=1;i<trs.size();i++){
		 System.out.println("----------------------------------------dddd-----------------------------------------");
		// System.out.println(trs.get(i));
		 System.out.println(trs.get(i).getElementsByTag("a").text());
	 }
	 String firstAskNo = trs.get(1).getElementsByTag("a").text();
	 
	 HttpResponse<String> response=Unirest
				.post("https://portal.incometaxindiaefiling.gov.in/e-Filing/MyAccount/ReturnStatusViewCombined.html?ID="+id+"&ackNo="+firstAskNo)
				.headers(headers)
				.body("requestID="+id)
				.asString();
	 
	// System.out.println(response.getBody());
	 headers.put(referer, "https://portal.incometaxindiaefiling.gov.in/e-Filing/MyAccount/ReturnStatusViewCombined.html?ID="+id+"&ackNo="+firstAskNo);
	 response = Unirest
				.get("https://portal.incometaxindiaefiling.gov.in/e-Filing/MyAccount/ViewMyDetails.html?ID="+id+"&ackNumber="+firstAskNo)
				.headers(headers)
				.asString();
	// System.out.println(response.getBody());
	 //System.out.println(response.getHeaders());
	 //System.out.println(response.getStatusText());
	 File targetFile = new File("ackk.pdf");
	 OutputStream fileOutputStream = new FileOutputStream(targetFile);
	 InputStream inputStream = response.getRawBody();
	 byte[] buffer = new byte[inputStream.available()];
	 inputStream.read(buffer);
	 fileOutputStream.write(buffer);
fileOutputStream.flush();
fileOutputStream.close();
		return null;
	}
 	
 	
 
//ViewReturnsFormsPage_userType
 //requestId
 //ViewReturnsFormsPage_userRoleId
 //userId
 //reqType.value='ITR'
//requestId=1181304249&userRoleId=7&userType=IN&panNo=CZZPK6947C&reqType=ITR
//requestId=1144108493&userType=IN&userRoleId=7&panNo=CZZPK6947C&dropkey=ViewMyReturnsITR&reqtype=ITR
 
	
	public static void main(String[] args) throws UnirestException, IOException {
		System.out.println("---");
		SkipProfileUpdate skipProfileUpdate = new SkipProfileUpdate();
		HttpResponse<String> res = 	firstReq();
		System.out.println(res.getHeaders());
		setAllCookies(res.getHeaders().get(setCookie));
		HttpResponse<String> loginRes = login(res);
		System.out.println(loginRes.getHeaders());
		System.out.println(loginRes.getBody());
		res= homeAfterLogin(loginRes);
		System.out.println(res.getBody());
		res = viewITRReturnsNavBar(res);
		clickAckNoPageAndGotoDownloadPdfPag(res);
	}

}
