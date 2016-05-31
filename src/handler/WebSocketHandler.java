package handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import utility.LoadOnStartAppConfiguration;
import utility.TextFiles;
import utility.WebsocketClientEndpoint;


/**
 * Servlet implementation class ConfigurationHandler
 */
@WebServlet({"/WebSocketHandler","/websocketHandler"})
public class WebSocketHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = (Logger) LoggerFactory.getLogger(getClass().getName()+".class");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebSocketHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String url2Sos = request.getParameter("_urlSosAddress");
		out.print("this is GeoserverwfsHandler doGet"+url2Sos);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
	
		if(LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService){
			LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService = false;
			
			if(LoadOnStartAppConfiguration.clientEndPoint !=null){
				LoadOnStartAppConfiguration.clientEndPoint._closeSession();
			}			
			logger.debug("LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService = false");
		
		}else{		
			new WebsocketClientEndpoint()._createConnection();					
			LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService = true;	
			logger.debug("LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService = true");
		}
		
		//takes all elements from the form
		Map<String, String[]> map_ParameterFromForm = request.getParameterMap();
				
//		try {
//			TextFiles.writeJson2ConfigFile(LoadOnStartAppConfiguration.configFile, map_ParameterFromForm);
//
//			RequestDispatcher rd = getServletContext().getRequestDispatcher("/ok.jsp");
//			//RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
//			rd.forward(request, response);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	    /*
		String url2Sos = request.getParameter("_urlSosAddress");
		String executeInsertObservationServicesOnStartUp = request.getParameter(executeInsertObservationServicesOnStartUp");
		String urlTstpGetZeitreihen = request.getParameter("_urlTstpGetZeitreihen");
		
		//TextFiles.writeUpdate1KeyValueInJson("tstp2sos_config.json", "executeInsertObservationServicesOnStartUp", executeInsertObservationServicesOnStartUp);
		TextFiles.writeUpdate1KeyValueInJson("tstp2sos_config.json", "urlTstpGetZeitreihen", urlTstpGetZeitreihen);
			*/	
			    
	    //out.println("this is ConfigurationHandler doPost"+url2Sos);
		//out.println("Es wird noch nichts abgespeichert!");
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/ok.jsp");
		rd.forward(request, response);
	}

}
