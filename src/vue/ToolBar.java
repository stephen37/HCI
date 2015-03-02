package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
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
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	public JSpinner spinner;

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
		// panel.setLayout(new BorderLayout());
		JLabel formesLabel = new JLabel(" Formes");
		formesLabel.setForeground(Color.GRAY);
		panel.add(formesLabel);
		panel.add(Box.createVerticalStrut(10));
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

			}
		});
		// /bouton permettant de dessiner des lignes droites.
		LineButton lineButton = new LineButton();
		lineButton.setPreferredSize(new Dimension(100, 20));
		lineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Line";

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
				if (GraphicalEditor.selection.isAnimated) {
					GraphicalEditor.selection.unanimated();
				} else {
					GraphicalEditor.selection.animated();
				}
			}
		});

		// Spinner a rajouté afin de modifier la taille des bordures
		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);
		spinner = new JSpinner(model);
		spinner.setSize(new Dimension(30, 30));
		spinner.setMaximumSize(new Dimension(100, 30));
		// spinner.setPreferredSize(new Dimension(100,20));
		spinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Value has changed ");
				// System.out.println("Graphics ! " +getGraphics().toString());
				// CanvasItem.value = (int) spinner.getValue();
			}
		});

		JCheckBox startHorizontale = new JCheckBox("start", false);
		JCheckBox stopHorizontale = new JCheckBox("stop", true);

		startHorizontale.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (startHorizontale.isSelected()) {
					stopHorizontale.setSelected(false);
					GraphicalEditor.anim.start();
				} else {
					GraphicalEditor.anim.stop();
					stopHorizontale.setSelected(true);
				}
			}
		});

		stopHorizontale.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (stopHorizontale.isSelected()) {
					startHorizontale.setSelected(false);
					GraphicalEditor.anim.stop();
					PersistentCanvas.resumeAnimations();
				}
			}
		});

		JLabel labelCouleurs = new JLabel(" Couleurs");
		labelCouleurs.setForeground(Color.gray);
		panel.add(selectMoveButton);
		panel.add(rectangleButton);
		panel.add(ellipseButton);
		panel.add(lineButton);
		panel.add(pathButton);
		panel.add(animationButton);

		panel.add(Box.createVerticalStrut(10));
		// JSeparator separator = new JSeparator();
		// panel.add(separator);
		panel.add(labelCouleurs);
		panel.add(Box.createVerticalStrut(10));
		panel.add(spinner);

		// panel.add(Box.createVerticalStrut(30));
		
		JPanel couleur = new JPanel();
		couleur.setLayout(new BoxLayout(couleur, BoxLayout.Y_AXIS));
		//couleur.setBackground(Color.BLACK);
		
		JPanel fillPan = new JPanel();
		fillPan.setMaximumSize(new Dimension(80, 50));
		fillPan.setLayout(new BorderLayout());
		
		fill = createColorSample(Color.LIGHT_GRAY);
		fill.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		fillPan.add(fill, BorderLayout.CENTER);
		
		JPanel outPan = new JPanel();
		outPan.setMaximumSize(new Dimension(80, 50));
		outPan.setLayout(new BorderLayout());
		
		outline = createColorSample(Color.BLACK);
		outline.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		outPan.add(outline, BorderLayout.CENTER);
		
	
		couleur.add(outPan);
		couleur.add(Box.createVerticalStrut(10));
		couleur.add(fillPan);
		
		panel.add(couleur);

		

		panel.add(Box.createVerticalStrut(10));
		operations = new ArrayList<JButton>();
		JLabel actionsLabel = new JLabel(" Actions");
		actionsLabel.setForeground(Color.GRAY);
		panel.add(actionsLabel);
		panel.add(Box.createVerticalStrut(7));
		panel.add(createOperation("Delete"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(createOperation(" Clone "));
		JPanel tabPanel = new JPanel();
		tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.X_AXIS));
		JTabbedPane tabbedPanel = new JTabbedPane();
		tabbedPanel.addTab("Actions", null);
		tabbedPanel.addTab("Anim", null);
//		tabPanel.add(tabbedPanel);
//		panel.add(tabPanel);
		JLabel animHorizontaleLabel = new JLabel(" Horizontale");
		animHorizontaleLabel.setForeground(Color.LIGHT_GRAY);
		panel.add(animHorizontaleLabel);
		panel.add(startHorizontale);
		panel.add(stopHorizontale);
		JLabel animVerticaleLabel = new JLabel(" Verticale");
		animVerticaleLabel.setForeground(Color.LIGHT_GRAY);
		panel.add(animVerticaleLabel);
		
		
		
		
		panel.add(Box.createVerticalStrut(150));

		pane.add(panel);
		
		// this.add(panel);
		// pane.add(panel);
		this.add(panel);
		setVisible(true);
		setResizable(false);
		setSize(110, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
				GraphicalEditor.repaintUndo();
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
		btn.setBackground(Color.white);
		btn.setEnabled(false);
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
