package jp.gr.java_conf.a_kura.chatter.message;

public class MentionSegment extends MessageSegment {

	private String type = "Mention";
	private String id;

	public MentionSegment() {
		id = "";
	}

	public MentionSegment(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String getType() {
		return type;
	}
	
}
