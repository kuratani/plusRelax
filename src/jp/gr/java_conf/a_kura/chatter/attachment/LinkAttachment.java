package jp.gr.java_conf.a_kura.chatter.attachment;


public class LinkAttachment extends Attachment {

	private String attachmentType = "Link";
	private String url = null;
	private String urlName = null;

	public LinkAttachment() {
		
	}

	public LinkAttachment(String url, String urlName) {
		this.url = url;
		this.urlName = urlName;
	}

	@Override
	public String getAttachmentType() {
		return attachmentType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	
}
