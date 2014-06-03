package jp.gr.java_conf.a_kura.chatter.message;

public class LinkSegment extends MessageSegment {

	private String type = "Link";
	private String url;
	
	public LinkSegment(String url) {
		this.url = url;
	}
	
	@Override
	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

}
