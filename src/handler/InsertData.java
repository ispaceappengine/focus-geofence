package handler;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.apache.jasper.tagplugins.jstl.core.Out;

import utility.CreateJson;
import utility.LoadOnStartAppConfiguration;
import utility.Networking;
import utility.ParseIOJson;
import utility.PointPolygon;
import utility.PointInPolygonComputation;
import utility.Service;
import utility.TextFiles;
import utility.ParserXmlJson;
import utility.WebsocketClientEndpoint;
import json.GeoJson;



import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * Servlet implementation class InsertData will receive the messages sent to this web service. Contains doPost and doGet. 
 */
@WebServlet({ "/InsertData", "/insertdata", "/service", "/is", "/io" })
public class InsertData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**Tells if the received string to the service is an InsertObservation, InsertSensor or a json format.*/
	String insertDataType = "";
	Boolean isPointInPolygonUnloading = false;
	Boolean isPointInPolygonWithin = false;
    Logger logger = (Logger) LoggerFactory.getLogger(getClass().getName()+".class"); 
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.println("Please send data with a POST method to this url.");
		
		
		if(LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService){
			/*
			LoadOnStartAppConfiguration.active_urlWebSocketService = false;
			
			if(LoadOnStartAppConfiguration.clientEndPoint !=null){
				LoadOnStartAppConfiguration.clientEndPoint._closeSession();
			}	
			*/		
			logger.debug("LoadOnStartAppConfiguration.active_urlWebSocketService: false");
		
		}else{		
			new WebsocketClientEndpoint()._createConnection();					
			LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService = true;	
			logger.debug("LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService = true");
		}		
		
		//if(LoadOnStartAppConfiguration.active_urlWebSocketService){
			String jsonText = new CreateJson().createJsonHardcoded();
			LoadOnStartAppConfiguration.clientEndPoint.sendMessage(jsonText);
		//}
		
		logger.debug("Tada!");
		
		PointPolygon point = new PointPolygon();
		PointPolygon polygonBbox = new PointPolygon();
		PointInPolygonComputation pipComputation = new PointInPolygonComputation();
		polygonBbox = pipComputation.getPolygonBboxFromWFSForUnloading("bbox", polygonBbox, LoadOnStartAppConfiguration.urlWfsBoundingBox);
		polygonBbox = pipComputation.getPolygonBboxFromWFSForUnloading("polygon", polygonBbox, LoadOnStartAppConfiguration.urlWfsPolygons);
		
		for(int j=0;j<polygonBbox.list_ofBoundingBox.size();j++){
			System.out.println("bbox: "+ polygonBbox.list_ofStrConsistingOf5CoordinatesForBoundingBox.get(j));
			System.out.println("polygon: "+polygonBbox.list_ofStrCoordinatesForPolygon.get(j));
			System.out.println("objectid: "+polygonBbox.list_BboxObjectidForUnloading.get(j));
		}
		
		pipComputation.pointInPolygonTestData(point, polygonBbox);
				
		String urlService ="http://ispacevm30.researchstudio.at/sos41/service";
		String msg ="fail";		
		Networking net = new Networking();
	//	String response_server =net.sendPOST2Webservice(urlService, msg);
		//System.out.println("InsertData.doGet response_server: "+response_server);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//transform received msg to string
		//checkIfInsertSensorOrInsertObservation
		//sendPOST2Webservice
		Networking net = new Networking();
		PointPolygon polygonBbox = new PointPolygon();
		PointInPolygonComputation pipComputation = new PointInPolygonComputation();
		System.out.println("\n\n\n\n----InsertData.doPost");
		
		
		Service service = Service.getInstance();
		
		//unloading
		polygonBbox = pipComputation.getPolygonBboxFromWFSForUnloading("bbox", polygonBbox, LoadOnStartAppConfiguration.urlWfsBoundingBox);
		polygonBbox = pipComputation.getPolygonBboxFromWFSForUnloading("polygon", polygonBbox, LoadOnStartAppConfiguration.urlWfsPolygons);
		
		
		//within geofence
		polygonBbox = pipComputation.getPolygonBboxFromWFSForWithin("bbox", polygonBbox, LoadOnStartAppConfiguration.urlWfsBoundingBox_withinGeofence);
		polygonBbox = pipComputation.getPolygonBboxFromWFSForWithin("polygon", polygonBbox, LoadOnStartAppConfiguration.urlWfsPolygons_withinGeofence);
		
		logger.debug("list_ofBoundingBox: "+polygonBbox.list_ofBoundingBox.size());
//		for(int j=0;j<polygonBbox.list_ofBoundingBox.size();j++){
//			System.out.println("bbox: "+ polygonBbox.list_ofStrConsistingOf5CoordinatesForBoundingBox.get(j));
//			System.out.println("polygon: "+polygonBbox.list_ofStrCoordinatesForPolygon.get(j));
//			System.out.println("objectid: "+polygonBbox.list_BboxObjectidForUnloading.get(j));
//		}
		
	//	String polygonPoints = "13.036148781321177 47.82378749043524 13.036770783951816 47.82404755187994 13.037296268932874 47.823262005454175 13.036148781321177 47.82378749043524";
				
		ServletInputStream serin = request.getInputStream();
		if(serin == null){
			System.out.println("No data received on doPOST()!");
			return;
		}
		StringBuilder stringBuilder = new StringBuilder();
	    Scanner scanner = new Scanner(request.getInputStream());
	    while (scanner.hasNextLine()) {
	        stringBuilder.append(scanner.nextLine());
	    }
	    scanner.close();
	    String insertData = stringBuilder.toString();
	    String insertDataFinal = insertData;
	    System.out.println("InsertData.doPOST received :"+insertData);
	    
	    //later for json files
	    //new ParseIOJson().parseIoJson(insertDataFinal);	    
	    /*try {
			System.out.println(TextFiles.prettyPrintXmlString(xml_og, 2));
		} catch (TransformerException e) {			
			e.printStackTrace();
		}*/
	    
		ParserXmlJson xmlJsonparser = new ParserXmlJson();
		insertDataType = xmlJsonparser.checkReceivedData(insertData);
		System.out.println("InsertData.doPOST insertDataType :"+insertDataType);
		
		String server_response = "";		
		
		if(insertDataType.equals("InsertSensor")){
			insertDataFinal = insertData; //need no modification
			server_response = net.sendPOST2Webservice(LoadOnStartAppConfiguration.urlSosService, insertDataFinal);
						
		}else if(insertDataType.equals("InsertObservation")){					
			
			PointPolygon point = xmlJsonparser.extractPointFromIO(insertData);	
			logger.debug("InsertData.doPost og_extracted_from_IO_x: "+point.point2D.getX());
			logger.debug("InsertData.doPost og_extracted_from_IO_y: "+point.point2D.getY());
					
			//point.setX_longitude(Double.parseDouble(LoadOnStartAppConfiguration.xCoordTestFake));  
			//point.setY_latitude(Double.parseDouble(LoadOnStartAppConfiguration.yCoordTestFake)); 
			//logger.debug("InsertData.doPost fake test x: "+point.getX_longitude());
			//logger.debug("InsertData.doPost fake test y: "+point.getY_latitude());
					
			
			 //pipComputation.pointInPolygonTestData(point, polygonBbox);
			isPointInPolygonUnloading = pipComputation.pointIsInPolygon(point, polygonBbox, polygonBbox.list_BboxObjectidForUnloading,"unloading");
			isPointInPolygonWithin = pipComputation.pointIsInPolygon(point, polygonBbox, polygonBbox.list_BboxObjectiWithin,"within");

			System.out.println("################### isPointInPolygonUnloading: "+ isPointInPolygonUnloading);
			System.out.println("################### isPointInPolygonWithin: "+ isPointInPolygonWithin);	
			
			if(isPointInPolygonUnloading)
			{
				System.out.println("InsertData.doGet isPointInPolygonUnloading: TRUE ");
				insertDataFinal = xmlJsonparser.modifyIoXml(insertData);
				System.out.println("InsertData.doGet insertDataFinal:\n" +insertDataFinal);
			}else{
				System.out.println("InsertData.doGet isPointInPolygonUnloading: FALSE ");
				//insertDataFinal = xmlJsonparser.modifyIoXml(insertData);
				System.out.println("InsertData.doGet insertDataFinal:\n" +insertDataFinal);
			}
			LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService=true;
			if(LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService){
				GeoJson geojson= xmlJsonparser.extractDataForGeoJsonFromIo(insertData);
				String jsonText = new CreateJson().createJson(geojson, isPointInPolygonWithin);				
				LoadOnStartAppConfiguration.clientEndPoint.sendMessage(jsonText);
			}
				
			
			server_response = net.sendPOST2Webservice(LoadOnStartAppConfiguration.urlSosService, insertDataFinal);
		} else if(insertDataType.equals("json")){
			System.out.println("InsertData.doPOST insertDataType :"+insertDataType);
			server_response = net.sendPOST2Webservice(LoadOnStartAppConfiguration.urlSosService, insertDataFinal);
		}

		//String server_response = net.sendPOST2Webservice(LoadOnStartAppConfiguration.urlSosService, insertDataFinal);
		PrintWriter outs = response.getWriter();
		response.setContentType("text/xml");
		//response.setContentType("application/xml");
		//response.setContentType("text/xml");
		//response.setCharacterEncoding("charset=UTF-8");
	
		//response.setContentType ("application/txt");
		if(insertDataType.equals("InsertSensor")){
			insertDataFinal = insertData; //need no modification
			outs.write(server_response);
		}else if(insertDataType.equals("InsertObservation")){
	//		outs.write("echter point is in polygon: "+isPointInPolygonUnloading);
		//	outs.write("\nFake point zum Testen is in polygon: "+LoadOnStartAppConfiguration.fakeTestPointInPolygon);
			outs.write(server_response);
		}
		//outs.write(server_response);
	}
}
