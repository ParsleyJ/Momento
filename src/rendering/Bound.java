package rendering;

import java.awt.Color;
import java.awt.Graphics;

import physics.UnitVector;
import physics.Vector;
import rendering.MeterPoint;

public class Bound {

	public double x1 = 0.0;
	public double y1 = 0.0;
	public double x2 = 1.0;
	public double y2 = 0.0;
	public double restitution = 1.0;
	private boolean enabled = true;

	public Bound(double xa, double ya, double xb, double yb)
	{
		x1=xa;
		x2=xb;
		y1=ya;
		y2=yb;
	}

	public Bound(double xa, double ya, double xb, double yb, double e)
	{
		x1=xa;
		x2=xb;
		y1=ya;
		y2=yb;
		restitution=e;
	}

	public double getDegAngle()
	{
		return Math.toDegrees(Math.atan2(y2-y1, x2-x1));
	}

	public MeterPoint getMP1()
	{
		return new MeterPoint(x1,y1);
	}

	public MeterPoint getMP2()
	{
		return new MeterPoint(x2,y2);
	}

	public void paint(Graphics g, double scale)
	{
		g.setColor(Color.white);
		g.drawLine((int)(x1*scale), (int)(y1*scale), (int)(x2*scale), (int)(y2*scale));
	}

	public static MeterPoint segmentIntersection(MeterPoint P1, MeterPoint P2, MeterPoint P3, MeterPoint P4)
	{
		double delta0, delta1, delta2, ka, kb;
		delta0=(P4.y - P3.y) * (P2.x - P1.x) - (P4.x - P3.x) * (P2.y - P1.y);
		if(delta0==0.0)return null;
		delta1 = (P4.x - P3.x) * (P1.y - P3.y) - (P4.y - P3.y) * (P1.x - P3.x);
		delta2 = (P2.x - P1.x) * (P1.y - P3.y) - (P2.y - P1.y) * (P1.x - P3.x);
		ka = delta1 / delta0;
		kb = delta2 / delta0;
		MeterPoint P0 = new MeterPoint();
		P0.x = P1.x + ka * (P2.x - P1.x);
		P0.y = P1.y + ka * (P2.y - P1.y);
		if ((ka >= 0 && ka <= 1) && (kb >= 0 && kb <= 1)) {
			return P0;
		}
		return null;
	}

	public MeterPoint simmetricalPoint(MeterPoint P)
	{
		MeterPoint Q = new MeterPoint();
		if(x1!=x2)
		{
			double m = (y2-y1)/(x2-x1);
			double q = (x1*y2-x2*y1)/(x1-x2);
			Q.x=((1.0-m*m)/(1.0+m*m))*P.x +((2.0*m)/(1.0+m*m))*P.y-((2.0*m*q)/(1.0+m*m));
			Q.y=((2.0*m)/(1.0+m*m))*P.x -((1.0-m*m)/(1.0+m*m))*P.y+((2.0*q)/(1.0+m*m));
		}
		else
		{
			Q.y=P.y;
			Q.x=2*x1-P.x;
		}

		return Q;
	}

	public UnitVector getNormal() {

		return new UnitVector(getDegAngle()+90.0);
	}

	public UnitVector getDirection() {

		return new UnitVector(getDegAngle());
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public static MeterPoint getLineCircleIntersection(double centerx, double centery, double linex, double liney, double m, double r)
	{
		double alfa  = m*m+1.0;
		double beta  = 2.0*m*liney-2.0*m*m*linex-2.0*centerx;
		double gamma = centerx*centerx+m*m*linex*linex+liney*liney-2.0*m*linex*liney+2.0*m*centery*linex-2*m*centery-2*centery*liney-r*r;
		
		double delta = beta*beta-4.0*alfa*gamma;
		
		if(delta<0.0)
			return null;
		else if(delta==0)
			return new MeterPoint(-beta/(2.0*alfa),m*(-beta/(2.0*alfa)-m*linex+liney));
		else
		{
			double x1=(-beta+Math.sqrt(delta))/(2.0*alfa);
			double x2=(-beta-Math.sqrt(delta))/(2.0*alfa);
			double y1=m*x1-m*linex+liney;
			double y2=m*x2-m*linex+liney;
			//returns the closest to the point (linex,liney):
			if(Math.hypot(x1-linex, y1-liney)<Math.hypot(x2-linex, y2-liney))
				return new MeterPoint(x1,y1);
			else 
				return new MeterPoint(x2,y2);
		}
	}
	
	public static double getCircleDirectionalDistance(double centerx, double centery, double linex, double liney, double m, double r)
	{
		MeterPoint p = getLineCircleIntersection(centerx, centery, linex, liney, m, r);
		if (p==null) return 0.0;
		return p.getDistanceFromPoint(linex, liney);
	}
	
	

}
