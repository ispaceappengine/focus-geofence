package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Used to read, write files, directories and work with strings!
 * <p><IMG SRC="../doc-files/zr_sos_tstp.gif">
 * @author slukic
 */
public class TextFiles {
	public TextFiles() {}
	
	/**Path to the classLoader directory. e.g. C:/tomcat/webapps/tstp2sos/WEB-INF/classes/ */
	public static String classLoaderPath ="";
	/**
	 * Used to save strings to text files with UTF-8.
	 * @param text String that will be saved to a text file.
	 * @param filePathName Provide the specific existing directory and file with the extension. e.g. resources/abc/file.json
	 * The default root directory is already given by setClassLoaderPath. e.g. C:/tomcat/webapps/tstp2sos/WEB-INF/classes/
	 * <br>
	 * The complete final path: e.g. C:/tomcat/webapps/tstp2sos/WEB-INF/classes/resources/abc/file.json 
	 */
	public static void writeString2FileUTF8(String text, String filePathName){			
		
		File path = new File(setClassLoaderPath()+filePathName);
		System.out.println("writeString2FileUTF8: "+path.getAbsolutePath());
		//File path = new File(classLoaderPath+File.separator+filePathName);
				
		BufferedWriter writer = null;
		print("classloader"+classLoaderPath+"ENDE");
		print(path.getPath());
	
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
		    writer.write(text);
		} catch (IOException ex) {
		
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}			 		
	}
	/**
	 * Used to save strings to text files with ISO-8859-1.
	 * @param text String that will be saved to a text file.
	 * @param filePathName 
	 */
	public static void writeString2FileISO_8859_1(String text, String filePathName){			
		
		File path = new File("C:\\java_workspace\\sensoriqs\\src\\resources\\"+filePathName);
		//File path = new File(classLoaderPath+File.separator+filePathName);
				
		BufferedWriter writer = null;
		print("classloader"+classLoaderPath+"ENDE");
		print(path.getPath());
	
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "ISO-8859-1"));
		    writer.write(text);
		} catch (IOException ex) {
		
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}			 		
	}
	
	/**
	 * Transforms a Document object (containing XML) to a string. No pretty formatting.
	 * @param doc XML Document object. 
	 * @return The XML as a string.
	 */
	public static String docXML2String(Document doc){
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		String output = "";
		try {
			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		} catch (TransformerException e){
			e.printStackTrace();
		}		
		return output;
	}	
	
	/**
	 * Used to transform a string XML into a Document object.
	 * @param xml The XML string that will be transformed into a Document object.
	 * @return A Document object (XML).
	 * @throws Exception
	 */
	public static Document xmlString2XmlDocument(String xml) throws Exception
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}
	
	/**
	 * Used in jsp/servlets to read a textfile from "src/resources/pathTofile". The argument has to be the path to the file starting from within resources/ directory.
	 * @param fileName Provide file name, that should be read. Files are stored in /src/resources/. Use e.g. file1.xml or registered_zrid/default.json to
	 * get /src/resources/file1.xml or /src/resources/registered_zrid/default.json.
	 * @return Text file content as a string.
	 */
	public static String readTextFileWithServletsAsStream_UTF8(String filePathName){
		String text ="";
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/"+filePathName);
		if(input == null){			
			System.out.println("Inputstream null. File konnte wahrscheinlich nicht gefunden werden!");			
		}else{			
			InputStreamReader isr = null;
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)input, "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String sCurrentLine;
				while ((sCurrentLine = reader.readLine()) != null) {
					sb.append(sCurrentLine);								
				}		
				text= sb.toString();		
			//	byte [] bray = text.getBytes("UTF-8");
			//	text = new String(bray,"UTF-8");
				
			} catch (Exception e1) { 
				e1.printStackTrace();
			}			
		}				
		return text;
	}
	
	public static String toString(Document doc) {
	    try {
	        StringWriter sw = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

	        transformer.transform(new DOMSource(doc), new StreamResult(sw));
	        return sw.toString();
	    } catch (Exception ex) {
	        throw new RuntimeException("Error converting to String", ex);
	    }
	}
	/**
	 * Transforms an xml doc object into a string. The returned string is also prettyprinted/formated. 
	 * @param document Document object containing the XML.
	 * @param indent The amount of moves to the right.
	 * @return Returns a prettyformated string.
	 * @throws TransformerException
	 */
	public static String xmlDocument2StringWithPrettyPrint(Document document, int indent){
		String xml="";
		try{
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    if (indent > 0) {
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(indent));
	    }

		StringWriter stringWriter = new StringWriter();		
		Source source = new DOMSource(document);
		Result streamResult = new StreamResult(stringWriter);		
				
		transformer.transform(source, streamResult);
		
		xml = stringWriter.toString();
		//System.out.println(xml);
		
		
		}catch(Exception ex){
			
		}
		return xml;
	}	
	
	/**
	 * Transforms an xml string into a prettyprinted xml string. The returned string is also prettyprinted/formated. 
	 * @param xml org The XML string.
	 * @param indent The size of indent to the right side for new subtags in the xml. e.g. 2
	 * @return Returns a prettyformated string.
	 * @throws TransformerException
	 */
	public static String prettyPrintXmlString(String xml_org, int indent) throws TransformerException{
		String xml="";
		
		try{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		//dbFactory.setNamespaceAware(true);
		DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();
		
		Document doc = TextFiles.xmlString2XmlDocument(xml_org);
		//dbuilder.parse erwartet keinen STRING, sondern IS
		InputSource is = new InputSource(new StringReader(xml_org));
	    Document document = dbuilder.parse(is);
		
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    if (indent > 0) {
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(indent));
	    }

		StringWriter stringWriter = new StringWriter();		
		Source source = new DOMSource(document);
		Result streamResult = new StreamResult(stringWriter);		
				
		transformer.transform(source, streamResult);
		
		xml = stringWriter.toString();
		//System.out.println(xml);		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return xml;
	}	
	/**
	 * Used for a simple System.out.println().
	 * @param text
	 */
	public static void print(String text){
		System.out.println(text);
	}
	
	/**
	 * Used to prettyprint a Document XML object and to display/save it. The outputStream defines what will happen.
	 * e.g. System.out, Writer, File, ... Pay attention to the encoding of the XML document! e.g. UTF-8 might
	 * misrepresent °C to Â°C. Try ISO-8859-1.
	 * @param document
	 * @param outputStream Defines what you want to do with the Document object. e.g. System.out to console or write into a file.
	 * @param indent
	 * @throws Exception
	 */	
	public static void xmlDocument2SomeSpecificOutputPrettyPrinted(Document document, StreamResult outputStream, int indent) throws Exception {
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//UTF-8ISO-8859-1
	    if (indent > 0) {
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(indent));
	    }
	    //Result result = new StreamResult(outputStream);
	    Result result = outputStream;
	    Source source = new DOMSource(document);
	    transformer.transform(source, result);	    
	}	
	
	/**
	 * Used to prettyPrint a long xml string line.
	 * @param xml
	 * @param encoding If you do not provide a specific encoding and send "", the encoding will be default UTF-8.
	 * @param indent The size of indent to the right side for new subtags in the xml. e.g. 2
	 * @return Returns a prettyPrinted XML string.
	 */
	public static String prettyPrintXMLString2Console(String xml, String encoding, int indent) {
		String xmlPretty ="";
		if(encoding.isEmpty())
			encoding = "UTF-8";
		try {
		    Document document = DocumentBuilderFactory.newInstance()
		            .newDocumentBuilder()
		            .parse(new InputSource(new ByteArrayInputStream(xml.getBytes(encoding))));
		 
		    XPath xPath = XPathFactory.newInstance().newXPath();
		    NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
		                                                  document,
		                                                  XPathConstants.NODESET);
		 
		    for (int i = 0; i < nodeList.getLength(); ++i) {
		        Node node = nodeList.item(i);
		        node.getParentNode().removeChild(node);
		    }
		 
		    Transformer transformer = TransformerFactory.newInstance().newTransformer();
		    transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
		    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(indent));
		 
		    StringWriter stringWriter = new StringWriter();
		    StreamResult streamResult = new StreamResult(stringWriter);
		 
		    transformer.transform(new DOMSource(document), streamResult);
		 
		    xmlPretty= stringWriter.toString();
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		return xmlPretty;
	}
	/**
	 * Used to get all registered zrid/procedures from the directory resources/registered_zrid. If InsertSensor.xml was successfully accepted by the Sensor Observation Service (SOS), 
	 * a "name_ort_zrid.json" is created for that specific zrid. Be aware, that the procedure could be deleted in the SOS, but would still remain as a "name_ort_zrid.json" file.
	 * There is no synchronization between the SOS backend system and the web application tstp2sos.
	 * @return A List with the names of all registered zrids.  
	 */
	public static ArrayList<String> getRegisteredZrids(String _path){
		
		String path = _path+"/resources/registered_zrid/";
		return getFileNamesFromdirectory(path);
	}
	
	/**
	 * Used to get all active InsertObservation services. Done by checking the resources/insert_observation_services directory.
	 * If InsertObservation is successfully executed, a "name_ort_zrid.json" file, will be created in the directory.
	 * @return A List with the names of all active InsertObservation services (json files).
	 */
	public static ArrayList<String> getActiveInsertObservationServices(){

		String path = classLoaderPath+"resources/insert_observation_services/";
		return getFileNamesFromdirectory(path);
	}
	
	/**
	 * Used to get all filenames in a directory. 
	 * @param path Path of the directory. Argument e.g. C:/tomcat/webapps/tstp2sos/WEB-INF/classes/resources/
	 * @return List of filenames.
	 */
	public static ArrayList<String> getFileNamesFromdirectory(String path){
		ArrayList<String> list_ofFileNames = new ArrayList<String>();
		File folder = new File(path);//registered_zrid
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  list_ofFileNames.add(listOfFiles[i].getName());
		     //   System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		      //  System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
		    return list_ofFileNames;
	}
	
	/**
	 * Used to delete a specific file.
	 * @param _path Filename that will be deleted
	 */
	public static void deleteFile(String _path){
		File file = new File(_path);
		file.delete();
		System.out.println(_path+" : file deleted!");
	}
	
	public static void deleteInsertObservationService(String fileNameWithExtension){
		String path = classLoaderPath+"resources/insert_observation_services/"+fileNameWithExtension;
		deleteFile(path);
	}
	
	/**
	 * Sets the path to the classLoader directory. 
	 * @return String with the path to the classLoader directory. e.g. C:/tomcat/webapps/tstp2sos/WEB-INF/classes/
	 */
	public static String setClassLoaderPath(){
		classLoaderPath = TextFiles.class.getClassLoader().getResource("").getPath();
		return classLoaderPath; 
	}
	/**
	 * Reads just one simple value (from key) from a json and returns the value. Uses a custom method to open and read file.
	 * @param path2File Provide file name, that should be read. Files are stored in /src/resources/. Use e.g. file1.xml or registered_zrid/default.json to
	 * get /src/resources/file1.xml or /src/resources/registered_zrid/default.json.
	 * @param key The key where you want to read the value from.
	 * @return One string value.
	 */
	public static String read1SimpleJsonValue(String path2File, String key){
		String jsonText = readTextFileWithServletsAsStream_UTF8(path2File);
		Gson gson = new Gson();
		JsonObject jobj = gson.fromJson(jsonText, JsonObject.class);
	//	System.out.println(jsonText);
	//	System.out.println(jobj.get(key).getAsString());
		return jobj.get(key).getAsString();
	}
	
/**
 * Reads a json file. UTF-8.
 * @return
 */
	public static String readJson(String filePathName){
		String jsonText = readTextFileWithServletsAsStream_UTF8(filePathName);
		
		Gson gson = new Gson();
		JsonObject jobj = gson.fromJson(jsonText, JsonObject.class);
		//jobj.addProperty("test", "200"); 
		//jsonText = jobj.toString();
		//System.out.println("readJson: "+jsonText);
		//TextFiles.writeString2FileUTF8(jsonText, "resources/"+filePathName);
		return jsonText;
	}
	
	/**
	 * Updates one specific value in a json.
	 * @param filePathName
	 * @param key
	 * @param value
	 */
	public static void writeUpdate1KeyValueInJson(String filePathName, String key, String value){
		String jsonText = readTextFileWithServletsAsStream_UTF8(filePathName);
		//the configfile must have at least the string: {} or else there will be an Exception
		if(jsonText.isEmpty()){
			jsonText = "{\n}";
			System.out.println("jsonText was empty");
		}

		Gson gson = new Gson();
		JsonObject jobj = gson.fromJson(jsonText, JsonObject.class);
		jobj.addProperty(key, value); 
		jsonText = jobj.toString();
		//System.out.println("readJson: "+jsonText);
		TextFiles.writeString2FileUTF8(jsonText, "resources/"+filePathName);	
		System.out.println(jsonText);
	}
	
	/**
	 * Reads a json file as a stream and returns the content as a JsonObject.
	 * Used in jsp/servlets to read a textfile from "src/resources/pathTofile". 
	 * The argument has to be the path to the file starting from within resources/ directory
	 * @param filePathName Path to file or filename that should be read.
	 * @return A JsonObject object containing the json.
	 */
	public static JsonObject readJsonfileAndReturnAsJsonObject(String filePathName){
		Gson gson = new Gson();
		
		String jsonText = TextFiles.readTextFileWithServletsAsStream_UTF8(filePathName);
		JsonObject jobj = gson.fromJson(jsonText, JsonObject.class);
		
		return jobj;
	}
	
	
	/**
	 * Writes values to a configuration (json) files.
	 * @param filePathName Path to file or filename that should be read and updated.
	 * @param map_ParameterFromForm Contains all elements from the form. New values will be added to the configuration json.
	 */
	public static void writeJson2ConfigFile(String filePathName, Map<String, String[]> map_ParameterFromForm){
		
		String jsonText ="";	
		String prettyJsonString ="";

		Gson gson = new Gson();
		JsonObject jobj = new JsonObject();
		//JsonObject jobj = gson.fromJson(jsonText, JsonObject.class);
		try{
			jobj = TextFiles.readJsonfileAndReturnAsJsonObject(filePathName);
		}catch(Exception ex){
			System.out.println("TextFiles.writeJson2ConfigFile : probably no configuration.json file found!");
			ex.printStackTrace();
		}
		try{
			for (Entry<String, String[]> entry : map_ParameterFromForm.entrySet())
		    {
		        System.out.println("TextFiles.writeJson2ConfigFile :"+entry.getKey() + "  ---  " + entry.getValue()[0]);
		        jobj.addProperty(entry.getKey(), entry.getValue()[0]);
		    }			
			jsonText = jobj.toString();		
			
			
			Gson gsonPretty = new GsonBuilder()
							.setPrettyPrinting()
							.disableHtmlEscaping() //aktivieren, ansonsten wird ein "=" zu einem "\u003d"
							.create(); 
		//	System.out.println(gsonPretty.toJson(jobj));
			
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(jsonText);
			prettyJsonString = gsonPretty.toJson(je);
			System.out.println("TextFiles.writeJson2ConfigFile prettyprint:\n"+prettyJsonString);	
	
		}catch(Exception ex){
			ex.printStackTrace();
		}
		TextFiles.writeString2FileUTF8(prettyJsonString, "resources/"+filePathName);	    
	}
}