package modele;

import vue.GraphicalEditor;
import vue.ToolBar;

public class Main {
	
	public static void main(String[] args) {

		ToolBar tool = new ToolBar();
		GraphicalEditor graphicalEditor = new GraphicalEditor("Editor ", 800, 800, tool);
		tool.setCanvas(graphicalEditor.getCanvas());
		tool.setSelection(graphicalEditor.getSelection());
//		new ToolBar(new GraphicalEditor("Editor", 800, 800));

//		new GraphicalEditor("Editor ", 800, 800);
//		new ToolBar();

	}

}
