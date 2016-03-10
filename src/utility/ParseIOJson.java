package utility;

import java.util.ArrayList;
import json.InsertObservationClasses.InsertObservationJson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ParseIOJson {
	
	public InsertObservationJson parseIoJson(String jsonText){
		InsertObservationJson io = new InsertObservationJson();
		Gson gson = new Gson();
		JsonObject jobj = gson.fromJson(jsonText, JsonObject.class);
		io = gson.fromJson(jsonText, InsertObservationJson.class);
		
		System.out.println("ParseIO: "+io.type.toString()+ gson.toJson(io));
		
	/*
		String description_Value = jobj.get("content").getAsJsonObject().get("description").getAsString();
		JsonArray jarr = jobj.get("content").getAsJsonObject().get("keywords").getAsJsonArray();
				
		ArrayList<String> list_keywords = new ArrayList<String>();
		list_keywords = gson.fromJson(jarr.toString(), ArrayList.class);
		
		//ObservedProperty [] obser =new Gson().fromJson(jobj.get("content").getAsJsonObject().get("observedProperties"), ObservedProperty[].class);
		String xyz= jobj.get("content").getAsJsonObject().get("observedProperties").getAsJsonArray().get(0).getAsJsonObject().get("observedPropertyNameHref").getAsString();
		
		//ObservedProperty ob =new Gson().fromJson(jobj.get("content").getAsJsonObject().get("observedProperties").getAsJsonArray().get(1), ObservedProperty.class);
		*/
		return io;
	}

}
