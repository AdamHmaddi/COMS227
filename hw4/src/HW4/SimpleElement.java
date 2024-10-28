package hw4;

import java.awt.Color;
import java.awt.Rectangle;

import api.AbstractElement;
import api.Drawable;

// TODO:
// Special documentation requirement: (see page 11 of documentation)
// you must add a comment to the top of the SimpleElement class
// with a couple of sentences explaining how you decided to organize
// the class hierarchy for the elements.

/**
 * Minimal concrete extension of AbstractElement. The <code>update</code> method
 * in this implementation just increments the frame count.
 * 
 * For the hierarchy, I decided for SimpleElement and VanishingElement to extend
 * AbstractElement because they had the needed methods in common. Then
 * MovingElement was extending SimpleElement becuase it needed those methods
 * plus three other methods. As for the rest of the classes, I decided to choose
 * MovingElement to be extended becuase it hada lot of methods used in common,
 * which made it easy to use in each one of those classes. The strategy I used
 * was to check the methods in common and use that class to be extended, and
 * also check if it is easy to extend it or not.
 * 
 * This class represents a basic element that can be used as either a static
 * obstacle or decorative element in the game. It extends AbstractElement to
 * inherit the common functionality and also implements the Drawable interface.
 * 
 * @author Adam Hmaddi
 */

public class SimpleElement extends AbstractElement {

	/**
	 * The x-coordinate of the upper left corner of the element
	 */
	private double x;
	/**
	 * The y-coordinate of the upper left corner of the element
	 */
	private double y;
	/**
	 * The width of the element
	 */
	private int width;
	/**
	 * The height of the element
	 */
	private int height;
	/**
	 * the number of times the update method was invoked for this object
	 */
	private int frameCount;
	/**
	 * It indicates if the element is marked for deletion
	 */
	private boolean markedForDeletion;

	/**
	 * Constructs a new SimpleElement.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public SimpleElement(double x, double y, int width, int height) {

		/**
		 * Initializing the instance variables
		 */
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		frameCount = 0;
		markedForDeletion = false;

	}

	/**
	 * Sets the position of the element
	 * 
	 * @param newX The new x-coordinate
	 * @param newY The new y-coordinate
	 */
	@Override
	public void setPosition(double newX, double newY) {
		this.x = newX;
		this.y = newY;
	}

	/**
	 * Returns the x-coordinate's exact value
	 * 
	 * @return The x-coordinate
	 */
	@Override
	public double getXReal() {
		return x;
	}

	/**
	 * Returns the y-coordinate's exact value
	 * 
	 * @return The y-coordinate
	 */
	@Override
	public double getYReal() {
		return y;
	}

	/**
	 * Updates the object's attributes for the next frame
	 */
	@Override
	public void update() {
		frameCount++;
	}

	/**
	 * Returns the number of times that update method was invoked for the object
	 * 
	 * @return The number of frames
	 */
	@Override
	public int getFrameCount() {
		return frameCount;
	}

	/**
	 * Returns true if the element was marked for deletion
	 * 
	 * @return The True if marked for deletion, otherwise it is false
	 */
	@Override
	public boolean isMarked() {
		if (markedForDeletion) {
			return true;
		}
		return false;
	}

	/**
	 * Marks the element for deletion
	 */
	@Override
	public void markForDeletion() {
		markedForDeletion = true;
	}

	/**
	 * This method determines if the element's bounding rectangle overlaps with the
	 * given element's bounding rectangle
	 * 
	 * @param other The given element
	 * @return True of the element overlaps the given element, otherwise returns
	 *         false
	 */
	@Override
	public boolean collides(AbstractElement other) {
		if (other instanceof SimpleElement) {
			Rectangle rect1 = new Rectangle(getXInt(), getYInt(), width, height);
			Rectangle rect2 = new Rectangle((int) other.getXReal(), (int) other.getYReal(), width, height);
			return rect1.intersects(rect2);
		}
		return false;
	}

	/**
	 * Returns the horizontal coordinate of the upper left corner of the object's
	 * bounding rectangle. Also it is rounded to the nearest integer
	 * 
	 * @return The x-coordinate of the bounding rectangle
	 */
	@Override
	public int getXInt() {
		// TODO Auto-generated method stub
		return (int) Math.round(x);
	}

	/**
	 * Returns the vertical coordinate of the upper left corner of the object's
	 * bounding rectangle. It is rounded to the nearest integer
	 * 
	 * @return The y-coordinate of the bounding rectangle
	 */
	@Override
	public int getYInt() {
		// TODO Auto-generated method stub
		return (int) Math.round(y);
	}

	/**
	 * Returns the width of the object's bounding rectangle
	 * 
	 * @return The width of the bounding rectangle
	 */
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	/**
	 * Returns the height of the object's bounding rectangle
	 * 
	 * @return The height of the bounding rectangle
	 */
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	/**
	 * Returns the bounding rectangle for the object as an instance of
	 * java.awt.Rectangle
	 * 
	 * @return The bounding rectangle
	 */
	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return new Rectangle(getXInt(), getYInt(), width, height);
	}

}