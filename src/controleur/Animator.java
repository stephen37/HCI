package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import modele.CanvasItem;
import modele.PersistentCanvas;
import vue.Plane;

/**
 * 
 * @author David Bonnet Modifications J�r�mie Garcia (15-01-2015)
 *
 */
public class Animator extends JPanel implements ActionListener {

	private Timer timer;
	private int delay;
	private PersistentCanvas canvas;
	public static int x;
	public static int y;
	PathAnimation path;
	ArrayList<PathAnimation> paths;
	ArrayList<Plane> planes;

	public Animator(PersistentCanvas canvas) {
		this(50);
		this.canvas = canvas;
		// x = canvas.getX();
		// y = canvas.getY();
	}

	public Animator(int delay) {
		super();
		// Timer
		this.delay = delay;
		timer = new Timer(delay, this);
		// Chemins et avions
		paths = new ArrayList<PathAnimation>();
		planes = new ArrayList<Plane>();
		// Listeners
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				// ************ à modifier ************
				path = new PathAnimation(event.getX(), event.getY());
				paths.add(path);
				// ************************************
				repaint();
			}

			public void mouseReleased(MouseEvent event) {
				// ************ à modifier ************
				Plane plane = new Plane(event.getPoint());
				plane.setPath(path);
//				path.setLeadsToAirport(false);
				planes.add(plane);
				// ************************************
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent event) {
				path.addPoint(event.getX(), event.getY());
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent event) {
				// Rien si la souris bouge sans bouton enfoncé
			}
		});
	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void setDelay(int delay) {
		this.delay = delay;
		timer.setDelay(delay);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		PersistentCanvas.processAnimation();
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
