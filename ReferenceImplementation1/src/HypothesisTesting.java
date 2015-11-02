import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HypothesisTesting {
	String hypothesisName;
	Query query;
	Document dom;
	
	public HypothesisTesting(String name, Query q )
	{
		this.hypothesisName = name;
		this.query = q;
	}
	
	public String toLTL()
	{
		parseXmlFile();
		String ltlFormula = getFormulaFromXml();
		String[] placeholders = {"P", "S", "R", "X", "Z"};
		for(int i = 0; i < query.getEvents().size(); i++)//problem: if the text we replace has a capital P, S, etc in it, we'll replace stuff we shouldn't
		{
			ltlFormula = ltlFormula.replaceAll(placeholders[i], query.getEvents().get(i));
		}
		return ltlFormula;
	}

	public String getHypothesisName() {
		return hypothesisName;
	}

	public void setHypothesisName(String hypothesisName) {
		this.hypothesisName = hypothesisName;
	}

//	public Patterns getSpecificationPattern() {
//		return specificationPattern;
//	}
//
//	public void setSpecificationPattern(Patterns specificationPattern) {
//		this.specificationPattern = specificationPattern;
//	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query q) {
		this.query = q;
	}
	
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
	
	private String getFormulaFromXml()
	{
		String formula = "not identified";
		Element docEle = dom.getDocumentElement();
		
		NodeList nl = docEle.getElementsByTagName(query.getPatternType());
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) 
			{
				Element el = (Element)nl.item(i);
				formula = getTextValue(el, "Globally");
			}
		}
		return formula;
	}
	
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
