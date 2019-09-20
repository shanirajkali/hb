package kali.web_crawler.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import kali.web_crawler.constants.HeadersName;

import org.jsoup.Connection.Response;

public  class Util {
	
	 public static void setGetCookie(Map<String, String> oldCookies, List<String> responseCookies){
		if(responseCookies==null) return;
		for(String s:responseCookies){
			String currCookie = null;
			try {
				currCookie = s.split(";")[0];
				String[] cook = currCookie.split("=");
				if(cook != null){
					if(cook.length>0){
						oldCookies.put(cook[0], cook[1]);
						System.out.println("cook "+cook[0]+" : "+cook[1]);
					}
				}
			} catch (Exception e) {
				System.out.println("error in cookie handling");
			}
		}
	}
	 
	public static String getCookieString(Map<String, String> cookies){
		Set<String> keys = cookies.keySet();
		String cookiesString = "";
		for(String key:keys){
			cookiesString= cookiesString+key+"="+cookies.get(key)+"; ";
		}
		return cookiesString;
	}
	
	public static void downloadCaptcha(String url,String folderPath,Map<String, String> headers,Map<String, String> oldCookies) throws IOException, UnirestException{
		System.out.println(url);
		String outputFolder= "D:\\shani rajkali\\CODE\\web_crawlers\\captcha\\"+System.currentTimeMillis()+".png";
		File targetFile = new File(outputFolder);
		 OutputStream fileOutputStream = new FileOutputStream(targetFile);
		 HttpResponse<String> response =Unirest.get(url).headers(headers).asString();
		 InputStream inputStream = response.getRawBody();
		 byte[] buffer = new byte[inputStream.available()];
		 inputStream.read(buffer);
		 fileOutputStream.write(buffer);
		 fileOutputStream.flush();
		 fileOutputStream.close();
		 setGetCookie(oldCookies, response.getHeaders().get(HeadersName.setCookie));
	}
	
	public static void downloadCsi(HttpResponse<String> response) throws IOException, UnirestException{
		
		String outputFolder= "D:\\shani rajkali\\CODE\\web_crawlers\\captcha\\"+System.currentTimeMillis()+".csi";
		File targetFile = new File(outputFolder);
		 OutputStream fileOutputStream = new FileOutputStream(targetFile);
		
		 InputStream inputStream = response.getRawBody();
		 byte[] buffer = new byte[inputStream.available()];
		 inputStream.read(buffer);
		 fileOutputStream.write(buffer);
		 fileOutputStream.flush();
		 fileOutputStream.close();
		 //setGetCookie(oldCookies, response.getHeaders().get(HeadersName.setCookie));
	}
}
