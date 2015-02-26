package controleur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import modele.PersistentCanvas;

/**
 * 
 * @author David Bonnet
 * Modifications J�r�mie Garcia (15-01-2015)
 *
 */
public class Animator implements ActionListener {

	private Timer timer;
	private int delay;
	private PersistentCanvas canvas;
	public static int x;
	public static int y;

	public Animator(PersistentCanvas canvas) {
		this(250);
		this.canvas = canvas;
//		x = canvas.getX();
//		y = canvas.getY();
	}

	public Animator(int delay) {
		super();
		// Timer
		this.delay = delay;
		timer = new Timer(delay, this);
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
