package json;

import java.awt.List;
import java.util.ArrayList;

/**Contains all elements needed for a GeoJson. Some elements have been assigned default values. Those default values will be
 * changed if some specific condition is met. e.g. type = "Feature", insidePolygon =0
 * 
 * @author slukic
 *
 */
public class GeoJson {
	
	public String type = "Feature";
	public String observationPhenomenonTime;
	public String samplingFOIName;
	public String samplingFOIIdentifier;
	public String procedure;
	/** Value = 1, if point in polygon*/
	public int insidePolygon =0;
	
	public String geometryType ="Point";
	public Double y_lat;
	public Double x_long;
	/**If there is a z value in the IO, the value will be assigned and z will be displayed in the geojson. Otherwise only x and y will be displayed. */
	public Double z_alt;
	
	public String crsType = "name";
	public String crsPropertiesName = "EPSG:4326";
	
	public ArrayList <GeoJson>list_geoJson = new ArrayList<GeoJson>();

}
