@isTest
public class SampleLogicTest {
	static testmethod void testExecute() {
		SampleLogic l = new SampleLogic();
		System.assert(l.execute());
	}
}