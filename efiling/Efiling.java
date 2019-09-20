package kali.web_crawlers.efiling;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class Efiling {
	
	private static String getRequestBody(InputStream inputStream) throws Exception{
		StringBuilder requestBody = new StringBuilder();

		// request inputstream that is in byte format now we have to convert bytes to string json
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		while((line=bufferedReader.readLine())!=null){
			requestBody.append(line);
		}
		inputStream.close();
		return requestBody.toString();
	}

	public static void main(String[] args) throws Exception {
		
		Response response= Jsoup.connect("https://incometaxindiaefiling.gov.in/e-Filing/CreateCaptcha.do")
				.ignoreContentType(true).execute();
		
		System.out.println(Jsoup.connect("https://incometaxindiaefiling.gov.in/home").get().toString());
		
		System.out.println(response.bodyAsBytes());
		
		String outputFolder= "/home/shanirajkali/Desktop/captcha/"+System.currentTimeMillis();
		
		FileOutputStream out= (new FileOutputStream(new java.io.File(outputFolder)));
		
		out.write(response.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
		out.close();
		
		Scanner scanner =new Scanner(System.in);
		//String captchaInput=scanner.nextLine();
		//System.out.println(captchaInput);
		
//		String result = 
//				Unirest.post("https://incometaxindiaefiling.gov.in/e-Filing/UserLogin/Login.html")
//				.header("Content-Type", "application/x-www-form-urlencoded")
//				.body("hindi=&requestId=2067090855&nextPage=&userName=CZZPK6947C&userPan=&password=%401KALIhpdlink&dob=04%2F10%2F1994&rsaToken=&requestId=2067090855&captchahindi=&requestId=2067090855&nextPage=&userName=CZZPK6947C&userPan=&password=%401KALIhpdlink&dob=04%2F10%2F1994&rsaToken=&requestId=2067090855&captchaCode=Code="+captchaInput)
//				.toString();
//		System.out.println(result);
//		
//		String parsedResult = Unirest.get("https://incometaxindiaefiling.gov.in/e-Filing/UserLogin/Login.html")
//				.asJson().toString();
//		System.out.println(parsedResult);
		
		//String string= Unirest.post("https://incometaxindiaefiling.gov.in/home").getBody().getEntity().toString();
	//	System.out.println(string);
		Map<String, String> headers =new HashMap<String, String>();
		headers.put("GET", "/home HTTP/1.1");
		headers.put("Host", "incometaxindiaefiling.gov.in");
		headers.put("Connection", "keep-alive");
		headers.put("Cache-Control", "max-age=0");
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers.put("DNT", "1");
		headers.put("Accept-Encoding", "gzip, deflate, sdch, br");
		headers.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
		headers.put("Cookie", "lang=eng; style=normal");
//		headers.put("", "");
//		headers.put("", "");
		//System.out.println(Unirest.get("https://incometaxindiaefiling.gov.in/home").headers(headers));
		HttpResponse<String> string2= Unirest.get("https://incometaxindiaefiling.gov.in/home").asString();
		
		System.out.println(string2.getBody());
		System.out.println("---------------------------------------------------------");
		System.out.println(string2.getStatus());
		System.out.println("---------------------------------------------------------");
		System.out.println(string2.getStatusText());
		System.out.println("---------------------------------------------------------");
		System.out.println(string2.getHeaders());
		System.out.println("---------------------------------------------------------");
		System.out.println(getRequestBody(string2.getRawBody()));
		

	}
}
