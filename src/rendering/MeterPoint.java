package rendering;

public class MeterPoint {
	public double x=0.0;
	public double y=0.0;
	public MeterPoint()
	{
		
	}
	public MeterPoint(double X, double Y)
	{
		x=X;
		y=Y;
	}
	
	public static double getPointsDistance(MeterPoint p, MeterPoint q)
	{
		return Math.hypot(q.x-p.x, q.y-p.y);
	}
	
	public double getDistanceFromPoint(MeterPoint q)
	{
		return Math.hypot(q.x-this.x, q.y-this.y);
	}
	
	public double getDistanceFromPoint(double x, double y)
	{
		return Math.hypot(x-this.x, y-this.y);
	}
}
