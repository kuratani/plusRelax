package jp.gr.java_conf.a_kura.ant.plusrelax.engine;

import java.util.Random;

import com.sforce.soap.apex.RunTestsResult;

public class CatRelaxEngine extends RelaxEngine {
	String[] msgSuccess = { "にゃ〜", "にゃ〜、にゃ〜", "(ごろごろ)" };
	String[] msgFailures = { "ふぎゃ〜！", "ぎゃぁおぉぉぉ〜〜ん" };
	String[] msgShortCoverage = { "しゃー！", "ぐぅう…" };

	protected CatRelaxEngine() {}

	public RelaxResult execute(RunTestsResult result) {
        StringBuffer buf = new StringBuffer();
        Random rnd = new Random();
        int judge;

        setUp(result);

		if(numFailures > 0) {
			buf.append(msgFailures[rnd.nextInt(msgFailures.length)]);
			judge = RelaxResult.FAILURE;
		} else if(rateCoverage < MIN_COVERAGE) {
			buf.append(msgShortCoverage[rnd.nextInt(msgShortCoverage.length)]);
			judge = RelaxResult.SHORT_COVERAGE;
		} else {
			buf.append(msgSuccess[rnd.nextInt(msgSuccess.length)]);
			judge = RelaxResult.SUCCESS;
		}
        buf.append(crlf);

		return new RelaxResult(judge, buf.toString());
	}

}
