package modele;

/**
 * 
 * @author jgarcia
 *
 */
public abstract class ItemAnimation {

	protected CanvasItem item;

	public ItemAnimation(CanvasItem item) {
		this.item = item;
	}

	//call this method to animate
	public abstract boolean process();
	
	//call this method to restore parameters
	public abstract void resume(int x, int y);
}
