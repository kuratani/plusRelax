package jp.gr.java_conf.a_kura.chatter.message;

public class HashTagSegment extends MessageSegment {

	private String type = "Hashtag";
	private String tag;

	public HashTagSegment() {
		this.tag = "";
	}
	
	public HashTagSegment(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	@Override
	public String getType() {
		return type;
	}

}
