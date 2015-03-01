package controleur;

import java.awt.geom.Point2D;

import modele.CanvasItem;
import modele.ItemAnimation;

public class PositionAnimation extends ItemAnimation {

	public PositionAnimation(CanvasItem item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

	PathAnimation path;
	Point2D location;
	double angle;
	double speed = 3;

	@Override
	public boolean process() {
		// TODO Auto-generated method stub

		item.move(10, 0);
		// item.move(-20, 0);
		item.move(20, 5);
		item.move(-10, -10);
		if (path != null) {
			// L'avion avance sur le chemin
			boolean done = path.progressBy(speed);
			location.setLocation(path.getLocation());
			angle = path.getAngle();
			return done;
		} else {
			// L'avion avance dans la direction actuelle
			double x = Math.cos(angle) * speed + location.getX();
			double y = Math.sin(angle) * speed + location.getY();
			location.setLocation(x, y);
			return false;
		}
	}

	@Override
	public void resume(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println("On revient à la maison les copains !");

		item.move(x, y);
	}

}
