package utility;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CreateJson {
	
	public String createJson(){
		String jsonText ="";
		String ObservationPhenomenonTime ="2015-10-29T16:26:31Z";
		Double x_long =13.038782998184578;
		Double y_lat =47.82329099489709;
		Double z_alt=423.40;
		
		Gson gson = new Gson();
		
		JsonObject jobj = new JsonObject();
		jobj.addProperty("type", "Feature");
		
		JsonObject jobjProperties = new JsonObject();
		jobjProperties.addProperty("SamplingFOIName", "LkwAlias_881632654508");
		jobjProperties.addProperty("SamplingFOIIdentifier", "LkwID_881632654508");
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

}
