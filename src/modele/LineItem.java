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
		shape = new Line2D.Float(((Line2D.Float) other.shape).getP1(), ((Line2D.Float)other.shape).getP2());
		isSelected = false;
		firstPoint = other.firstPoint;
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new LineItem(this));
	}

	public void update(Point p) {
		((Line2D.Float) shape).setLine(firstPoint, p);
		canvas.repaint();
	}

	public void move(int dx, int dy) {
		((Line2D.Float) shape).x1 += dx;
		((Line2D.Float) shape).y1 += dx;
		((Line2D.Float) shape).x2 += dy;
		((Line2D.Float) shape).y2 += dy;
		canvas.repaint();
	}

}
