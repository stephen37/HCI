package modele;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class PathItem extends CanvasItem {

		public PathItem(PersistentCanvas c, Color o, Color f, Point p) {
			super(c, o, f);
			GeneralPath path = new GeneralPath();
			path.moveTo(p.x, p.y);
			shape = path;
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
			canvas.repaint();
		}

		public void move(int dx, int dy) {
			shape = AffineTransform.getTranslateInstance(dx, dy)
					.createTransformedShape(shape);
			canvas.repaint();
		}

		public void paint(Graphics2D g) {
			drawShape(g);
		}
}
