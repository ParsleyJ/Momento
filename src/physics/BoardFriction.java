package physics;

import rendering.objects.Obj;

public class BoardFriction implements EnviromentalForceGenerator {

	private boolean enabled=true;
	private double coefficient = 1.0;
	public BoardFriction(double coeff)
	{
		coefficient=coeff;
	}
	@Override
	public Vector getForce(Enviroment env, Obj obj) {
		if(enabled)
		{
			Vector v = new Vector();
			if(obj.velocity.getMagnitude()<0.01) v.setPolar(0.0, 0.0);
			else v.setPolar(obj.mass*env.g*coefficient, obj.velocity.getDegAngle()+180.0);
			return v;
		}
		else
		{
			return new Vector();
		}
	}

	@Override
	public boolean toBeDisposed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void appliedToAll() {
		// TODO Auto-generated method stub

	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}

}
