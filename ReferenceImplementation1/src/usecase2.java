

public class usecase2 {
	public static void main(String[] args)
	{
		Experiment e = new Experiment("schellingModel", "comparative");
		Objective o = new Objective("SchellingObjective", "COMPARATIVE");
		e.setDescription("A string");
		e.setO(o);
		
		Design design = new Design("SchellingDesign", Design.Types.OTHERS);
		
		Values factorValue= new Values(70);
		Factor f = new Factor("percentLikeNeighbors", Factor.VariableTypes.NONBINARY, factorValue);
		Response r = new Response("AgentSatisfaction", Response.RTypes.SIMPLE);
		IndependentVariables i = new IndependentVariables(f);
		DependentVariables d = new DependentVariables(r);
		Variables v = new Variables(i, d);
		
		design.setVariables(v);
		
		Values expectedValue = new Values(0.7);
		PerformanceMeasure p = new PerformanceMeasure("SchellingPerformanceMeasure", r,expectedValue);
		
		//String []eventList = {"LookingForNewSite","AgentSatisfaction<0.7"};
		//Events events = new Events(eventList);
		//Query q = new Query(events, Query.temp.until, Query.linkers.is, Query.Exp.TRUE);
		Query q = new Query("LookingForNewSite = true is absent");
		HypothesisTesting h = new HypothesisTesting("MechanisticHypothesis", q);
		String LTL = h.toLTL();
		
		System.out.println(LTL);
		
		String var[] = {"LookingForNewSite=true", "LookingForNewSite=false", "AgentSatisfaction>0.7" , "AgentSatisfaction<0.7" };
		MessageType m = new MessageType("Message1", var);
		Channel c = new Channel("a2b", 0, m);
		Transition t = new Transition("A");
		State s = new State("S0", true);
		t.setS(s);
		FiniteStateAutomaton fsa = new FiniteStateAutomaton(m, c, t);
		System.out.print("Finished");
	}

}
