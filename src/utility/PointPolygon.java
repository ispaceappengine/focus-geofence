package utility;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 * Represents points, polygons or bounding boxes. Creates points with x_longitude and y_latitude. 
 * A polygon, which consists of many X and Y coordinates, can be created too.
 * @author slukic
 *
 */
public class PointPolygon {
	
	Double x_longitude;
	Double y_latitude;
	
	public Double getX_longitude() {
		return x_longitude;
	}
	public void setX_longitude(Double x_longitude) {
		this.x_longitude = x_longitude;
	}
	public Double getY_latitude() {
		return y_latitude;
	}
	public void setY_latitude(Double y_latitude) {
		this.y_latitude = y_latitude;
	}
	
	
	/**Contains PointPolygon objects and each of them has a polygon (polygonPath2D). */
	public ArrayList<Path2D> list_ofPolygons = new ArrayList<Path2D>();
	public ArrayList<Path2D> list_ofBoundingBox = new ArrayList<Path2D>();
	public ArrayList<String> list_ofStrConsistingOf5CoordinatesForBoundingBox = new ArrayList<String>();
	public ArrayList<String> list_ofStrCoordinatesForPolygon = new ArrayList<String>();
	public Path2D polygonPath2D = new Path2D.Double();
	public Path2D boundingBoxPath2D = new Path2D.Double();
	public Point2D point2D = new Point2D.Double();
	public ArrayList<String> list_BboxObjectid = new ArrayList<String>();
	
	/**
	 * Creates a polygon that consists of many points or a bounding box.
	 * @param strContainingXYCoordinates A String with many X and Y coordinates, separated by a space/blank.
	 * @param polygonOrbBox If String.equals("bbox") => create a bounding box. Else create a polygon.
	 * @return polygon A PointPolygon object.
	 */
	public PointPolygon createPolygonBbox(String strContainingXYCoordinates, String polygonOrBbox){
		
	    String []stray = strContainingXYCoordinates.split(" ");
	    Path2D polygonBuilder = new Path2D.Double();
	    
	    // 4 elemente = 2 Punkte. 0=X,1=Y und dann 2,3; 2<4-1
	    for (int j = 0; j < stray.length - 1; j = j + 2) { 
	    	//start the polygon with 0=x,1=y Position
			if (j == 0) {
				polygonBuilder.moveTo(Double.parseDouble(stray[j]),	Double.parseDouble(stray[j+1]));
			}
			polygonBuilder.lineTo(Double.parseDouble(stray[j]),	Double.parseDouble(stray[j + 1]));
	    }
	    PointPolygon polygon = new PointPolygon();
	    if(polygonOrBbox.equals("bbox")){
	    	polygon.boundingBoxPath2D = polygonBuilder;
	    	this.list_ofBoundingBox.add(polygonBuilder);
	    }else{
	    	polygon.polygonPath2D = polygonBuilder;
	    	this.list_ofPolygons.add(polygonBuilder);
	    }
	    return polygon;	    
	}
	
	/**
	 * Creates a bounding box. 
	 * @return A bounding box as a PointPolygon object.
	 */
	public PointPolygon createBoundingBox(){
		PointPolygon poly_boundingBox = new PointPolygon();

		
		return poly_boundingBox;
	}	
}