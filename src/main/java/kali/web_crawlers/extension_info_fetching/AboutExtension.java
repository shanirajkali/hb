package kali.web_crawlers.extension_info_fetching;


/**
 * this class will hold data about a particular extension
 */
public class AboutExtension {
	//'category' , 'filTpe' , 'format' , 'description' , 'usage' , 'mimeType' , 'applications' are properties about a extension
	private String category;
	private String fileTpe;
	private String format;
	private String description;
	private String usage;
	private String mimeType;
	private String applications;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFileTpe() {
		return fileTpe;
	}
	public void setFileTpe(String fileTpe) {
		this.fileTpe = fileTpe;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getApplications() {
		return applications;
	}
	public void setApplications(String applications) {
		this.applications = applications;
	}
}
