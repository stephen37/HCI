package vue;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import java.awt.event.MouseMotionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.glass.ui.Cursor;

import modele.CanvasItem;
import modele.CercleItem;
import modele.LineItem;
import modele.PathItem;
import modele.PersistentCanvas;
import modele.RectangleItem;

/**
 * @author Nicolas Roussel (roussel@lri.fr) Modified by Cedric Fleury
 *         (cfleury@lri.fr) - 18.10.2013
 */
@SuppressWarnings("serial")
public class GraphicalEditor extends JFrame implements DropTargetListener,
		Serializable, KeyListener {

	// Graphical Interface
	public static ArrayList<JButton> operations;

	private Point mousepos; // Stores the previous mouse position

	protected static String title; // Changes according to the mode

	public static PersistentCanvas canvas; // Stores the created items
	public static CanvasItem selection; // Stores the selected item
	ToolBar toolbar;
	public static String mode;
	public static String newMode;
	Image image;
	JMenuBar menu;
	JMenu fileMenu, editMenu;

	File file;
	ObjectOutputStream oos;
	FileOutputStream fileOutput;
	FileInputStream fileInput;
	File fileSelected;
	ObjectInputStream ois;
	ArrayList<CanvasItem> listSelection = new ArrayList<CanvasItem>();

	// Constructor of the Graphical Editor

	public GraphicalEditor(String theTitle, int width, int height, ToolBar tool) {

		// Constructor of the Graphical Editor
		title = theTitle;
		selection = null;
		toolbar = tool;
		newMode = "Rectangle";
		mode = toolbar.getMode();
		operations = toolbar.getOperations();
		// JPanel menuPanel = new JPanel();
		// menu = new JMenuBar();
		// fileMenu = new JMenu("File");
		// editMenu = new JMenu("Edit");
		// menu.add(fileMenu);
		// menu.add(editMenu);
		// menuPanel.add(menu);
		// this.setLayout(new BorderLayout());
		// this.add(menuPanel, BorderLayout.NORTH);

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
		new DropTarget(canvas, this);
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

					// SELECTION RECTANGLE
					/*
					 * } else if (mode.equals("Select/Move") &&
					 * SwingUtilities.isRightMouseButton(e)) {
					 * select(canvas.getItemAt(p)); item = new
					 * RectangleItem(canvas, o, f, p);
					 * System.out.println(canvas.addItem(item)); select(item);
					 * // FIN
					 */
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
					updateTitle();
					if (mode.equals("Animation")) {
						deselect(selection);
						canvas.getItemAt(p).animated();
					}

					// for (CanvasItem itemCanvas : canvas.items) {
					// if (itemCanvas.isAnimated) {
					// // CanvasItem test = itemCanvas;
					// // while (itemCanvas.isAnimated) {
					// // canvas.items.remove(itemCanvas);
					// // canvas.addItem(test);
					// // }
					//
					// select(itemCanvas);
					// System.out.println("Item " + itemCanvas);
					// for (int i = 0; i < 5; i++) {
					// itemCanvas.move(i, i);
					// System.out.println(i);
					// try {
					// Thread.sleep(1000);
					// } catch (InterruptedException e1) {
					// // TODO Auto-generated catch block
					// e1.printStackTrace();
					// }
					// }
					//
					// }
					// }
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
		this.addKeyListener(this);

		// pane.addKeyListener(keyboardListener);
		pack();
		updateTitle();
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(150, 0);
	}

	// Listen the mode changes and update the Title
	public ActionListener modeListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// TODO you can use the function updateTitle();
			mode = e.getActionCommand();
			updateTitle();

		}

	};

	// Update the Title
	public void updateTitle() {
		setTitle(title + " - " + mode);
	}

	// Deselect an Item
	public static void deselect(CanvasItem item) {
		if (selection != null) {
			selection.deselect();
		}
		for (JButton op : operations) {
			op.setEnabled(false);
		}
	}

	// Select an Item
	public static void select(CanvasItem item) {
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

	/************************************* DROPTARGETLISTENER *****************************************/

	@SuppressWarnings("unchecked")
	@Override
	public void drop(DropTargetDropEvent event) {

		// Accept copy drops
		event.acceptDrop(DnDConstants.ACTION_COPY);

		// Get the transfer which can provide the dropped item data
		Transferable transferable = event.getTransferable();

		// Get the data formats of the dropped item
		DataFlavor[] flavors = transferable.getTransferDataFlavors();

		// Loop through the flavors
		for (DataFlavor flavor : flavors) {

			try {

				// If the drop items are files
				if (flavor.isFlavorJavaFileListType()) {

					// Get all of the dropped files
					List<File> files = (List<File>) transferable
							.getTransferData(flavor);

					// Loop them through
					for (File file : files) {

						// Print out the file path
						System.out.println("File path is '" + file.getPath()
								+ "'.");
						this.file = file;
						image = ImageIO.read(file);
						paintComponents((Graphics2D) getGraphics());

					}

				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		// Inform that the drop is complete
		event.dropComplete(true);

	}

	@Override
	public void dragEnter(DropTargetDragEvent event) {
	}

	@Override
	public void dragExit(DropTargetEvent event) {
	}

	@Override
	public void dragOver(DropTargetDragEvent event) {
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent event) {
	}

	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);

		if (image != null) {
			g.drawImage(image, 0, 0, null);
			System.out.println("Le DnD c'est cool !");
		}
	}

	/********************************** SERIALIZATION *****************************/

	public void save() {
		System.out.println("Saving !");
		try {
			fileOutput = new FileOutputStream("graph.txt");
			oos = new ObjectOutputStream(fileOutput);
			oos.writeChars("qsdsqd ");
			// for (CanvasItem item : canvas.getItems()) {
			// Iterator<Integer> iterator = item.getPoints().iterator();
			// while (iterator.hasNext()) {
			// oos.writeChars(" test ");
			// System.out.println("Writing value " + iterator.next());
			// }
			// }
			oos.flush();
			oos.close();
			fileOutput.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
				fileOutput.close();
			} catch (IOException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

	
	public void open() {
		JFileChooser filechooser = new JFileChooser(".");
		if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				fileSelected = filechooser.getSelectedFile();
				BufferedReader br = new BufferedReader(new FileReader(
						fileSelected));

				// for (CanvasItem item : canvas.getItems()) {
				// canvas.addItem(item);
				// canvas.addItem(item.duplicate());
				// }
				// fileInput = new FileInputStream("graph.txt");
				ois = new ObjectInputStream(fileInput);
				CanvasItem item = (CanvasItem) ois.readObject();
				canvas.addItem(item);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fileInput.close();
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
	
	
	// Toolkit tk = Toolkit.getDefaultToolkit();
	// Image imgSelect = tk.getImage("ImagesSouris/Select");
	// Image imgRectangle = tk.getImage("ImagesSouris/Rectangle");
	// Image imgEllipse = tk.getImage("ImagesSouris/Ellipse");
	// Cursor monCurseur = tk.createCustomCursor(imgSelect, new Point(0, 0),
	// "Select Cursor");

	/************************************* KEYLISTENER *****************************/
	@Override
	public void keyTyped(KeyEvent e) {
	}

	// /In order to know if the ctrl key has been pressed, we can use the method
	// event.isControlDown();
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println(e.getKeyChar());
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			canvas.removeItem(selection);
			deselect(selection);
		}

		if ((e.getKeyCode() == KeyEvent.VK_S)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			save();
		}
		if ((e.getKeyCode() == KeyEvent.VK_O)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			open();
		}
		if ((e.getKeyCode() == KeyEvent.VK_V)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			CanvasItem clone = selection.duplicate();
			clone.move(10, 10);
			select(clone);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
