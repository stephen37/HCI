package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author fabriceb
 */
public class TestPanel extends JPanel {

	public static final String IMAGE_PROPERTY = "image";

	public TestPanel() {
		this(null);
	}

	public TestPanel(BufferedImage image) {
		super();
		setFocusable(true);
		setImage(image);
	}

	public BufferedImage getImage() {
		return (BufferedImage) getClientProperty(IMAGE_PROPERTY);
	}

	public void setImage(BufferedImage image) {
		putClientProperty(IMAGE_PROPERTY, image);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage image = getImage();
		Dimension size = getSize();
		Insets insets = getInsets();
		int width = size.width - (insets.left + insets.right);
		int height = size.height - (insets.top + insets.bottom);
		if (image != null && width > 0 && height > 0) {
			Graphics2D g2d = (Graphics2D) g.create(insets.left, insets.top,
					width, height);
			try {
				g.drawImage(image, (width - image.getWidth()) / 2,
						(height - image.getHeight()) / 2, null);
			} finally {
				g2d.dispose();
			}
		}
	}

	private static class ImageTransferable implements Transferable {

		BufferedImage image;

		public ImageTransferable(BufferedImage image) {
			this.image = image;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { DataFlavor.imageFlavor,
					DataFlavor.stringFlavor };
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			boolean result = false;
			for (DataFlavor f : getTransferDataFlavors()) {
				result = (f.equals(flavor));
				if (result) {
					break;
				}
			}
			return result;
		}

		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (flavor == DataFlavor.imageFlavor) {
				return image;
			} else if (flavor == DataFlavor.stringFlavor) {
				return "Sorry we can only export an image.";
			}
			throw new UnsupportedFlavorException(flavor);
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				// Creates test image.
				GraphicsConfiguration gc = GraphicsEnvironment
						.getLocalGraphicsEnvironment().getDefaultScreenDevice()
						.getDefaultConfiguration();
				BufferedImage image = gc.createCompatibleImage(100, 100,
						Transparency.TRANSLUCENT);
				Graphics2D g2d = image.createGraphics();
				try {
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.setPaint(new LinearGradientPaint(0, 0, 100, 100,
							new float[] { 0f, 1f }, new Color[] { Color.CYAN,
									Color.BLUE }));
					g2d.fillOval(0, 0, 100, 100);
				} finally {
					g2d.dispose();
				}
				// Creates source panel.
				final TestPanel test1 = new TestPanel(image);
				MouseInputListener m1Listener = new MouseInputAdapter() {

					@Override
					public void mousePressed(MouseEvent event) {
						System.out.println("Source: mousePressed()");
						test1.requestFocus();

					}

					@Override
					public void mouseDragged(MouseEvent event) {
						System.out.println("Source: mouseDragged()");
						// System.out.println("Mouse dragged " + event.getX() +
						// ", " + event.getY());
						JComponent component = (JComponent) event.getSource();
						// Tell the transfer handler to initiate the drag.
						TransferHandler handler = component
								.getTransferHandler();
						handler.exportAsDrag(component, event,
								TransferHandler.COPY);
					}
				};
				// We need that because by default JPanel cannot drag.
				test1.addMouseListener(m1Listener);
				test1.addMouseMotionListener(m1Listener);
				// Install source transfer handler.
				test1.setTransferHandler(new TransferHandler() {

					/**
					 * {@inheritDoc}
					 */
					@Override
					public int getSourceActions(JComponent component) {
						System.out.println("Source: getSourceActions()");
						return COPY;
					}

					@Override
					public Transferable createTransferable(JComponent c) {
						System.out.println("Source: createTransferable()");
						TestPanel source = (TestPanel) c;
						Transferable result = new ImageTransferable(source
								.getImage());
						return result;
					}

					@Override
					public Icon getVisualRepresentation(Transferable t) {
						System.out.println("Source: getVisualRepresentation()");
						if (t instanceof ImageTransferable) {
							ImageTransferable it = (ImageTransferable) t;
							final BufferedImage image = it.image;
							return new Icon() {

								public void paintIcon(Component c, Graphics g,
										int x, int y) {
									g.drawImage(image, x, y, getIconWidth(),
											getIconHeight(), null);
								}

								public int getIconWidth() {
									return 32;
								}

								public int getIconHeight() {
									return 32;
								}
							};
						} else {
							return super.getVisualRepresentation(t);
						}
					}
				});
				test1.setBorder(new TitledBorder("Source"));
				// Creates destination panel.
				final TestPanel test2 = new TestPanel();
				test2.setBorder(new TitledBorder("Destination"));
				MouseInputListener m2Listener = new MouseInputAdapter() {

					@Override
					public void mousePressed(MouseEvent event) {
						System.out.println("Destination: mousePressed()");
						test2.requestFocus();
					}
				};
				test2.addMouseListener(m2Listener);
				test2.addMouseMotionListener(m2Listener);
				InputMap inputMap = test2.getInputMap();
				ActionMap actionMap = test2.getActionMap();
				Action pasteAction = new AbstractAction("Paste", null) {

					public void actionPerformed(ActionEvent event) {
						System.out.println("Destination: actionPerformed()");
						Transferable t = Toolkit.getDefaultToolkit()
								.getSystemClipboard().getContents(test2);
						test2.getTransferHandler().importData(test2, t);
					}
				};
				String key = (String) pasteAction.getValue(Action.NAME);
				KeyStroke keystroke = KeyStroke.getKeyStroke("control V");
				pasteAction.putValue(Action.ACCELERATOR_KEY, keystroke);
				// Install into key map.
				inputMap.put(keystroke, key);
				// Install into action map.
				actionMap.put(key, pasteAction);
				// Install destination transfer handler.
				test2.setTransferHandler(new TransferHandler() {

					public boolean canimport(JComponent comp,
							DataFlavor[] transferFlavors) {
						System.out.println("Destination: canImport()");
						System.out.print("Got flavors: ");
						for (DataFlavor flavor : transferFlavors) {
							System.out.print(flavor + "\t");
						}
						System.out.println();
						boolean result = false;
						for (DataFlavor flavor : transferFlavors) {
							result = (DataFlavor.imageFlavor.equals(flavor)
									|| DataFlavor.javaFileListFlavor
											.equals(flavor) || DataFlavor.stringFlavor
									.equals(flavor));
							System.out.println((result ? "Accepted flavor : "
									: " Rejected flavor") + flavor);
							if (result) {
								break;
							}
						}
						return result;
					}

					@Override
					public boolean importData(JComponent comp, Transferable t) {
						System.out.println("Destination: importData()");
						try {
							TestPanel destination = (TestPanel) comp;
							if (t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
								System.out.println("DataFlavor.imageFlavor");
								BufferedImage image = (BufferedImage) t
										.getTransferData(DataFlavor.imageFlavor);
								destination.setImage(image);
								return true;
							} else if (t
									.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
								System.out
										.println("DataFlavor.javaFileListFlavor");
								@SuppressWarnings("unchecked")
								List<File> files = (List<File>) t
										.getTransferData(DataFlavor.javaFileListFlavor);
								File file = files.get(0);
								if (file.exists()) {
									BufferedImage image = ImageIO.read(file);
									destination.setImage(image);
									return true;
								}
							} else if (t
									.isDataFlavorSupported(DataFlavor.stringFlavor)) {
								System.out.println("DataFlavor.stringFlavor");
								String path = (String) t
										.getTransferData(DataFlavor.stringFlavor);
								File file = new File(path);
								if (file.exists()) {
									BufferedImage image = ImageIO.read(file);
									destination.setImage(image);
									return true;
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return false;
					}
				});
				// The application frame.
				JFrame frame = new JFrame("Test");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BoxLayout(frame.getContentPane(),
						BoxLayout.X_AXIS));
				frame.add(test1);
				frame.add(test2);
				frame.setSize(400, 400);
				frame.setVisible(true);
			}
		});
	}
}