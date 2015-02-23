package vue;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import modele.CanvasItem;
import modele.PersistentCanvas;
import controleur.EllipseButton;
import controleur.LineButton;
import controleur.PathButton;
import controleur.RectangleButton;
import controleur.SelectMoveButton;

public class ToolBar extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mode;
	JPanel fill;
	JPanel outline;
	ArrayList<JButton> operations;
<<<<<<< HEAD
	PersistentCanvas canvas;
	private CanvasItem selection;
	
=======
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885

	// CanvasItem selection = ge.getSelection();
	// PersistentCanvas canvas = ge.getCanvas();

	public ToolBar() {

		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
<<<<<<< HEAD
		selection = null;
=======
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885
		// panel.setBackground(Color.red);

		// Create the mode selection button list
		mode = "Rectangle";

<<<<<<< HEAD
		//Bouton select/Move
		SelectMoveButton selectMoveButton = new SelectMoveButton();
		selectMoveButton.setPreferredSize(new Dimension(100, 20));
		//Bouton permettant de dessiner des rectangles.		
		RectangleButton rectangleButton = new RectangleButton();
		rectangleButton.setPreferredSize(new Dimension(100, 20));
		//Bouton permettant de dessiner des ellipses
		EllipseButton ellipseButton = new EllipseButton();
		ellipseButton.setPreferredSize(new Dimension(100, 20));
		///bouton permettant de dessiner des lignes droites.
		LineButton lineButton = new LineButton();
		lineButton.setPreferredSize(new Dimension(100, 20));
		//Bouton permettant de dessiner des lignes.
=======
		// ButtonGroup group = new ButtonGroup();
		/*
		 * panel.add(createMode("Select/Move", group));
		 * panel.add(createMode("Rectangle", group));
		 * panel.add(createMode("Ellipse", group)); panel.add(createMode("Line",
		 * group)); panel.add(createMode("Path", group));
		 */
		SelectMoveButton selectMoveButton = new SelectMoveButton();
		selectMoveButton.setPreferredSize(new Dimension(100, 20));
		RectangleButton rectangleButton = new RectangleButton();
		rectangleButton.setPreferredSize(new Dimension(100, 20));
		EllipseButton ellipseButton = new EllipseButton();
		ellipseButton.setPreferredSize(new Dimension(100, 20));
		LineButton lineButton = new LineButton();
		lineButton.setPreferredSize(new Dimension(100, 20));
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885
		PathButton pathButton = new PathButton();
		pathButton.setPreferredSize(new Dimension(100, 20));

		panel.add(selectMoveButton);
		panel.add(rectangleButton);
		panel.add(ellipseButton);
		panel.add(lineButton);
		panel.add(pathButton);

		panel.add(Box.createVerticalStrut(30));
		fill = createColorSample(Color.LIGHT_GRAY);
		panel.add(fill);
		panel.add(Box.createVerticalStrut(10));
		outline = createColorSample(Color.BLACK);
		panel.add(outline);
		panel.add(Box.createVerticalStrut(30));
		operations = new ArrayList<JButton>();
		panel.add(createOperation("Delete"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(createOperation("Clone"));
		panel.add(Box.createVerticalGlue());
<<<<<<< HEAD
		pane.add(panel);
		// this.add(panel);
=======
		// pane.add(panel);
		this.add(panel);
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885
		setVisible(true);
		setResizable(false);
		setSize(100, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private MouseAdapter colorListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			JPanel p = (JPanel) e.getSource();
			Color c = p.getBackground();
			c = JColorChooser.showDialog(null, "Select a color", c);
<<<<<<< HEAD
			System.out.println("Color listener");
			if (selection == null) {
				p.setBackground(c);
			} else if (p == outline) {
				System.out.println("Outline selectionné ! ");
				p.setBackground(c);
				selection.setOutlineColor(c);
			} else if (p == fill) {
				System.out.println("Fill selectionné ! ");
				p.setBackground(c);
				selection.setFillColor(c);
			}

=======
			// TODO Manage the color change

			/*
			 * if (selection == null) { p.setBackground(c); } else if (p ==
			 * outline) { p.setBackground(c); selection.setOutlineColor(c); }
			 * else if (p == fill) { p.setBackground(c);
			 * selection.setFillColor(c); }
			 */
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885
			repaint();
		}

	};

	// Listen the mode changes and update the Title
	private ActionListener modeListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// TODO you can use the function updateTitle();
			mode = e.getActionCommand();

		}
	};

	// Listen the action on the button
	private ActionListener operationListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// if (selection == null)
			// return;

			String op = e.getActionCommand();
			if (op.equals("Delete")) {
<<<<<<< HEAD
				 canvas.removeItem(selection);
				 System.out.println("Canvas : "+ selection);
			} else if (op.equals("Clone")) {
				 CanvasItem clone = selection.duplicate();
				 clone.move(10, 10);
				 canvas.addItem(clone);
=======
				// canvas.removeItem(selection);
			} else if (op.equals("Clone")) {
				// CanvasItem clone = selection.duplicate();
				// clone.move(10, 10);
				// canvas.addItem(clone);
>>>>>>> 1c0dd89a6b93e2891ed05e3dee7d5b993aef5885

				// select(clone);
			}
		}
	};

	private JRadioButton createMode(String description, ButtonGroup group) {
		JRadioButton rbtn = new JRadioButton(description);
		rbtn.setActionCommand(description);
		if (mode == description)
			rbtn.setSelected(true);
		rbtn.addActionListener(modeListener);
		group.add(rbtn);
		return rbtn;
	}

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
		p.setMaximumSize(new Dimension(100, 150));
		p.addMouseListener(colorListener);
		return p;
	}

	public String getMode() {
		return mode;
	}

	public Color getOutlineColor() {
		return outline.getBackground();
	}

	public JPanel getOutlinePanel() {
		return outline;
	}

	public Color getFillColor() {
		return fill.getBackground();
	}

	public JPanel getFillPanel() {
		return fill;
	}

	public ArrayList<JButton> getOperations() {
		return operations;
	}
	
	public void setCanvas(PersistentCanvas can) {
		canvas = can;
		
	}
	
	public void setSelection(CanvasItem select) {
		selection = select;
		System.out.println(select);
	}
}
