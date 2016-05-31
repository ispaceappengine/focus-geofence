package servlets2jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.CreateJson;
import utility.LoadOnStartAppConfiguration;
import utility.PointInPolygonComputation;
import utility.TextFiles;
import utility.WebsocketClientEndpoint;

/**
 * Used to handle the configuration of the web service. Static data, that is needed for other
 * parts of the web application is also controlled in this class.
 */
@WebServlet({ "/withingeofence"})
public class WithinGeofence extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WithinGeofence() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("This is withinGeofence doGet!");
		//System.out.println("This is Configuration.java:doGet");
		
//			LoadOnStartAppConfiguration.urlSosService = TextFiles.read1SimpleJsonValue("geofence_config.json", "urlSosService");
//		 	LoadOnStartAppConfiguration.urlWfsBoundingBox = TextFiles.read1SimpleJsonValue("tstp2sos_config.json", "urlWfsBoundingBox");	         
//	        LoadOnStartAppConfiguration.urlWfsPolygons = TextFiles.read1SimpleJsonValue("tstp2sos_config.json", "urlWfsPolygons");
	        //LoadOnStartAppConfiguration.arbeitsbereichXmlTagBoundingBox = new PointInPolygonComputation().getArbeitsbereichXmlTagFromWfs("bbox");
	        //LoadOnStartAppConfiguration.arbeitsbereichXmlTagPolygon = new PointInPolygonComputation().getArbeitsbereichXmlTagFromWfs("whateva");
 
			LoadOnStartAppConfiguration.readConfigurationFileIntoVariables();
		
			
	        request.setAttribute("urlWebSocketURI", LoadOnStartAppConfiguration.urlWebSocketURI);
		 	//in .jsp value="${executeInsertObservationServicesOnStartUp}"
	        request.setAttribute("urlWfsBoundingBox_withinGeofence", LoadOnStartAppConfiguration.urlWfsBoundingBox_withinGeofence);
	        request.setAttribute("urlWfsPolygons_withinGeofence",  LoadOnStartAppConfiguration.urlWfsPolygons_withinGeofence);
	        	        
	        
	    //    try {
	            // open websocket
	            //final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("wss://real.okcoin.cn:10440/websocket/okcoinapi"));
	            //final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://ispacevm20.researchstudio.at/geo-websocket/websocket/chat"));
	            // add listener
//	        	if(LoadOnStartAppConfiguration.clientEndPoint == null){
//	        		LoadOnStartAppConfiguration.reloadClientEndpointObject();
//	        	}
//	            LoadOnStartAppConfiguration.clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
//	                public void handleMessage(String message) {
//	                    System.out.println("This is addMessageHandler: "+message);
//	                }
//	            });
	        	
	        	   //LoadOnStartAppConfiguration.clientEndPoint.sendMessage("jsonText");
	        
	            //new CreateJson().createJson();
	            // send message to websocket
	         //   LoadOnStartAppConfiguration.clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");

	            // wait 5 seconds for messages from websocket
	            //Thread.sleep(5000);

	      //  } catch (InterruptedException ex) {
	        //    System.err.println("InterruptedException exception: " + ex.getMessage());
	        //}
	        
	        RequestDispatcher rd = getServletContext().getRequestDispatcher("/within_geofence.jsp");
	        rd.forward(request, response);			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
