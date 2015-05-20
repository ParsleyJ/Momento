package rendering;

import java.awt.Dimension;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import physics.Enviroment;
import physics.EnviromentalForceGenerator;
import physics.UnitVector;
import physics.Vector;
import rendering.objects.CircleObj;
import rendering.objects.Obj;
import rendering.objects.Obj.CollisionDetectModes;

public class ObjectLayer extends Layer {

	private List<Obj> objects;
	private List<Bound> bounds;
	private double ppmscale = 1.0;
	
	public boolean topBound = true;
	public boolean bottomBound = true;
	public boolean leftBound = true;
	public boolean rightBound = true;
	public double boundRestitution = 0.5;
	public Dimension size = new Dimension(400,400);
	public Enviroment enviroment = new Enviroment();
	public ArrayList<EnviromentalForceGenerator> envForces = new ArrayList<EnviromentalForceGenerator>();
	
	public ObjectLayer(Dimension size)
	{
		objects=new ArrayList<Obj>();
		bounds = new ArrayList<Bound>();
		this.size=size;
	}
	
	public void addObj(Obj o)
	{
		objects.add(o);
	}
	
	public void addBound(Bound b)
	{
		bounds.add(b);
	}
	@Override
	public void paint(double spf) {
		for (Bound b: bounds)
			b.paint(graphics, ppmscale);
		
		
		for (Obj o: objects)
		{
			
			MeterPoint prevP = new MeterPoint(o.x,o.y);
			boolean alreadyupdated = false;
			for(Obj o2: objects)
			{
				if(o.equals(o2)) continue;
				if(o.cdMode==CollisionDetectModes.Radius)
				{
					CircleObj co = (CircleObj) o;
					if(o2.cdMode==CollisionDetectModes.Radius)
					{
						CircleObj co2= (CircleObj) o2;
						
						//creates ghost object;
						CircleObj cog = new CircleObj(co);
						cog.updatePosition(spf);
						CircleObj co2g = new CircleObj(co2);
						co2g.updatePosition(spf);
						
						if(Math.hypot(co2.x-cog.x, co2.y-cog.y)<=cog.radius+co2.radius)//COLLISION DETECTED!!!
						{
							//System.out.println("obj"+co.objID+": collision with obj"+co2.objID+" at time "+System.currentTimeMillis());
							UnitVector normal = new UnitVector(Math.toDegrees(Math.atan2(co2.y-cog.y, co2.x-cog.x)));
							//UnitVector normal = (new Bound(co.x,co.y,co2.x,co2.y).getDirection());
							double e = co.restitution*co2.restitution;
							
							//speculative contact resolver
							if(co.velocity.xcomponent!=0){
								double d =Bound.getCircleDirectionalDistance(co2.x, co2.y, co.x, co.y, Math.tan(Math.toRadians(co.velocity.getDegAngle())), co.radius+co2.radius);
								if(d!=0.0)
								{
									System.out.println("obj"+co.objID+": original velocity:"+co.velocity.getMagnitude());
									co.setSpeculativeContactVelocityAdjustment(co.velocity.getMagnitude()/d);
									
								}
									
							}
							o.updatePosition(spf);
							alreadyupdated=true;
							System.out.println("obj"+co.objID+": postcoll velocity:"+co.velocity.getMagnitude());
							//apply forces
							Vector impactForce = new Vector(co.velocity.getMagnitude()*Math.cos(normal.angle-co.velocity.getDegAngle())*e*co.mass/(2.0*spf), normal);
							System.out.println(Math.cos(normal.angle-co.velocity.getDegAngle()));
							co2.applyForce(impactForce, spf);
							impactForce.rotateDegAngle(180.0);
							co.applyForce(impactForce, spf);
							
							

						}
							
					}
				}
			}
			
			if(!alreadyupdated)
				o.updatePosition(spf);
			MeterPoint newP = new MeterPoint(o.x,o.y);
			for(Bound b: bounds)
			{
				if(!b.isEnabled()) continue;
				MeterPoint intersection = Bound.segmentIntersection(prevP, newP, b.getMP1(), b.getMP2());
				if(intersection!=null)//COLLISION DETECTED!!!
				{
					o.velocity = Vector.sum(o.velocity, Vector.scalarPerVector(-1, 
							Vector.scalarPerVector(1.0+b.restitution, Vector.scalarPerVector(Vector.scalarProduct(o.velocity, b.getNormal()), b.getNormal()))));
					MeterPoint simmP = b.simmetricalPoint(newP);
					o.x=simmP.x;
					o.y=simmP.y;
				}
			}
			
			checkbounds(o);
			
			ArrayList<Vector> forces = new ArrayList<Vector>();
			
			for(EnviromentalForceGenerator efg: envForces){
				if(efg.toBeDisposed()) {
					envForces.remove(efg);
				}
			}
			
			for(EnviromentalForceGenerator efg: envForces){
				forces.add(efg.getForce(enviroment, o));
			}
			
			o.applyForce(Vector.sum(forces), spf);
			
			//if(o.velocity.getMagnitude()<0.1)
			//{
			//	o.velocity.setMagnitude(0.0);
			//}
			
			o.paint(graphics,ppmscale);
		}
		for(EnviromentalForceGenerator efg: envForces){
			efg.appliedToAll();
		}
	}
	
	public void checkbounds(Obj o)
	{
		if(topBound)
			if(o.y<=0.0) o.velocity.ycomponent= -o.velocity.ycomponent;
		if(leftBound)
			if(o.x<=0.0) o.velocity.xcomponent= -o.velocity.xcomponent;
		if(rightBound)
			if(o.x>=(double)size.width/ppmscale) o.velocity.xcomponent= -o.velocity.xcomponent;
		if(bottomBound)
			if(o.y>=(double)size.height/ppmscale) o.velocity.ycomponent= -o.velocity.ycomponent;
	}
	
	public void setObjects(ArrayList<Obj> objs)
	{
		objects = objs;
	}
	
	public void setBounds(List<Bound> bounds) {
		this.bounds = bounds;
	}

	public double getPpmscale() {
		return ppmscale;
	}
	public void setPpmscale(double ppmscale) {
		this.ppmscale = ppmscale;
	}

}
