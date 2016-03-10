package utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class GetEmail {
	
	public String getEmail(){
		String json ="";
		 Properties props = new Properties();
	        props.setProperty("mail.store.protocol", "imaps");
	        try {
	        	String fromAsFoi ="";
	            Session session = Session.getInstance(props, null);
	            Store store = session.getStore();
	            store.connect("imap.researchstudio.at", "iridium", "geoadmin16#");
	            Folder inbox = store.getFolder("INBOX");
	            inbox.open(Folder.READ_ONLY);
	            Message msg = inbox.getMessage(inbox.getMessageCount());
	            Address[] in = msg.getFrom();
	            for (Address address : in) {
	                System.out.println("FROM:" + address.toString());
	                fromAsFoi = address.toString();
	            }
	            
	            System.out.println("Total messages = " + inbox.getMessageCount());
	               System.out.println("New messages = " + inbox.getNewMessageCount());
	               System.out.println("unread messages = " + inbox.getUnreadMessageCount());
	               System.out.println("deleted messages = " + inbox.getDeletedMessageCount());
	               
	               Object content = msg.getContent();  
	               if (content instanceof String)  
	               {  
	                   String emailBody = (String)content;  
	                   System.out.println("emailBody: "+emailBody);
	                   json= new GetEmail().emailStringSplitter(emailBody, fromAsFoi, msg.getSentDate());	                
	               }  
	               else if (content instanceof Multipart)  
	               {  
	                   Multipart mp = (Multipart)content;  	                
	               }  
	               //new Starter().dateTimeFormatter("Fri Feb 12 15:03:57 CET 2016");
	             //  new Starter().dateTimeFormatter(msg.getSentDate().toString());
//	            Multipart mp = (Multipart) msg.getContent();
//	            BodyPart bp = mp.getBodyPart(0);
	            System.out.println("SENT DATE:" + msg.getSentDate());
	            System.out.println("SUBJECT:" + msg.getSubject());
	         //   System.out.println("CONTENT:" + bp.getContent());
	        } catch (Exception mex) {
	            mex.printStackTrace();
	        }
	        return json;
	}

	public String createJson(EmailContent emailContent){
		String jsonText ="";
		String ObservationPhenomenonTime =emailContent.time;  //"2015-10-29T16:26:31Z";
		Double x_long =emailContent.x_lon; //13.038782998184578;
		Double y_lat =emailContent.y_lat; // 47.82329099489709;
		Double z_alt=emailContent.z_alt; //423.40;
		
		Gson gson = new Gson();
		
		JsonObject jobj = new JsonObject();
		jobj.addProperty("type", "Feature");
		
		JsonObject jobjProperties = new JsonObject();
		jobjProperties.addProperty("SamplingFOIName", emailContent.foi); //"LkwAlias_881632654508"
		jobjProperties.addProperty("SamplingFOIIdentifier", emailContent.foi);
		jobjProperties.addProperty("ObservationPhenomenonTime", ObservationPhenomenonTime);
		
		jobj.add("properties", jobjProperties);
		
		JsonObject jobjGeometry = new JsonObject();
		JsonObject jobjGeometryContent = new JsonObject();
		jobjGeometryContent.addProperty("type", "Point");
		//jobjGeometry.add("geometry",jobjGeometryContent);
		JsonObject  jobjCoordinates = new JsonObject();
		

		Double[] coordinates = new Double[]{x_long, y_lat ,z_alt } ;
		
		jobjGeometryContent.add("coordinates", gson.toJsonTree(coordinates));
		jobj.add("geometry", jobjGeometryContent);		
		
		JsonObject jobjCrs = new JsonObject();
		JsonObject jobjCrsContent = new JsonObject();
		jobjCrs.add("crs", jobjCrsContent);
		jobjCrsContent.addProperty("type", "name");
		JsonObject jobjCrsContentProperties = new JsonObject();
		jobjCrsContentProperties.addProperty("name", "EPSG:4326");
		jobjCrsContent.add("properties", jobjCrsContentProperties);
		
		//jobjGeometryContent.add("crs", jobjCrs);
		jobjGeometryContent.add("crs", jobjCrsContent);
		jsonText= jobj.toString();			
		System.out.println(jobj.toString());
		
		Gson gsonPretty = new GsonBuilder()
							.setPrettyPrinting()
							.disableHtmlEscaping()
							.create();
		
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonText);
		String prettyJsonString = gsonPretty.toJson(je);
		System.out.println("\nTextFiles.writeJson2ConfigFile prettyJsonString: "+prettyJsonString);	
		
		return jsonText;
	}
	public String emailStringSplitter(String emailString,String fromAsFOI, Date date){
		// emailString = I am here Lat+47.264850 Lon+15.322578 Alt+1329ft GPS Sats seen 07 http://map.iridium.com/m?lat=47.264850&lon=15.322578 Sent via Iridium GO!
		//+ is a special character which denotes a quantifier meaning one or more occurrences.
		// replace + with other character
		
		emailString = emailString.replaceAll("\\+", "#@#");
		System.out.println("neuer Text: "+ emailString);
		String [] stray = emailString.split(" ");
		EmailContent email = new EmailContent();
		for(int i = 0; i<stray.length; i++){
			System.out.println("stray an der Stelle "+i+": "+stray[i]);
			if(stray[i].startsWith("Lat#@#")){
				String [] value = stray[i].split("#@#");
				for(String s:value)
					System.out.println(s);
				System.out.println("val"+value);
				email.y_lat= Double.parseDouble(stray[i].split("#@#")[1]);
			}
			if(stray[i].startsWith("Lon#@#")){
				email.x_lon= Double.parseDouble(stray[i].split("#@#")[1]);
			}
			if(stray[i].startsWith("Alt#@#")){
				email.z_alt= Double.parseDouble(stray[i].split("#@#")[1].split("ft")[0]);
				email.z_alt = email.z_alt *0.3048;
				
				email.z_alt = (double)Math.round(email.z_alt);				
			}
						
			email.time = dateTimeFormatter(date);
			email.foi = fromAsFOI;
		}		
		
		return createJson(email);
	}
	
	public String dateTimeFormatter(Date ogDate){
		String dateFormatted ="";
		//og_date ="Fri Feb 12 15:03:57 CET 2016";
				
	//	Date yourDate = new Date();

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		String date = DATE_FORMAT.format(ogDate);
		
		System.out.println("zeit: "+date);
		return date;
	}
}
