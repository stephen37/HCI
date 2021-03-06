package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
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

public class ToolBar2 extends JFrame {

	// Caracteristiques
	private static final long serialVersionUID = 1L;
	private String mode;

	protected JPanel panel;
	protected JPanel dessin;
	protected JPanel animation;
	protected JTabbedPane ongletPan;
	protected JPanel fill;
	protected JPanel outline;

	public JSlider slidVitesse;
	public ArrayList<JButton> operations;
	public PersistentCanvas canvas;

	// Constructeur
	public ToolBar2() {
		init();
	}

	// Initialise la fenetre
	public void init() {
		setName("ToolBar");

		mode = "Rectangle";
		operations = new ArrayList<JButton>();

		panel = new JPanel();
		ongletPan = new JTabbedPane();

		initDessin();
		initAnimation();

		add(ongletPan);

		// setLocation(250, 250);

		setVisible(true);
		setResizable(false);
		setSize(1000, 135);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// Initialise le panel de l'onglet "Dessin"
	public void initDessin() {
		dessin = new JPanel();
		dessin.setLayout(new GridLayout(1, 3, 5, 5));

		/*********** Boutons ***********/
		// Bouton permettant de selectionner un item
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
		// Bouton permettant de dessiner des lignes droites.
		LineButton lineButton = new LineButton();
		lineButton.setPreferredSize(new Dimension(100, 20));
		lineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Line";
			}
		});
		// Bouton permettant de dessiner des chemins
		PathButton pathButton = new PathButton();
		pathButton.setPreferredSize(new Dimension(100, 20));
		pathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Path";
			}
		});

		/*********** Panels ***********/
		// Panel pour les boutons de formes
		JPanel boutonPanel = new JPanel();
		boutonPanel.setLayout(new BoxLayout(boutonPanel, BoxLayout.Y_AXIS));
		JPanel bt = new JPanel();
		bt.setLayout(new GridLayout(2, 3, 2, 2));

		// Panel pour les couleurs
		JPanel couleurPanel = new JPanel();
		couleurPanel.setLayout(new BoxLayout(couleurPanel, BoxLayout.Y_AXIS));
		JPanel cl = new JPanel();
		cl.setLayout(new GridLayout(1, 2, 2, 2));
		// Interieur
		JPanel fillPan = new JPanel();
		fillPan.setMaximumSize(new Dimension(120, 75));
		fillPan.setLayout(new BorderLayout());
		fill = createColorSample(Color.LIGHT_GRAY);
		// Exterieur
		JPanel outPan = new JPanel();
		outPan.setMaximumSize(new Dimension(120, 75));
		outPan.setLayout(new BorderLayout());
		outline = createColorSample(Color.BLACK);

		// Panel pour les boutons d'actions
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		JPanel at = new JPanel();
		at.setLayout(new GridLayout(2, 3, 2, 2));

		/*********** Labels ***********/
		// Label 'Forme'
		JLabel labelForme = new JLabel("Formes");
		labelForme.setForeground(Color.gray);

		// Label 'Couleur'
		JLabel labelCouleurs = new JLabel("Couleurs");
		labelCouleurs.setForeground(Color.gray);

		// Label 'Action'
		JLabel labelAction = new JLabel("Actions");
		labelAction.setForeground(Color.gray);

		/*********** Placement ***********/
		// Formes
		boutonPanel.add(labelForme);
		bt.add(selectMoveButton);
		bt.add(rectangleButton);
		bt.add(ellipseButton);
		bt.add(lineButton);
		bt.add(pathButton);
		boutonPanel.add(bt);

		// Couleurs
		fillPan.add(fill, BorderLayout.CENTER);
		outPan.add(outline, BorderLayout.CENTER);
		couleurPanel.add(labelCouleurs);
		cl.add(outPan);
		cl.add(fillPan);
		couleurPanel.add(cl);

		// Actions
		actionPanel.add(labelAction);
		at.add(createOperation("Delete "));
		at.add(createOperation(" Clone "));
		at.add(createOperation("Resize"));
		at.add(createOperation("Rotation"));
		actionPanel.add(at);

		/*********** Ajout ***********/
		dessin.add(boutonPanel);
		dessin.add(couleurPanel);
		dessin.add(actionPanel);

		ongletPan.addTab("Dessin", dessin);
	}

	// Initialise le panel de l'onglet "Animation"
	public void initAnimation() {
		animation = new JPanel();
		animation.setLayout(new GridLayout(1, 3, 5, 5));

		/*********** Boutons ***********/
		// Bouton permettant de deplacer un item horizontalement
		HorizontalButton horizontalButton = new HorizontalButton();
		horizontalButton.setSize(new Dimension(30, 30));
		horizontalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Horizontal";
			}
		});
		// Bouton permettant de deplacer un item verticalement
		VerticalButton verticalButton = new VerticalButton();
		verticalButton.setSize(new Dimension(30, 30));
		verticalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Vertical";
			}
		});
		// Bouton permettant de faire clignoter un item
		BlinkButton blinkButton = new BlinkButton();
		blinkButton.setSize(new Dimension(30, 30));
		blinkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalEditor.mode = "Blink";
			}
		});
		// CheckBox permettant de lancer et stoper l'animation
		final JCheckBox startCheckBox = new JCheckBox("Start", false);
		final JCheckBox stopCheckBox = new JCheckBox("Stop", true);
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

		/*********** Panels ***********/
		// Boutons
		JPanel boutonPanel = new JPanel();
		boutonPanel.setLayout(new BoxLayout(boutonPanel, BoxLayout.Y_AXIS));
		JPanel bt = new JPanel();
		bt.setLayout(new GridLayout(1, 3, 2, 2));

		// CheckBox
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		JPanel cb = new JPanel();
		cb.setLayout(new BoxLayout(cb, BoxLayout.Y_AXIS));

		// Slider
		JPanel slidPanel = new JPanel();
		slidPanel.setLayout(new BoxLayout(slidPanel, BoxLayout.Y_AXIS));

		// CheckBox & Slider
		JPanel boxNSlid = new JPanel();
		boxNSlid.setLayout(new BoxLayout(boxNSlid, BoxLayout.X_AXIS));

		/*********** Labels ***********/
		// Boutons
		JLabel animLabel = new JLabel("Animation");
		animLabel.setForeground(Color.LIGHT_GRAY);

		// CheckBox
		JLabel lancLabel = new JLabel("Lancement");
		lancLabel.setForeground(Color.LIGHT_GRAY);

		// Vitesse
		JLabel vitLabel = new JLabel("Vitesse");
		vitLabel.setForeground(Color.LIGHT_GRAY);

		/*********** Slider ***********/
		slidVitesse = new JSlider(1, 9);
		slidVitesse.setMinorTickSpacing(1);
		slidVitesse.setMajorTickSpacing(4);
		slidVitesse.setPaintTicks(true);
		slidVitesse.setPaintLabels(true);

		slidVitesse.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (GraphicalEditor.selection != null) {
					GraphicalEditor.selection.vitesse = slidVitesse.getValue();
				}
			}
		});

		/*********** Placement ***********/
		// Boutons
		boutonPanel.add(animLabel);
		bt.add(horizontalButton);
		bt.add(verticalButton);
		bt.add(blinkButton);
		boutonPanel.add(bt);

		// CheckBox
		checkBoxPanel.add(lancLabel);
		cb.add(startCheckBox);
		cb.add(stopCheckBox);
		checkBoxPanel.add(cb);

		// Slider
		slidPanel.add(vitLabel);
		slidPanel.add(slidVitesse);

		// BoxNSlid
		boxNSlid.add(checkBoxPanel);
		boxNSlid.add(slidPanel);

		/*********** Ajout ***********/
		animation.add(boutonPanel);
		animation.add(new JPanel());
		animation.add(boxNSlid);

		ongletPan.addTab("Animation", animation);
	}

	// Cree un bouton permettant de faire une opération et lui ajoute un
	// listener
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

	// Listener permettant de savoir si la couleur a été changée.
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
			} else if (op.equals("Rotation")) {
				GraphicalEditor.mode = "Rotation";
			}
		}
	};

	// Renvoie le mode
	public String getMode() {
		return mode;
	}

	// Renvoie la couleur exterieure
	public Color getOutlineColor() {
		return outline.getBackground();
	}

	// Renvoie la couleur interieure
	public Color getFillColor() {
		return fill.getBackground();
	}

	// Renvoie la liste des boutons
	public ArrayList<JButton> getOperations() {
		return operations;
	}

	// Renvoie la valeur du slider
	public int getSlidValue() {
		return slidVitesse.getValue();
	}

	// Modifie le canvas
	public void setCanvas(PersistentCanvas can) {
		canvas = can;
	}

}
