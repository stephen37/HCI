package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import sun.java2d.loops.DrawRect;

import java.awt.Container;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

<<<<<<< HEAD
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import modele.CanvasItem;
import modele.CercleItem;
import modele.LineItem;
import modele.PathItem;
import modele.PersistentCanvas;
import modele.RectangleItem;

=======
import modele.*;

/**
 * @author Nicolas Roussel (roussel@lri.fr) Modified by Cedric Fleury
 *         (cfleury@lri.fr) - 18.10.2013
 */
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885
@SuppressWarnings("serial")
public class GraphicalEditor extends JFrame {

	// Graphical Interface
	private ArrayList<JButton> operations;

	private Point mousepos; // Stores the previous mouse position

	private String title; // Changes according to the mode

	public PersistentCanvas canvas; // Stores the created items
	private CanvasItem selection; // Stores the selected item
<<<<<<< HEAD
	ToolBar toolbar;
	String mode;
	JPanel outline;
	JPanel fill;

	// Constructor of the Graphical Editor

	public GraphicalEditor(String theTitle, int width, int height, ToolBar tool) {
=======

	// Constructor of the Graphical Editor
	public GraphicalEditor(String theTitle, int width, int height) {
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885
		title = theTitle;
		selection = null;
		toolbar = tool;
		mode = toolbar.getMode();
		outline = toolbar.getOutlinePanel();
		fill = toolbar.getFillPanel();
		operations = toolbar.getOperations();
		

		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Create the canvas for drawing
		canvas = new PersistentCanvas();
		canvas.setBackground(Color.WHITE);
		canvas.setPreferredSize(new Dimension(width, height));
		pane.add(canvas);

		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				Color o = toolbar.getOutlineColor();
				Color f = toolbar.getFillColor();
				CanvasItem item = null;

				if (mode.equals("Select/Move")
						&& SwingUtilities.isLeftMouseButton(e)) {
					// TODO you can use the function select(CanvasItem item);
					select(canvas.getItemAt(p));
<<<<<<< HEAD
					// SELECTION RECTANGLE
=======
//SELECTION RECTANGLE 
					/*
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885
				} else if (mode.equals("Select/Move")
						&& SwingUtilities.isRightMouseButton(e)) {
					select(canvas.getItemAt(p));
					item = new RectangleItem(canvas, o, f, p);
					System.out.println(canvas.addItem(item));
					select(item);
<<<<<<< HEAD
					// FIN
=======
					*/
//FIN
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885
				} else {

					if (mode.equals("Rectangle")) {
						item = new RectangleItem(canvas, o, f, p);
					} else if (mode.equals("Ellipse")) {
						// TODO create a new ellipse
						item = new CercleItem(canvas, o, f, p);
					} else if (mode.equals("Line")) {
						// TODO create a new line
						item = new LineItem(canvas, o, f, p);
					} else if (mode.equals("Path")) {
						// TODO create a new path
						item = new PathItem(canvas, o, f, p);
					}
					canvas.addItem(item);
					select(item);
				}
				mousepos = p;
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (selection == null)
					return;
				if (mode.equals("Select/Move")) {
					// TODO move the selected object
					selection.move(e.getX() - mousepos.x, e.getY() - mousepos.y);
				} else {
					selection.update(e.getPoint());
				}
				mousepos = e.getPoint();
			}
		});

		// pane.addKeyListener(keyboardListener);
		pack();
		updateTitle();
		setVisible(true);
<<<<<<< HEAD
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(150, 0);
=======

>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885
	}

	

	// Listen the mode changes and update the Title
	private ActionListener modeListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// TODO you can use the function updateTitle();
			mode = e.getActionCommand();
			updateTitle();

		}
	};

	// Listen the action on the button
	private ActionListener operationListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (selection == null)
				return;

			String op = e.getActionCommand();
			if (op.equals("Delete")) {
				// TODO delete the selected item
				canvas.removeItem(selection);
				deselect(selection);
			} else if (op.equals("Clone")) {
				// TODO duplicate and translate the selected item
				CanvasItem clone = selection.duplicate();
				clone.move(10, 10);
				canvas.addItem(clone);

				select(clone);
			}
		}
	};

	private KeyListener keyboardListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Touche : " + e.getKeyChar());

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Touche : " + e.getKeyChar());

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Touche : " + e.getKeyChar());

		}
	};

	// Listen the click on the color chooser
	private MouseAdapter colorListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			JPanel p = (JPanel) e.getSource();
			Color c = p.getBackground();
			c = JColorChooser.showDialog(null, "Select a color", c);
			// TODO Manage the color change

			// if (p.getName().equals("outline")) {
			// System.out.println("Outline");
			// }
			// if (p.getName().equals("fill")) {
			// System.out.println("fill ");
			if (c == null) {
				// JOptionPane.showMessageDialog(GraphicalEditor.this,
				// "ColorChooser Canceled !");
			} else {
				p.setBackground(c);
				// System.out.println("Choosen color : " + c);
				repaint();
			}
			// }
			// You can test if the action have been done
			// on the fill JPpanel or on the outline JPanel

			if (selection == null) {
				p.setBackground(c);
			} else if (p == outline) {
				p.setBackground(c);
				selection.setOutlineColor(c);
			} else if (p == fill) {
				p.setBackground(c);
				selection.setFillColor(c);
			}
			repaint();
		}

	};

	// Create the radio button for the mode
	private JRadioButton createMode(String description, ButtonGroup group) {
		JRadioButton rbtn = new JRadioButton(description);
		rbtn.setActionCommand(description);
		if (mode == description)
			rbtn.setSelected(true);
		rbtn.addActionListener(modeListener);
		group.add(rbtn);
		return rbtn;
	}

	// Create the button for the operation
	private JButton createOperation(String description) {
		JButton btn = new JButton(description);
		btn.setActionCommand(description);
		btn.addActionListener(operationListener);
		operations.add(btn);
		return btn;
	}

	// Create the color sample used to choose the color
	private JPanel createColorSample(Color c) {
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.setOpaque(true);
		p.setBackground(c);
		p.setMaximumSize(new Dimension(500, 150));
		p.addMouseListener(colorListener);
		return p;
	}

	// Update the Title
	private void updateTitle() {
		setTitle(title + " - " + mode);
	}

	
	// Deselect an Itam
	private void deselect(CanvasItem item){
		if(selection != null){
			selection.deselect();
		}
		for(JButton op : operations){
			op.setEnabled(false);
		}
	}
	
	
	// Select an Item
	private void select(CanvasItem item) {
		if (selection != null)
			selection.deselect();

		selection = item;
		if (selection != null) {
			selection.select();
			// TODO set the color of the fill and outline JPanel
			// to the color of the selected object
			for (JButton op : operations)
				op.setEnabled(true);
		} else {
			for (JButton op : operations)
				op.setEnabled(false);
		}
	}
	public CanvasItem getSelection() {
		return selection;

	}

	public PersistentCanvas getCanvas() {
		return canvas;
	}
}
