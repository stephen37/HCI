package controleur;

import modele.CanvasItem;
import modele.ItemAnimation;

public class BlinkAnimation extends ItemAnimation {

	public BlinkAnimation(CanvasItem item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		item.blink();
		return true;
	}

	@Override
	public void resume(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
