public class location{

	private double lat;
	private double lon;
	private double alt;

	public location(double lat, double lon, double alt){
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
	}

	public double getLat(){
		return lat;
	}

	public double getLon(){
		return lon;
	}

	public double getAlt(){
		return alt;
	}

	public void setLat(double lat){
		this.lat = lat;
	}

	public void setLon(double lon){
		this.lon = lon;
	}

	public void setAlt(double alt){
		this.alt = alt;
	}
}