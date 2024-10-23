package hw3;

import static api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;

/**
 * Represents a Lizard as a collection of body segments.
 * 
 * @author Adam Hmaddi
 */
public class Lizard {

	private ArrayList<BodySegment> segments;

	/**
	 * Constructs a Lizard object.
	 */

	public Lizard() {

		segments = new ArrayList<>();
	}

	/**
	 * Sets the segments of the lizard. Segments should be ordered from tail to
	 * head.
	 * 
	 * @param segments list of segments ordered from tail to head
	 */
	public void setSegments(ArrayList<BodySegment> segments) {

		this.segments = segments;
	}

	/**
	 * Gets the segments of the lizard. Segments are ordered from tail to head.
	 * 
	 * @return a list of segments ordered from tail to head
	 */
	public ArrayList<BodySegment> getSegments() {

		return segments;
	}

	/**
	 * Gets the head segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the head segment
	 */
	public BodySegment getHeadSegment() {

		if (segments.isEmpty()) {
			return null;
		}

		return segments.get(segments.size() - 1);
	}

	/**
	 * Gets the tail segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the tail segment
	 */
	public BodySegment getTailSegment() {

		if (segments.isEmpty()) {
			return null;
		}

		return segments.get(0);
	}

	/**
	 * Gets the segment that is located at a given cell or null if there is no
	 * segment at that cell.
	 * 
	 * @param cell to look for lizard
	 * @return the segment that is on the cell or null if there is none
	 */
	public BodySegment getSegmentAt(Cell cell) {

		for (int i = 0; i < segments.size(); i++) {
			BodySegment segment = segments.get(i);
			if (segment.getCell() == cell) {
				return segment;
			}
		}

		return null;
	}

	/**
	 * Get the segment that is in front of (closer to the head segment than) the
	 * given segment. Returns null if there is no segment ahead.
	 * 
	 * @param segment the starting segment
	 * @return the segment in front of the given segment or null
	 */
	public BodySegment getSegmentAhead(BodySegment segment) {

		int i = segments.indexOf(segment);
		if (i == segments.size() - 1 || i == -1) {
			return null;
		}

		return segments.get(i + 1);
	}

	/**
	 * Get the segment that is behind (closer to the tail segment than) the given
	 * segment. Returns null if there is not segment behind.
	 * 
	 * @param segment the starting segment
	 * @return the segment behind of the given segment or null
	 */
	public BodySegment getSegmentBehind(BodySegment segment) {

		int i = segments.indexOf(segment);
		if (i == 0 || i == -1) {
			return null;
		}

		return segments.get(i - 1);
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment ahead (in front of) of it. Returns null if there is no segment ahead
	 * of the given segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment ahead of the given segment or null
	 */
	public Direction getDirectionToSegmentAhead(BodySegment segment) {

		BodySegment nextSegment = getSegmentAhead(segment);
		if (nextSegment != null) {
			return computingTheDirection(segment.getCell(), nextSegment.getCell());
		}

		return null;
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment behind it. Returns null if there is no segment behind of the given
	 * segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment behind of the given segment or null
	 */
	public Direction getDirectionToSegmentBehind(BodySegment segment) {

		BodySegment previousSegment = getSegmentBehind(segment);
		if (previousSegment != null) {
			return computingTheDirection(segment.getCell(), previousSegment.getCell());
		}

		return null;
	}

	/**
	 * Gets the direction in which the head segment is pointing. This is the
	 * direction formed by going from the segment behind the head segment to the
	 * head segment. A lizard that does not have more than one segment has no
	 * defined head direction and returns null.
	 * 
	 * @return the direction in which the head segment is pointing or null
	 */
	public Direction getHeadDirection() {

		if (segments.size() <= 1) {
			return null;
		}

		BodySegment headSegment = getHeadSegment();
		BodySegment previousSegment = getSegmentBehind(headSegment);
		return computingTheDirection(previousSegment.getCell(), headSegment.getCell());
	}

	/**
	 * Gets the direction in which the tail segment is pointing. This is the
	 * direction formed by going from the segment ahead of the tail segment to the
	 * tail segment. A lizard that does not have more than one segment has no
	 * defined tail direction and returns null.
	 * 
	 * @return the direction in which the tail segment is pointing or null
	 */
	public Direction getTailDirection() {

		if (segments.size() <= 1) {
			return null;
		}

		BodySegment tailSegment = getTailSegment();
		BodySegment nextSegment = getSegmentAhead(tailSegment);
		return computingTheDirection(nextSegment.getCell(), tailSegment.getCell());
	}

	/**
	 * Computes the direction from the current cell to the next cell. It calculates
	 * the direction from the current cell to the next cell based on the row and
	 * column number. It returns directions like UP, DOWN, LEFT, RIGHT or null if
	 * the cells are not adjacent.
	 *
	 * @param currCell the current cell
	 * @param nextCell the next cell
	 * @return the direction from the current cell to the next cell. Returns null if
	 *         the cells are not adjacent
	 * 
	 */
	private Direction computingTheDirection(Cell currCell, Cell nextCell) {
		int dRow = nextCell.getRow() - currCell.getRow();
		int dCol = nextCell.getCol() - currCell.getCol();

		if (dRow == 1) {
			return Direction.DOWN;
		} else if (dRow == -1) {
			return Direction.UP;
		} else if (dCol == 1) {
			return Direction.RIGHT;
		} else if (dCol == -1) {
			return Direction.LEFT;
		}

		return null;
	}

	@Override
	public String toString() {
		String result = "";
		for (BodySegment seg : getSegments()) {
			result += seg + " ";
		}
		return result;
	}
}
