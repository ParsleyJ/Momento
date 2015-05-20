package rendering.objects;

import java.awt.Color;
import java.awt.Graphics;

public class CircleObj extends Obj {
	public double radius = 5.0;
	public double restitution = 1.0;
	private Color color = Color.white;
	
	public CircleObj(double x, double y, double radius)
	{
		super(x,y);
		this.setRadius(radius);
		this.cdMode=CollisionDetectModes.Point;
	}
	
	public CircleObj(CircleObj o) {
		super(o);
		this.radius=o.radius;
		this.restitution=o.restitution;
	}

	@Override
	public void paint(Graphics g, double scale){
		double tlx = x-radius;
		double tly = y-radius;
		int px = (int)(tlx*scale);
		int py = (int)(tly*scale);
		g.setColor(color);
		g.fillOval(px, py, (int)(radius*2*scale), (int)(radius*2*scale));
	}
	
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
