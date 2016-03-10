package utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * Used for post/get (networking) requests purposes. The response will be in most cases an xml file. 
 * @author slukic
 */
public class Networking {

	private String url = "";
	
	/**
	 * Sends a GET request to the given URL to receive a XML, that contains polygons with its coordinates.
	 * @param url_geofenceWfs URL where to get the geofence XML from.
	 * @return GeofenceXML XML that contains the polygons with its coordinates.
	 */
	public String sendGET2getGeofenceFromWFS(String url_geofenceWfs){
		BufferedReader br;
		OutputStreamWriter wr;
		//url_geofenceWfs = "http://ispacevm22.researchstudio.at/geoserver/geofence_sbg/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=geofence_sbg:geofence_sbg&maxFeatures=50&outputFormat=text/xml;%20subtype=gml/3.2";
		String response_txt = "";
		try
		{
		    URL url = new URL(url_geofenceWfs);
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.flush();

		    // Get the response
		    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    
			StringBuilder strBuilder = new StringBuilder();
						
			while((line = br.readLine()) != null){
					strBuilder.append(line);
					//System.out.println(line);
				}
			response_txt = strBuilder.toString();

		}
		catch (Exception e) {
		        System.out.println(e.toString());
		    }
		return response_txt;
	}


	/**
	 * Sends a POST request with a text/xml msg to a web service.
	 * @param urlService Address from server/service.
	 * @param msg Content, that will be sent to the service.
	 * @return The response from server.
	 */
	public String sendPOST2Webservice(String urlService, String msg){
		//msg= TextFiles.readTextFileWithServletsAsStream_UTF8("InsertSensor.xml");
	//	System.out.println("---Networking.sendPOST2Webservice : ");
			
		//urlService="http://ispacevm30.researchstudio.at/focus/service"; 
		String charset = "UTF-8";
		OutputStreamWriter writer = null;
		HttpURLConnection con = null;
		String response_txt ="";
		InputStream iss = null;
		
		try {
			//byte [] bray = msg.getBytes("UTF-8");
			//msg = new String(bray);
			
			URL url = new URL(urlService);
			con = (HttpURLConnection)url.openConnection();
			con.setDoOutput(true); //triggers POST
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("accept-charset", charset);
			//con.setRequestProperty("Content-Type", "application/soap+xml");
			con.setRequestProperty("Content-Type", "application/soap+xml;charset=UTF-8");
			
			System.out.println("---Networking.sendPOST2Webservice : WIRD zum SERVICE geschickt: \n\n"+msg);
			
			writer = new OutputStreamWriter(con.getOutputStream(), charset);
			writer.write(msg); //send POST data string		
			//System.out.println("this is sendPOST2Webservice: "+msg);
			writer.flush();
			writer.close();
		//	System.out.println("---Networking.sendPOST2Webservice : nach writer ");
			Boolean error = false;
			BufferedReader br = null;
			
			if(con.getResponseCode()==200){
			//get response from the web service
			//InputStream is = con.getInputStream();
				System.out.println("---Networking.sendPOST2Webservice : 200 \n\n\n");
				//macht aus &%/ Fehlern ein ganz normales ü
				br = new BufferedReader(new InputStreamReader(con.getInputStream(),charset));
			}else{
				System.out.println("---Networking.sendPOST2Webservice : Fehler ");
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(),charset));
				error = true;
			}
			
			String line ="";
			StringBuilder strBuilder = new StringBuilder();
						
			while((line= br.readLine())!=null){
					strBuilder.append(line);
				}
			response_txt = strBuilder.toString();

			if(error){
			System.out.println("---networking: Response_txt mit error!! : "+TextFiles.prettyPrintXMLString2Console(response_txt, "", 2));
			}
			br.close();			
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			//System.out.println("this is finally in sendPOST2Webservice:");
			if(writer!=null){
				try{
					writer.close();
				}catch(IOException ex){
					
				}
			}	
			if(con!=null){
				try{
					con.disconnect();
				}catch(Exception ex){
				}
			}	
		}
		return response_txt;
	}
	
	
	public static String docToString(Document doc) {
		try {
			StringWriter sw = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		} catch (Exception ex) {
			throw new RuntimeException("Error converting to String", ex);
		}
	}

}
