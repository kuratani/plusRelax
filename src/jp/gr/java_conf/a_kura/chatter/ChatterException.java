package jp.gr.java_conf.a_kura.chatter;

public class ChatterException extends Exception {

    public ChatterException(Exception e) {
        super(e);
    }
   
	public ChatterException(String message) {
		super(message);
	}

}
