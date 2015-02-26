// PieMenu.java
// A class to implement a Pie menu. This one is not round - it has angles.
// Poika Isokoski 2003.
package vue;

import java.awt.*;
import java.io.*;
import java.util.*;

public class PieMenu {

	class BadValueException extends Exception {
		public BadValueException(String s) {
			super(s);
		}
	}

	LinkedList<PieMenuItem> slices;
	Point location;
	Point ldlocation; // last drawn location
	int nitems;
	int innerradius;
	int outerradius;
	int delay;

	PieMenu() {
		slices = new LinkedList<PieMenuItem>();
		location = new Point();
		ldlocation = new Point(-1, -1);
	}

	// getters/setters
	public Point getLocation() {
		return location;
	}

	public void setLocation(int x, int y) {
		location.setLocation(x, y);
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int d) {
		delay = d;
	}

	public int getInnerradius() {
		return innerradius;
	}

	public void setInnerradius(int ir) {
		innerradius = ir;
	}

	// getters/setters end.

	public void fromFile(String filename) throws IOException,
			NoSuchElementException, NumberFormatException, BadValueException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line = in.readLine();
		while (line != null) {
			StringTokenizer st = new StringTokenizer(line);
			if (st.hasMoreTokens()) {
				String t = st.nextToken();
				if (t.equals("items")) {
					nitems = -1;
					t = st.nextToken();
					nitems = Integer.parseInt(t);
					if (nitems <= 0) {
						throw new BadValueException(
								"invalid items line in config file \""
										+ filename + "\": (no items).");
					}
				} else if (t.equals("innerradius")) {
					innerradius = -1;
					t = st.nextToken();
					innerradius = Integer.parseInt(t);
					// System.out.println("innerradius="+innerradius);
					if (innerradius < 0) {
						throw new BadValueException(
								"invalid innerradius line in config file \""
										+ filename + "\": (negative radius).");
					}
				} else if (t.equals("outerradius")) {
					outerradius = -1;
					t = st.nextToken();
					outerradius = Integer.parseInt(t);
					if (outerradius <= innerradius) {
						throw new BadValueException(
								"invalid outerradius line in config file \""
										+ filename + "\": (<= innerradius).");
					}
				} else if (t.equals("delay")) {
					delay = 300;
					t = st.nextToken();
					delay = Integer.parseInt(t);
				} else if (t.equals("item")) {
					if (slices.size() < nitems) {
						slices.add(generateSlice(slices.size(), st.nextToken(),
								st.nextToken()));
					}
				}
			}
			line = in.readLine();
		}
	}

	// this should not be called unless all other menu parameters have already
	// been set
	// to sensible values (including the radii and nitems).
	private PieMenuItem generateSlice(int number, String function, String label) {
		double slicewidth = 2 * Math.PI / nitems;
		// System.out.println("generateSlice(): Slicewidth="+slicewidth);
		double angle1 = number * slicewidth - slicewidth / 2;
		double angle2 = number * slicewidth + slicewidth / 2;
		// System.out.println("generateSlice(): angle1="+angle1+" angle2="+angle2);
		PieMenuItem i = new PieMenuItem();
		i.setFunction(function);
		i.setLabel(label);
		i.addPoint((int) (Math.sin(angle1) * (double) innerradius),
				(int) (Math.cos(angle1) * (double) innerradius));
		i.addPoint((int) (Math.sin(angle1) * (double) outerradius),
				(int) (Math.cos(angle1) * (double) outerradius));
		i.addPoint(
				(int) (Math.sin((angle2 + angle1) / 2) * (double) outerradius),
				(int) (Math.cos((angle2 + angle1) / 2) * (double) outerradius));
		i.addPoint((int) (Math.sin(angle2) * (double) outerradius),
				(int) (Math.cos(angle2) * (double) outerradius));
		i.addPoint((int) (Math.sin(angle2) * (double) innerradius),
				(int) (Math.cos(angle2) * (double) innerradius));
		i.addPoint(
				(int) (Math.sin((angle2 + angle1) / 2) * (double) innerradius),
				(int) (Math.cos((angle2 + angle1) / 2) * (double) innerradius));
		double labelangle = (double) number * slicewidth;
		double labelradius = innerradius + (outerradius - innerradius) / 2;
		i.setCenter((int) (Math.sin(labelangle) * labelradius),
				(int) (Math.cos(labelangle) * labelradius));
		return i;
	}

	public boolean draw(Graphics g) {
		return draw(g, (int) location.getX(), (int) location.getY());
	}

	public boolean draw(Graphics g, int x, int y) {
		// if(ldlocation.getX()!=x || ldlocation.getY()!=y){
		ListIterator<PieMenuItem> li = slices.listIterator();
		while (li.hasNext()) {
			PieMenuItem item = ((PieMenuItem) li.next());
			item.draw(g, x, y);
		}
		ldlocation.setLocation(x, y);
		return true;
		// }
		// return false;
	}

	public void drawFeedback(Graphics g, int x, int y) {
		PieMenuItem item = hitTestItem(x, y);
		if (item != null) {
			Point c = item.getCenter();
			// g.setColor(Color.red);
			Color bg = item.getBackground();
			Color fg = item.getForeground();
			item.setBackground(fg);
			item.setForeground(bg);
			item.draw(g, (int) location.getX(), (int) location.getY());
			item.setBackground(bg);
			item.setForeground(fg);
			// g.drawLine(location.x,location.y,(int)(location.x+c.getX()),(int)(location.y+c.getY()));
		}
	}

	public String hitTest(int x, int y) {
		PieMenuItem item = hitTestItem(x, y);
		if (item != null) {
			return item.getFunction();
		}
		return null;
	}

	public PieMenuItem hitTestItem(int x, int y) {
		ListIterator<PieMenuItem> li = slices.listIterator();
		while (li.hasNext()) {
			PieMenuItem item = ((PieMenuItem) li.next());
			if (item.hitTest((int) (x - location.getX()),
					(int) (y - location.getY()))) {
				return item;
			}
		}
		return null;
	}

	public void resetRedraw() {
		ldlocation.setLocation(-1, -1);
	}

	public PieMenuItem findItemByFunction(String fstring) {
		ListIterator<PieMenuItem> li = slices.listIterator();
		while (li.hasNext()) {
			PieMenuItem item = (PieMenuItem) li.next();
			if (item.getFunction().equals(fstring)) {
				return item;
			}
		}
		return null;
	}
}
