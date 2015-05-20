package physics;

import java.util.ArrayList;

public class Vector {
	public double xcomponent = 0.0;
	public double ycomponent = 0.0;
	public Vector()
	{
		xcomponent = 0.0;
		ycomponent = 0.0;		
	}
	public Vector(double xcomp, double ycomp)
	{
		xcomponent = xcomp;
		ycomponent = ycomp;
	}
	
	public Vector(Vector v)
	{
		xcomponent = v.xcomponent;
		ycomponent = v.ycomponent;
	}
	
	public Vector(double magnitude, UnitVector direction)
	{
		xcomponent = magnitude*Math.cos(Math.toRadians(direction.angle));
		ycomponent = magnitude*Math.sin(Math.toRadians(direction.angle));
	}
	
	public void setCartesian(double xcomp, double ycomp)
	{
		xcomponent = xcomp;
		ycomponent = ycomp;
	}
	
	public void setPolar(double magnitude, double angle)
	{
		xcomponent = magnitude*Math.cos(Math.toRadians(angle));
		ycomponent = magnitude*Math.sin(Math.toRadians(angle));
	}
	public double getDegAngle()
	{
		return Math.toDegrees(Math.atan2(ycomponent, xcomponent));
	}
	
	public void setDegAngle(double angle)
	{
		setPolar(getMagnitude(),angle);
	}
	
	public void rotateDegAngle(double angle)
	{
		setPolar(getMagnitude(),getDegAngle()+angle);
	}
	
	public double getMagnitude()
	{
		return Math.hypot(xcomponent,ycomponent);
	}
	
	public void setMagnitude(double magnitude)
	{
		setPolar(magnitude, getDegAngle());
	}
	
	public void increaseMagnitude(double delta)
	{
		setPolar(getMagnitude()+delta,getDegAngle());
	}
	
	public Vector scaleMagnitude(double scale)
	{
		setPolar(getMagnitude()*scale,getDegAngle());
		return this;
	}
	
	public void addVector(Vector b)
	{
		this.xcomponent+=b.xcomponent;
		this.ycomponent+=b.ycomponent;
	}
	
	public static Vector sum(Vector a, Vector b)
	{
		return new Vector(a.xcomponent+b.xcomponent,a.ycomponent+b.ycomponent);
	}
	
	public static Vector sum(ArrayList<Vector> vectors)
	{
		double xc=0.0, yc=0.0;
		for(Vector v: vectors)
		{
			xc+=v.xcomponent;
			yc+=v.ycomponent;
		}
		return new Vector(xc,yc);
	}
	
	public static double scalarProduct(Vector a, Vector b)
	{
		return a.getMagnitude()*b.getMagnitude()*Math.cos(Math.toRadians(b.getDegAngle()-a.getDegAngle()));
	}
	
	public static double scalarProduct(Vector a, UnitVector b)
	{
		return a.getMagnitude()*Math.cos(Math.toRadians(b.getDegAngle()-a.getDegAngle()));
	}
	
	public static Vector scalarPerVector(double a, Vector b)
	{
		Vector v = new Vector();
		v.setPolar(b.getMagnitude()*a,b.getDegAngle());
		return v;
	}
	
	public static Vector scalarPerVector(double a, UnitVector b)
	{
		Vector v = new Vector();
		v.setPolar(a,b.getDegAngle());
		return v;
	}
}
