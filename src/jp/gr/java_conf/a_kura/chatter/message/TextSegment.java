package jp.gr.java_conf.a_kura.chatter.message;

public class TextSegment extends MessageSegment {

	private String type = "Text";
	private String text;

	public TextSegment() {
		this.text = "";
	}

	public TextSegment(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String getType() {
		return type;
	}

}
