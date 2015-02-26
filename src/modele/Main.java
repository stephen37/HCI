package modele;

import vue.GraphicalEditor;
import vue.ToolBar;

public class Main {

	public static void main(String[] args) {

		ToolBar tool = new ToolBar();
		new GraphicalEditor("Editor ", 800, 800, tool);
	}

}
