
public class Query {

	Event e;
	public Event getE() {
		return e;
	}

	public void setE(Event e) {
		this.e = e;
	}

	public temp getTemporalLogic() {
		return temporalLogic;
	}

	public void setTemporalLogic(temp temporalLogic) {
		this.temporalLogic = temporalLogic;
	}

	public log getLogical() {
		return Logical;
	}

	public void setLogical(log logical) {
		Logical = logical;
	}

	public linkers getLink() {
		return Link;
	}

	public void setLink(linkers link) {
		Link = link;
	}

	public Exp getExpression() {
		return expression;
	}

	public void setExpression(Exp expression) {
		this.expression = expression;
	}

	public enum temp {precedes, between, eventually, always,  before, until, never, leads, Exists}
	public enum log {and, or, not};
	public enum linkers {is, occurs, to};
	public enum Exp {TRUE, FALSE}
	
	temp temporalLogic;
	log Logical;
	linkers Link;
	Exp expression;
	
	public Query(Event e, temp t, linkers link, Exp exp){
		this.e =e;
		this.temporalLogic =t;
		
		this.Link =link;
		this.expression = exp;
	}
	
	public Query(Event e, log l, linkers link, Exp exp){
		this.e =e;
		
		this.Logical =l;
		this.Link =link;
		this.expression = exp;
	}
	
	public Query(Event e, temp t, log l, linkers link, Exp exp){
		this.e =e;
		this.temporalLogic =t;
		this.Logical =l;
		this.Link =link;
		this.expression = exp;
	}
}
