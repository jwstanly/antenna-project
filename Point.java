public class Point{

	private double x;
	private double y;
	private double z;
	private double r;
	private double nx;
	private double ny;
	private double nz;

	public point(double x, double y, double z, double r, double nx, double ny, double nz){
		this.x=x;
		this.y=y;
		this.z=z;
		this.r=r;
		this.nx=nx;
		this.ny=ny;
		this.nz=nz;
	}
	
	public point(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
		r=1;
		nx=0;
		ny=0;
		nz=0;
	}

	public point(double x, double y, double z, double r){
		this.x=x;
		this.y=y;
		this.z=z;
		this.r=r;
		nx=0;
		ny=0;
		nz=0;
	}

	public void setX(double x){
		this.x = x;
	}

	public double getX(){
		return x;
	}

	public void setY(double y){
		this.y = y;
	}

	public double getY(){
		return y;
	}

	public void setZ(double z){
		this.z = z;
	}

	public double getZ(){
		return z;
	}

	public void setR(double r){
		this.r = r;
	}

	public double getR(){
		return R;
	}

	public void setNx(double nx){
		this.nx = nx;
	}

	public double getNx(){
		return nx;
	}

	public void setNy(double ny){
		this.ny = ny;
	}

	public double getNy(){
		return ny;
	}

	public void setNz(double nz){
		this.nz = nz;
	}

	public double getNz(){
		return nz;
	}
}
