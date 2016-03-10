package utility;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import java.net.URI;
import java.net.URISyntaxException;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The class is called on Server/Application start. Mainly used to initialize specific variables and lists 
 * with data, that are needed in later process.
 * 
 * @author slukic
 */
public class LoadOnStartAppConfiguration extends HttpServlet {
	
	/** Specifies the string e.g. "=" that will separate NAME, ORT, etc.	 */	 
	public static String elementSeparator = "=";	
	
	public static String urlSosService = "";
	public static String xCoordTestFake, yCoordTestFake, urlWfsBoundingBox, urlWfsPolygons;	
	public static String arbeitsbereichXmlTagBoundingBox, arbeitsbereichXmlTagPolygon ="";
	public static boolean fakeTestPointInPolygon;
	public static String configFile ="geofence_config.json";
	public static  WebsocketClientEndpoint clientEndPoint = null;
	//public static String webSocketURI ="ws://ispacevm20.researchstudio.at/geo-websocket/websocket/chat";
	public static String webSocketURI ="ws://localhost:8080/geo-websocket/websocket/iridium";
	
	public void init() throws ServletException {

		String classLoaderPath = TextFiles.setClassLoaderPath();
		
		 try {
			clientEndPoint=new WebsocketClientEndpoint(new URI(webSocketURI));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
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
		System.out.println("\n");
		System.out.println("############ Start ############ ");
		System.out.println("This is LoadOnStartAppConfiguration: geofence");
		System.out.println("ClassLoaderPath: " + classLoaderPath);
		System.out.println("############ End of LoadOnStartAppConfiguration! ############ \n");
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
		LoadOnStartAppConfiguration.arbeitsbereichXmlTagBoundingBox = new PointInPolygonComputation().getArbeitsbereichXmlTagFromWfs("bbox");
        LoadOnStartAppConfiguration.arbeitsbereichXmlTagPolygon = new PointInPolygonComputation().getArbeitsbereichXmlTagFromWfs("whateva");
	}
	
	/**
	 * Provides a new reference, if the clientEndPoint object is null, to a new WebsocketClientEndpoint. 
	 * That will occur if the websocket connection has been closed and a new one needs to be reopened.
	 */
	public static void reloadClientEndpointObject(){
		try {
			clientEndPoint=new WebsocketClientEndpoint(new URI(webSocketURI));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}