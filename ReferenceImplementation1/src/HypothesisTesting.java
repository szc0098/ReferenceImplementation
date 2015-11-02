
public class HypothesisTesting {
	String hypothesisName;
	public enum Patterns { Precedence, Existence , Universality , Absence , Response , Custom};
	Patterns specificationPattern;
	Query q;
	
	public HypothesisTesting(String name, Patterns p, Query q ){
		this.hypothesisName = name;
		this.q = q;
		this.specificationPattern = p;
		
	}

	public String getHypothesisName() {
		return hypothesisName;
	}

	public void setHypothesisName(String hypothesisName) {
		this.hypothesisName = hypothesisName;
	}

	public Patterns getSpecificationPattern() {
		return specificationPattern;
	}

	public void setSpecificationPattern(Patterns specificationPattern) {
		this.specificationPattern = specificationPattern;
	}

	public Query getQ() {
		return q;
	}

	public void setQ(Query q) {
		this.q = q;
	}

}
