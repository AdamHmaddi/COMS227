package hw4;

/**
 * An element in which the <code>update</code> method updates the position each
 * frame according to a <em>velocity</em> vector (deltaX, deltaY). The units are
 * assumed to be "pixels per frame".
 * 
 * This class extends the SimpleElement class and represents an element that
 * moves across the screen with a velocity defined by the deltaX and deltaY
 * parameters.
 * 
 * @author Adam Hmaddi
 */
public class MovingElement extends SimpleElement {

	/**
	 * The change in x-coordinate per frame
	 */
	private double deltaX;
	/**
	 * The change in y-coordinate per frame
	 */
	private double deltaY;

	/**
	 * Constructs a MovingElement with a default velocity of zero in both
	 * directions.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  object's width
	 * @param height object's height
	 */
	public MovingElement(double x, double y, int width, int height) {

		/**
		 * Initializing instance variables and setting the default velocity
		 */
		super(x, y, width, height);
		deltaX = 0;
		deltaY = 0;
	}

	/**
	 * Update method adds both deltaX and deltaY to the current position
	 */
	@Override
	public void update() {
		/**
		 * Calling the super update from SimpleElement to increment the frame count
		 */
		super.update();

		/**
		 * Updates the position based on its velocity
		 */
		setPosition(getXReal() + deltaX, getYReal() + deltaY);
	}

	/**
	 * Sets the velocity for the object
	 * 
	 * @param deltaX The change in x-coordinate per frame
	 * @param deltaY The change in y-coordinate per frame
	 */
	public void setVelocity(double deltaX, double deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	/**
	 * Returns the x-component of the object's velocity
	 * 
	 * @return The change in x-coordinate per frame
	 */
	public double getDeltaX() {
		return deltaX;
	}

	/**
	 * Returns the y-component of the object's velocity
	 * 
	 * @return The change in y-coordinate per frame
	 */
	public double getDeltaY() {
		return deltaY;
	}

}
