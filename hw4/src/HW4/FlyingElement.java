package hw4;

import java.awt.Rectangle;

import api.AbstractElement;

/**
 * Moving element in which the vertical velocity is adjusted each frame by a
 * gravitational constant to simulate gravity. The element can be set to
 * "grounded", meaning gravity will no longer influence its velocity.
 * 
 * This class extends the MovingElement class and represents an element that
 * moves with simulated gravity, where the vertical velocity is adjusted by a
 * gravitational constant each frame. It can be set to "grounded" to prevent
 * gravity from influencing its velocity.
 * 
 * @author Adam Hmaddi
 */
public class FlyingElement extends MovingElement {

	/**
	 * Indicates if the element is grounded
	 */
	private boolean grounded;
	/**
	 * Gravitational constant
	 */
	private double gravity;

	/**
	 * Constructs a new FlyingElement. By default it should be grounded, meaning
	 * gravity does not influence its velocity.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public FlyingElement(double x, double y, int width, int height) {

		/**
		 * Initializing instance variables and setting them to the default values
		 */
		super(x, y, width, height);
		grounded = false;
		gravity = 0;
	}

	/**
	 * THis method updates the position and adds the gravitational constant to the
	 * y-component of the velocity when needed
	 */
	@Override
	public void update() {

		super.update();
		if (!grounded) {
			setVelocity(getDeltaX(), getDeltaY() + gravity);
		}

	}

	/**
	 * Returns true if the element is grounded
	 * 
	 * @return true if grounded, otherwise returns false
	 */
	public boolean isGrounded() {
		return grounded;
	}

	/**
	 * Sets the grounded status of the element
	 * 
	 * @param grounded true to set the element as grounded, otherwise it is false
	 */
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	/**
	 * Sets the gravitational constant for the element
	 * 
	 * @param gravity Gravitational constant
	 */
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

}
