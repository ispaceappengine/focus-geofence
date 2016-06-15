package utility;

import java.awt.List;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import json.GeoJson;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ch.qos.logback.classic.Logger;

/**
 * Contains all methods that deal with strings (XML, JSON).
 * @author slukic
 */
public class ParserXmlJson {

	Logger logger = (Logger) LoggerFactory.getLogger(getClass().getName()+".class");
	/**
	 * Checks what kind of msg the insertData is (e.g. InsertObservation, InsertSensor, Json)
	 * @param insertData String that will be checked for its type. e.g. IO, IS or json.
	 * @return The information if the string is an IO, IS or a json.
	 */
	public String checkReceivedData(String insertData){
		String insertDataType = "undefined";		
		
		if(insertData.startsWith("{"))
		{
			insertDataType ="json";
		}
		else if(insertData.contains("<swes:InsertSensor"))
		{
			insertDataType = "InsertSensor";			
		}
		else if(insertData.contains("<sos:InsertObservation"))
		{
			insertDataType ="InsertObservation";						
		}		
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
	        	//book[title/@lang = 'it'] [@uom='abc']
				//myNodeList.item(0).setNodeValue("Hi mom!");
	        	
	        	
	        	//find Loading value
				String path_loading = "//om:OM_Observation[om:observedProperty[@xlink:href='http://ispace.researchstudio.at/ont/swe/property/Loading']]/om:result";
				NodeList nodes = (NodeList)xPath.compile(path_loading).evaluate(doc, XPathConstants.NODESET);
			
				String messwert = "";
				for(int n = 0; n<nodes.getLength(); n++){
					messwert = nodes.item(n).getTextContent();
					//replace original value with new value
					if(messwert.equals(LoadOnStartAppConfiguration.value_from)){
						nodes.item(n).setTextContent(LoadOnStartAppConfiguration.value_to);;
					}				
				}		
				
				
			//	System.out.println(TextFiles.xmlDocument2StringWithPrettyPrint(doc, 2));
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		xml_final = TextFiles.xmlDocument2StringWithPrettyPrint(doc, 2);
		return xml_final;
	}
	
	/**
	 * Extracts data for InsertObservation.xml that is needed for the GeoJson.
	 * @param xml_og The InsertObservation.xml
	 * @return A GeoJson object containing an ArrayList with GeoJson objects, that contain the needed data for each single InsertObservation.
	 * Right now only the object at position 0 is returned. Since we are checking only for coordinates, there is no need to check all observation blocks.
	 */
	public GeoJson extractDataForGeoJsonFromIo(String xml_og){
		
		GeoJson geoJson = new GeoJson();
		
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
			                }else if("gml".equals(args)){
			                	return "http://www.opengis.net/gml/3.2";
			                }else if("sams".equals(args)){
			                	return "http://www.opengis.net/samplingSpatial/2.0";
			                }else{
			                return null;}
			            }
				});				
				/*
				String path_offering = "/soap:Envelope/soap:Body/sos:Capabilities/@version";
				Node node_offering = (Node)xPath.compile(path_offering).evaluate(doc, XPathConstants.NODE);
				System.out.println("offering: "+node_offering.getTextContent());
				
				String path_procedures = "//om:OM_Observation[@name='GetObservation']/ows:Parameter[@name='procedure']/ows:AllowedValues/ows:Value"; */
				//String path_loading = "//om:OM_Observation[om:observedProperty[@xlink:href='http://ispace.researchstudio.at/ont/swe/property/Loading']]/om:result";
	        	
	        	//The path to the time is only for the first observation block valid; other blocks have another path
	        	//book[title/@lang = 'it'] [@uom='abc']
				//myNodeList.item(0).setNodeValue("Hi mom!");
	        	
	        	
				
				String path_SamplingFOIIdentifier = "//sams:SF_SpatialSamplingFeature/gml:identifier";
				NodeList nodes_path_SamplingFOIIdentifier = (NodeList)xPath.compile(path_SamplingFOIIdentifier).evaluate(doc, XPathConstants.NODESET);
				
				String path_y_lat_x_long = "//gml:pos";
				NodeList nodes_path_y_lat_x_long = (NodeList)xPath.compile(path_y_lat_x_long).evaluate(doc, XPathConstants.NODESET);						
	        	
				
				String messwert = "";	
				
				//for(int n = 0; n<nodes_procedure.getLength(); n++){
				//we need the data from only the first observation block
				for(int n = 0; n<1; n++){
					
					
					String path_time = "//gml:timePosition";
					NodeList nodes_path_time = (NodeList)xPath.compile(path_time).evaluate(doc, XPathConstants.NODESET);				
					String path_SamplingFOIName = "//sams:SF_SpatialSamplingFeature/gml:name";
					NodeList nodes_path_SamplingFOIName = (NodeList)xPath.compile(path_SamplingFOIName).evaluate(doc, XPathConstants.NODESET);
					String path_procedure = "//om:OM_Observation/om:procedure/@xlink:href";
					NodeList nodes_procedure = (NodeList)xPath.compile(path_procedure).evaluate(doc, XPathConstants.NODESET);
					
					try{
						GeoJson gjson = new GeoJson();
						geoJson.list_geoJson.add(gjson);
						
						gjson.observationPhenomenonTime = nodes_path_time.item(0).getTextContent();						
						gjson.samplingFOIName = nodes_path_SamplingFOIName.item(n).getTextContent();						
						gjson.samplingFOIIdentifier = nodes_path_SamplingFOIIdentifier.item(n).getTextContent();
						gjson.procedure = nodes_procedure.item(n).getTextContent();	
					
						String coords = nodes_path_y_lat_x_long.item(n).getTextContent();
						String [] stray = coords.split(" ");
						gjson.y_lat = Double.parseDouble(coords.split(" ")[0]);
						gjson.x_long = Double.parseDouble(coords.split(" ")[1]);
						
						if(stray.length==3){
							gjson.z_alt = Double.parseDouble(coords.split(" ")[2]); 
						}					
						
					}catch(Exception ex){
						
						logger.error("some error", ex);						
					}						
				}		
				GeoJson gj = geoJson.list_geoJson.get(0);
				for (Field field : gj.getClass().getDeclaredFields()) {
					logger.debug(field.getName()+ ": "+field.get(gj));
				}
				
			//	System.out.println(TextFiles.xmlDocument2StringWithPrettyPrint(doc, 2));
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		//xml_final = TextFiles.xmlDocument2StringWithPrettyPrint(doc, 2);
		return geoJson.list_geoJson.get(0);
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
				
				//String pathToCoordinates ="//gml:LinearRing/gml:posList";
	        	
				//book[title/@lang = 'it'] [@uom='abc']
				//myNodeList.item(0).setNodeValue("Hi test!");
				
	        	String pathToCoordinates ="//gml:Point/gml:pos";
				NodeList nodes_position = (NodeList)xPath.compile(pathToCoordinates).evaluate(doc, XPathConstants.NODESET);
	        	
				ArrayList<String> list_xy = new ArrayList<String>();
				
				for(int n = 0; n<nodes_position.getLength(); n++){		
					list_xy.add(nodes_position.item(n).getTextContent());
				}			

				try{				
					point.y_latitude = Double.parseDouble(list_xy.get(0).split(" ")[0]);
					point.x_longitude = Double.parseDouble(list_xy.get(0).split(" ")[1]);										
					point.point2D.setLocation(point.y_latitude, point.x_longitude);
				
				}catch(Exception e){					
					e.printStackTrace();					
				}
				
				
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return point;				
	}
	String nameSpaceUri;
	/**
	 * Extracts the XY coordinates and objectids from the WFS XML. Every Polygon/BoundingBox in the XML has its own string containing XY coordinates. 
	 * @param xml_og XML/String that will be parsed for X Y coordinates.
	 * @param url_geofenceWfs URL where from the xml_og was retrieved. The URL is needed to extract the arbeitsbereich/namespace tag. e.g. focus
	 * @return list_coords_objectid  List of Lists. The first list contains many coordinates. The second list contains objectids.
	 */
	public ArrayList<ArrayList<String>> extractCoordsFromWfsXml(String xml_og, String url_geofenceWfs, String serviceIdentifier) {
		
		//logger.debug(xml_og);
		nameSpaceUri = getArbeitsbereichXmlTagFromWfs(url_geofenceWfs); //z.B. focus
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
			              if(nameSpaceUri.equals(args)){
			                	return nameSpaceUri;
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
				

	        	String pathToObjectid = "//"+nameSpaceUri +":objectid";
	        	NodeList nodes_Objectid = (NodeList)xPath.compile(pathToObjectid).evaluate(doc, XPathConstants.NODESET);
	        	
				String pathToCoordinates ="//gml:LinearRing/gml:posList";
				NodeList nodes_position = (NodeList)xPath.compile(pathToCoordinates).evaluate(doc, XPathConstants.NODESET);
				//book[title/@lang = 'it'] [@uom='abc']
				//myNodeList.item(0).setNodeValue("Hi test!");
				String xy= "";
				
			//	logger.debug("vor for loop ParserXmlJson.extractPointFromIO:"+ nodes_position.getLength());	
				for(int n = 0; n<nodes_position.getLength(); n++){
					
					if(serviceIdentifier.equals("within")){
						point.list_ofStrConsistingOf5CoordinatesForBoundingBox.add(nodes_position.item(n).getTextContent());
					}else{
						point.list_ofStrConsistingOf5CoordinatesForBoundingBoxWithin.add(nodes_position.item(n).getTextContent());
					}			
					
					list_coords.add(nodes_position.item(n).getTextContent());
					
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

	
	/**
	 * Extracts the ArbeitsbereichXmlTag from the URL. This is needed because the namespace of the xml elements is the ArbeitsbereichXmlTag.
	 * Without the namespace, there is no working access to the xml elements
	 * @param url URL that contains the arbeitsbereichXmlTag string that will be extracted.
	 * @return String Contains the arbeitsbereichXmlTag that is used as namespace to parse the WFS polygon xml.
	 */
	public String getArbeitsbereichXmlTagFromWfs(String url){
		String arbeitsbereichXmlTag = ""; //z.B. focus
		String url2Wfs =url.toLowerCase();
		try{			
			String separator = "typename";
			//e.g. http://ispacevm22.researchstudio.at/geoserver/geofence_sbg/wfs?service=WFS&version=1.0.0&request=GetFeature&typename=geofence_sbg:geofence_sbg_151215_bbox
		//	logger.debug("url:"+url);
			String [] stray = url2Wfs.split(separator);
			String typenamePlusRest = stray[1]; //e.g.=geofence_sbg:geofence_sbg_151215_bbox
			String [] stray2 = typenamePlusRest.substring(1).split(":");
			arbeitsbereichXmlTag = stray2[0];

		}catch(Exception ex){
			ex.printStackTrace();

		}
		return arbeitsbereichXmlTag; 
	}
}
