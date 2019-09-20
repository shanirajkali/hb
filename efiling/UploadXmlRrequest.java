package kali.web_crawlers.efiling;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.generic.NEW;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.MultipartBody;

public class UploadXmlRrequest {

	public static void main(String[] args) throws UnirestException, IOException {
		
		System.out.println("-----------------------------------upload xml file------------------------------");
		Map<String, String > uploadXmlHeader= new HashMap<String,String>();
	//	uploadXmlHeader.put("Referer", "https://incometaxindiaefiling.gov.in/e-Filing/MyAccount/UploadReturnWithEverify.html");
	//	uploadXmlHeader.put("POST","/e-Filing/MyAccount/UploadReturnWithOptedEVerification.html HTTP/1.1");
	//	uploadXmlHeader.put("Host", "incometaxindiaefiling.gov.in");
		uploadXmlHeader.put("Connection", "keep-alive");
		uploadXmlHeader.put("Cache-Control", "max-age=0");
	//	uploadXmlHeader.put("Origin", "https://incometaxindiaefiling.gov.in");
		uploadXmlHeader.put("Upgrade-Insecure-Requests", "1");
	//	loginFormHeader.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36");
		uploadXmlHeader.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		uploadXmlHeader.put("DNT", "1");
		uploadXmlHeader.put("Content-Type", "multipart/form-data");
		uploadXmlHeader.put("Accept-Encoding", "gzip, deflate, sdch, br");
		uploadXmlHeader.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
		uploadXmlHeader.put("Cookie","");
		
		File efiling = new File("/home/shanirajkali/Desktop/efiling.xml");
		System.out.println(efiling.exists());
		
		HttpResponse<String> uploadXmlResponse = Unirest.post("http://httpbin.org/post")
				.headers(uploadXmlHeader)
				//.body("------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"uploadToCheck\"\r\n\r\ntrue\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"file\"; filename=\"pageLink.txt\"\r\nContent-Type: false\r\n\r\n\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"kkk\"\r\n\r\n\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--")
				.field("file", new File("sessions.txt"))
				//.field("ID", "4353")
				.asString();
		StringBuffer sBuffer=new StringBuffer();
		
		System.out.println(uploadXmlResponse.getBody());
		System.out.println(uploadXmlResponse.getStatus());
		System.out.println(uploadXmlResponse.getHeaders());
		System.out.println(uploadXmlResponse.getRawBody());
		
		
		System.out.println("-------------------------------------------------------------");
		
		
		  File temp1 = File.createTempFile("myimage", ".image");
		    Files.write("Hello world1111", temp1, Charsets.UTF_8);

		    File temp2 = File.createTempFile("myimage", ".image");
		    Files.write("Hello world2222", temp2, Charsets.UTF_8);

		    File temp3 = File.createTempFile("myimage", ".image");
		    Files.write("Hello 33333", temp3, Charsets.UTF_8);


		    try {
		        MultipartBody field = Unirest.post("http://httpbin.org/post")
		                .field("name", "bingoo.txt")
		                .field("file", temp1)
		                .field("fil", temp2)
		                .field("files", temp3);
		        HttpResponse<String> file = field.asString();
		        System.out.println(file.getStatus());
		        System.out.println(file.getBody());

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

	}

}
