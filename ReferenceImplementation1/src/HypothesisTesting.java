import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class will take a query and convert it into LTL 
 * @author krdou_000
 * @version 11/2/15
 */
public class HypothesisTesting {
	String hypothesisName;
	Query query;
	Document dom;
	/**
	 * Constructor for HypothesisTesting
	 * 
	 * @param name The identifier for the hypothesis
	 * @param q The temporal query from the DSL
	 */
	public HypothesisTesting(String name, Query q )
	{
		this.hypothesisName = name;
		this.query = q;
	}
	
	/**
	 * Replaces P, S, R, X, and Z from a temporal pattern template with their actual program names
	 * @return ltlFormula The formula as it will be passed to SPIN
	 */
	public String toLTL()
	{
		parseXmlFile();
		String ltlFormula = getFormulaFromXml();
		char[] placeholders = {'P', 'S', 'R', 'Z'};
		boolean formulaComplete = false;
		for(int i = 0; i < ltlFormula.length(); i++)
		{
			if(formulaComplete)
			{
				break;
			}
			for(int j = 0; j < placeholders.length; j++)
			{
				if(ltlFormula.charAt(i) == placeholders[j])
				{
					String front = ltlFormula.substring(0, i);
					String back = processSubstring(ltlFormula.substring(i + 1));
					ltlFormula = front + query.getEvents().get(j) + back;
					formulaComplete = true;
					break;
				}
			}
		}
		return ltlFormula;
	}
	
	private String processSubstring(String backIn)
	{
		String output = backIn;
		char[] placeholders = {'P', 'S', 'R', 'X', 'Z'};
		for(int i = 0; i < backIn.length(); i++)
		{
			for(int j = 0; j < placeholders.length; j++)
			{
				if(backIn.charAt(i) == placeholders[j])
				{
					String front = backIn.substring(0, i);
					String back = processSubstring(backIn.substring(i + 1));
					output = front + query.getEvents().get(j) + back;
					
				}
			}
		}
		return output;
	}

	/**
	 * Getter for hypothesis name
	 * 
	 * @return hypothesisName The identifier for this hypothesis.
	 */
	public String getHypothesisName() {
		return hypothesisName;
	}

	/**
	 * Setter for Hypothesis name
	 * 
	 * @param hypothesisName The new identifier for the hypothesis
	 */
	public void setHypothesisName(String hypothesisName) {
		this.hypothesisName = hypothesisName;
	}

	/**
	 * Getter for hypothesis name
	 * 
	 * @return hypothesisName The identifier for this hypothesis.
	 */
	public Query getQuery() {
		return query;
	}

	public void setQuery(Query q) {
		this.query = q;
	}
	
	/**
	 * Gets a global dom object for the xml file that holds the temporal specification patterns.
	 * 
	 */
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse("C:\\Users\\krdou_000\\Documents\\Repast Workspace\\LTLtoXML\\Patterns.xml");
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Finds the formula that matches the query from the patterns.xml file.
	 * @return formula The pattern formula template.
	 */
	private String getFormulaFromXml()
	{
		String formula = "not identified";
		Element docEle = dom.getDocumentElement();
		
		NodeList nl = docEle.getElementsByTagName(query.getPatternType());
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) 
			{
				Element el = (Element)nl.item(i);
				formula = getTextValue(el, query.getPostfix());
			}
		}
		return formula;
	}
	
	/**
	 * Helper function that gets the value of a text node in xml
	 * 
	 * @param ele The DOM element that we want to get the value of
	 * @param tagName The tag name of the child element of ele
	 * @return textVal The value of the xml text node 
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

}
