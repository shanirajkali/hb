package kali.web_crawlers.efiling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

public class UploadXml {
		
	public static void uploadXml(Document typeOfEfilingForm,String currentSession) throws Exception{
		
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
		uploadXmlHeader.put("Accept-Encoding", "gzip, deflate, br");
		uploadXmlHeader.put("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
		uploadXmlHeader.put("Cookie",currentSession);
		
		
	//	String url = "https://incometaxindiaefiling.gov.in/e-Filing/MyAccount/UploadReturnWithOptedEVerification.html";
		String charset = "UTF-8";
		File xmlFile = new File("pom.xml");
		String boundary = "--WebKitFormBoundary"+Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.
		
		URL url = new URL("https://incometaxindiaefiling.gov.in/e-Filing/MyAccount/UploadReturnWithOptedEVerification.html");
	//	URL url = new URL("http://httpbin.org/post");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0");
		connection.setRequestProperty("Cookie", currentSession);
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8,es;q=0.6,hi;q=0.4,fil;q=0.2");
	//	connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		connection.setRequestProperty("DNT", "1");
	//	connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
		connection.setRequestProperty("Origin", "https://incometaxindiaefiling.gov.in");
		connection.setRequestProperty("Cache-Control", "max-age=0");
		connection.setRequestProperty("Connection", "keep-alive");
		connection.setRequestProperty("Host", "incometaxindiaefiling.gov.in");
		connection.setRequestProperty("POST","/e-Filing/MyAccount/UploadReturnWithOptedEVerification.html HTTP/1.1");
		connection.setRequestProperty("Referer", "https://incometaxindiaefiling.gov.in/e-Filing/MyAccount/UploadReturnWithEverify.html");
		
		try (
			    OutputStream output = connection.getOutputStream();
			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
			) {
			    // Send normal param.
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"ID\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_ID").val()).append(CRLF).flush();
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.flagDetails.dscFlag\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_flagDetails_dscFlag").val()).append(CRLF).flush();
			    
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.evcOption\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_evcOption").val()).append(CRLF).flush();
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.bankAccNumber\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_bankAccNumber").val()).append(CRLF).flush();
			   
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.bankId\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_bankId").val()).append(CRLF).flush();
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.nbUserId\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_nbUserId").val()).append(CRLF).flush();
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.panNo\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_panNo").val()).append(CRLF).flush();
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.formName\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_formName").val()).append(CRLF).flush();
			    			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.asstYear\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_asstYear").val()).append(CRLF).flush();
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.flagDetails.section44AB\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_flagDetails_section44AB").val()).append(CRLF).flush();
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.sec92EFlag\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_sec92EFlag").val()).append(CRLF).flush();
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"retDtlsBean.filingType\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_retDtlsBean_filingType").val()).append(CRLF).flush();
			    
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"captchaCode\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF).append(typeOfEfilingForm.getElementById("UploadReturnWithOptedEVerification_captchaCode").val()).append(CRLF).flush();
			    

			    // Send text file.
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"uploadFile\"; filename=\"" + xmlFile.getName() + "\"").append(CRLF);
			    writer.append("Content-Type: text/xml; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
			    writer.append(CRLF).flush();
			    Files.copy(xmlFile.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

			    
			    // End of multipart/form-data.
			    writer.append("--" + boundary + "--").append(CRLF).flush();
			}

			// Request is lazily fired whenever you need to obtain information about response.
			int responseCode = ((HttpURLConnection) connection).getResponseCode();
			System.out.println(responseCode); // Should be 200
			System.out.println("---------------------------------------------------");
		//	writeInputStream(connection.getInputStream());
			String string=getRequestBody(connection.getInputStream());
			System.out.println(string);
			System.out.println(connection.getHeaderField("Set-Cookie"));
		//	System.out.println(connection.toString());
			System.out.println(connection.getHeaderField("Date"));
	}
	
	public static void writeInputStream(InputStream is){
		  try {
	            
	            OutputStream os = new FileOutputStream("new_source");
	            
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            //read from is to buffer
	            while((bytesRead = is.read(buffer)) !=-1){
	                os.write(buffer, 0, bytesRead);
	            }
	            is.close();
	            //flush OutputStream to write any buffered data to file
	            os.flush();
	            os.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	 public static String getRequestBody(InputStream inputStream) throws Exception{
	        StringBuilder requestBody = new StringBuilder();

	        // request inputstream that is in byte format now we have to convert bytes to string json

	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	        String line = "";
	        while((line=bufferedReader.readLine())!=null){
	        	System.out.println(line);
	            requestBody.append(line);
	        }
	        inputStream.close();
	        return requestBody.toString();
	    }
	
}
