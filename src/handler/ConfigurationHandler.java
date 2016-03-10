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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import utility.TextFiles;

/**
 * Servlet implementation class ConfigurationHandler
 */
@WebServlet("/ConfigurationHandler")
public class ConfigurationHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfigurationHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String url2Sos = request.getParameter("_urlSosAddress");
		out.print("this is ConfigurationHandler doPost"+url2Sos);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		//takes all elements from the form
		Map<String, String[]> map_ParameterFromForm = request.getParameterMap();
		try {
			TextFiles.writeJson2ConfigFile("geofence_config.json", map_ParameterFromForm);

			RequestDispatcher rd = getServletContext().getRequestDispatcher("/ok.jsp");
			//RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	    /*
		String url2Sos = request.getParameter("_urlSosAddress");
		String executeInsertObservationServicesOnStartUp = request.getParameter(executeInsertObservationServicesOnStartUp");
		String urlTstpGetZeitreihen = request.getParameter("_urlTstpGetZeitreihen");
		
		//TextFiles.writeUpdate1KeyValueInJson("tstp2sos_config.json", "executeInsertObservationServicesOnStartUp", executeInsertObservationServicesOnStartUp);
		TextFiles.writeUpdate1KeyValueInJson("tstp2sos_config.json", "urlTstpGetZeitreihen", urlTstpGetZeitreihen);
			*/	
			    
	    //out.println("this is ConfigurationHandler doPost"+url2Sos);
		//out.println("Es wird noch nichts abgespeichert!");
	}

}
