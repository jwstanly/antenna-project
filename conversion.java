public class conversion{

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

	public point locationToPoint(location input){
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

		point output = new point(x, y, z, radius, nx, ny, nz);
		return output;
	}

	public point rotateGlobe(location b, location a, double bradius, double aradius) {

		// Get modified coordinates of 'b' by rotating the globe so that 'a' is at lat=0, lon=0.
		location br = new location(b.getLat(), (b.getLon() - a.getLon()), b.getAlt() );
		point brp = LocationToPoint(br);

		    // Rotate brp cartesian coordinates around the z-axis by a.lon degrees,
		    // then around the y-axis by a.lat degrees.
		    // Though we are decreasing by a.lat degrees, as seen above the y-axis,
		    // this is a positive (counterclockwise) rotation (if B's longitude is east of A's).
		    // However, from this point of view the x-axis is pointing left.
		    // So we will look the other way making the x-axis pointing right, the z-axis
		    // pointing up, and the rotation treated as negative.

		double alat = GeocentricLatitude(-a.getLat() * Math.PI / 180.0);
		double acos = Math.cos(alat);
		double asin = Math.sin(alat);

		double bx = (brp.getX() * acos) - (brp.getZ() * asin);
		double by = brp.getY();
		double bz = (brp.getX() * asin) + (brp.getZ() * acos);

		point output = new point(bx, by, bz, bradius, 0, 0, 0);
			//JW: I recognize this is a shitty output... idk how to nicely encapsulate this data
		return output;
	}

}