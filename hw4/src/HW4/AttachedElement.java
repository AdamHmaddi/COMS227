package hw4;

import api.AbstractElement;
import java.util.ArrayList;

/**
 * An attached element is one that is associated with another "base" element
 * such as a PlatformElement or a LiftElement. Specifically, the attached
 * element's movement is determined by the movement of the base element, the
 * element always remains a fixed distance away.
 * 
 * This class extends the MovingElement class and represents an element that is
 * attached to a base element. Its movement is determined by the movement of the
 * base element. It remains a fixed distance away from the base
 * 
 * @author Adam Hmaddi
 */
public class AttachedElement extends MovingElement {

	/**
	 * The vertical offset from the base element
	 */
	private int hover;
	/**
	 * The horizental offset from the base element
	 */
	private int offset;
	/**
	 * The base element where this element is attached
	 */
	private AbstractElement base;

	/**
	 * Constructs a new AttachedElement. Before being added to an associated "base"
	 * element such as a PlatformElement or LiftElement, the x and y coordinates are
	 * initialized to zero. When the base object is set (not in this constructor),
	 * the x-coordinate will be calculated as the base object's x-coordinate, plus
	 * the given offset, and the y-coordinate will become the base object's
	 * y-coordinate, minus this element's height, minus the hover amount.
	 * 
	 * @param width  element's width
	 * @param height element's height
	 * @param offset when added to a base object, this amount will be added to the
	 *               other object's x-coordinate to calculate this element's
	 *               x-coordinate
	 * @param hover  when added to a base object, this element's y-coordinate is the
	 *               other object's y-coordinate, minus this element's height, minus
	 *               the hover amount
	 */
	public AttachedElement(int width, int height, int offset, int hover) {
		/**
		 * Initializing instance variables and setting them to the default values
		 */
		super(0, 0, width, height);
		this.hover = hover;
		this.offset = offset;
	}

	/**
	 * Sets the base element for the AttachedElement
	 * 
	 * @param b The base element to set
	 */
	public void setBase(AbstractElement b) {
		base = b;
		updatePosition();
	}

	/**
	 * This method updates the position of this element based on the movement of the
	 * base element
	 */
	@Override
	public void update() {
		super.update();
		if (base != null) {
			updatePosition();
		}
	}

	/**
	 * This is a helper method to update the position of this element based on the
	 * base element's position, and to make less redundant code
	 */
	protected void updatePosition() {
		setPosition(base.getXReal() + offset, base.getYReal() - getHeight() - hover);
	}

}
