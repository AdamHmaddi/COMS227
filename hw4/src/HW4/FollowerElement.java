package hw4;

import api.AbstractElement;

/**
 * A follower element is one that is associated with another "base" element such
 * as a PlatformElement or LiftElement. Specifically, the follower element's
 * movement is determined by the movement of the base element, when the base
 * move up 10 pixels, the follower moves up 10 pixels. However, the follower may
 * not always be at a fixed location relative to the base. When the horizontal
 * velocity of the follower is set to a non-zero value, the follower will
 * oscillate between the left and right edges of the PlatformElement or
 * LiftElement it is associated with.
 * 
 * This class extends the MovingElement class and represents an element that
 * follows another base element. Its movement mirrors the movement of the base
 * element vertically, and it oscillates horizontally within the boundaries of
 * the base element when provided with a non zero horizontal velocity
 * 
 * @author Adam Hmaddi
 */
public class FollowerElement extends MovingElement {

	/**
	 * The initial x-coordinate offset from the base element
	 */
	private int initialOffset;
	/**
	 * The left boundary for the follower's movement
	 */
	private double min;
	/**
	 * The right boundary for the follower's movement
	 */
	private double max;
	/**
	 * The base element to which the follower is attached
	 */
	private AbstractElement base;
	/**
	 * The current horizontal offset from the base element
	 */
	private double offset;

	/**
	 * Constructs a new FollowerElement. Before being added to a "base" element such
	 * as a PlatformElement or LiftElement, the x and y coordinates are zero. When a
	 * base element is set, the initial x-coordinate becomes the base's
	 * x-coordinate, plus the given offset, and the y-coordinate becomes the base's
	 * y-coordinate, minus this element's height.
	 * 
	 * @param width         element's width
	 * @param height        element's height
	 * @param initialOffset when added to a base, this amount will be added to the
	 *                      bases's x-coordinate to calculate this element's initial
	 *                      x-coordinate
	 */
	public FollowerElement(int width, int height, int initialOffset) {

		/**
		 * Initializing the instance variables and setting the default values
		 */
		super(0, 0, width, height);
		this.initialOffset = initialOffset;
		offset = 0;
	}

	/**
	 * Sets the base element for this FollowerElement
	 * 
	 * @param b The base element to set
	 */
	public void setBase(AbstractElement b) {
		base = b;
		setBounds(base.getXReal(), base.getXReal() + base.getWidth());
		offset = initialOffset;
		setPosition(base.getXReal() + offset, base.getYReal() - getHeight());
	}

	/**
	 * Returns the right boundary for the Follower's movement
	 * 
	 * @return The right boundary
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Returns the left boundary for the Follower's movement
	 * 
	 * @return The left boundary
	 */
	public double getMin() {
		return min;
	}

	/**
	 * It updates the position of this element based on the movement of the base
	 * element and its own velocity
	 */
	@Override
	public void update() {
		super.update();

		// Updates the position based on the base element's movement and velocity
		setBounds(base.getXReal(), base.getXReal() + base.getWidth());
		setPosition(base.getXReal() + offset + getDeltaX(), base.getYReal() - getHeight());

		// Handling the boundary conditions for horizontal movement
		if (getXReal() + getWidth() >= max) {
			setPosition(max - getWidth(), base.getYReal());
			setVelocity(getDeltaX() * -1, getDeltaY());
		} else if (getXReal() <= min) {
			setPosition(min, base.getYReal());
			setVelocity(getDeltaX() * -1, getDeltaY());
		}

		// Updating the current offset from the base element
		offset = getXReal() - base.getXReal();
	}

	/**
	 * Sets the right and left boundaries for the Follower's movement
	 * 
	 * @param min The left boundary
	 * @param max The right boundary
	 */
	public void setBounds(double min, double max) {
		this.min = min;
		this.max = max;
	}

}