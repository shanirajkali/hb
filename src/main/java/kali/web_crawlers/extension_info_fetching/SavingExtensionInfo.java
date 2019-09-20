package kali.web_crawlers.extension_info_fetching;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class will save information about extension in a file .
 */
public class SavingExtensionInfo {
	private static int counter ;
	public SavingExtensionInfo() {
		counter++;
	}
	
	/**
	 * @param extension : this is a extension given by user like 'pdf'
	 * @param aboutExtension : This is a AbuotExtension class object which containing information about extension 
	 * @param fileToStoreExtensionInformation : this is file where we will store information
	 */
	public synchronized void printExtensionInfo(String extension, AboutExtension aboutExtension, File fileToStoreExtensionInformation) throws IOException{
		StringBuffer extensionInfo = new StringBuffer();
		extensionInfo.append("*** Extension "+counter+" : "+extension+" ***\n");
		extensionInfo.append("File Type  \t:\t"+aboutExtension.getFileTpe()+"\n\n");
		extensionInfo.append("Category   \t:\t" +aboutExtension.getCategory()+"\n\n");
		extensionInfo.append("Format     \t:\t"+aboutExtension.getFormat()+"\n\n");
		extensionInfo.append("Usage      \t:\t"+aboutExtension.getUsage()+"\n\n");
		extensionInfo.append("Description\t:\t"+aboutExtension.getDescription()+"\n\n");
		extensionInfo.append("MIME Type  \t:\t"+aboutExtension.getMimeType()+"\n\n");
		extensionInfo.append("Associated Applications :\n"+aboutExtension.getApplications()+"\n\n");
		
		FileWriter fileWriter = new FileWriter(fileToStoreExtensionInformation,true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(extensionInfo.toString());
		bufferedWriter.newLine();
		bufferedWriter.flush();
		bufferedWriter.close();
		System.out.println("data has written in "+fileToStoreExtensionInformation+" for "+extension +" extension");
		
	}

}
