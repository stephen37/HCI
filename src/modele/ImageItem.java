package modele;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;



public class ImageItem extends CanvasItem{

	private static final long serialVersionUID = 1L;
	Point firstpoint;
	Point lastPoint;
	
	public ImageItem(PersistentCanvas c, Color o, Color f, Point p, Image img) {
		super(c, o, f);
		shape = new Rectangle(1,23,img.getWidth(null), img.getHeight(null));
		System.out.println("Hauteur " + img.getHeight(null));
		System.out.println("Largeur " + img.getWidth(null));
		firstpoint = p;
		background = img;
		modifSelect();
	}

	public ImageItem(ImageItem other) {
		super(other.canvas, other.outline, other.fill);
		shape = new Rectangle((Rectangle) other.shape);
		isSelected = false;
		firstpoint = other.firstpoint;
		background = other.background;
		modifSelect();
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new ImageItem(this));
	}

	public void update(Point p) {
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
		return "Image";
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

	@Override
	public int getMinX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
