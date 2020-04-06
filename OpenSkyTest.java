import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;
import org.opensky.model.StateVector;

import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import java.io.IOException;

public class OpenSkyTest {
	public static void main(String args[]) throws IOException {
		
		OpenSkyApi api = new OpenSkyApi("GTUsers", "#!jsawk2020");

		Scanner kb = new Scanner(System.in);		
		System.out.print("Min lat: ");
			double minLat = kb.nextDouble();
		System.out.print("Max lat: ");
			double maxLat = kb.nextDouble();
		System.out.print("Min lon: ");
			double minLon = kb.nextDouble();
		System.out.print("Max lon: ");
			double maxLon = kb.nextDouble();

		OpenSkyStates os = api.getStates(0, null, new OpenSkyApi.BoundingBox(minLat, maxLat, minLon, maxLon));

		Collection<StateVector> list = os.getStates();
		System.out.println("\nNUMBER OF PLANES SPOTTED IN PARAMETER: " + os.getStates().size()+"\n");

		StateVector[] array = new StateVector[list.size()];
		
		Iterator<StateVector> iterator = list.iterator();
		
		for(int x=0;x<array.length;x++)
			array[x] = iterator.next();
		
		Location homeLocation = new Location(30.2754, -81.7005, 30);

		for(int x=0;x<array.length;x++){
			System.out.println("----------------------------" + (x+1) + "----------------------------");
			System.out.println("\tIdentification");
			System.out.println("\t\tCallsign: \t" + array[x].getCallsign());
			System.out.println("\t\tICAO24: \t" + array[x].getIcao24());
			System.out.println("\t\tOriginCountry: \t" + array[x].getOriginCountry());
			
			System.out.println("\tLocation");
			System.out.println("\t\tLatitude: \t" + array[x].getLatitude()+"*");
			System.out.println("\t\tLongitude: \t" + array[x].getLongitude()+"*");
			System.out.println("\t\tBaroAltitude: \t" + array[x].getBaroAltitude()+" m");
			System.out.println("\t\tGeoAltitude: \t" + array[x].getGeoAltitude()+" m");			
			System.out.println("\t\tVelocity: \t" + array[x].getVelocity()+" m/s");
			System.out.println("\t\tAscendRate: \t" + array[x].getVerticalRate());			
			System.out.println("\t\tHeading: \t" + array[x].getHeading()+"*");
			
			System.out.println("\tTrajectory");
			if(array[x].getLatitude()==null || array[x].getLongitude() == null || array[x].getBaroAltitude() == null){
				System.out.println("\t\tERROR: Insufficient data to calculate");
			}
			else{
				Location tempLocation = new Location(array[x].getLatitude(),
								array[x].getLongitude(),
								array[x].getBaroAltitude());
				(new Conversion()).calculate(homeLocation, tempLocation);
			}
		}
		
		//System.out.println(os.getStates().size());
	}
}