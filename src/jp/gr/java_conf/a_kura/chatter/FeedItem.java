package jp.gr.java_conf.a_kura.chatter;

import jp.gr.java_conf.a_kura.chatter.attachment.Attachment;
import jp.gr.java_conf.a_kura.chatter.message.Message;

public class FeedItem {
	private Message body;
	private Attachment attachment;
	
	public FeedItem() {
		
	}

	public FeedItem(Message body) {
		this.body = body;
	}
	
	public FeedItem(Message body, Attachment attachment) {
		this.body = body;
		this.attachment = attachment;
	}
	
	public Message getBody() {
		return body;
	}
	
	public void setBody(Message body) {
		this.body = body;
	}

	public Attachment getAttachment() {
		return attachment;
	}
	
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

}
