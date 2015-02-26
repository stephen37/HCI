// PieMenuItem.java
// Implements a menuitem for the PieMenu class.
// Poika Isokoski 2003.
package vue;

import java.awt.*;

public class PieMenuItem {
	Polygon p;
	Point center;
	String label;
	String function;
	Color background;
	Color foreground;

	PieMenuItem() {
		p = new Polygon();
		label = "";
		function = "";
		background = Color.white;
		foreground = Color.black;
		center = new Point();
	}

	// Getters/Setters
	public void setLabel(String l) {
		label = new String(l);
	}

	public String getLabel() {
		return label;
	}

	public void setFunction(String f) {
		function = new String(f);
	}

	public String getFunction() {
		return function;
	}

	public void setForeground(Color c) {
		foreground = c;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setBackground(Color c) {
		background = c;
	}

	public Color getBackground() {
		return background;
	}

	public void setLabelLocation(Point l) {
		center.setLocation(l);
	}

	public void setCenter(int x, int y) {
		center.setLocation(x, y);
	}

	public Point getCenter() {
		return center;
	}

	// Getters/setters end.

	public void addPoint(int x, int y) {
		p.addPoint(x, y);
	}

	// Item polygon coordinates are relative to the center of the pie menu
	// To be able to draw the menu anywhere on a window, we give the
	// menu center poin to the item draw function, which translates the
	// drawn polygon accordingly.
	public void draw(Graphics g, int x, int y) {
		Polygon windowcoordpoly = new Polygon(p.xpoints, p.ypoints, p.npoints);
		windowcoordpoly.translate(x, y);
		g.setColor(background);
		g.fillPolygon(windowcoordpoly);
		g.setColor(foreground);
		g.drawPolygon(windowcoordpoly);
		FontMetrics fm = g.getFontMetrics();
		g.drawString(label, (int) center.getX() + x - (fm.getHeight() / 4)
				* label.length(), (int) center.getY() + y + fm.getHeight() / 3);
	}

	public boolean hitTest(int x, int y) {
		return p.contains(x, y);
	}

	public void translate(int x, int y) {
		p.translate(x, y);
		center.setLocation(center.getX() + x, center.getY());
	}

	public String toString() {
		return "function=" + function + " label=" + label + " center=" + center
				+ " polygon=" + polygonToString();
	}

	private String polygonToString() {
		String s = new String();
		for (int c = 0; c < p.npoints; c++) {
			s += "<" + p.xpoints[c] + "," + p.ypoints[c] + ">";
		}
		return s;
	}
}
