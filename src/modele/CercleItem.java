package modele;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D.Float;


public class CercleItem extends CanvasItem {
	
	Point firstPoint;
	
	public CercleItem(PersistentCanvas c, Color o, Color f, Point p){
		super(c, o, f);
		shape = new Ellipse2D.Float(p.x, p.y, 0, 0);
		firstPoint = p;
	}
	
	public CercleItem(CercleItem other) {
		super(other.canvas, other.outline, other.fill);
		//shape = new Ellipse2D.Float((Ellipse2D) other.shape);
		isSelected = false;
		firstPoint = other.firstPoint;	
	}
	
	public CanvasItem duplicate() {
		return canvas.addItem(new CercleItem(this));
	}
	
	public void update(Point p) {
		((Ellipse2D.Float) shape).setFrameFromDiagonal(firstPoint, p);
		canvas.repaint();
	}

	public void move(int dx, int dy) {
		((Ellipse2D.Float) shape).x += dx;
		((Ellipse2D.Float) shape).y += dy;
		canvas.repaint();
	}
}
