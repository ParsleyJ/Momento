package physics;

import rendering.objects.Obj;

public class EdgeGravity implements EnviromentalForceGenerator {
	private boolean enabled = true;
	private boolean disp = false;
	private double direction = 90.0;
	@Override
	public Vector getForce(Enviroment env, Obj obj) {
		Vector f = new Vector();
		if(enabled){
			f.setPolar(env.g*obj.mass, direction);
		}
		else f.setCartesian(0, 0);
		return f;
	}

	public void dispose()
	{
		disp=true;
	}
	
	@Override
	public boolean toBeDisposed() {
		return disp;
	}

	@Override
	public void appliedToAll(){
		
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

}
