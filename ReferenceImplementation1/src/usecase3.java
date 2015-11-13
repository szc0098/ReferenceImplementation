import java.io.BufferedReader;
import java.io.InputStreamReader;


public class usecase3 {
	public static void main(String[] args)
	{
		String promelaFilePath = args[0];
		Query q = new Query("(seen < N) || (tour > MAX) always exists");
		//Query q = new Query("x>12 precedes die( )");
		HypothesisTesting h = new HypothesisTesting("MechanisticHypothesis", q);
		String LTL = h.toLTL();
		System.out.println(LTL);
		
		try{
			ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", "cd \"C:\\Users\\krdou_000\\Dropbox\\Research\\LTL\" && spin -a salesman1.pml && gcc -o salesman1.exe pan.c && salesman1.exe -a");//-f" + LTL);
		        builder.redirectErrorStream(true);
		        Process p = builder.start();
		        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        String line;
		        while (true) {
		            line = r.readLine();
		            if (line == null) { break; }
		            System.out.println(line);
		        }
		        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
