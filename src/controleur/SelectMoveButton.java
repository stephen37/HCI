package controleur;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class SelectMoveButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	File file = new File("./Images/mouse.jpg");
	BufferedImage img;

	public SelectMoveButton() {
		this.setSize(30, 30);
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
//		g2.drawRect(7, 0, 20, 7);
		g2.drawImage(img, 0, 0, null);
		
		
		
	}
}
