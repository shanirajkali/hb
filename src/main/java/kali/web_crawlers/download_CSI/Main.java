package kali.web_crawlers.download_CSI;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import kali.web_crawler.constants.HeadersName;
import kali.web_crawler.util.Util;

public class Main {

	public static final String base = "https://tin.tin.nsdl.com";
	public static final String baseUrl = "https://tin.tin.nsdl.com/oltas/index.html";
	public static final String tanSearch = "https://tin.tin.nsdl.com/oltas/servlet/TanSearch"; //call by post
	
	static Map<String, String> headers = new HashMap<>();
	static Map<String,String> cookies = new HashMap<>();
	
	public Main(){
		Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));
		 for(String log:loggers) { 
			    Logger logger = (Logger)LoggerFactory.getLogger(log);
			    logger.setLevel(Level.INFO);
			    logger.setAdditive(false);
			    }
		
		Unirest.setDefaultHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0");		
		headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("accept--language", "en-US,en;q=0.9,es;q=0.8,hi;q=0.7,fil;q=0.6");
		headers.put("cache-control", "no-cache");
		headers.put("Connection", "keep-alive");
		headers.put(HeadersName.dnt, "1");
		headers.put(HeadersName.referer, "https://www.google.co.in/");
		headers.put("upgrade-insecure-requests", "1");
		headers.put("pragma", "no-cache");
			
	}
	
	
	
	public static void main(String[] args) throws UnirestException, IOException, ScriptException {
		Main v = new Main();
		HttpResponse<String> response=Unirest
				.get(baseUrl)
				.headers(headers)
				.asString();
		
		List<String> coockies = 	response.getHeaders().get(HeadersName.setCookie);
		Util.setGetCookie(cookies, coockies);
		
		headers.put(HeadersName.contentType, HeadersName.xurl);
		headers.put(HeadersName.referer, baseUrl);
		headers.put(HeadersName.origin, "https://tin.tin.nsdl.com");
		headers.put(HeadersName.host, "tin.tin.nsdl.com");
		headers.put(HeadersName.cookie, Util.getCookieString(cookies));
		System.out.println("--------newreq--------");
		System.out.println(Util.getCookieString(cookies));
		response =Unirest.post(tanSearch).headers(headers).body("firstTime=yes&submit=TAN Based View").asString();
		Util.setGetCookie(cookies, response.getHeaders().get(HeadersName.setCookie));
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("javascript");

	    
		System.out.println(engine.eval("Math.random()"));
		Document document = Jsoup.parse(response.getBody());
		String captchaUrl = document.getElementById("imgCode").attr("src");
		System.out.println("-------");
		System.out.println(captchaUrl);
		headers.put(HeadersName.accept, "image/webp,image/apng,image/*,*/*;q=0.8");
		Util.downloadCaptcha(base+"/oltas/servlet/CaptchaServicetansearch?rand="+engine.eval("Math.random()").toString(), null, headers,cookies);
		
		headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put(HeadersName.referer, "https://tin.tin.nsdl.com/oltas/servlet/TanSearch");
		//headers.put("Content-Length", "307");
		headers.put(HeadersName.cookie, Util.getCookieString(cookies));
		headers.put(HeadersName.contentType, HeadersName.xurl);
		System.out.println(Util.getCookieString(cookies));
		Scanner scanner=new Scanner(System.in);
		String captchaCode=scanner.nextLine();
		String tanDetails = "TAN_NO=AHMJ00656F&TAN_FROM_DT_DD=03&TAN_FROM_DT_MM=01&TAN_FROM_DT_YY=2018&TAN_TO_DT_DD=07&TAN_TO_DT_MM=06&TAN_TO_DT_YY=2018&HID_IMG_TXT="+captchaCode+"&HIDDEN_TAN_FROM_DT_DD=01&HIDDEN_TAN_FROM_DT_MM=04&HIDDEN_TAN_TO_DT_DD=31&HIDDEN_TAN_TO_DT_MM=03&HIDDEN_TAN_TO_DT_YY=&appUser=T&submit=Download+Challan+file&appUser=T";
		System.out.println(tanDetails);
		response =Unirest.post(tanSearch).headers(headers).body(tanDetails).asString();
		Util.downloadCsi(response);
	}

}
