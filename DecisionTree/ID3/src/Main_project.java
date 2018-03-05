import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main_project {
// The main function would read all the data and load them and make the decision tree
	public static void main(String[] args) {

		int LValue = 20;
		int KValue = 5;
		String trainingFileName = "C:/Users/sritika/Documents/Research Project/Development/OntologyWeb/ID3/data_sets2/training_set.csv";
		String testFileName = "C:/Users/sritika/Documents/Research Project/Development/OntologyWeb/ID3/data_sets2/test_set.csv";
		String validationFileName = "C:/Users/sritika/Documents/Research Project/Development/OntologyWeb/ID3/data_sets2/validation_set.csv";
		String Print="yes";
		
		
		
		Decision_tree tree = new Decision_tree();
		Tree_inst file_data =load_data(trainingFileName);
		Tree_inst file_rows = get_inst(file_data, 0, 0, file_data.getRowCount(), file_data.getColCount() - 1);
		Tree_inst resultRows =get_inst(file_data, 0, file_data.getColCount() - 1, file_data.getRowCount(), 1);
		Tree_nodes root1 = tree.makeTree(file_rows, resultRows, 1);
		Tree_nodes root2 = tree.makeTree(file_rows, resultRows, 2);

		if(Print.equals("yes") || Print.equals("Yes")){
			try {

				tree.printTree(root1);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Tree_inst test_data = load_data(testFileName);
		Tree_inst testDataRows = get_inst(test_data, 0, 0, test_data.getRowCount(), test_data.getColCount() - 1);
		Tree_inst testResultRows = get_inst(test_data, 0, test_data.getColCount() - 1,  test_data.getRowCount(), 1);
		Decision_tree_accuaracy Tree_1_accuracy = new Decision_tree_accuaracy();
		Tree_1_accuracy.parseInstances(testDataRows, testResultRows, root1);
		Decision_tree_accuaracy treeAcc2 = new Decision_tree_accuaracy();
		treeAcc2.parseInstances(testDataRows, testResultRows, root2);
		
		System.out.println("Decision Tree - 1 : Accuracy");
		Tree_1_accuracy.Accuracy();
		

		Tree_inst validation_data = load_data(validationFileName);
		Tree_inst valDataRows = get_inst(validation_data, 0, 0, validation_data.getRowCount(), validation_data.getColCount() - 1);
		Tree_inst valResultRows = get_inst(validation_data, 0, validation_data.getColCount() - 1,  validation_data.getRowCount(), 1);
		Decision_tree_accuaracy treePrunAccb1 = new Decision_tree_accuaracy();
		treePrunAccb1.parseInstances(testDataRows, testResultRows, root1);

		double acc1prunb = treePrunAccb1.getAcc();

		if(Print.equals("yes") || Print.equals("Yes")){
			try {
				
				tree.printTree(root1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Tree_pruning treePrune1 = new Tree_pruning();
		System.out.println("Pruning Decision Tree1");
		Tree_nodes root1prun =treePrune1.pruning(valDataRows,valResultRows, LValue, KValue, root1);

		Decision_tree_accuaracy treePrunAcca1 = new Decision_tree_accuaracy();
		treePrunAcca1.parseInstances(testDataRows, testResultRows, root1prun);
		System.out.println("Accuracy of Decision Tree1 after pruning");
		if(acc1prunb > treePrunAcca1.getAcc()) {
			treePrunAccb1.Accuracy();
		}
		else {
			treePrunAccb1.Accuracy();
		}

		Decision_tree_accuaracy treePrunAccb2 = new Decision_tree_accuaracy();
		treePrunAccb2.parseInstances(testDataRows, testResultRows, root2);
		double acc2prunb = treePrunAccb2.getAcc();

		
		
		

	}


	public static Tree_inst load_data(String  fileName){
		
		List<int[]> rows_val = new ArrayList<int[]>();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		Tree_inst ins = new Tree_inst();
		try{
			File file = new File(fileName);
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			String header = bufferedReader.readLine();
			String[] colNames = header.split(",");

			if(colNames != null && colNames.length > 0){
				List<String> colList = Arrays.asList(colNames);
				ins.setColumnNames(colList);
				int length = colList.size();

				String line = "";
				while(( line = bufferedReader.readLine()) != null ){
					StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
					int count = 0;
					int[] rowData = new int[length];
					while(stringTokenizer.hasMoreTokens()){
						String data = stringTokenizer.nextToken();
						try{
							rowData[count++] = Integer.parseInt(data);
						}catch (Exception e) {
							rowData[count++] = 0;
						}
					}
					rows_val.add(rowData);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bufferedReader != null ){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileReader != null ){
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		ins.setRowVal(rows_val);
		return ins;
	}

	public static Tree_inst get_inst( Tree_inst entities, int rowIndex,	int colIndex,int rowCnt, int colCnt){
		
		List<int[]> tempRowLst = new ArrayList< int[] >();
		for(int j = 0; j < rowCnt; j++) {
			int[] rowData = entities.getRow(rowIndex + j);
			int[] subRowData = new int[colCnt];
			for(int i = 0; i < colCnt; i++)
				subRowData[i] = rowData[colIndex + i];
			tempRowLst.add(subRowData);
		}
		List<String> tempNameLst = new ArrayList<String>();
		for(int i = 0; i < colCnt; i++) {
			tempNameLst.add(entities.getColumnNames().get(colIndex + i));
		}

		Tree_inst subIns = new Tree_inst(tempRowLst, tempNameLst);
		return subIns;
	}


}

class Decision_tree_accuaracy {
	public int correct,wrong;
	public double accuracy;

	public void parseInstances(Tree_inst testDataRows, Tree_inst testResults, Tree_nodes root){
		//Compute the accuracy
		//

		List<Integer> accuracyList = new ArrayList<Integer>();
		accuracyList.add(0);
		accuracyList.add(0);
		List<int[]> dataRowLst = testDataRows.getRowVal();
		for(int rowIndex = 0 ; rowIndex <  dataRowLst.size(); rowIndex++ ){
			parseTree(dataRowLst.get(rowIndex), testResults, root,accuracyList , rowIndex);
		}

		correct= accuracyList.get(0);
		wrong = accuracyList.get(1);
		accuracy = ( accuracyList.get(0) * 100.0 / ( accuracyList.get(0) + accuracyList.get(1)));
	}

	public void Accuracy() {

		System.out.println("Correct : " + this.correct );
		System.out.println("Total : " + ( this.correct + this.wrong) );
		System.out.println("% Accuracy : " + this.accuracy );

	}

	public double getAcc() {
		return this.accuracy;
	}

	private void parseTree( int[] dataArray, Tree_inst testResults, Tree_nodes root, List<Integer> accuracyList , int rowIndex ){

		if( root.getSubTreeList().size() > 0 ){
			int columnIndex = root.getColumns().getIndex();
			try{
				parseTree(dataArray, testResults, root.getSubTreeList().get(dataArray[columnIndex]), accuracyList , rowIndex);
			}catch (Exception e) {
				accuracyList.set(1, accuracyList.get(1) + 1 );
			}
		}else{
			if(testResults.getRow(rowIndex)[0] == root.getValue() ){
				accuracyList.set(0, accuracyList.get(0) + 1 );
			}else{
				accuracyList.set(1, accuracyList.get(1) + 1 );
			}
		}
	}

}

