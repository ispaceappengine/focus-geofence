package utility;

import java.awt.List;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Contains all methods that deal with strings (XML, JSON).
 * @author slukic
 */
public class ParserXmlJson {

	/**
	 * Checks what kind of msg the insertData is (e.g. InsertObservation, InsertSensor, Json. If xml.contains("swes:InsertSensor service")  it is an InsertSensor operation and
	 * the response will be "InsertSensor". If xml.contains("sos:InsertObservation service")  it is an InsertObservation operation  and
	 * the response will be "InsertObservation".
	 * @param insertData String that will be checked für its type. e.g. IO, IS or json.
	 * @return insertDataType The information if the string is an IO, IS or a json.
	 */
	public String checkReceivedData(String insertData){
		String insertDataType = "";		
		
		if(insertData.startsWith("{"))
		{
			insertDataType ="json";
		}
		else if(insertData.contains("swes:InsertSensor service"))
		{
			insertDataType = "InsertSensor";			
		}
		else if(insertData.contains("sos:InsertObservation service"))
		{
			insertDataType ="InsertObservation";						
		}
		System.out.println("ParserXmlJson.checkIfInsertSensorOrInsertObservation");
		return insertDataType;
	}

	
	/**
	 * Used to parse the InsertObservation and edit the value for "unloading".
	 * @param InsertObservation XML string, that will be parsed and edited.
	 * @return xml_final A String/XML with the new edited value.
	 */
	public String modifyIoXml(String xml_og){
		DocumentBuilder dbuilder = null;
		Document doc =  null;
		String xml_final;
		
		try {			
			XPath xPath = XPathFactory.newInstance().newXPath();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
	        DocumentBuilder builder = dbFactory.newDocumentBuilder();
	        doc = builder.parse(new InputSource(new StringReader(xml_og)));
	        	        
	        	xPath.setNamespaceContext(new NamespaceContext() {					
					@Override
					public Iterator getPrefixes(String namespaceURI) {
						// TODO Auto-generated method stub
						return null;
					}					
					@Override
					public String getPrefix(String namespaceURI) {
						// TODO Auto-generated method stub
						return null;
					}					
					   @Override
			            public String getNamespaceURI(String args) {
			                if("swe".equals(args)) {
			                    return "http://www.opengis.net/swe/1.0.1";
			                }else if("env".equals(args)){
			                	return "http://www.w3.org/2003/05/soap-envelope";
			                }else if("sos".equals(args)){
			                	return "http://www.opengis.net/sos/2.0";
			                }else if("ows".equals(args)){
			                	return "http://www.opengis.net/ows/1.1";
			                }else if("soap".equals(args)){
			                	return "http://www.w3.org/2003/05/soap-envelope";             	
			                }else if("om".equals(args)){
			                	return "http://www.opengis.net/om/2.0";
			                }else if("xlink".equals(args)){
			                	return "http://www.w3.org/1999/xlink";
			                }else{
			                return null;}
			            }
				});				
				/*
				String path_offering = "/soap:Envelope/soap:Body/sos:Capabilities/@version";
				Node node_offering = (Node)xPath.compile(path_offering).evaluate(doc, XPathConstants.NODE);
				System.out.println("offering: "+node_offering.getTextContent());
				
				String path_procedures = "//om:OM_Observation[@name='GetObservation']/ows:Parameter[@name='procedure']/ows:AllowedValues/ows:Value"; */
				String path_loading = "//om:OM_Observation[om:observedProperty[@xlink:href='http://ispace.researchstudio.at/ont/swe/property/Loading']]/om:result";
				NodeList nodes = (NodeList)xPath.compile(path_loading).evaluate(doc, XPathConstants.NODESET);
				//book[title/@lang = 'it'] [@uom='abc']
				//myNodeList.item(0).setNodeValue("Hi mom!");
				String messwert = "";
				for(int n = 0; n<nodes.getLength(); n++){
					messwert = nodes.item(n).getTextContent();
					System.out.println("ParserXmlJson.parseInsertObservation:parser Loading OG: "+messwert);
					
					if(messwert.equals("1")){
						nodes.item(n).setTextContent("2222");;
					}
					
					System.out.println("ParserXmlJson.parseInsertObservation:parser Loading EDITED:"+nodes.item(n).getTextContent());
				}			
			//	System.out.println(TextFiles.xmlDocument2StringWithPrettyPrint(doc, 2));
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		xml_final = TextFiles.xmlDocument2StringWithPrettyPrint(doc, 2);
		return xml_final;
	}

	/**
	 * Extracts the X Y coordinates from the InsertObservation XML.
	 * @param xml_og XML/String that will be parsed for X Y coordinates.
	 * @return point A PointPolygon object that has a list of many coordinates forming a polygon.
	 */
	public PointPolygon extractPointFromIO(String xml_og) {
		PointPolygon point = new PointPolygon();
		
		DocumentBuilder dbuilder = null;
		try {			
			XPath xPath = XPathFactory.newInstance().newXPath();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
	        DocumentBuilder builder = dbFactory.newDocumentBuilder();
	        Document doc = builder.parse(new InputSource(new StringReader(xml_og)));
	        	        
	        	xPath.setNamespaceContext(new NamespaceContext() {
					
					@Override
					public Iterator getPrefixes(String namespaceURI) {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public String getPrefix(String namespaceURI) {
						// TODO Auto-generated method stub
						return null;
					}			
					
					   @Override
			            public String getNamespaceURI(String args) {
			                if("swe".equals(args)) {
			                    return "http://www.opengis.net/swe/1.0.1";
			                }else if("env".equals(args)){
			                	return "http://www.w3.org/2003/05/soap-envelope";
			                }else if("sos".equals(args)){
			                	return "http://www.opengis.net/sos/2.0";
			                }else if("ows".equals(args)){
			                	return "http://www.opengis.net/ows/1.1";
			                }else if("soap".equals(args)){
			                	return "http://www.w3.org/2003/05/soap-envelope";             	
			                }else if("om".equals(args)){
			                	return "http://www.opengis.net/om/2.0";
			                }else if("xlink".equals(args)){
			                	return "http://www.w3.org/1999/xlink";
			                }else if("gml".equals(args)){
			                	return "http://www.opengis.net/gml/3.2";
			                }else{
			                return null;}
			            }
				});				
				/*
				String path_offering = "/soap:Envelope/soap:Body/sos:Capabilities/@version";
				Node node_offering = (Node)xPath.compile(path_offering).evaluate(doc, XPathConstants.NODE);
				System.out.println("offering: "+node_offering.getTextContent());
				
				String path_procedures = "//om:OM_Observation[@name='GetObservation']/ows:Parameter[@name='procedure']/ows:AllowedValues/ows:Value"; */
				//String pathToLoading = "//om:OM_Observation[om:observedProperty[@xlink:href='http://ispace.researchstudio.at/ont/swe/property/Loading']]/om:result";
				
				//jetzt werden hier aber alle X Y Koordinaten,die sich in InsertObservation.xml wiederholen, ausgelesen werden
				String pathToCoordinates ="//gml:Point/gml:pos";
				//String pathToCoordinates ="//gml:LinearRing/gml:posList";
				NodeList nodes_position = (NodeList)xPath.compile(pathToCoordinates).evaluate(doc, XPathConstants.NODESET);
				//book[title/@lang = 'it'] [@uom='abc']
				//myNodeList.item(0).setNodeValue("Hi mom!");
				String xy= "";
				ArrayList<String> list_xy = new ArrayList<String>();
				
				for(int n = 0; n<nodes_position.getLength(); n++){
					System.out.println("ParserXmlJson.extractPointFromIO: "+nodes_position.item(n).getTextContent());		
					list_xy.add(nodes_position.item(n).getTextContent());
					//node_procedures.item(n).setTextContent("4444");
					//System.out.println("ParserXmlJson.parseInsertObservation:parser EDITED:"+node_procedures.item(n).getTextContent());
				}			
			//	System.out.println(TextFiles.xmlDocument2StringWithPrettyPrint(doc, 2));
				try{
				
					point.y_latitude = Double.parseDouble(list_xy.get(0).split(" ")[0]);
					point.x_longitude = Double.parseDouble(list_xy.get(0).split(" ")[1]);
					
					point.point2D.setLocation(point.x_longitude, point.y_latitude);
				
				}catch(Exception e){
					System.out.println("Error: maybe no coordinates!");
					e.printStackTrace();					
				}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return point;				
	}
	
	/**
	 * Extracts the XY coordinates and objectids from the WFS XML. Every Polygon/BoundingBox in the XML has its own string containing XY coordinates. 
	 * @param xml_og XML/String that will be parsed for X Y coordinates.
	 * @return list_coords_objectid  List of Lists. The first list contains many coordinates. The second list contains objectids.
	 */
	public ArrayList<ArrayList<String>> extractCoordsFromWfsXml(String xml_og) {
		System.out.println("ParserXmlJson.extractBoundingBoxFromWfsXml: "+ xml_og);
		PointPolygon point = new PointPolygon();
		ArrayList<String> list_objectid = new ArrayList<String>();
		ArrayList<String> list_coords = new ArrayList<String>();
		ArrayList<ArrayList<String>> list_coords_objectid = new ArrayList<ArrayList<String>>();
		
		
		DocumentBuilder dbuilder = null;
		try {			
			XPath xPath = XPathFactory.newInstance().newXPath();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
	        DocumentBuilder builder = dbFactory.newDocumentBuilder();
	        Document doc = builder.parse(new InputSource(new StringReader(xml_og)));
	        	        
	        	xPath.setNamespaceContext(new NamespaceContext() {
					
					@Override
					public Iterator getPrefixes(String namespaceURI) {
						return null;
					}
					
					@Override
					public String getPrefix(String namespaceURI) {
						return null;
					}			
					
					   @Override
			            public String getNamespaceURI(String args) {
			              if(LoadOnStartAppConfiguration.arbeitsbereichXmlTagPolygon.equals(args)){
			                	return LoadOnStartAppConfiguration.arbeitsbereichXmlTagPolygon;
			                }else if("gml".equals(args)){
			                	return "http://www.opengis.net/gml/3.2";             	
			                }else{
			                	return null;
			                }
			            }
				});		
//	        	String path_offering = "/wfs:FeatureCollection/wfs:member/geofence_sbg:geofence_sbg_bbox/@gml:id";
//				Node node_offering = (Node)xPath.compile(path_offering).evaluate(doc, XPathConstants.NODE);
//				System.out.println("offering: "+node_offering.getNodeValue());
				/*
				String path_offering = "/soap:Envelope/soap:Body/sos:Capabilities/@version";
				Node node_offering = (Node)xPath.compile(path_offering).evaluate(doc, XPathConstants.NODE);
				System.out.println("offering: "+node_offering.getTextContent());
				
				String path_procedures = "//om:OM_Observation[@name='GetObservation']/ows:Parameter[@name='procedure']/ows:AllowedValues/ows:Value"; */
//				String pathToLoading = "//om:OM_Observation[om:observedProperty[@xlink:href='http://ispace.researchstudio.at/ont/swe/property/Loading']]/om:result";
				
				//jetzt werden hier aber alle X Y Koordinaten,die sich in InsertObservation.xml wiederholen, ausgelesen werden
	        	String pathToObjectid = "//geofence_sbg:objectid";
	        	NodeList nodes_Objectid = (NodeList)xPath.compile(pathToObjectid).evaluate(doc, XPathConstants.NODESET);
	        	
				String pathToCoordinates ="//gml:LinearRing/gml:posList";
				NodeList nodes_position = (NodeList)xPath.compile(pathToCoordinates).evaluate(doc, XPathConstants.NODESET);
				//book[title/@lang = 'it'] [@uom='abc']
				//myNodeList.item(0).setNodeValue("Hi mom!");
				String xy= "";
				
				System.out.println("vor for loop ParserXmlJson.extractPointFromIO:"+ nodes_position.getLength());	
				for(int n = 0; n<nodes_position.getLength(); n++){
					System.out.println("ParserXmlJson.extractPointFromIO: "+nodes_position.item(n).getTextContent());
					point.list_ofStrConsistingOf5CoordinatesForBoundingBox.add(nodes_position.item(n).getTextContent());
					list_coords.add(nodes_position.item(n).getTextContent());
					System.out.println("ParserXmlJson.extractPointFromIO objectid: "+nodes_Objectid.item(n).getTextContent());
					list_objectid.add(nodes_Objectid.item(n).getTextContent());
									
					//node_procedures.item(n).setTextContent("4444");
					//System.out.println("ParserXmlJson.parseInsertObservation:parser EDITED:"+node_procedures.item(n).getTextContent());
				}			
			//	System.out.println(TextFiles.xmlDocument2StringWithPrettyPrint(doc, 2));
				try{
					
				}catch(Exception e){
					System.out.println("Error: maybe no coordinates!");
					e.printStackTrace();					
				}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		list_coords_objectid.add(list_coords);
		list_coords_objectid.add(list_objectid);
		return list_coords_objectid;//point.list_ofStrConsistingOf5CoordinatesForBoundingBox;				
	}
}
