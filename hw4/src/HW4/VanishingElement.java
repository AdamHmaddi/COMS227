package hw4;

/**
 * An element that does not move. Instead, it is intended to appear on the
 * screen for a fixed number of frames.
 * 
 * This class extends SimpleElement class and represents an element with a
 * predefined lifespan. In addition, once its lifespan expires, it marks itself
 * for deletion.
 * 
 * @author Adam Hmaddi
 */
public class VanishingElement extends SimpleElement {

	/**
	 * Initial lifespan of the element in frames
	 */
	private int initialLife;

	/**
	 * Constructs a new VanishingElement.
	 * 
	 * @param x           x-coordinate of upper left corner
	 * @param y           y-coordinate of upper left corner
	 * @param width       element's width
	 * @param height      element's height
	 * @param initialLife the number of frames until this element marks itself for
	 *                    deletion
	 */
	public VanishingElement(double x, double y, int width, int height, int initialLife) {
		/**
		 * Initializing instance variables and setting the initial lifespan.
		 */
		super(x, y, width, height);
		this.initialLife = initialLife;
	}

	/**
	 * This method decrements the remaining life of the element. If it is expired,
	 * then marks it for deletion
	 */
	@Override
	public void update() {
		/**
		 * Incrementing the frame count
		 */
		super.update();

		/**
		 * Checking if the remaining life equals frame count to mark it for deletion
		 */
		if (initialLife == getFrameCount()) {
			super.markForDeletion();
		}

	}

}
