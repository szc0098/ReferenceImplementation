
import java.util.ArrayList;

import projectManagement.AspectJGenerator;
import projectManagement.PrismInterface;
import projectManagement.SimulationModelInterface;
import DataRecording.DataRecordManager;
import DataRecording.DataType;
import DataRecording.DataTypeInt;
import Evidence.HypothesisTesting;
import Evidence.Property;
import FSACreation.Condition;
import FSACreation.MarkovChain;
import FSACreation.State;

public class Main {
	public static void main(String[] args)
	{
		Property p = new Property("inflammation>-1 eventually exists");// after inflammatoryAgent > inflammatoryAgentThreshold");
		HypothesisTesting h = new HypothesisTesting("E1", p);
		String LTL = h.toLTL();
		System.out.println(LTL);
		
		//Add FSA code to model
		String[] inputList = {"step(),string"};
		AspectJGenerator fileMgr = new AspectJGenerator("segregation.SegregationBatch");
		fileMgr.generate("answers", "Answer1", inputList);
		
//		//run model
//		String modelPath = "C:/RepastSimphony-2.3.1/models/Schelling";
//		String modelName = "uchicago.src.sim.schelling.SchellingModel";
//		String parametersPath = "C:/RepastSimphony-2.3.1/models/Schelling/batch/batch_params.xml";
//		SimulationModelInterface schelling = new SimulationModelInterface(modelPath, modelName, parametersPath);
//		schelling.runSimulation(5);
//		
		//create markov chain
		int numVars = 3;
		DataRecordManager dataMgr = new DataRecordManager(numVars, "C:\\Users\\sritika\\Documents\\Research Project\\Development\\ExperimentOntologyNew1\\ModelChecking\\ModelChecking\\sampleData.txt");
		ArrayList<DataType[]> matrix = dataMgr.getFullMatrixFromDataFile();
		DataTypeInt in = new DataTypeInt(-1);
		Condition c = new Condition("inflammation", ">", in);
		Condition[] ca = {c};
		MarkovChain markovChain = new MarkovChain(numVars, dataMgr.getVariableNames(), ca);
        markovChain.developFSAFromData(matrix);
        
        //create visualization
        //ArrayList<State> s=markovChain.getStates();
      
        //SimpleGraph sg = new SimpleGraph(matrix);
        
        PrismInterface pi = new PrismInterface("ISHC", LTL, markovChain, dataMgr);
        pi.runPrismSource();
	}

}
