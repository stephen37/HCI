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

public class ToolBar extends JFrame {

	private String mode;
	private String title;
	JPanel fill;
	JPanel outline;
	ArrayList<JButton> operations;
	GraphicalEditor ge = new GraphicalEditor("Editor", 800, 800);
	CanvasItem selection = ge.getSelection();
	PersistentCanvas canvas = ge.getCanvas();

	public ToolBar() {
		// TODO Auto-generated constructor stub

		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Create the mode selection button list
		mode = "Rectangle"; // TODO you can change that later
		ButtonGroup group = new ButtonGroup();
		panel.add(createMode("Select/Move", group));
		panel.add(createMode("Rectangle", group));
		panel.add(createMode("Ellipse", group));
		panel.add(createMode("Line", group));
		panel.add(createMode("Path", group));
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
		pane.add(panel);
	}

	// Update the Title
	private void updateTitle() {
		setTitle(title + " - " + mode);
	}

	private MouseAdapter colorListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			JPanel p = (JPanel) e.getSource();
			Color c = p.getBackground();
			c = JColorChooser.showDialog(null, "Select a color", c);
			// TODO Manage the color change

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
			} else if (op.equals("Clone")) {
				// TODO duplicate and translate the selected item
				CanvasItem clone = selection.duplicate();
				clone.move(10, 10);
				canvas.addItem(clone);

//				select(clone);
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
		p.setMaximumSize(new Dimension(500, 150));
		p.addMouseListener(colorListener);
		return p;
	}
}
