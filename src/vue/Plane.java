package vue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controleur.PathAnimation;

public class Plane {
	
	Point2D location;
	double angle;
	double speed;
	PathAnimation path;
	double scale;
	BufferedImage image;
	Area selectionRing;
	boolean selected;
	
	public Plane(int x, int y) {
		this("/home/stephen/Documents/Developpement/ProgInteractive/TP7/Animations/images/plane.png", new Point2D.Double(x, y), 0, 0.5d, 2d);
	}
	
	public Plane(Point2D point) {
		this("/home/stephen/Documents/Developpement/ProgInteractive/TP7/Animations/images/plane.png", point, 0, 0.5d, 2d);
	}
	
	public Plane(Point2D point, double angle) {
		this("/home/stephen/Documents/Developpement/ProgInteractive/TP7/Animations/images/plane.png", point, angle, 0.5d, 2d);
	}
	
	public Plane (String imgPath, Point2D point, double angle, double scale, double speed) {
		try {
			this.image = ImageIO.read(new File(imgPath));
			this.location = (Point2D) point.clone();
			this.angle = angle;
			this.scale = scale;
			this.speed = speed;
			this.selectionRing = new Area(new Ellipse2D.Double(0, 0, image.getWidth(), image.getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean progress() {
		if (path != null) {
			// L'avion avance sur le chemin
			boolean done = path.progressBy(speed);
			location.setLocation(path.getLocation());
			angle = path.getAngle();
			return done;
		} else {
			// L'avion avance dans la direction actuelle
			double x = Math.cos(angle)*speed + location.getX();
			double y = Math.sin(angle)*speed + location.getY();
			location.setLocation(x, y);
			return false;
		}
	}
	
	public void draw(Graphics2D g2d) {
		AffineTransform at = getAffineTransform();
		if (selected) {
			g2d.setColor(new Color(60, 170, 50));
			g2d.fill(selectionRing.createTransformedArea(at));
		}
		g2d.drawImage(image, at, null);
	}
	
	public boolean contains(Point2D point) {
		return selectionRing.createTransformedArea(getAffineTransform()).contains(point);
	}
	
	public PathAnimation getPath() {
		return path;
	}
	
	public void setPath(PathAnimation path) {
		this.path = path;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public Point2D getLocation() {
		return location;
	}

	public double getAngle() {
		return angle;
	}

	public double getSpeed() {
		return speed;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public AffineTransform getAffineTransform() {
		AffineTransform at = new AffineTransform();
		// Translate
		at.translate(location.getX(), location.getY());
		// Rotate
		at.rotate(angle+Math.PI/2);
		// Scale
		at.scale(scale, scale);
		// Center
		at.translate(-image.getWidth() / 2f, -image.getHeight() / 2f);
		return at;
	}
}
