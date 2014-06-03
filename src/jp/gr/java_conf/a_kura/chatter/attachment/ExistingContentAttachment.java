package jp.gr.java_conf.a_kura.chatter.attachment;


public class ExistingContentAttachment extends Attachment {

	private String attachmentType = "ExistingContent";
	private String contentDocumentId = null;
	
	public ExistingContentAttachment() {
		
	}
	
	public ExistingContentAttachment(String contentDocumentId) {
		this.contentDocumentId = contentDocumentId;
	}

	@Override
	public String getAttachmentType() {
		return attachmentType;
	}
	
	public String getContentDocumentId() {
		return contentDocumentId;
	}
	
	public void setContentDocumentId(String contentDocumentId) {
		this.contentDocumentId = contentDocumentId;
	}

}
