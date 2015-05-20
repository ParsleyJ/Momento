package rendering.objects;

import java.awt.Graphics;

import physics.Vector;



public class Obj {
	public static int objIDCounter = 0;
	public enum CollisionDetectModes {None, Point, Segment, Radius}
	public double x;
	public double y;
	public boolean visible = true;
	public Vector velocity;
	public double mass = 0.0;
	public CollisionDetectModes cdMode = CollisionDetectModes.Point;
	private double scadjustment = 0.0;
	private boolean screbalance = false;
	public int objID = 0;
	
	
	
	public Obj(double x, double y) {
		this.x=x;
		this.y=y;
		velocity=new Vector();
		objID = objIDCounter++;
	}
	public Obj(double x, double y, double m) {
		this.x=x;
		this.y=y;
		this.mass=m;
		velocity=new Vector();
		objID = objIDCounter++;
	}
	public Obj(Obj o) {
		this.x=o.x;
		this.y=o.y;
		this.visible=o.visible;
		this.velocity=new Vector(o.velocity);
		this.mass=o.mass;
		this.cdMode=o.cdMode;
		objID = objIDCounter++;
	}
	public void paint(Graphics g, double scale){
		
	}
	public void updatePosition(double spf) 
	{
		//System.out.println("v1="+velocity.getMagnitude());
		if(scadjustment!=0.0)
		{
			if(!screbalance)
			{
				velocity.scaleMagnitude(scadjustment);
				screbalance = true;
			}
			else
			{
				//velocity.scaleMagnitude(1.0/scadjustment);
				scadjustment=0.0;
				screbalance=false;
			}
		}
		//System.out.println("v2="+velocity.getMagnitude());
		this.x+=velocity.xcomponent*spf;
		this.y+=velocity.ycomponent*spf;
		
	}
	public void applyForce(Vector f, double spf) 
	{
		velocity = Vector.sum(f.scaleMagnitude(spf/mass), velocity);
	}
	
	public void move(Vector r)
	{
		this.x+=r.xcomponent;
		this.y+=r.ycomponent;
	}
	
	public void setSpeculativeContactVelocityAdjustment(double scadjustment) {
		this.scadjustment = scadjustment;
	}
}
