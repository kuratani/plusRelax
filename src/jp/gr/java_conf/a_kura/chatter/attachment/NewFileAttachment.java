package jp.gr.java_conf.a_kura.chatter.attachment;


public class NewFileAttachment extends Attachment {
	private String attachmentType = "NewFile";
	private String description;
	private String title;
	
	public NewFileAttachment() {
		
	}

	public NewFileAttachment(String description, String title) {
		this.description = description;
		this.title = title;
	}

	@Override
	public String getAttachmentType() {
		return attachmentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
