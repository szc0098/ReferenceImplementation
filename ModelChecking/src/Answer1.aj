package answers;

import DataRecording.DataRecordManager;
public aspect Answer1 {

	int numVariables = 1;
	String dataStorageLocation = "src/answers/Answer1Data.txt";

	DataRecordManager dataMgr;

	after() :  execution(void segregation.SegregationBatch.setup()) {
		dataMgr = new DataRecordManager(numVariables, dataStorageLocation);
	}
	after() : execution(void segregation.SegregationBatch.atEnd()) {
		dataMgr.writeRecentDataToFile();
	}

	pointcut step()Event():
		execution(String step()());
	String around(): step()Event() {
		String result = proceed();
		dataMgr.recordData("step()", result);
		return result;
	}


}