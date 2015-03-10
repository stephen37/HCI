
package vue;

import java.awt.BorderLayout;
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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import modele.CanvasItem;
import modele.PersistentCanvas;
import controleur.BlinkButton;
import controleur.EllipseButton;
import controleur.HorizontalButton;
import controleur.LineButton;
import controleur.PathButton;
import controleur.RectangleButton;
import controleur.SelectMoveButton;
import controleur.VerticalButton;

/**
 * @author stephen BATIFOL
 *
 */
@SuppressWarnings("unused")
public class ToolBar extends JFrame {

	private static final long serialVersionUID = 1L;
	private String mode;
	JPanel fill;
	JPanel outline;
	ArrayList<JButton> operations;
	JPanel panel;

	PersistentCanvas canvas;

	private CanvasItem selection;
	public JSpinner spinner;

	public ToolBar() {
		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		selection = null;
		JLabel formesLabel = new JLabel(" Formes");
		formesLabel.setForeground(Color.GRAY);
		panel.add(formesLabel);
		panel.add(Box.createVerticalStrut(10));
		mode = "Rectangle";
		init();
		pane.add(panel);
		this.add(panel);
		setVisible(true);
		setResizable(true);
		setSize(120, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void init() {
		SelectMoveButton selectMoveButton = new SelectMoveButton();
		selectMoveButton.setPreferredSize(new Dimension(100, 20));
		selectMoveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Select/Move";

			}
		});
		// Bouton permettant de dessiner des rectangles.
		RectangleButton rectangleButton = new RectangleButton();
		rectangleButton.setPreferredSize(new Dimension(100, 20));
		rectangleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Rectangle";
			}
		});
		// Bouton permettant de dessiner des ellipses
		EllipseButton ellipseButton = new EllipseButton();
		ellipseButton.setPreferredSize(new Dimension(100, 20));
		ellipseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
		PathButton pathButton = new PathButton();
		pathButton.setPreferredSize(new Dimension(100, 20));
		pathButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Path";

			}
		});

		BlinkButton BlinkButton = new BlinkButton();
		BlinkButton.setPreferredSize(new Dimension(100, 20));


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

		JLabel labelCouleurs = new JLabel(" Couleurs");
		labelCouleurs.setForeground(Color.gray);
		panel.add(selectMoveButton);
		panel.add(rectangleButton);
		panel.add(ellipseButton);
		panel.add(lineButton);
		panel.add(pathButton);

		panel.add(Box.createVerticalStrut(10));
		panel.add(labelCouleurs);
		panel.add(Box.createVerticalStrut(10));
	
		JPanel couleur = new JPanel();
		couleur.setLayout(new BoxLayout(couleur, BoxLayout.Y_AXIS));

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
		panel.add(createOperation("Delete "));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(createOperation(" Clone "));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(createOperation("Resize"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		JPanel tabPanel = new JPanel();
		tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.X_AXIS));
		JTabbedPane tabbedPanel = new JTabbedPane();
		tabbedPanel.addTab("Actions", null);
		tabbedPanel.addTab("Anim", null);
		// tabPanel.add(tabbedPanel);
		// panel.add(tabPanel);

		JLabel animLabel = new JLabel("Animation");
		animLabel.setForeground(Color.LIGHT_GRAY);
		JPanel animPanel = new JPanel();
		animPanel.setLayout(new BoxLayout(animPanel, BoxLayout.Y_AXIS));
		panel.add(animLabel);
		panel.add(Box.createVerticalStrut(7));

		HorizontalButton horizontalButton = new HorizontalButton();
		horizontalButton.setSize(new Dimension(30, 30));

		//animPanel.setLayout(new GridLayout(3, 2, 0, 0));
		animPanel.setLayout(new BoxLayout(animPanel, BoxLayout.Y_AXIS));
		panel.add(animLabel);
		panel.add(Box.createVerticalStrut(7));
		
//		horizontalButton.setMaximumSize(new Dimension(30, 30));
//		horizontalPanel.setSize(new Dimension(30, 30));
		horizontalButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Horizontal";
			}
		});

		animPanel.add(horizontalButton);


		animPanel.add(Box.createVerticalStrut(5));
		VerticalButton verticalButton = new VerticalButton();
		verticalButton.setSize(new Dimension(30, 30));
//		JLabel hori = new JLabel("Horizontal");
//		hori.setSize(30, 30);
//		hori.setMaximumSize(new Dimension(30, 30));
//		animPanel.add(hori);
		animPanel.add(Box.createVerticalStrut(5));
//		verticalButton.setMaximumSize(new Dimension(30, 30));
//		verticalPanel.setSize(new Dimension(30, 30));
		verticalButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Vertical";
			}
		});
		animPanel.add(verticalButton);


//		JLabel vert = new JLabel("Vertical");
//		vert.setSize(30, 30);
//		vert.setMaximumSize(new Dimension(30, 30));
//		animPanel.add(vert);

		animPanel.add(Box.createVerticalStrut(5));
		BlinkButton blinkButton = new BlinkButton();
		blinkButton.setSize(new Dimension(30, 30));

//		blinkButton.setMaximumSize(new Dimension(30, 30));
//		blinkPanel.setSize(new Dimension(30, 30));

		blinkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Blink";
			}
		});
		animPanel.add(blinkButton);

		JCheckBox startCheckBox = new JCheckBox("Start", false);
		JCheckBox stopCheckBox = new JCheckBox("Stop", true);

//		JLabel blin = new JLabel("Blink");
//		blin.setSize(30, 30);
//		blin.setMaximumSize(new Dimension(30, 30));
//		animPanel.add(blin);
		
		startCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (startCheckBox.isSelected()) {
					stopCheckBox.setSelected(false);
					GraphicalEditor.anim.start();
				} else {
					GraphicalEditor.anim.stop();
					stopCheckBox.setSelected(true);
				}
			}
		});

		stopCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stopCheckBox.isSelected()) {
					startCheckBox.setSelected(false);
					GraphicalEditor.anim.stop();
				} else {
					stopCheckBox.setSelected(true);
					GraphicalEditor.anim.stop();
				}
			}
		});

		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		checkBoxPanel.add(startCheckBox);
		checkBoxPanel.add(stopCheckBox);
		panel.add(animPanel);
		panel.add(checkBoxPanel);
		panel.add(Box.createVerticalGlue());
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

	//Listener permettant de savoir si la couleur a été changée.
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
			repaint();
		}
	};

	// Listen the action on the button
	private ActionListener operationListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String op = e.getActionCommand();
			if (op.equals("Delete ")) {
				GraphicalEditor.canvas.removeItem(GraphicalEditor.selection);
				GraphicalEditor.deselect(GraphicalEditor.selection);
				GraphicalEditor.repaintUndo();
			} else if (op.equals(" Clone ")) {
				CanvasItem clone = GraphicalEditor.selection.duplicate();
				clone.move(10, 10);
				GraphicalEditor.select(clone);
			} else if (op.equals("Resize")) {
				GraphicalEditor.mode = "Resize";
			}
		}
	};

	//Crée un bouton permettant de faire une opération et lui ajoute un listener
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
//package vue;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.ArrayList;
//
//import javax.swing.BorderFactory;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JColorChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JSpinner;
//import javax.swing.JTabbedPane;
//
//import modele.CanvasItem;
//import modele.PersistentCanvas;
//import controleur.BlinkButton;
//import controleur.EllipseButton;
//import controleur.HorizontalButton;
//import controleur.LineButton;
//import controleur.PathButton;
//import controleur.RectangleButton;
//import controleur.SelectMoveButton;
//import controleur.VerticalButton;
//
///**
// * @author stephen BATIFOL
// *
// */
//@SuppressWarnings("unused")
//public class ToolBar extends JFrame {
//
//	private static final long serialVersionUID = 1L;
//	private String mode;
//	JPanel fill;
//	JPanel outline;
//	ArrayList<JButton> operations;
//	JPanel panel;
//
//	PersistentCanvas canvas;
//
//	private CanvasItem selection;
//	public JSpinner spinner;
//
//	public ToolBar() {
//		Container pane = getContentPane();
//		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
//		panel = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		selection = null;
//		JLabel formesLabel = new JLabel(" Formes");
//		formesLabel.setForeground(Color.GRAY);
//		panel.add(formesLabel);
//		panel.add(Box.createVerticalStrut(10));
//		mode = "Rectangle";
//		init();
//		pane.add(panel);
//		this.add(panel);
//		setVisible(true);
//		setResizable(true);
//		setSize(120, 800);
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//	}
//
//	public void init() {
//		SelectMoveButton selectMoveButton = new SelectMoveButton();
//		selectMoveButton.setPreferredSize(new Dimension(100, 20));
//		selectMoveButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GraphicalEditor.mode = "Select/Move";
//
//			}
//		});
//		// Bouton permettant de dessiner des rectangles.
//		RectangleButton rectangleButton = new RectangleButton();
//		rectangleButton.setPreferredSize(new Dimension(100, 20));
//		rectangleButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GraphicalEditor.mode = "Rectangle";
//			}
//		});
//		// Bouton permettant de dessiner des ellipses
//		EllipseButton ellipseButton = new EllipseButton();
//		ellipseButton.setPreferredSize(new Dimension(100, 20));
//		ellipseButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GraphicalEditor.mode = "Ellipse";
//
//			}
//		});
//		// /bouton permettant de dessiner des lignes droites.
//		LineButton lineButton = new LineButton();
//		lineButton.setPreferredSize(new Dimension(100, 20));
//		lineButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GraphicalEditor.mode = "Line";
//
//			}
//		});
//		PathButton pathButton = new PathButton();
//		pathButton.setPreferredSize(new Dimension(100, 20));
//		pathButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GraphicalEditor.mode = "Path";
//
//			}
//		});
//
//		BlinkButton BlinkButton = new BlinkButton();
//		BlinkButton.setPreferredSize(new Dimension(100, 20));
//
//		// Spinner a rajouté afin de modifier la taille des bordures
//		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);
//		spinner = new JSpinner(model);
//		spinner.setSize(new Dimension(30, 30));
//		spinner.setMaximumSize(new Dimension(100, 30));
//		// spinner.setPreferredSize(new Dimension(100,20));
//		spinner.addChangeListener(new ChangeListener() {
//
//			@Override
//			public void stateChanged(ChangeEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println("Value has changed ");
//				// System.out.println("Graphics ! " +getGraphics().toString());
//				// CanvasItem.value = (int) spinner.getValue();
//			}
//		});
//
//
//		JLabel labelCouleurs = new JLabel(" Couleurs");
//		labelCouleurs.setForeground(Color.gray);
//		panel.add(selectMoveButton);
//		panel.add(rectangleButton);
//		panel.add(ellipseButton);
//		panel.add(lineButton);
//		panel.add(pathButton);
//
//		panel.add(Box.createVerticalStrut(10));
//		panel.add(labelCouleurs);
//		panel.add(Box.createVerticalStrut(10));
//	
//		JPanel couleur = new JPanel();
//		couleur.setLayout(new BoxLayout(couleur, BoxLayout.Y_AXIS));
//
//		JPanel fillPan = new JPanel();
//		fillPan.setMaximumSize(new Dimension(80, 50));
//		fillPan.setLayout(new BorderLayout());
//
//		fill = createColorSample(Color.LIGHT_GRAY);
//		fill.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
//
//		fillPan.add(fill, BorderLayout.CENTER);
//
//		JPanel outPan = new JPanel();
//		outPan.setMaximumSize(new Dimension(80, 50));
//		outPan.setLayout(new BorderLayout());
//
//		outline = createColorSample(Color.BLACK);
//		outline.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
//
//		outPan.add(outline, BorderLayout.CENTER);
//
//		couleur.add(outPan);
//		couleur.add(Box.createVerticalStrut(10));
//		couleur.add(fillPan);
//
//		panel.add(couleur);
//
//		panel.add(Box.createVerticalStrut(10));
//		operations = new ArrayList<JButton>();
//		JLabel actionsLabel = new JLabel(" Actions");
//		actionsLabel.setForeground(Color.GRAY);
//		panel.add(actionsLabel);
//		panel.add(Box.createVerticalStrut(7));
//		panel.add(createOperation("Delete "));
//		panel.add(Box.createRigidArea(new Dimension(0, 5)));
//		panel.add(createOperation(" Clone "));
//		panel.add(Box.createRigidArea(new Dimension(0, 5)));
//		panel.add(createOperation("Resize"));
//		panel.add(Box.createRigidArea(new Dimension(0, 5)));
//		JPanel tabPanel = new JPanel();
//		tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.X_AXIS));
//		JTabbedPane tabbedPanel = new JTabbedPane();
//		tabbedPanel.addTab("Actions", null);
//		tabbedPanel.addTab("Anim", null);
//		// tabPanel.add(tabbedPanel);
//		// panel.add(tabPanel);
//
//		JLabel animLabel = new JLabel("Animation");
//		animLabel.setForeground(Color.LIGHT_GRAY);
//		JPanel animPanel = new JPanel();
//<<<<<<< HEAD
//		animPanel.setLayout(new BoxLayout(animPanel, BoxLayout.Y_AXIS));
//		panel.add(animLabel);
//		panel.add(Box.createVerticalStrut(7));
//
//		HorizontalButton horizontalButton = new HorizontalButton();
//		horizontalButton.setSize(new Dimension(30, 30));
//=======
//		//animPanel.setLayout(new GridLayout(3, 2, 0, 0));
//		animPanel.setLayout(new BoxLayout(animPanel, BoxLayout.Y_AXIS));
//		panel.add(animLabel);
//		panel.add(Box.createVerticalStrut(7));
//		
//		HorizontalButton horizontalButton = new HorizontalButton();
//		horizontalButton.setSize(new Dimension(30, 30));
////		horizontalButton.setMaximumSize(new Dimension(30, 30));
////		horizontalPanel.setSize(new Dimension(30, 30));
//>>>>>>> ea097fe6958d389489c312a6a6f89e7724bc5377
//		horizontalButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GraphicalEditor.mode = "Horizontal";
//			}
//		});
//
//		animPanel.add(horizontalButton);
//
//<<<<<<< HEAD
//		animPanel.add(Box.createVerticalStrut(5));
//		VerticalButton verticalButton = new VerticalButton();
//		verticalButton.setSize(new Dimension(30, 30));
//=======
////		JLabel hori = new JLabel("Horizontal");
////		hori.setSize(30, 30);
////		hori.setMaximumSize(new Dimension(30, 30));
////		animPanel.add(hori);
//		animPanel.add(Box.createVerticalStrut(5));
//		VerticalButton verticalButton = new VerticalButton();
//		verticalButton.setSize(new Dimension(30, 30));
////		verticalButton.setMaximumSize(new Dimension(30, 30));
////		verticalPanel.setSize(new Dimension(30, 30));
//>>>>>>> ea097fe6958d389489c312a6a6f89e7724bc5377
//		verticalButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GraphicalEditor.mode = "Vertical";
//			}
//		});
//		animPanel.add(verticalButton);
//<<<<<<< HEAD
//=======
//
////		JLabel vert = new JLabel("Vertical");
////		vert.setSize(30, 30);
////		vert.setMaximumSize(new Dimension(30, 30));
////		animPanel.add(vert);
//>>>>>>> ea097fe6958d389489c312a6a6f89e7724bc5377
//
//		animPanel.add(Box.createVerticalStrut(5));
//		BlinkButton blinkButton = new BlinkButton();
//		blinkButton.setSize(new Dimension(30, 30));
//<<<<<<< HEAD
//=======
////		blinkButton.setMaximumSize(new Dimension(30, 30));
////		blinkPanel.setSize(new Dimension(30, 30));
//
//>>>>>>> ea097fe6958d389489c312a6a6f89e7724bc5377
//		blinkButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GraphicalEditor.mode = "Blink";
//			}
//		});
//		animPanel.add(blinkButton);
//
//<<<<<<< HEAD
//		JCheckBox startCheckBox = new JCheckBox("Start", false);
//		JCheckBox stopCheckBox = new JCheckBox("Stop", true);
//=======
////		JLabel blin = new JLabel("Blink");
////		blin.setSize(30, 30);
////		blin.setMaximumSize(new Dimension(30, 30));
////		animPanel.add(blin);
//		final JCheckBox startCheckBox = new JCheckBox("Start", false);
//		final JCheckBox stopCheckBox = new JCheckBox("Stop", true);
//>>>>>>> ea097fe6958d389489c312a6a6f89e7724bc5377
//		startCheckBox.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (startCheckBox.isSelected()) {
//					stopCheckBox.setSelected(false);
//					GraphicalEditor.anim.start();
//				} else {
//					GraphicalEditor.anim.stop();
//					stopCheckBox.setSelected(true);
//				}
//			}
//		});
//
//		stopCheckBox.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (stopCheckBox.isSelected()) {
//					startCheckBox.setSelected(false);
//					GraphicalEditor.anim.stop();
//				} else {
//					stopCheckBox.setSelected(true);
//					GraphicalEditor.anim.stop();
//				}
//			}
//		});
//
//		JPanel checkBoxPanel = new JPanel();
//		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
//		checkBoxPanel.add(startCheckBox);
//		checkBoxPanel.add(stopCheckBox);
//		panel.add(animPanel);
//		panel.add(checkBoxPanel);
//		panel.add(Box.createVerticalGlue());
//	}
//
//	// Deselect an Item
//	public void deselect(CanvasItem item) {
//		if (GraphicalEditor.selection != null) {
//			GraphicalEditor.selection.deselect();
//		}
//		for (JButton op : GraphicalEditor.operations) {
//			op.setEnabled(false);
//		}
//	}
//
//	//Listener permettant de savoir si la couleur a été changée.
//	private MouseAdapter colorListener = new MouseAdapter() {
//		public void mouseClicked(MouseEvent e) {
//			JPanel p = (JPanel) e.getSource();
//			Color c = p.getBackground();
//			c = JColorChooser.showDialog(null, "Select a color", c);
//			if (GraphicalEditor.selection == null) {
//				p.setBackground(c);
//			} else if (p == outline) {
//				p.setBackground(c);
//				GraphicalEditor.selection.setOutlineColor(c);
//			} else if (p == fill) {
//				p.setBackground(c);
//				GraphicalEditor.selection.setFillColor(c);
//			}
//			repaint();
//		}
//	};
//
//	// Listen the action on the button
//	private ActionListener operationListener = new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			String op = e.getActionCommand();
//			if (op.equals("Delete ")) {
//				GraphicalEditor.canvas.removeItem(GraphicalEditor.selection);
//				GraphicalEditor.deselect(GraphicalEditor.selection);
//				GraphicalEditor.repaintUndo();
//			} else if (op.equals(" Clone ")) {
//				CanvasItem clone = GraphicalEditor.selection.duplicate();
//				clone.move(10, 10);
//				GraphicalEditor.select(clone);
//			} else if (op.equals("Resize")) {
//				GraphicalEditor.mode = "Resize";
//			}
//		}
//	};
//
//	//Crée un bouton permettant de faire une opération et lui ajoute un listener
//	private JButton createOperation(String description) {
//		JButton btn = new JButton(description);
//		btn.setActionCommand(description);
//		btn.setBackground(Color.white);
//		btn.setEnabled(false);
//		btn.addActionListener(operationListener);
//		operations.add(btn);
//		return btn;
//	}
//
//	// Create the color sample used to choose the color
//	private JPanel createColorSample(Color c) {
//		JPanel p = new JPanel();
//		p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//		p.setOpaque(true);
//		p.setBackground(c);
//		p.setMaximumSize(new Dimension(100, 150));
//		p.addMouseListener(colorListener);
//		return p;
//	}
//
//	public String getMode() {
//		return mode;
//	}
//
//	public Color getOutlineColor() {
//		return outline.getBackground();
//	}
//
//	public JPanel getOutlinePanel() {
//		return outline;
//	}
//
//	public Color getFillColor() {
//		return fill.getBackground();
//	}
//
//	public JPanel getFillPanel() {
//		return fill;
//	}
//
//	public ArrayList<JButton> getOperations() {
//		return operations;
//	}
//
//	public void setCanvas(PersistentCanvas can) {
//		canvas = can;
//
//	}
//
//	public void setSelection(CanvasItem select) {
//		selection = select;
//		System.out.println(select);
//	}
//}
