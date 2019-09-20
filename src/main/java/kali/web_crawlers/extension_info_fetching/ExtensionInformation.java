package kali.web_crawlers.extension_info_fetching;


import java.io.File;
import java.io.IOException;

/**
 *this class will collect data from different url.
 */
public class ExtensionInformation implements Runnable{
	//'extension' is given extension by user like 'pdf' 
	private String extension;
	//'fileToStoreExtensionInformation' : it is a file where we will store information about extensions
	private File fileToStoreExtensionInformation;
	public ExtensionInformation(String extension, File fileToStoreExtensionInformation) {
		this.extension = extension;
		this.fileToStoreExtensionInformation = fileToStoreExtensionInformation;
	}
	public void run(){
		try{
			AboutExtension aboutExtension = new AboutExtension();
			FetchingDataFromFirstUrl obj1 = new FetchingDataFromFirstUrl(extension,aboutExtension);
			FetchingDataFromSecondUrl obj2 = new FetchingDataFromSecondUrl(extension,aboutExtension);
			FetchingDataFromThirdUrl obj3 = new FetchingDataFromThirdUrl(extension,aboutExtension);

			obj1.start();
			obj2.start();
			obj3.start();
			try{
		    	obj1.join();
		    	obj2.join();
		    	obj3.join(); 
		    }catch(InterruptedException e){}
			
			new SavingExtensionInfo().printExtensionInfo(extension, aboutExtension, fileToStoreExtensionInformation);
			
		}catch(IOException e){
				//System.err.println("Some error to fetching data about "+extension +" extension");
		}
	}
}