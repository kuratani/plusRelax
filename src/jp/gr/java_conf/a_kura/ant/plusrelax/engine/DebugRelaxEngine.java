package jp.gr.java_conf.a_kura.ant.plusrelax.engine;

import com.sforce.soap.apex.RunTestsResult;

public class DebugRelaxEngine extends RelaxEngine {

	protected DebugRelaxEngine() {}

	public RelaxResult execute(RunTestsResult result) {
        StringBuffer buf = new StringBuffer();
        int judge;

        setUp(result);

		if(numFailures > 0) {
			buf.append("テスト失敗しました");
			judge = RelaxResult.FAILURE;
		} else if(rateCoverage < MIN_COVERAGE) {
			buf.append("テストカバレッジが不足しています");
			judge = RelaxResult.SHORT_COVERAGE;
		} else {
			buf.append("テスト成功しました");
			judge = RelaxResult.SUCCESS;
		}
		
        buf.append(crlf);
        buf.append("Test/Success/Failures: ");
        buf.append(numTestsRun);
        buf.append("/");
        buf.append(numSuccess);
        buf.append("/");
        buf.append(numFailures);
        buf.append(crlf);
        buf.append("Coverage: ");
        buf.append(numLocations>0 ? df.format(rateCoverage) : "-%");
        buf.append("(");
        buf.append(numLocationsCovered);
        buf.append("/");
        buf.append(numLocations);
        buf.append(")");
        buf.append(crlf);
        buf.append("Time: ");
        buf.append(totalTime / 1000.0);
        buf.append(" sec");
        buf.append(crlf);

        return new RelaxResult(judge, buf.toString());
	}

}
