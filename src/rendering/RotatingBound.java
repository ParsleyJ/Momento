package rendering;

import java.awt.Graphics;


public class RotatingBound extends Bound {
	
	private double spinspeed = 1.0;
	private double radius = 1.0;
	private double curangle = 0.0;
	private double centerx = 0.0;
	private double centery = 0.0;
	
	public RotatingBound(double xa, double ya, double xb, double yb, double e) {
		super(xa, ya, xb, yb, e);
		radius = Math.hypot(xb-xa, yb-ya)/2.0;
		curangle=Math.toDegrees(Math.atan2(yb-ya, xb-xa));
		centerx=(xa+xb)/2.0;
		centery=(ya+yb)/2.0;
	}
	
	public RotatingBound(double xa, double ya, double xb, double yb, double e, double ss) {
		super(xa, ya, xb, yb, e);
		spinspeed=ss;
		radius = Math.hypot(xb-xa, yb-ya)/2.0;
		curangle=Math.toDegrees(Math.atan2(yb-ya, xb-xa));
		centerx=(xa+xb)/2.0;
		centery=(ya+yb)/2.0;
	}

	@Override
	public void paint(Graphics g, double scale)
	{
		super.paint(g, scale);
		curangle=(curangle+spinspeed)%360.0;
		x1=Math.cos(Math.toRadians(curangle))*radius+centerx;
		x2=Math.cos(Math.toRadians(curangle+180.0))*radius+centerx;
		//x2=centerx;
		y1=Math.sin(Math.toRadians(curangle))*radius+centery;
		y2=Math.sin(Math.toRadians(curangle+180.0))*radius+centery;
		//y2=centery;
		
		
	}

	public double getSpinspeed() {
		return spinspeed;
	}

	public void setSpinspeed(double spinspeed) {
		this.spinspeed = spinspeed;
	}

}
