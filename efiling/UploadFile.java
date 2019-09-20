package kali.web_crawlers.efiling;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;


public class UploadFile {
	
	 public static String getRequestBody(InputStream inputStream) throws Exception{
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
		// TODO Auto-generated method stub
		
		String url = "http://httpbin.org/post";
		String charset = "UTF-8";
		String param = "value";
		File textFile = new File("sessions.txt");
		File binaryFile = new File("pom.xml");
		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.

		
		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0");

		try (
		    OutputStream output = connection.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
		) {
		    // Send normal param.
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append(param).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"ID\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append("kaliji").append(CRLF).flush();

		    // Send text file.
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
		    writer.append(CRLF).flush();
		    Files.copy(textFile.toPath(), output);
		    output.flush(); // Important before continuing with writer!
		    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

		    // Send binary file.
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
		    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
		    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
		    writer.append(CRLF).flush();
		    Files.copy(binaryFile.toPath(), output);
		    output.flush(); // Important before continuing with writer!
		    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
		    
		  
		    
		    // End of multipart/form-data.
		    writer.append("--" + boundary + "--").append(CRLF).flush();
		}

		// Request is lazily fired whenever you need to obtain information about response.
		int responseCode = ((HttpURLConnection) connection).getResponseCode();
		System.out.println(responseCode); // Should be 200
		System.out.println(getRequestBody(connection.getInputStream()));

	}

}
