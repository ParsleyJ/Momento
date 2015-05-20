package physics;

import rendering.objects.Obj;

public class EnviromentalInstantForce implements EnviromentalForceGenerator {

	private boolean disp = false;
	private Vector f;
	
	public EnviromentalInstantForce()
	{
		f=new Vector();
	}
	
	public EnviromentalInstantForce(Vector force)
	{
		f=force;
	}
	@Override
	public Vector getForce(Enviroment env, Obj obj) {
		return f;
	}

	public void dispose()
	{
		disp=true;
	}
	
	public void setForce(Vector force)
	{
		f=new Vector(force);
	}
	
	@Override
	public boolean toBeDisposed() {
		return disp;
	}
	@Override
	public void appliedToAll() {
		f.xcomponent=0;
		f.ycomponent=0;
		
	}

}
