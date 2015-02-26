package modele;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class PathItem extends CanvasItem {
	int x, y;
	protected GeneralPath path;
	protected ArrayList<Point> listPoint;

	public PathItem(PersistentCanvas c, Color o, Color f, Point p) {
		super(c, o, f);
		path = new GeneralPath();
		path.moveTo(p.x, p.y);
		shape = path;
		x = p.x;
		y = p.y;
		listPoint = new ArrayList<Point>();
		listPoint.add(new Point(x,y));
	//	System.out.println(x + " ; " + y);
	}

	public PathItem(PathItem other) {
		super(other.canvas, other.outline, other.fill);
		shape = new GeneralPath(other.shape);
		isSelected = false;
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new PathItem(this));
	}

	public void update(Point p) {
		GeneralPath path = (GeneralPath) shape;
		path.lineTo(p.x, p.y);
		listPoint.add(new Point(p.x, p.y));
		//System.out.println(p.x + " ; " + p.y);
		canvas.repaint();
	}

	public void move(int dx, int dy) {
		shape = AffineTransform.getTranslateInstance(dx, dy)
				.createTransformedShape(shape);
		for(Point point : listPoint){
			point.x += dx;
			point.y += dy;
		//	System.out.println(point.x + " ; " + point.y);
		}
		canvas.repaint();
	}

	public void modifSelect() {

	}

	public void paint(Graphics2D g) {
		drawShape(g);
	}
	
	public String getType(){
		return "Path";
	}
	
	public ArrayList<Point> getListPoint(){
		return listPoint;
	}

	@Override
	public ArrayList<Integer> getPoints() {
		// TODO Auto-generated method stub

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(x);
		list.add(y);
		return list;
	}
}
