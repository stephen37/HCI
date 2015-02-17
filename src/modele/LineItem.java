package modele;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;

public class LineItem extends CanvasItem {

	Point firstPoint;

	public LineItem(PersistentCanvas c, Color o, Color f, Point p) {
		super(c, o, f);
		shape = new Line2D.Float(p, p);
		firstPoint = p;
	}

	public LineItem(LineItem other) {
		super(other.canvas, other.outline, other.fill);
		// shape = new Ellipse2D.Float((Ellipse2D) other.shape);
		isSelected = false;
		firstPoint = other.firstPoint;
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new LineItem(this));
	}

	public void update(Point p) {
		((Line2D) shape).setLine(firstPoint, p);
		canvas.repaint();
	}

	public void move(int dx, int dy) {
		((Line2D.Float) shape).x1 += dx;
		((Line2D.Float) shape).y1 += dy;
		canvas.repaint();
	}

}
