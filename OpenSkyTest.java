import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;

import java.io.IOException;

public class OpenSkyTest {
	public static void main(String args[]) throws IOException {
		OpenSkyApi api = new OpenSkyApi(null, null);
		
		
		//Lakeland: 28.01.40N, 82.01.47W
		//Titusville: 28.36.39N, 80.49.41W
		
		OpenSkyStates os = api.getStates(0, null, 
			new OpenSkyApi.BoundingBox(
				28 + 01 / 60. + 40 / 3600.,
				28 + 36 / 60. + 39 / 3600.,
				-(82 + 01 / 60. + 47 / 3600.),
				-(80 + 49 / 60. + 41 / 3600.)
			)
		);
		
		System.out.println(os.getStates().size());
	}
}