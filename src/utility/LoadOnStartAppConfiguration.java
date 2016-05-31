package utility;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;





import ch.qos.logback.classic.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The class is called on Server/Application start. Mainly used to initialize specific variables and lists 
 * with data, that are needed in later processes.
 * 
 * @author slukic
 */
public class LoadOnStartAppConfiguration extends HttpServlet {
	
	Logger logger = (Logger) LoggerFactory.getLogger(getClass().getName()+".class");
	/** Specifies the string e.g. "=" that will separate NAME, ORT, etc.	 */	 
	
	public static String elementSeparator = "=";	
	public static ArrayList<Double []> list_coords = new ArrayList<Double[]>();
	
	
	public static String xCoordTestFake, yCoordTestFake, urlWfsBoundingBox, urlWfsPolygons;
	public static String urlWfsBoundingBox_withinGeofence;
	public static String urlWfsPolygons_withinGeofence;	
	public static String arbeitsbereichXmlTagBoundingBox, arbeitsbereichXmlTagPolygon ="";
	public static boolean fakeTestPointInPolygon;
	public static String configFile ="geofence_config.json";
	public static  WebsocketClientEndpoint clientEndPoint = null;
	public static WebsocketClientEndpoint clientEndpoint_withinGeofence;
	//public static String urlWebSocketURI ="ws://ispacevm20.researchstudio.at/geo-websocket/websocket/chat";
	public static String urlWebSocketURI ="";
	
	public static String urlSosService = "";
	public static Boolean active_urlUnloadingWfsService = false;
	public static Boolean active_urlWithinGeofenceWfsService = false;
	public static String urlGeoserverWfsService = urlWfsPolygons;
	public static String value_from;
	public static String value_to;
	
	public void init() throws ServletException {
		
		//can be deleted
		/* 
		int n =2;
		for(int i =0; i<4; i++){
		Double [] arrDouble = new Double[n];
		arrDouble[0] = 47.21 +i;
		arrDouble[1] = 13.61 +i*2;
		if(i%2==0){
			arrDouble[0] = 37.21 +i;
			arrDouble[1] = 10.61 +i*2;
		}
		}
		//list_coords.add(arrDouble);
		*/

		list_coords.add(new Double[]{44.10, 12.10 });
		list_coords.add(new Double[]{44.10, 14.10 });
		list_coords.add(new Double[]{49.10, 14.10 });
		list_coords.add(new Double[]{49.10, 12.10 });
		
		logger.debug(String.valueOf(list_coords.size()));

		String classLoaderPath = TextFiles.setClassLoaderPath();
		/*
		 try {
			clientEndPoint=new WebsocketClientEndpoint(new URI(urlWebSocketURI));
			logger.info("WebSocket unter: "+urlWebSocketURI+" verfügbar!");
		} catch (Exception e) {
			e.printStackTrace();	
			logger.error("some error:",e);
			logger.debug(e.getMessage());			
		}
		*/
		// String jsonText =
		// TextFiles.readTextFileWithServletsAsStream_UTF8("tstp2sos_config.json");
		try {
			readConfigurationFileIntoVariables();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Probably no config json file, missing key-value in the config json file,");
		}		

		
//		Gson g = new Gson();
//		JsonObject jobj2 = g.fromJson(jsonText, JsonObject.class);
//		Boolean executeInsertObservationServicesOnStartUp = jobj2.get("executeInsertObservationServicesOnStartUp").getAsBoolean();
//		elementSeparator = jobj2.get("elementSeparator").getAsString();
		
		Instant time = Instant.now();
		logger.debug("time: "+time);
		
		logger.debug("############ Start ############ ");
		logger.debug("This is LoadOnStartAppConfiguration geofence");
		logger.debug("ClassLoaderPath: " + classLoaderPath);
		logger.debug("############ End of LoadOnStartAppConfiguration! ############ \n");
	}
	
	/**
	 * Reads the configuration file (json) and populates the matching in code variables.
	 */
	public static void readConfigurationFileIntoVariables(){
		LoadOnStartAppConfiguration.xCoordTestFake = TextFiles.read1SimpleJsonValue(configFile, "xCoordTestFake");
		LoadOnStartAppConfiguration.yCoordTestFake = TextFiles.read1SimpleJsonValue(configFile, "yCoordTestFake");
		LoadOnStartAppConfiguration.urlSosService = TextFiles.read1SimpleJsonValue(configFile, "urlSosService");
		LoadOnStartAppConfiguration.urlWfsBoundingBox = TextFiles.read1SimpleJsonValue(configFile, "urlWfsBoundingBox");
		LoadOnStartAppConfiguration.urlWfsPolygons = TextFiles.read1SimpleJsonValue(configFile, "urlWfsPolygons");
		LoadOnStartAppConfiguration.urlWebSocketURI = TextFiles.read1SimpleJsonValue(configFile, "urlWebSocketURI");
		//LoadOnStartAppConfiguration.arbeitsbereichXmlTagBoundingBox = new ParserXmlJson().getArbeitsbereichXmlTagFromWfs("bbox");
        //LoadOnStartAppConfiguration.arbeitsbereichXmlTagPolygon = new ParserXmlJson().getArbeitsbereichXmlTagFromWfs("whateva");
        LoadOnStartAppConfiguration.urlWfsBoundingBox_withinGeofence = TextFiles.read1SimpleJsonValue(configFile, "urlWfsBoundingBox_withinGeofence");
        LoadOnStartAppConfiguration.urlWfsPolygons_withinGeofence = TextFiles.read1SimpleJsonValue(configFile, "urlWfsPolygons_withinGeofence");
        LoadOnStartAppConfiguration.value_from = TextFiles.read1SimpleJsonValue(configFile, "value_from");
        LoadOnStartAppConfiguration.value_to = TextFiles.read1SimpleJsonValue(configFile, "value_to");
	}
	
	/**
	 * Provides a new reference, if the clientEndPoint object is null, to a new WebsocketClientEndpoint. 
	 * That will occur if the websocket connection has been closed and a new one needs to be reopened.
	 */
	public static void reloadClientEndpointObject(){
		try {
			clientEndPoint=new WebsocketClientEndpoint(new URI(urlWebSocketURI));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}