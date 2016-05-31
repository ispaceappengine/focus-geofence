package utility;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import utility.PointPolygon;

/**
 * Provides logic to test if a point is within a polygon.
 * @author slukic *
 */
public class PointInPolygonComputation {
	
	Logger logger = (Logger) LoggerFactory.getLogger(getClass().getName()+".class");
	
	
	/**
	 * Checks if a point is within a polygon.
	 * @param point The point with X and Y coordinates. The point is tested if it is within a polygon.
	 * @param polygonBbox The polygon, consisting of many points, that is used to test if a specific point lies within the polygon.
	 * @return true: if point is in polygon; false: else.
	 */
	public Boolean pointIsInPolygon(PointPolygon point, PointPolygon polygonBbox, ArrayList<String> _list_objectId, String serviceIdentifier ){
		ArrayList<String> list_objectId = _list_objectId;
		Boolean isPointInBoundingBox  = false;
		Boolean pointIsInPolygon = false;
		String objectid ="";		

		logger.debug("====size"+list_objectId);		
		
		if(serviceIdentifier.equals("within")){
			for(int e=0; e <polygonBbox.list_ofBoundingBoxWithin.size(); e++){
				isPointInBoundingBox = polygonBbox.list_ofBoundingBoxWithin.get(e).contains(point.point2D);

//						logger.debug("box:"+polygonBbox.list_ofStrConsistingOf5CoordinatesForBoundingBoxWithin.get(e));
//						logger.debug("point:"+point.getX_longitude()+", "+point.getY_latitude()+" ist in box:"+isPointInBoundingBox);
				
				if(isPointInBoundingBox){	
					
					objectid = list_objectId.get(e);				
				
					System.out.println("SIZE of polygonBbox.list_ofPolygonsWithin: " + polygonBbox.list_ofPolygonsWithin.size());
				pointIsInPolygon = polygonBbox.list_ofPolygonsWithin.get(e).contains(point.point2D);
				System.out.println("##########################################"+pointIsInPolygon+"################################" );
				}						
			}
		}else{
			for(int e=0; e <polygonBbox.list_ofBoundingBox.size(); e++){
				
				isPointInBoundingBox = polygonBbox.list_ofBoundingBox.get(e).contains(point.point2D);
				System.out.println("polygon: "+ isPointInBoundingBox +"--" + polygonBbox.list_ofStrConsistingOf5CoordinatesForBoundingBox.get(e)+"------:"+point.point2D.getY()+", "+point.point2D.getX());
//						logger.debug("box:"+polygonBbox.list_ofStrConsistingOf5CoordinatesForBoundingBox.get(e));
//						logger.debug("point:"+point.getX_longitude()+", "+point.getY_latitude()+" ist in box:"+isPointInBoundingBox);
				
				if(isPointInBoundingBox){	
					
					objectid = list_objectId.get(e);		
					//System.out.println("SIZE of polygonBbox.list_ofPolygons: " + polygonBbox.list_ofPolygons.size());
				
				pointIsInPolygon = polygonBbox.list_ofPolygons.get(e).contains(point.point2D);
//						logger.debug("polygon:"+polygonBbox.list_ofStrCoordinatesForPolygon.get(e));
//						logger.debug("point:"+point.getX_longitude()+", "+point.getY_latitude()+" ist in polygon:"+pointIsInPolygon);
				}
//				logger.debug("####");				
			}
		}
		
		/*
		Rectangle2D rectangle2D = polygonBbox.polygonPath2D.getBounds2D();
		 pointInBoundingBox =  rectangle2D.contains(point.point2D);
		Boolean pointInBoundingBoxFakeTest = rectangle2D.contains(point.x_longitude,point.y_latitude);
		
		if(pointInBoundingBoxFakeTest){
			System.out.println("Fake point ist in BoundingBox!");
		}
		
		isPointInPolygonUnloading = false;
		isPointInPolygonUnloading = polygonBbox.polygonPath2D.contains(point.point2D);

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
					objectid = polygonBbox.list_BboxObjectidForUnloading.get(e);
								
					pointIsInPolygon = polygonBbox.list_ofPolygons.get(e).contains(point.getX_longitude(), point.getY_latitude());
					System.out.println("polygon:"+polygonBbox.list_ofStrCoordinatesForPolygon.get(e));
					System.out.println("point:"+point.getX_longitude()+", "+point.getY_latitude()+" ist in polygon:"+pointIsInPolygon);
				}
				System.out.println("####");
				
			}
	}
	
	/**
	 * Gets a polygon/bbox from a WFS and sums up all polygons/bbox and their string coordinates (line of many coordinates).
	 * @param polygonOrBbox If .equals("bbox") => create bounding box; else create polygon.
	 * @param pp PointPolygon object.
	 * @param url_geofenceWfs URL to the server where from to get the data.
	 * @return pp PointPolygon object.
	 */
	public PointPolygon getPolygonBboxFromWFSForUnloading(String polygonOrBbox, PointPolygon pp,  String url_geofenceWfs){
		Networking net = new Networking();		
		String xml_WfsGeofenceResponse = net.sendGET2getGeofenceFromWFS(url_geofenceWfs);	
		ArrayList<ArrayList<String>> list_coords_objectid = new ParserXmlJson().extractCoordsFromWfsXml(xml_WfsGeofenceResponse, url_geofenceWfs, "unloading");
		
		if(polygonOrBbox.equals("bbox")){			
			
			pp.list_ofStrConsistingOf5CoordinatesForBoundingBox.addAll(list_coords_objectid.get(0));
			pp.list_BboxObjectidForUnloading.addAll(list_coords_objectid.get(1));
		
			
			//the new data from list_coords_objectid.get(0) will be added the the already existing one
			for(int i=0;i<list_coords_objectid.get(0).size();i++){
				pp.boundingBoxPath2D = pp.createPolygonBbox(list_coords_objectid.get(0).get(i), "bbox");
				pp.list_ofBoundingBox.add(pp.boundingBoxPath2D);
				}			
		return pp;		
		}
		else
		{			
			pp.list_ofStrCoordinatesForPolygon.addAll(list_coords_objectid.get(0));
			pp.list_BboxObjectiWithin.addAll(list_coords_objectid.get(1));

				//the new data from list_coords_objectid.get(0) will be added the the already existing one pp.list_ofPolygons
				for(int i=0;i<list_coords_objectid.get(0).size();i++){
					pp.polygonPath2D = pp.createPolygonBbox(list_coords_objectid.get(0).get(i), "polygon");
					pp.list_ofPolygons.add(pp.polygonPath2D);
				}				
			return pp;
		}
		
	}
	
	public PointPolygon getPolygonBboxFromWFSForWithin(String polygonOrBbox, PointPolygon pp,  String url_geofenceWfs){
		Networking net = new Networking();		
		String xml_WfsGeofenceResponse = net.sendGET2getGeofenceFromWFS(url_geofenceWfs);	
		ArrayList<ArrayList<String>> list_coords_objectid = new ParserXmlJson().extractCoordsFromWfsXml(xml_WfsGeofenceResponse, url_geofenceWfs, "within");
		
		if(polygonOrBbox.equals("bbox")){
			pp.list_ofStrConsistingOf5CoordinatesForBoundingBoxWithin.addAll(list_coords_objectid.get(0));
			pp.list_BboxObjectidForUnloading.addAll(list_coords_objectid.get(1));
			
			logger.debug("pp.list_ofBoundingBox.size(): "+pp.list_ofBoundingBox.size());
			
			//the new data from list_coords_objectid.get(0) will be added the the already existing one
			for(int i=0;i<list_coords_objectid.get(0).size();i++){
				pp.boundingBoxPath2D = pp.createPolygonBbox(list_coords_objectid.get(0).get(i), "bbox");
				pp.list_ofBoundingBoxWithin.add(pp.boundingBoxPath2D);
				}
			
			logger.debug("AFTER summing up pp.list_ofBoundingBox.size(): "+pp.list_ofBoundingBox.size());
			
		return pp;		
		}
		else
		{			
			pp.list_ofStrCoordinatesForPolygon.addAll(list_coords_objectid.get(0));

				//the new data from list_coords_objectid.get(0) will be added the the already existing one pp.list_ofPolygons
				for(int i=0;i<list_coords_objectid.get(0).size();i++){
					pp.polygonPath2D = pp.createPolygonBbox(list_coords_objectid.get(0).get(i), "polygon");
					pp.list_ofPolygonsWithin.add(pp.polygonPath2D);					
				}
				logger.debug("After summing up pp.list_ofPolygons.size(): "+pp.list_ofPolygons.size());
				logger.debug("pp.list_ofStrCoordinatesForPolygon: "+pp.list_ofStrCoordinatesForPolygon.size());
			return pp;
		}
		
	}
		
	
}
