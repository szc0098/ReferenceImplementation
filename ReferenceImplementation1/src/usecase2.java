

public class usecase2 {
	public static void main(String[] args)
	{
		Query q = new Query("lipopolysaccharide > 0 leads to metabolicCapacity(-1) after start()");
		HypothesisTesting h = new HypothesisTesting("Response", q);
		System.out.println(h.toLTL());
		
		q = new Query("x>12 precedes die( )");
		h = new HypothesisTesting("Precedence", q);
		System.out.println(h.toLTL());
		
	}

}
