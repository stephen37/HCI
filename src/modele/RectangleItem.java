package modele;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 * 
 */
public class RectangleItem extends CanvasItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Point firstpoint;
	Point lastPoint;
	
	public RectangleItem(PersistentCanvas c, Color o, Color f, Point p) {
		super(c, o, f);
		shape = new Rectangle(p.x, p.y, 0, 0);
		firstpoint = p;
		
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
		lastPoint = p;
	}

	public void modifSelect(){
		if(isSelected){
			Rectangle modif = new Rectangle(firstpoint, new Dimension(5, 5));
			modifRect.add(modif);
		}
	}
	
	public void move(int dx, int dy) {
		((Rectangle) shape).x += dx;
		((Rectangle) shape).y += dy;
		canvas.repaint();
	}
	
	public String getType(){
		return "Rectangle";
	}
	
	public int getP1X(){
		return (int)((Rectangle)shape).getMinX();
	}
	
	public int getP1Y(){
		return (int)((Rectangle)shape).getMinY();
	}
	
	public int getP2X(){
		return (int)((Rectangle)shape).getMaxX();
	}
	
	public int getP2Y(){
		return (int)((Rectangle)shape).getMaxY();
	}
	
	@Override
	public ArrayList<Integer> getPoints() {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(firstpoint.x);
		list.add(firstpoint.y);
		return list;
	}

}
