package jp.gr.java_conf.a_kura.ant.plusrelax.engine;

import java.text.DecimalFormat;

import com.sforce.soap.apex.CodeCoverageResult;
import com.sforce.soap.apex.RunTestsResult;

public abstract class RelaxEngine {

	static final double MIN_COVERAGE = 0.75;

    DecimalFormat df = new DecimalFormat("##0.00%");
    String crlf = System.getProperty("line.separator");

    int numTestsRun = 0;
    int numFailures = 0;
    int numSuccess = 0;
    double totalTime = 0;
    int numLocations = 0;
    int numLocationsNotCoverd = 0;
    int numLocationsCovered = 0;
    double rateCoverage = 0;
    
	public abstract RelaxResult execute(RunTestsResult result);

	void setUp(RunTestsResult result) {
        numTestsRun = result.getNumTestsRun();
        numFailures = result.getNumFailures();
        numSuccess = numTestsRun - numFailures;
        totalTime = result.getTotalTime();
        
        for(CodeCoverageResult coverage : result.getCodeCoverage()) {
        	numLocations += coverage.getNumLocations();
        	numLocationsNotCoverd += coverage.getNumLocationsNotCovered();
        }
        numLocationsCovered = numLocations - numLocationsNotCoverd;
        rateCoverage = (numLocations > 0) ? ((double)numLocationsCovered / (double)numLocations) : 0;
	}

}
