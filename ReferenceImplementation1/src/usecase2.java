

public class usecase2 {
	public static void main(String[] args)
	{
		Query q = new Query("LookingForNewSite = true is absent");
		HypothesisTesting h = new HypothesisTesting("MechanisticHypothesis", q);
		String LTL = h.toLTL();
		
		System.out.println(LTL);
	}

}
