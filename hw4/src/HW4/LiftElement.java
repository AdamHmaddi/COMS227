package hw4;

import api.AbstractElement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An element with two distinctive behaviors. First, it can be set up to move
 * vertically within a fixed set of boundaries. On reaching a boundary, the
 * y-component of its velocity is reversed. Second, it maintains a list of
 * <em>associated</em> elements whose basic motion all occurs relative to the
 * LiftElement.
 * 
 * This class extends the MovingElement class and represents an element with
 * vertical movement within specified boundaries. It also manages a list of
 * associated elements whose motion is relative to the lift element.
 * 
 * @author Adam Hmaddi
 */
public class LiftElement extends MovingElement {

	/**
	 * The upper boundary for the lift's movement
	 */
	private double min;
	/**
	 * The lower boundary for the lift's movement
	 */
	private double max;
	/**
	 * List of all the associated elements
	 */
	private ArrayList<AbstractElement> associatedElement;

	/**
	 * Constructs a new Elevator. Initially the upper and lower boundaries are
	 * <code>Double.NEGATIVE_INFINITY</code> and
	 * <code>Double.POSITIVE_INFINITY</code>, respectively.
	 * 
	 * @param x      x-coordinate of initial position of upper left corner
	 * @param y      y-coordinate of initial position of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public LiftElement(double x, double y, int width, int height) {

		// Initializing the instance variables and setting them to default values
		super(x, y, width, height);
		min = Double.NEGATIVE_INFINITY;
		max = Double.POSITIVE_INFINITY;
		associatedElement = new ArrayList<>();
	}

	/**
	 * Adds an associated element to the LiftElement and sets this object to be the
	 * AttachedElement's base
	 * 
	 * @param attached The AttachedElement needed to add
	 */
	public void addAssociated(AttachedElement attached) {
		attached.setBase(this);
		getAssociated().add(attached);
	}

	/**
	 * Adds an associated element to the LiftElement and sets this object to be the
	 * FollowerElement's base
	 * 
	 * @param follower The FollowerElement needed to add
	 */
	public void addAssociated(FollowerElement follower) {
		follower.setBase(this);
		getAssociated().add(follower);
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
	 * Returns the list of all associated elements
	 * 
	 * @return The list of the associated elements
	 */
	public ArrayList<AbstractElement> getAssociated() {
		return associatedElement;
	}

	/**
	 * Updating the state of this LiftElement for a new frame and calls update on
	 * all its associated elements.
	 */
	@Override
	public void update() {
		super.update();

		// Handling the boundary conditions for vertical movement
		if (getYReal() + getHeight() >= min) {
			setPosition(max - getHeight(), getYReal());
			setVelocity(getDeltaX() * -1, getDeltaY());
		} else if (getYReal() <= max) {
			setPosition(min, getYReal());
			setVelocity(getDeltaX() * -1, getDeltaY());
		}

		// Updating the state of the associated elements
		for (AbstractElement a : getAssociated()) {
			a.update();
		}
	}

	/**
	 * Sets the upper and lower boundaries for the LiftElement's movement
	 * 
	 * @param min The upper boundary
	 * @param max The lower boundary
	 */
	public void setBounds(double min, double max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Returns the lower boundary for the LiftElement's movement
	 * 
	 * @return The lower boundary
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Returns the upper boundary for the LiftElement's movement
	 * 
	 * @return The upper boundary
	 */
	public double getMin() {
		return min;
	}

}