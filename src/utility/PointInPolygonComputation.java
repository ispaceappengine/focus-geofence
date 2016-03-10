package utility;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import utility.PointPolygon;

/**
 * Provides logic to test if a point is within a polygon.
 * @author slukic *
 */
public class PointInPolygonComputation {
	
	
	/**
	 * Checks if a point is within a polygon.
	 * @param point The point with X and Y coordinates. The point is tested if it is within a polygon.
	 * @param polygonBbox The polygon, consisting of many points, that is used to test if a specific point lies within the polygon.
	 * @return true: if point is in polygon; false: else.
	 */
	public Boolean pointIsInPolygon(PointPolygon point, PointPolygon polygonBbox ){
		Boolean pointInBoundingBox  = false;
		Boolean pointIsInPolygon = false;
		String objectid ="";		

		System.out.println("=====");
		System.out.println("=====");
			for(int e=0; e <polygonBbox.list_ofBoundingBox.size(); e++){
				pointInBoundingBox = polygonBbox.list_ofBoundingBox.get(e).contains(point.point2D);
				System.out.println("box:"+polygonBbox.list_ofStrConsistingOf5CoordinatesForBoundingBox.get(e));
				System.out.println("point:"+point.getX_longitude()+", "+point.getY_latitude()+" ist in box:"+pointInBoundingBox);
				
				if(pointInBoundingBox){
					objectid = polygonBbox.list_BboxObjectid.get(e);				
				
				pointIsInPolygon = polygonBbox.list_ofPolygons.get(e).contains(point.point2D);
				System.out.println("polygon:"+polygonBbox.list_ofStrCoordinatesForPolygon.get(e));
				System.out.println("point:"+point.getX_longitude()+", "+point.getY_latitude()+" ist in polygon:"+pointIsInPolygon);
				}
				System.out.println("####");
				
			}
		
		
		/*
		Rectangle2D rectangle2D = polygonBbox.polygonPath2D.getBounds2D();
		 pointInBoundingBox =  rectangle2D.contains(point.point2D);
		Boolean pointInBoundingBoxFakeTest = rectangle2D.contains(point.x_longitude,point.y_latitude);
		
		if(pointInBoundingBoxFakeTest){
			System.out.println("Fake point ist in BoundingBox!");
		}
		
		pointIsInPolygon = false;
		pointIsInPolygon = polygonBbox.polygonPath2D.contains(point.point2D);

		//polygon.polygonPath.contains( 13.036801240999978, 47.82366281100002)
		
		LoadOnStartAppConfiguration.fakeTestPointInPolygon = polygonBbox.polygonPath2D.contains( point.x_longitude, point.y_latitude);
		System.out.println("##### FAKE X und FAKE Y: point in polygon: "+ LoadOnStartAppConfiguration.fakeTestPointInPolygon);
				
				*/
		return pointIsInPolygon;
	}	
	
	/**
	 * Tests point in bounding box and point in polygon. Point has testdata.
	 * @param point
	 * @param polygonBbox
	 */
	public void pointInPolygonTestData(PointPolygon point, PointPolygon polygonBbox ){
		Boolean pointInBoundingBox  = false;
		Boolean pointIsInPolygon = false;
		String objectid ="";
		point.setX_longitude(13.039591312408447); 
		point.setY_latitude(47.824146314246214);

		System.out.println("=====");
		System.out.println("=====");
			for(int e=0;e<polygonBbox.list_ofBoundingBox.size();e++){
				pointInBoundingBox = polygonBbox.list_ofBoundingBox.get(e).contains(point.getX_longitude(),point.getY_latitude());
				System.out.println("box:"+polygonBbox.list_ofStrConsistingOf5CoordinatesForBoundingBox.get(e));
				System.out.println("point:"+point.getX_longitude()+", "+point.getY_latitude()+" ist in box:"+pointInBoundingBox);
				
				if(pointInBoundingBox){
					objectid = polygonBbox.list_BboxObjectid.get(e);
								
					pointIsInPolygon = polygonBbox.list_ofPolygons.get(e).contains(point.getX_longitude(), point.getY_latitude());
					System.out.println("polygon:"+polygonBbox.list_ofStrCoordinatesForPolygon.get(e));
					System.out.println("point:"+point.getX_longitude()+", "+point.getY_latitude()+" ist in polygon:"+pointIsInPolygon);
				}
				System.out.println("####");
				
			}
	}
	
	/**
	 * Gets a polygon/bbox from a WFS.	
	 * @param polygonOrBbox If .equals("bbox") => create bounding box; else create polygon.
	 * @param pp PointPolygon object.
	 * @param url_geofenceWfs URL to the server where from to get the data.
	 * @return pp PointPolygon object.
	 */
	public PointPolygon getPolygonBboxFromWFS(String polygonOrBbox, PointPolygon pp,  String url_geofenceWfs){
		Networking net = new Networking();		
		String xml_WfsGeofenceResponse = net.sendGET2getGeofenceFromWFS(url_geofenceWfs);	

		if(polygonOrBbox.equals("bbox")){
			ArrayList<ArrayList<String>> helper = new ParserXmlJson().extractCoordsFromWfsXml(xml_WfsGeofenceResponse);
			pp.list_ofStrConsistingOf5CoordinatesForBoundingBox = helper.get(0);
			pp.list_BboxObjectid = helper.get(1);
			System.out.println("pp.list_ofBoundingBox.size(): "+pp.list_ofBoundingBox.size());
			for(int i=0;i<pp.list_ofStrConsistingOf5CoordinatesForBoundingBox.size();i++){
				pp.createPolygonBbox(pp.list_ofStrConsistingOf5CoordinatesForBoundingBox.get(i), "bbox");
				}
			System.out.println("pp.list_ofBoundingBox.size(): "+pp.list_ofBoundingBox.size());
		return pp;		
		}
		else
		{
			ArrayList<ArrayList<String>> helper = new ParserXmlJson().extractCoordsFromWfsXml(xml_WfsGeofenceResponse);
			pp.list_ofStrCoordinatesForPolygon = helper.get(0);
			System.out.println("pp.list_ofPolygons.size(): "+pp.list_ofPolygons.size());
				for(int i=0;i<pp.list_ofStrCoordinatesForPolygon.size();i++){
					pp.createPolygonBbox(pp.list_ofStrCoordinatesForPolygon.get(i), "polygon");
				}
				System.out.println("pp.list_ofPolygons.size(): "+pp.list_ofPolygons.size());
			return pp;
		}
	}
	/**
	 * Extracts the ArbeitsbereichXmlTag from the URL. This is needed because the namespace of the xml elements is the ArbeitsbereichXmlTag.
	 * Without the namespace, there is no working access to the xml elements
	 * @param polygonOrBbox Use "bbox" as argument if bounding box desired.
	 * @return String Contains the arbeitsbereichXmlTag, that is used as namespace to parse the WFS polygon xml.
	 */
	public String getArbeitsbereichXmlTagFromWfs(String polygonOrBbox){
		String arbeitsbereichXmlTag = "";
		String url2Wfs ="";
		try{
			if(polygonOrBbox.equals("bbox")){
				url2Wfs =LoadOnStartAppConfiguration.urlWfsBoundingBox.toLowerCase();//wegen separator typeName/typename
			}else{
				url2Wfs =LoadOnStartAppConfiguration.urlWfsPolygons.toLowerCase();//wegen separator typeName/typename
			}
			String separator = "typename";
			//e.g. http://ispacevm22.researchstudio.at/geoserver/geofence_sbg/wfs?service=WFS&version=1.0.0&request=GetFeature&typename=geofence_sbg:geofence_sbg_151215_bbox
			String [] stray = url2Wfs.split(separator);
			String typenamePlusRest = stray[1]; //e.g.=geofence_sbg:geofence_sbg_151215_bbox
			String [] stray2 = typenamePlusRest.substring(1).split(":");
			arbeitsbereichXmlTag = stray2[0];
			System.out.println("PointInPolygonComputation.getArbeitsbereichXmlTagFromWfs :"+arbeitsbereichXmlTag);
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error while parsing the URL for arbeitsbereichXmlTag");
		}
		return arbeitsbereichXmlTag;
	}
}
