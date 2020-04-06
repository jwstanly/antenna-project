public class Conversion{

	public double geocentricLatitude(double lat){
		double e2 = 0.00669437999014;
		double clat = Math.atan((1.0 - e2) * Math.tan(lat));
		return clat;
	}

	public double earthRadiusInMeters(double latitudeRadians){
		double a = 6378137.0; // equatorial radius in meters
		double b = 6356752.3; // polar radius in meters

		double cos = Math.cos(latitudeRadians);
		double sin = Math.sin(latitudeRadians);
		double t1 = a * a * cos;
		double t2 = b * b * sin;
		double t3 = a * cos;
		double t4 = b * sin;

		return Math.sqrt((t1*t1 + t2*t2) / (t3*t3 + t4*t4));
	}

	public Point locationToPoint(Location input){
		double lat = input.getLat() * Math.PI / 180.0;
		double lon = input.getLon() * Math.PI / 180.0;
		double radius = earthRadiusInMeters(lat);
		double clat = geocentricLatitude(lat);

		double cosLon = Math.cos(lon);
		double sinLon = Math.sin(lon);
		double cosLat = Math.cos(clat);
		double sinLat = Math.sin(clat);
		double x = radius * cosLon * cosLat;
		double y = radius * sinLon * cosLat;
		double z = radius * sinLat;

		// We used geocentric latitude to calculate (x,y,z) on the Earth's ellipsoid.
		// Now we use geodetic latitude to calculate normal vector from the surface, to correct for elevation.
		double cosGlat = Math.cos(lat);
		double sinGlat = Math.sin(lat);

		double nx = cosGlat * cosLon;
		double ny = cosGlat * sinLon;
		double nz = sinGlat;

		x += input.getAlt() * nx;
    		y += input.getAlt() * ny;
    		z += input.getAlt() * nz;

		Point output = new Point(x, y, z, radius, nx, ny, nz);
		return output;
	}

	public double distance(Point a, Point b){
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
      		double dz = a.getZ() - b.getZ();
        	return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}
	
	public Point rotateGlobe(Location b, Location a, double bradius, double aradius) {

		// Get modified coordinates of 'b' by rotating the globe so that 'a' is at lat=0, lon=0.
		Location br = new Location(b.getLat(), (b.getLon() - a.getLon()), b.getAlt() );
		Point brp = locationToPoint(br);

		    // Rotate brp cartesian coordinates around the z-axis by a.lon degrees,
		    // then around the y-axis by a.lat degrees.
		    // Though we are decreasing by a.lat degrees, as seen above the y-axis,
		    // this is a positive (counterclockwise) rotation (if B's longitude is east of A's).
		    // However, from this point of view the x-axis is pointing left.
		    // So we will look the other way making the x-axis pointing right, the z-axis
		    // pointing up, and the rotation treated as negative.

		double alat = geocentricLatitude(-a.getLat() * Math.PI / 180.0);
		double acos = Math.cos(alat);
		double asin = Math.sin(alat);

		double bx = (brp.getX() * acos) - (brp.getZ() * asin);
		double by = brp.getY();
		double bz = (brp.getX() * asin) + (brp.getZ() * acos);

		Point output = new Point(bx, by, bz, bradius, 0, 0, 0);
			//JW: I recognize this is a shitty output... idk how to nicely encapsulate this data
		return output;
	}
	public Point normalizeVectorDiff(Point b, Point a) {

		double dx = b.getX() - a.getX();
		double dy = b.getY() - a.getY();
		double dz = b.getZ() - a.getZ();

		double dist2 = dx * dx + dy * dy + dz * dz;

		if(dist2 == 0) return null;

		double dist = Math.sqrt(dist2);

		return new Point(dx / dist, dy / dist, dz / dist);

	}

	public void calculate(Location a, Location b) {
		Point ap = locationToPoint(a);
		Point bp = locationToPoint(b);
		double distKm = 1E-3 * distance(ap, bp);
		

		Point br = rotateGlobe(b, a, bp.getR(), ap.getR());

		if(br.getZ()*br.getZ() + br.getY()*br.getY() > 1E-6) {
			double theta = Math.atan2(br.getZ(), br.getY())  * 180 / Math.PI;
			double azimuth = 90 - theta;
			if(azimuth < 0) {
				azimuth += 360;
			}
			if(azimuth > 360) {
				azimuth -= 360;
			}
			System.out.println("\t\tAzimuth: \t" + azimuth);
		}

		Point bma = normalizeVectorDiff(bp, ap);
		if(bma != null) {
			double altitude = 90.0 - (180.0 / Math.PI) * Math.acos(bma.getX() * ap.getNx() + bma.getY() * ap.getNy() + bma.getZ()*ap.getNz());
			System.out.println("\t\tAltitude: \t" + altitude);
		}
	}

	enum Directions {
		NORTH, SOUTH,
		EAST, WEST
	};
	
	public static double dmsToDecimal(int degree, int minute, int second, Directions dir) {
		double rtn = degree + minute / 60. + second / 3600.;
		if(dir == Directions.SOUTH || dir == Directions.WEST) {
			rtn *= -1;
		}
		return rtn;
	}



	/*
	public static void main(String args[]) {

		Location l1 = new Location(50, 50, 0);
		Location l2 = new Location(70, 70, 500);

		(new Conversion()).calculate(l1, l2);
	}//*/

}
