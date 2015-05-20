package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import physics.BoardFriction;
import physics.Enviroment;
import physics.EnviromentalForceGenerator;
import physics.EdgeGravity;
import physics.EnviromentalInstantForce;
import physics.Vector;
import rendering.Bound;
import rendering.ObjectLayer;
import rendering.Renderer;
import rendering.RotatingBound;
import rendering.objects.CircleObj;
import rendering.objects.Obj;


public class MomentoTest {
	static int height = 800;
	static int width = 800;
	static Color bgcolor = Color.white;
	static ObjectLayer olay;
	static CircleObj obj;
	static EnviromentalInstantForce istForce;
	static EdgeGravity gravity;
	static BoardFriction friction;
	public static void main(String[] args)
	{
		JFrame gameFrame = new JFrame("MomentoTest");
		gameFrame.setLocation(new Point(450,100));
		Renderer renderer = new Renderer(bgcolor, new Dimension(width,height));
		gameFrame.setSize(width, height+23);
		gameFrame.setResizable(false);
		gameFrame.add(renderer,BorderLayout.CENTER);
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		olay = new ObjectLayer(new Dimension(width,height));
		renderer.addLayer(olay);
		olay.setPpmscale(16.0);
		//obj = new CircleObj(30, 25, 1.0);
		//obj.setColor(Color.cyan);
		//obj.mass=1.0;
		//olay.addObj(obj);
		initObjs();
		gravity = new EdgeGravity();
		gravity.setEnabled(false);
		//gravity.setDirection(45);
		olay.envForces.add(gravity);
		friction=new BoardFriction(0.8);
		friction.setEnabled(false);
		olay.envForces.add(friction);
		istForce=new EnviromentalInstantForce();
		olay.envForces.add(istForce);
		olay.addBound(new Bound(0,0,50,0,0.8));
		olay.addBound(new Bound(0,0,0,50,0.8));
		olay.addBound(new Bound(49.999,0,49.999,50,0.8));
		olay.addBound(new Bound(0,50,50,50,0.8));
		
		//olay.addBound(new RotatingBound(10, 25, 40, 25, 1.0, 1.0));
		
		olay.addBound(new Bound(5,35,15,45,0.8));
		olay.addBound(new Bound(15,45,25,35,0.8));
		olay.addBound(new Bound(25,35,35,45,0.8));
		olay.addBound(new Bound(35,45,45,35,0.8));
		olay.addBound(new Bound(5,5,45,5,0.8));
		
		gameFrame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keycode = e.getKeyCode();
				switch(keycode){
				case KeyEvent.VK_UP:
					obj.velocity.increaseMagnitude(+10);
					break;
				case KeyEvent.VK_DOWN:
					obj.velocity.increaseMagnitude(-10);
					break;
				case KeyEvent.VK_LEFT:
					obj.velocity.rotateDegAngle(-15.0);
					break;
				case KeyEvent.VK_RIGHT:
					obj.velocity.rotateDegAngle(+15.0);
					break;
				case KeyEvent.VK_D:
				{
					Vector v = new Vector();
					v.setPolar(500.0, 0.0);
					istForce.setForce(v);
					break;
				}
				case KeyEvent.VK_A:
				{
					Vector v = new Vector();
					v.setPolar(500.0, 180.0);
					istForce.setForce(v);
					break;
				}
				case KeyEvent.VK_W:
				{
					Vector v = new Vector();
					v.setPolar(500.0, 270.0);
					istForce.setForce(v);
					break;
				}
				case KeyEvent.VK_S:
				{
					Vector v = new Vector();
					v.setPolar(500.0, 90.0);
					istForce.setForce(v);
					break;
				}
				case KeyEvent.VK_G:
					gravity.setEnabled(!gravity.isEnabled());;
					break;
				case KeyEvent.VK_F:
					friction.setEnabled(!friction.isEnabled());
					break;
				case KeyEvent.VK_N:
				{
					CircleObj o =new CircleObj(25.0, 25.0, 1.0);
					o.mass=1.0;
					olay.addObj(o);
					break;
				}
				}
				
				
				
			}
		});
	}
	
	
	public static void initObjs(){
		ArrayList<Obj> objs=new ArrayList<Obj>();
		
		/*for(int i = 0; i<72; ++i)
		{
			CircleObj e = new CircleObj(200, 200, 1);
			e.velocity.setPolar(0.1, 5*i);
			objs.add(e);
		}*/
		
		
		for(int i = 0; i<360; ++i)
		{
			CircleObj e = new CircleObj(25, 25, 0.2);
			e.mass=1.0;
			e.velocity.setPolar(10, i);
			objs.add(e);
		}
		
		/*
		for(int i = 0; i<360; ++i)
		{
			CircleObj e = new CircleObj(20+i, 200, 2);
			e.velocity.set(0.0001*i, 270);
			objs.add(e);
		}
		*/
		olay.setObjects(objs);
	}
}
