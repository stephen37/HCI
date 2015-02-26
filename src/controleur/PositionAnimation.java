package controleur;
import java.awt.Color;

import modele.CanvasItem;
import modele.ItemAnimation;


public class PositionAnimation extends ItemAnimation {
	
	
	public PositionAnimation(CanvasItem item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		item.setFillColor(Color.YELLOW);
		
		item.move(10, 0);
//		item.move(-20, 0);
		item.move(20, 5);
		item.move(-10, -10);
		
	}

	@Override
	public void resume(int x, int y ) {
		// TODO Auto-generated method stub
		System.out.println("On revient à la maison les copains !");
		
		item.move(x,y);
		
		item.setFillColor(Color.blue);
	}

}
