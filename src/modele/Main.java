package modele;

import javax.swing.JFrame;

import vue.GraphicalEditor;
import vue.MainPanel;
import vue.ToolBar;

public class Main {

	public static void main(String[] args) {

		// ToolBar tool = new ToolBar();
		// GraphicalEditor graphicalEditor = new GraphicalEditor("Editor ", 800,
		// 800, tool);
		// tool.setCanvas(graphicalEditor.getCanvas());
		// tool.setSelection(graphicalEditor.getSelection());
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(600, 600);
		frame.add(new MainPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
