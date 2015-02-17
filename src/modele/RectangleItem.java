package modele;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 * 
 */
public class RectangleItem extends CanvasItem {

	Point firstpoint;

	public RectangleItem(PersistentCanvas c, Color o, Color f, Point p) {
		super(c, o, f);
		shape = new Rectangle(p.x, p.y, 0, 0);
		firstpoint = p;
	}

	public RectangleItem(RectangleItem other) {
		super(other.canvas, other.outline, other.fill);
		shape = new Rectangle((Rectangle) other.shape);
		isSelected = false;
		firstpoint = other.firstpoint;
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new RectangleItem(this));
	}

	public void update(Point p) {
		((Rectangle) shape).setFrameFromDiagonal(firstpoint, p);
		canvas.repaint();
	}

	public void move(int dx, int dy) {
		((Rectangle) shape).x += dx;
		((Rectangle) shape).y += dy;
		canvas.repaint();
	}

}
