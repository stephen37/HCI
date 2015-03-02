package modele;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import vue.GraphicalEditor;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 * 
 */
public abstract class CanvasItem {

	protected PersistentCanvas canvas;
	protected Color outline, fill;
	protected Shape shape;
	protected Boolean isSelected;

	protected Image background;

	public Boolean horizAnimate;
	public Boolean verticAnimate;
	public Boolean blinkAnimate;
	
	public static int value = 2;

	protected ArrayList<Rectangle> modifRect;

	Point firstPoint;
	Point lastPoint;
	Timer timer;
	Timer timer2;
	Integer stroke;

	public CanvasItem(PersistentCanvas c, Color o, Color f) {
		canvas = c;
		fill = f;
		outline = o;
		shape = null;
		isSelected = false;
		background = null;
		horizAnimate = false;
		verticAnimate = false;
		blinkAnimate = false;
		stroke = null;
		// modifRect = new ArrayList<Rectangle>();
	}

	public void setOutlineColor(Color c) {
		outline = c;
		canvas.repaint();
	}

	public void setFillColor(Color c) {
		fill = c;
		System.out.println("setFillColor !!! ");
		canvas.repaint();
	}

	public void setStroke(int s, Graphics2D g) {
		System.out
				.println("Nous sommes dans le stroke MA GUEULE !!! La value du stroke est "
						+ s);
		g.setStroke(new BasicStroke(s));
		value = s;
		g.drawString("YOLO SWAG CLUB ", 200, 200);
		canvas.repaint();
		canvas.revalidate();
	}

	public void select() {
		isSelected = true;
		canvas.repaint();
	}

	public void deselect() {
		isSelected = false;
		canvas.repaint();
	}

	public void blinkAnimated() {
		blinkAnimate = true;
		canvas.repaint();
		this.blink();
	}

	public void blickUnanimated() {
		blinkAnimate = false;
		canvas.repaint();
		timer.stop();
		timer2.stop();
		canvas.addItem(GraphicalEditor.selection);
	}

	public void blink() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.removeItem(GraphicalEditor.selection);

			}
		});
		timer2 = new Timer(1100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				canvas.addItem(GraphicalEditor.selection);

			}
		});
		timer.start();
		timer2.start();
	}

	protected void fillShape(Graphics2D g) {
//		g.setStroke(new BasicStroke(value));
		g.setColor(fill);
		g.fill(shape);
	}

	protected void drawShape(Graphics2D g) {
		Stroke oldstrk = null;
		if (isSelected) {
			oldstrk = g.getStroke();
			// g.setStroke(new BasicStroke(2));
			 g.setStroke(new BasicStroke(value));
//			 setStroke(value, g);
		}
		g.setColor(outline);
		g.draw(shape);
		if (oldstrk != null)
			g.setStroke(oldstrk);
	}

	public void paint(Graphics2D g) {
		if (background == null) {
			fillShape(g);
			drawShape(g);
		} else {
			g.drawImage(background, (int) shape.getBounds().getMinX(),
					(int) shape.getBounds().getMinY(), null);
			drawShape(g);
		}
		
	}

	public Boolean contains(Point p) {
		return shape.contains(p);
	}

	public String getColorInterieur() {
		return outline.getRed() + " " + outline.getGreen() + " "
				+ outline.getBlue();
	}

	public String getColorExterieur() {
		return fill.getRed() + " " + fill.getGreen() + " " + fill.getBlue();
	}

	public abstract void modifSelect();

	public abstract CanvasItem duplicate();

	public abstract void update(Point p);

	public abstract void move(int dx, int dy);

	public abstract String getType();

	public abstract ArrayList<Integer> getPoints();

	public abstract int getMinX();

	public abstract int getMinY();

	public abstract int getWidth();
	
	public abstract int getHeight();

}
