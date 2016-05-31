package utility;

import java.util.ArrayList;

/**
 * Contains all PointPolygon objects.
 * @author slukic
 *
 */
public class Service {
	private static Service serviceInstance = null;
	   protected Service() {
		   //to ensure singleton
	   }
	   public static Service getInstance() {
	      if(serviceInstance == null) {
	    	  serviceInstance = new Service();
	      }
	      return serviceInstance;
	   }
	   ArrayList<PointPolygon> list_ofPointPolygon = new ArrayList<PointPolygon>();
}
