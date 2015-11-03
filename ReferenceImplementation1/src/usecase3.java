
public class usecase3 {
	public static void main(String[] args)
	{
		Query q = new Query("(seen < N) || (tour > MAX) always exists");
		HypothesisTesting h = new HypothesisTesting("MechanisticHypothesis", q);
		String LTL = h.toLTL();
		
		try{
			Process tr = Runtime.getRuntime().exec("spin -a salesman1.pml");
			Process pr = Runtime.getRuntime().exec("gcc -o salesman1.exe pan.c");
			Process cr = Runtime.getRuntime().exec("./salesman1.exe -a -f" + LTL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
