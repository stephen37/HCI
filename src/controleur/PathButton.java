package controleur;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PathButton extends JButton {

	
	private static final long serialVersionUID = 1L;
	File file = new File("./Images/path.gif");
	BufferedImage img;

	public PathButton() {
		this.setSize(30, 30);
		try {
			img = ImageIO.read(file);
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			panel.add(this);
//			panel.add(new JButton("sdqsd"));
			frame.add(panel);
		
			frame.setVisible(true);
			frame.setSize(400, 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawRect(7, 0, 20, 7);
		g2.drawString("this is a test ", 20, 20);
		g2.drawImage(img, 0, 0, null);
		
		
		
	}
}
