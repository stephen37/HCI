package modele;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 * 
 */
public class RectangleItem extends CanvasItem {

	Point firstpoint;
	ArrayList<Rectangle> modifRect;

	public RectangleItem(PersistentCanvas c, Color o, Color f, Point p) {
		super(c, o, f);
		shape = new Rectangle(p.x, p.y, 0, 0);
		firstpoint = p;
		modifRect = new ArrayList<Rectangle>();
		modifSelect();
	}

	public RectangleItem(RectangleItem other) {
		super(other.canvas, other.outline, other.fill);
		shape = new Rectangle((Rectangle) other.shape);
		isSelected = false;
		firstpoint = other.firstpoint;
		modifSelect();
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new RectangleItem(this));
	}

	public void update(Point p) {
		((Rectangle) shape).setFrameFromDiagonal(firstpoint, p);
		canvas.repaint();
	}

	public void modifSelect(){
		if(isSelected){
			Rectangle modif = new Rectangle(firstpoint, new Dimension(5, 5));
		}
	}
	
	public void move(int dx, int dy) {
		((Rectangle) shape).x += dx;
		((Rectangle) shape).y += dy;
		canvas.repaint();
	}

}
