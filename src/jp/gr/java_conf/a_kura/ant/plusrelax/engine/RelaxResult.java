package jp.gr.java_conf.a_kura.ant.plusrelax.engine;

public class RelaxResult {
	public static final int SUCCESS = 0;
	public static final int FAILURE = 1;
	public static final int SHORT_COVERAGE = 2;

	private int judge;
	private String message;
	
	public RelaxResult(int judge, String message) {
		this.judge = judge;
		this.message = message;
	}
	
	public int getJudge() {
		return judge;
	}
	
	public String getMessage() {
		return message;
	}

}
