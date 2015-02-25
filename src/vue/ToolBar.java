package vue;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javafx.animation.Animation;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.sun.javafx.geom.Rectangle;

import modele.CanvasItem;
import modele.PersistentCanvas;
import controleur.AnimationButton;
import controleur.EllipseButton;
import controleur.LineButton;
import controleur.PathButton;
import controleur.RectangleButton;
import controleur.SelectMoveButton;

public class ToolBar extends JFrame {

	private static final long serialVersionUID = 1L;
	private String mode;
	JPanel fill;
	JPanel outline;
	ArrayList<JButton> operations;

	PersistentCanvas canvas;
	private CanvasItem selection;

	// CanvasItem selection = ge.getSelection();
	// PersistentCanvas canvas = ge.getCanvas();

	public ToolBar() {

		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		selection = null;
		// panel.setBackground(Color.red);

		// Create the mode selection button list
		mode = "Rectangle";

		// Bouton select/Move
		SelectMoveButton selectMoveButton = new SelectMoveButton();
		selectMoveButton.setPreferredSize(new Dimension(100, 20));
		selectMoveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GraphicalEditor.mode = "Select/Move";

//				System.out.println(GraphicalEditor.title + " - "
//						+ GraphicalEditor.mode);
//				System.out.println(GraphicalEditor.title + " - "
//						+ GraphicalEditor.newMode);
//				if (GraphicalEditor.mode != GraphicalEditor.newMode) {
//					System.out.println("trololo");
//					GraphicalEditor.newMode = GraphicalEditor.mode;
//				}

			}
		});
		// Bouton permettant de dessiner des rectangles.
		RectangleButton rectangleButton = new RectangleButton();
		rectangleButton.setPreferredSize(new Dimension(100, 20));
		rectangleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GraphicalEditor.mode = "Rectangle";
//				System.out.println(GraphicalEditor.title + " - "
//						+ GraphicalEditor.mode);
//				System.out.println(GraphicalEditor.title + " - "
//						+ GraphicalEditor.newMode);
//				if (GraphicalEditor.mode != GraphicalEditor.newMode) {
//					System.out.println("trololo");
//					GraphicalEditor.newMode = GraphicalEditor.mode;
//				}
			}
		});
		// Bouton permettant de dessiner des ellipses
		EllipseButton ellipseButton = new EllipseButton();
		ellipseButton.setPreferredSize(new Dimension(100, 20));
		ellipseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GraphicalEditor.mode = "Ellipse";
				// System.out.println(GraphicalEditor.title + " - " +
				// GraphicalEditor.mode);
				// System.out.println(GraphicalEditor.title + " - " +
				// GraphicalEditor.newMode);
				// if(GraphicalEditor.mode != GraphicalEditor.newMode){
				// System.out.println("trololo");
				// GraphicalEditor.newMode = GraphicalEditor.mode;
				// }
			}
		});
		// /bouton permettant de dessiner des lignes droites.
		LineButton lineButton = new LineButton();
		lineButton.setPreferredSize(new Dimension(100, 20));
		lineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Line";
				// System.out.println(GraphicalEditor.title + " - " +
				// GraphicalEditor.mode);
				// System.out.println(GraphicalEditor.title + " - " +
				// GraphicalEditor.newMode);
				// if(GraphicalEditor.mode != GraphicalEditor.newMode){
				// System.out.println("trololo");
				// GraphicalEditor.newMode = GraphicalEditor.mode;
				// }
			}
		});
		// Bouton permettant de dessiner des lignes.
		// ButtonGroup group = new ButtonGroup();

		PathButton pathButton = new PathButton();
		pathButton.setPreferredSize(new Dimension(100, 20));
		pathButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GraphicalEditor.mode = "Path";
				// System.out.println(GraphicalEditor.title + " - " +
				// GraphicalEditor.mode);
				// System.out.println(GraphicalEditor.title + " - " +
				// GraphicalEditor.newMode);
				// if(GraphicalEditor.mode != GraphicalEditor.newMode){
				// System.out.println("trololo");
				// GraphicalEditor.newMode = GraphicalEditor.mode;
				// }
			}
		});

		// Bouton permettant de faire clignoter un item
		AnimationButton animationButton = new AnimationButton();
		animationButton.setPreferredSize(new Dimension(100, 20));
		animationButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GraphicalEditor.mode = "Animation";
				GraphicalEditor.deselect(GraphicalEditor.selection);
				// System.out.println(GraphicalEditor.title + " - " +
				// GraphicalEditor.mode);
				// System.out.println(GraphicalEditor.title + " - " +
				// GraphicalEditor.newMode);
				// if(GraphicalEditor.mode != GraphicalEditor.newMode){
				// System.out.println("trololo");
				// GraphicalEditor.newMode = GraphicalEditor.mode;
				// }
			}
		});

		panel.add(selectMoveButton);
		panel.add(rectangleButton);
		panel.add(ellipseButton);
		panel.add(lineButton);
		panel.add(pathButton);
		panel.add(animationButton);

		JLabel couleurInterieure = new JLabel("Intérieure");
		JLabel couleurExterieure = new JLabel("Extérieure");
		
		couleurExterieure.setBackground(Color.blue);
		couleurInterieure.setBackground(Color.red);
		
		panel.add(couleurInterieure);
		panel.add(Box.createVerticalStrut(30));
		fill = createColorSample(Color.LIGHT_GRAY);
		panel.add(fill);
		panel.add(couleurExterieure);
		panel.add(Box.createVerticalStrut(10));
		outline = createColorSample(Color.BLACK);
		panel.add(outline);
		panel.add(Box.createVerticalStrut(30));
		operations = new ArrayList<JButton>();
		panel.add(createOperation("Delete"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(createOperation("Clone"));
		panel.add(Box.createVerticalGlue());

		pane.add(panel);
		// this.add(panel);
		// pane.add(panel);
		this.add(panel);
		setVisible(true);
		setResizable(false);
		setSize(100, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// Deselect an Item
	public void deselect(CanvasItem item) {
		if (GraphicalEditor.selection != null) {
			GraphicalEditor.selection.deselect();
		}
		for (JButton op : GraphicalEditor.operations) {
			op.setEnabled(false);
		}
	}

	private MouseAdapter colorListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			JPanel p = (JPanel) e.getSource();
			Color c = p.getBackground();
			c = JColorChooser.showDialog(null, "Select a color", c);
			if (GraphicalEditor.selection == null) {
				p.setBackground(c);
			} else if (p == outline) {
				p.setBackground(c);
				GraphicalEditor.selection.setOutlineColor(c);
			} else if (p == fill) {
				p.setBackground(c);
				GraphicalEditor.selection.setFillColor(c);
			}

			/*
			 * if (selection == null) { p.setBackground(c); } else if (p ==
			 * outline) { p.setBackground(c); selection.setOutlineColor(c); }
			 * else if (p == fill) { p.setBackground(c);
			 * selection.setFillColor(c); }
			 */
			repaint();
		}

	};

	// Listen the mode changes
	private ActionListener modeListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
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
				GraphicalEditor.canvas.removeItem(GraphicalEditor.selection);
				GraphicalEditor.deselect(GraphicalEditor.selection);
			} else if (op.equals("Clone")) {
				CanvasItem clone = GraphicalEditor.selection.duplicate();
				clone.move(10, 10);
				GraphicalEditor.select(clone);
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

	private JButton createModeButton(String description) {
		JButton btn = new JButton();
		if (mode == description) {
			btn.setSelected(true);
		}
		btn.addActionListener(modeListener);
		return btn;
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
