package utility;

import java.util.Arrays;

import json.GeoJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CreateJson {
	
	/**
	 * Creates the GeoJson.
	 * @param geo Object containing all needed information to create a GeoJson.
	 * @param isPointInPolygonUnloading Tells if a point is located within a polygon.
	 * @return GeoJson string.
	 */
	public String createJson(GeoJson geo, Boolean pointIsInPolygon){
		String jsonText ="";
		
		Double x_long = geo.x_long;
		Double y_lat = geo.y_lat;
		Double[] coordinates = new Double[]{x_long, y_lat } ;
		
		Double z_alt;
		
		if(geo.z_alt!=null){
			z_alt= geo.z_alt;
			coordinates = new Double[]{x_long, y_lat, z_alt } ;
		}
				
		Gson gson = new Gson();
		
		JsonObject jobj = new JsonObject();
		jobj.addProperty("type", "Feature");
		
		JsonObject jobjProperties = new JsonObject();
		jobjProperties.addProperty("samplingFOIName", geo.samplingFOIName);
		jobjProperties.addProperty("samplingFOIIdentifier", geo.samplingFOIIdentifier);
		jobjProperties.addProperty("observationPhenomenonTime", geo.observationPhenomenonTime);
		jobjProperties.addProperty("procedure", geo.procedure);
		
		if(pointIsInPolygon){
			geo.insidePolygon = 1;
		}
		
		jobjProperties.addProperty("insidePolygon", geo.insidePolygon);				
		jobj.add("properties", jobjProperties);
				
		JsonObject jobjGeometryContent = new JsonObject();
		jobjGeometryContent.addProperty("type", "Point");
		
		
		
		JsonObject jobjGeometry = new JsonObject();
		//jobjGeometry.add("geometry",jobjGeometryContent);
		JsonObject  jobjCoordinates = new JsonObject();
		
		
		
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

	public String createJsonHardcoded(){
		String jsonText ="";
		String ObservationPhenomenonTime ="2016-02-29T16:26:31Z";
		Double x_long =13.0387;
		Double y_lat =47.8232;
		Double z_alt=423.40;
		
		Gson gson = new Gson();
		
		JsonObject jobj = new JsonObject();
		jobj.addProperty("type", "Feature");
		
		JsonObject jobjProperties = new JsonObject();
		jobjProperties.addProperty("samplingFOIName", "LkwAlias_881632654508");
		jobjProperties.addProperty("samplingFOIIdentifier", "LkwID_881632654508");
		jobjProperties.addProperty("observationPhenomenonTime", ObservationPhenomenonTime);
		
		jobjProperties.addProperty("procedure", "geo.procedure");
		jobjProperties.addProperty("insidePolygon", 1);
		
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
