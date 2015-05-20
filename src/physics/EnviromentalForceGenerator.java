package physics;

import rendering.objects.Obj;

public interface EnviromentalForceGenerator {
	public Vector getForce(Enviroment env, Obj obj);
	public boolean toBeDisposed();
	public void appliedToAll();
}
