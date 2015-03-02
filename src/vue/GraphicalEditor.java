package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import modele.CanvasItem;
import modele.CercleItem;
import modele.ImageItem;
import modele.LineItem;
import modele.PathItem;
import modele.PersistentCanvas;
import modele.RectangleItem;
import controleur.Animator;

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
	public static JMenuItem undoItem;
	JPanel menuPanel;
	File file;
	BufferedWriter writer;
	BufferedReader reader;
	File fileSelected;
	ObjectInputStream ois;
	ArrayList<CanvasItem> listSelection = new ArrayList<CanvasItem>();
	File fileChoosen;
	static JFrame frame;
	public static Animator anim;
	public static int widthWindow;
	public static int heightWindow;
	String dataByte = "";

	// Constructor of the Graphical Editor

	public GraphicalEditor(String theTitle, int width, int height, ToolBar tool) {
		frame = this;
		widthWindow = width;
		heightWindow = height;
		// Constructor of the Graphical Editor
		title = theTitle;
		selection = null;
		toolbar = tool;
		newMode = "Rectangle";
		mode = toolbar.getMode();
		operations = toolbar.getOperations();
		anim = new Animator(canvas);
		initMenu();
		this.setLayout(new BorderLayout());
		this.add(menuPanel, BorderLayout.NORTH);

		// Container pane = getContentPane();
		// pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// Create the canvas for drawing
		canvas = new PersistentCanvas();
		canvas.setBackground(Color.WHITE);
		canvas.setPreferredSize(new Dimension(width, height));
		this.add(canvas);
		new DropTarget(canvas, this);

		// AJOUT DE L'ANIMATION !!!
		panel.add(anim);
		// ////////////////////////////

		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				try {
					try {
						saveUndo();
					} catch (Base64DecodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Point p = e.getPoint();
				Color o = toolbar.getOutlineColor();
				Color f = toolbar.getFillColor();
				CanvasItem item = null;
				if (mode.equals("Select/Move")
						&& SwingUtilities.isLeftMouseButton(e)) {
					// TODO you can use the function select(CanvasItem item);
					select(canvas.getItemAt(p));

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
					repaintUndo();
					if (mode.equals("Animation")) {
						deselect(selection);
						canvas.getItemAt(p).animated();
					}
				}
				mousepos = p;
				if (e.isMetaDown()) {

					System.out.println("Clique droit !!!!");
				}
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
					// System.out.println(selection.get);
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
		setLocation(160, 0);
	}

	public static void repaintUndo() {
		if (!canvas.items.isEmpty() && selection != null) {
			undoItem.setEnabled(true);
		} else {
			undoItem.setEnabled(false);
		}
		frame.repaint();
	}

	public void initMenu() {
		menuPanel = new JPanel();
		menuPanel.setLayout(new BorderLayout());

		menu = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");

		menu.add(fileMenu);
		menu.add(editMenu);
		menuPanel.add(menu, BorderLayout.WEST);

		JMenuItem newItem = new JMenuItem("New");
		// newItem.setMnemonic(KeyEvent.CTRL_MASK && KeyEvent.VK_N);
		fileMenu.add(newItem);
		JMenuItem openItem = new JMenuItem("Open ...");
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		JMenuItem saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);
		JMenuItem saveAsItem = new JMenuItem("Save As ...");
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);

		JMenuItem pasteItem = new JMenuItem("Paste");
		editMenu.add(pasteItem);
		menuPanel.setBackground(Color.lightGray);
		menu.setBackground(Color.lightGray);
		fileMenu.setBackground(Color.lightGray);
		editMenu.setBackground(Color.lightGray);

		undoItem = new JMenuItem("Undo");
		undoItem.setBackground(Color.lightGray);
		undoItem.setEnabled(false);
		menu.add(undoItem);
		// newItem.setBackground(Color.lightGray.brighter());
		// openItem.setBackground(Color.lightGray.brighter());
		// saveItem.setBackground(Color.lightGray.brighter());
		// saveAsItem.setBackground(Color.lightGray.brighter());
		// exitItem.setBackground(Color.lightGray.brighter());
		// pasteItem.setBackground(Color.lightGray.brighter());

		// System.out.println(menu.);

		/************ Listeners ***********/

		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					open();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		undoItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (canvas.items.isEmpty() && selection == null) {
					undoItem.setEnabled(false);
					frame.repaint();
				} else {
					try {
						undo();
						repaintUndo();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					try {
						save();
					} catch (Base64DecodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		saveAsItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					try {
						saveAs();
					} catch (Base64DecodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// frame.setVisible(false);
				frame.dispose();
				toolbar.dispose();
			}
		});

		pasteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CanvasItem clone = selection.duplicate();
				clone.move(10, 10);
				select(clone);
			}
		});
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
					String imagePath = "";
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
						imagePath = file.getPath();
					}
					ImageItem item = new ImageItem(canvas, Color.black, null,
							new Point(0, 0), image, imagePath);
					canvas.items.add(item);
					repaint();
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
	}

	/**********************************
	 * SERIALIZATION
	 * 
	 * @throws Base64DecodingException
	 *****************************/
	public void save() throws IOException, Base64DecodingException {
		System.out.println("sauvegarde debut");
		String data = "";
		for (CanvasItem item : canvas.items) {
			if (item.getType() == "Rectangle") {
				RectangleItem newItem = (RectangleItem) item;
				data += "1";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Ellipse") {
				CercleItem newItem = (CercleItem) item;
				data += "2";
				data += " ";
				data += newItem.getX();
				data += " ";
				data += newItem.getY();
				data += " ";
				data += newItem.getGrandRayon();
				data += " ";
				data += newItem.getPetitRayon();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Line") {
				LineItem newItem = (LineItem) item;
				data += "3";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Path") {
				PathItem newItem = (PathItem) item;
				data += "4";
				data += " ";
				for (Point point : newItem.getListPoint()) {
					data += (int) point.getX();
					data += " ";
					data += (int) point.getY();
					data += " ";
				}
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Image") {
				ImageItem newItem = (ImageItem) item;
				dataByte = "";
				data += "5";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				for (byte temp : newItem.getByte()) {
					data += (char) temp;
					data += " ";
					dataByte += (char) temp;
				}
			}
			data += "\t";
			deselect(selection);
			selection = null;

			menuPanel.setVisible(false);
			menu.setVisible(false);

			BufferedImage image = new BufferedImage(this.getSize().width,
					this.getSize().height, BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.createGraphics();
			this.paint(g);
			g.dispose();
			try {
				ImageIO.write(image, "png", new File("test.png"));
			} catch (Exception e) {
			}
		}
		// System.out.println(data);
		if (fileChoosen != null) {
			BufferedWriter saveBuff;
			saveBuff = new BufferedWriter(new FileWriter(fileChoosen));
			saveBuff.write(data);
			saveBuff.close();
		} else {
			saveAs();
		}

		menuPanel.setVisible(true);
		System.out.println("sauvegarde fin");
	}

	public void saveUndo() throws IOException, Base64DecodingException {
		System.out.println("SaveUndo debut");
		String data = "";
		for (CanvasItem item : canvas.items) {
			if (item.getType() == "Rectangle") {
				RectangleItem newItem = (RectangleItem) item;
				data += "1";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Ellipse") {
				CercleItem newItem = (CercleItem) item;
				data += "2";
				data += " ";
				data += newItem.getX();
				data += " ";
				data += newItem.getY();
				data += " ";
				data += newItem.getGrandRayon();
				data += " ";
				data += newItem.getPetitRayon();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Line") {
				LineItem newItem = (LineItem) item;
				data += "3";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Path") {
				PathItem newItem = (PathItem) item;
				data += "4";
				data += " ";
				for (Point point : newItem.getListPoint()) {
					data += (int) point.getX();
					data += " ";
					data += (int) point.getY();
					data += " ";
				}
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Image") {
				ImageItem newItem = (ImageItem) item;
				dataByte = "";
				data += "5";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				for (byte temp : newItem.getByte()) {
					data += (char) temp;
					data += " ";
					dataByte += (char) temp;
				}
			}
			data += "\t";
		}
		BufferedWriter saveBuff;
		saveBuff = new BufferedWriter(new FileWriter("tmp.txt"));
		saveBuff.write(data);
		saveBuff.close();
		System.out.println("SaveUndo fin");
	}

	public void saveAs() throws IOException, Base64DecodingException {

		JFileChooser fileChooser = new JFileChooser(".");
		int retrieval = fileChooser.showSaveDialog(null);
		fileChoosen = fileChooser.getSelectedFile();
		if (retrieval == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter fileWriter = new FileWriter(fileChoosen);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Beginning of Save As...");
		String data = "";
		for (CanvasItem item : canvas.items) {
			if (item.getType() == "Rectangle") {
				RectangleItem newItem = (RectangleItem) item;
				data += "1";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Ellipse") {
				CercleItem newItem = (CercleItem) item;
				data += "2";
				data += " ";
				data += newItem.getX();
				data += " ";
				data += newItem.getY();
				data += " ";
				data += newItem.getGrandRayon();
				data += " ";
				data += newItem.getPetitRayon();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Line") {
				LineItem newItem = (LineItem) item;
				data += "3";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Path") {
				PathItem newItem = (PathItem) item;
				data += "4";
				data += " ";
				for (Point point : newItem.getListPoint()) {
					data += (int) point.getX();
					data += " ";
					data += (int) point.getY();
					data += " ";
				}
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Image") {
				ImageItem newItem = (ImageItem) item;
				dataByte = "";
				data += "5";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				for (byte temp : newItem.getByte()) {
					data += (char) temp;
					data += " ";
					dataByte += (char) temp;
				}
			}

			data += "\t";
		}
		System.out.println(data);
		BufferedWriter saveBuff;
		saveBuff = new BufferedWriter(new FileWriter(fileChoosen));
		saveBuff.write(data);
		saveBuff.close();
		// fileWriter.close();
		System.out.println("End of Save As...");
	}

	public void undo() throws IOException {
		BufferedReader readFile = new BufferedReader(new FileReader("tmp.txt"));
		String line = readFile.readLine();
		System.out.println("OpenUndo debut");
		canvas.removeAll();
		while (line != null) {
			String[] itemList = line.split("\t");
			line = readFile.readLine();
			for (String item : itemList) {
				String[] paramList = item.split(" ");
				if (Integer.parseInt(paramList[0]) == 1) {
					RectangleItem canvasItem = new RectangleItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])));
					canvasItem.update(new Point(Integer.parseInt(paramList[3]),
							Integer.parseInt(paramList[4])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 2) {
					CercleItem canvasItem = new CercleItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])));
					canvasItem.update(new Point(Integer.parseInt(paramList[4]),
							Integer.parseInt(paramList[3])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 3) {
					LineItem canvasItem = new LineItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])));
					canvasItem.update(new Point(Integer.parseInt(paramList[3]),
							Integer.parseInt(paramList[4])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 4) {
					PathItem canvasItem = new PathItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])));
					for (int i = 5; i < paramList.length - 1 - 5; i += 2) {
						canvasItem.update(new Point(Integer
								.parseInt(paramList[i]), Integer
								.parseInt(paramList[i + 1])));
					}
					canvas.addItem(canvasItem);
				}
			}
		}
		deselect(selection);
		selection = null;
		System.out.println("OpenUndo fin");
		readFile.close();
	}

	public void open() throws IOException {
		System.out.println("open debut");
		canvas.removeAll();
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.showOpenDialog(null);
		File fichier = fileChooser.getSelectedFile();
		String fileName = fichier.getName();
		BufferedReader readFile = new BufferedReader(new FileReader(fileName));
		String line = readFile.readLine();
		fileChoosen = fichier;

		while (line != null) {
			String[] itemList = line.split("\t");
			line = readFile.readLine();
			for (String item : itemList) {
				String[] paramList = item.split(" ");
				if (Integer.parseInt(paramList[0]) == 1) {
					RectangleItem canvasItem = new RectangleItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])));
					canvasItem.update(new Point(Integer.parseInt(paramList[3]),
							Integer.parseInt(paramList[4])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 2) {
					CercleItem canvasItem = new CercleItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])));
					canvasItem.update(new Point(Integer.parseInt(paramList[4]),
							Integer.parseInt(paramList[3])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 3) {
					LineItem canvasItem = new LineItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])));
					canvasItem.update(new Point(Integer.parseInt(paramList[3]),
							Integer.parseInt(paramList[4])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 4) {
					PathItem canvasItem = new PathItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])));
					for (int i = 5; i < paramList.length - 1 - 5; i += 2) {
						canvasItem.update(new Point(Integer
								.parseInt(paramList[i]), Integer
								.parseInt(paramList[i + 1])));
					}
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 5) {
					String data = "";
					for (int i = 3; i < paramList.length; i++) {
						data += paramList[i];
					}
					byte[] bytes = dataByte.getBytes("UTF-8");
					ImageItem canvasItem = new ImageItem(canvas, Color.black,
							Color.black, new Point(
									Integer.parseInt(paramList[1]),
									Integer.parseInt(paramList[2])), null, null);
					// canvasItem.setByte(bytes);
					canvas.addItem(canvasItem);
				}
			}
		}
		readFile.close();
		System.out.println("open fin");
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
			if (!canvas.items.isEmpty()) {
				undoItem.setEnabled(true);
			} else {
				if (selection != null)
					undoItem.setEnabled(false);
			}
		}

		if ((e.getKeyCode() == KeyEvent.VK_S)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			try {
				try {
					save();
				} catch (Base64DecodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if ((e.getKeyCode() == KeyEvent.VK_O)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			try {
				open();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				if (!canvas.items.isEmpty()) {
					undoItem.setEnabled(true);
				} else {
					undoItem.setEnabled(false);
				}
			}
		}
		if ((e.getKeyCode() == KeyEvent.VK_V)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			CanvasItem clone = selection.duplicate();
			clone.move(10, 10);
			select(clone);
		}

		if (e.isShiftDown() && e.isControlDown()
				&& e.getKeyCode() == KeyEvent.VK_S) {
			try {
				try {
					saveAs();
				} catch (Base64DecodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
			try {
				undo();
				repaintUndo();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
