package hw4;

import java.util.ArrayList;

import api.AbstractElement;

/**
 * A PlatformElement is an element with two distinctive behaviors. First, it can
 * be set up to move horizontally within a fixed set of boundaries. On reaching
 * a boundary, the x-component of its velocity is reversed. Second, it maintains
 * a list of <em>associated</em> elements whose basic motion all occurs relative
 * to the PlatformElement.
 *
 * This class extends the MovingElement class and represents a platform element
 * with horizontal movement within specified boundaries. It also manages a list
 * of associated elements whose motion is relative to the platform.
 * 
 * @author Adam Hmaddi
 */
public class PlatformElement extends MovingElement {

	/**
	 * The left boundary for the platform's movement
	 */
	private double min;
	/**
	 * The right boundary for the platform's movement
	 */
	private double max;
	/**
	 * THe list of the associated elements
	 */
	private ArrayList<AbstractElement> elementAssociated;

	/**
	 * Constructs a new PlatformElement. Initially the left and right boundaries are
	 * <code>Double.NEGATIVE_INFINITY</code> and
	 * <code>Double.POSITIVE_INFINITY</code>, respectively.
	 * 
	 * @param x      x-coordinate of initial position of upper left corner
	 * @param y      y-coordinate of initial position of upper left corner
	 * @param width  object's width
	 * @param height object's height
	 */
	public PlatformElement(double x, double y, int width, int height) {

		// Initializing the instance variables and setting them to default values
		super(x, y, width, height);
		min = Double.NEGATIVE_INFINITY;
		max = Double.POSITIVE_INFINITY;
		elementAssociated = new ArrayList<>();

	}

	/**
	 * It adds an associated element to this PlatformElement and sets the object to
	 * be the AttachedElement's base
	 * 
	 * @param attached The AttachedElement to add
	 */
	public void addAssociated(AttachedElement attached) {
		getAssociated().add(attached);
		attached.setBase(this);
	}

	/*
	 * It adds an associated element to this PlatformElement and sets the object to
	 * be the FollowerElement's base
	 * 
	 * @param follower The FollowerElement to add
	 */
	public void addAssociated(FollowerElement follower) {
		getAssociated().add(follower);
		follower.setBase(this);
	}

	/**
	 * This method deletes all associated elements that have been marked for
	 * deletion
	 */
	public void deleteMarkedAssociated() {
		ArrayList<AbstractElement> markedDeletion = new ArrayList<>();

		for (AbstractElement associatedElement : getAssociated()) {
			if (associatedElement.isMarked()) {
				markedDeletion.add(associatedElement);
			}
		}

		getAssociated().removeAll(markedDeletion);
	}

	/**
	 * Returns the list of the associated elements
	 * 
	 * @return The list of associated elements
	 */
	public ArrayList<AbstractElement> getAssociated() {
		return elementAssociated;
	}

	/**
	 * Setting the upper and lower boundaries for the Platform's movement
	 * 
	 * @param min The left boundary
	 * @param max The right boundary
	 */
	public void setBounds(double min, double max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * It updates the state of the PlatformElement for a new frame and calls update
	 * on its associated elements
	 */
	@Override
	public void update() {
		super.update();

		if (getXReal() + getWidth() >= max) {
			setPosition(max - getWidth(), getYReal());
			setVelocity(getDeltaX() * -1, getDeltaY());
		} else if (getXReal() <= min) {
			setPosition(min, getYReal());
			setVelocity(getDeltaX() * -1, getDeltaY());
		}

		for (AbstractElement a : getAssociated()) {
			a.update();
		}
	}

}
