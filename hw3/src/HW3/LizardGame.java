package hw3;

import static api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;
import api.Exit;
import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Wall;

/**
 * Class that models a game.
 * 
 * @author Adam Hmaddi
 */
public class LizardGame {
	/*
	 * This listener's role is to display dialog messages to the user.
	 */
	private ShowDialogListener dialogListener;

	/*
	 * This listener's role is to update the scores of the player
	 */
	private ScoreUpdateListener scoreListener;

	/*
	 * This represents the grid of cells in the game
	 */
	private Cell[][] grid;

	/*
	 * List of lizards in our game
	 */
	private ArrayList<Lizard> lizards;

	/**
	 * Constructs a new LizardGame object with given grid dimensions.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public LizardGame(int width, int height) {

		grid = new Cell[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid[i][j] = new Cell(j, i);
			}
		}

		lizards = new ArrayList<Lizard>();

	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width of the grid
	 */
	public int getWidth() {

		return grid[0].length;
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height of the grid
	 */
	public int getHeight() {

		return grid.length;
	}

	/**
	 * Adds a wall to the grid.
	 * <p>
	 * Specifically, this method calls placeWall on the Cell object associated with
	 * the wall (see the Wall class for how to get the cell associated with the
	 * wall). This class assumes a cell has already been set on the wall before
	 * being called.
	 * 
	 * @param wall to add
	 */
	public void addWall(Wall wall) {

		wall.getCell().placeWall(wall);

	}

	/**
	 * Adds an exit to the grid.
	 * <p>
	 * Specifically, this method calls placeExit on the Cell object associated with
	 * the exit (see the Exit class for how to get the cell associated with the
	 * exit). This class assumes a cell has already been set on the exit before
	 * being called.
	 * 
	 * @param exit to add
	 */
	public void addExit(Exit exit) {

		if (exit != null) {
			exit.getCell().placeExit(exit);
		}
	}

	/**
	 * Gets a list of all lizards on the grid. Does not include lizards that have
	 * exited.
	 * 
	 * @return lizards list of lizards
	 */
	public ArrayList<Lizard> getLizards() {

		return lizards;

	}

	/**
	 * Adds the given lizard to the grid.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards.
	 * 
	 * @param lizard to add
	 */
	public void addLizard(Lizard lizard) {

		lizards.add(lizard);

		// Updates the score if a score listener is registered.
		if (scoreListener != null) {
			scoreListener.updateScore(getLizards().size());
		}

	}

	/**
	 * Removes the given lizard from the grid. Be aware that each cell object knows
	 * about a lizard that is placed on top of it. It is expected that this method
	 * updates all cells that the lizard used to be on, so that they now have no
	 * lizard placed on them.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards using
	 * updateScore().
	 * 
	 * @param lizard to remove
	 */
	public void removeLizard(Lizard lizard) {

		if (lizard != null) {
			ArrayList<BodySegment> segments = lizard.getSegments();

			// Clears the cells where the lizard was previously located at.
			for (BodySegment segment : segments) {
				Cell cell = segment.getCell();
				if (cell != null) {
					cell.removeLizard();
				}
			}

			// Removing the lizard from the game
			lizards.remove(lizard);

			// Updating the score listener with the current number of lizards
			if (scoreListener != null) {
				scoreListener.updateScore(getLizards().size());
			}

		}
	}

	/**
	 * Gets the cell for the given column and row.
	 * <p>
	 * If the column or row are outside of the boundaries of the grid the method
	 * returns null.
	 * 
	 * @param col column of the cell
	 * @param row of the cell
	 * @return the cell or null
	 */
	public Cell getCell(int col, int row) {

		if (col < 0 || row < 0 || col >= getWidth() || row >= getHeight()) {
			return null;
		} else {
			return grid[row][col];
		}
	}

	/**
	 * Gets the cell that is adjacent to (one over from) the given column and row,
	 * when moving in the given direction. For example (1, 4, UP) returns the cell
	 * at (1, 3).
	 * <p>
	 * If the adjacent cell is outside of the boundaries of the grid, the method
	 * returns null.
	 * 
	 * @param col the given column
	 * @param row the given row
	 * @param dir the direction from the given column and row to the adjacent cell
	 * @return the adjacent cell or null
	 */
	public Cell getAdjacentCell(int col, int row, Direction dir) {

		if (dir == Direction.UP) {
			row--;
		} else if (dir == Direction.DOWN) {
			row++;
		} else if (dir == Direction.LEFT) {
			col--;
		} else if (dir == Direction.RIGHT) {
			col++;
		}
		return getCell(col, row);
	}

	/**
	 * Resets the grid. After calling this method the game should have a grid of
	 * size width x height containing all empty cells. Empty means cells with no
	 * walls, exits, etc.
	 * <p>
	 * All lizards should also be removed from the grid.
	 * 
	 * @param width  number of columns of the resized grid
	 * @param height number of rows of the resized grid
	 */
	public void resetGrid(int width, int height) {

		grid = new Cell[height][width];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				grid[j][i] = new Cell(i, j);
			}
		}
		lizards = new ArrayList<Lizard>();
	}

	/**
	 * Returns true if a given cell location (col, row) is available for a lizard to
	 * move into. Specifically the cell cannot contain a wall or a lizard. Any other
	 * type of cell, including an exit is available.
	 * 
	 * @param row of the cell being tested
	 * @param col of the cell being tested
	 * @return true if the cell is available, false otherwise
	 */
	public boolean isAvailable(int col, int row) {

		// Checks if that specific cell is out of the bounds of the grid
		if (col < 0 || col > getWidth() || row < 0 || row > getHeight()) {
			return false;
		}

		Cell cell = getCell(col, row);

		// Checks if the cell has a wall or a lizard
		if (cell.getWall() != null || cell.getLizard() != null) {
			return false;
		}

		// if none of those conditions are met, then the cell is available
		return true;

	}

	/**
	 * Move the lizard specified by its body segment at the given position (col,
	 * row) one cell in the given direction. The entire body of the lizard must move
	 * in a snake like fashion, in other words, each body segment pushes and pulls
	 * the segments it is connected to forward or backward in the path of the
	 * lizard's body. The given direction may result in the lizard moving its body
	 * either forward or backward by one cell.
	 * <p>
	 * The segments of a lizard's body are linked together and movement must always
	 * be "in-line" with the body. It is allowed to implement movement by either
	 * shifting every body segment one cell over or by creating a new head or tail
	 * segment and removing an existing head or tail segment to achieve the same
	 * effect of movement in the forward or backward direction.
	 * <p>
	 * If any segment of the lizard moves over an exit cell, the lizard should be
	 * removed from the grid.
	 * <p>
	 * If there are no lizards left on the grid the player has won the puzzle the
	 * the dialog listener should be used to display (see showDialog) the message
	 * "You win!".
	 * <p>
	 * It is possible that the given direction is not in-line with the body of the
	 * lizard (as described above), in that case this method should do nothing.
	 * <p>
	 * It is possible that the given column and row are outside the bounds of the
	 * grid, in that case this method should do nothing.
	 * <p>
	 * It is possible that there is no lizard at the given column and row, in that
	 * case this method should do nothing.
	 * <p>
	 * It is possible that the lizard is blocked and cannot move in the requested
	 * direction, in that case this method should do nothing.
	 * <p>
	 * <b>Developer's note: You may have noticed that there are a lot of details
	 * that need to be considered when implement this method method. It is highly
	 * recommend to explore how you can use the public API methods of this class,
	 * Grid and Lizard (hint: there are many helpful methods in those classes that
	 * will simplify your logic here) and also create your own private helper
	 * methods. Break the problem into smaller parts are work on each part
	 * individually.</b>
	 * 
	 * @param col the given column of a selected segment
	 * @param row the given row of a selected segment
	 * @param dir the given direction to move the selected segment
	 */
	public void move(int col, int row, Direction dir) {

		// Gets the cell at the specified position
		Cell selectedCell = getCell(col, row);
		if (selectedCell == null) {
			return;
		}

		// Checks if there is a lizard at the selected cell
		Lizard lizard = selectedCell.getLizard();
		if (lizard == null) {
			return;
		}

		// Determines the new cell for the head or tail segment depending on the
		// direction
		Cell newCell;
		if (dir == Direction.UP) {
			newCell = getAdjacentCell(col, row, Direction.UP);
		} else if (dir == Direction.DOWN) {
			newCell = getAdjacentCell(col, row, Direction.DOWN);
		} else if (dir == Direction.LEFT) {
			newCell = getAdjacentCell(col, row, Direction.LEFT);
		} else if (dir == Direction.RIGHT) {
			newCell = getAdjacentCell(col, row, Direction.RIGHT);
		} else {
			return;
		}

		// Gets references for the head, tail, and segment at the current selected cell
		BodySegment head = lizard.getHeadSegment();
		BodySegment tail = lizard.getTailSegment();
		BodySegment selectedSegment = lizard.getSegmentAt(selectedCell);

		// Moves the lizard based on its direction and which part of the lizard was
		// selected(body, head, or tail)
		if (newCell != null && isAvailable(newCell.getCol(), newCell.getRow())) {
			if (selectedSegment == head) {

				// Moving the head segment
				BodySegment segmentBehind = lizard.getSegmentBehind(selectedSegment);
				if (segmentBehind.getCell() == newCell) {
					moveTail(lizard, newCell);
				} else {
					moveHead(lizard, newCell);
				}
			} else if (selectedSegment == tail) {

				// Moving the tail segment
				BodySegment segmentAhead = lizard.getSegmentAhead(selectedSegment);
				if (segmentAhead.getCell() == newCell) {
					moveHead(lizard, newCell);
				} else {
					moveTail(lizard, newCell);
				}
			} else if (lizard.getDirectionToSegmentAhead(head) == dir) {

				// Moving tail segment
				moveTail(lizard, newCell);
			} else if (lizard.getDirectionToSegmentBehind(tail) == dir) {

				// Moving head segment
				moveHead(lizard, newCell);
			}

			// Checks if the new cell has an exit
			if (newCell.getExit() != null) {

				// Removing the lizard if it moves to an exit
				removeLizard(lizard);

				// Checks if the lizards were removed from the grid
				if (getLizards().isEmpty()) {

					// Letting the player know that they won
					if (dialogListener != null) {
						dialogListener.showDialog("You win!");
					}
				}
			}
		}
	}

	/**
	 * Moves the head segment of the lizard to the specified cell. It updates the
	 * position of the head segment of the lizard to that specified cell. It also
	 * adjusts the positions of the other segments to maintain the continuity of the
	 * lizard's body.
	 *
	 * @param liz          the lizard
	 * @param moveHeadCell the cell to which the head segment should be moved
	 * 
	 */
	private void moveHead(Lizard liz, Cell moveHeadCell) {
		ArrayList<BodySegment> segments = liz.getSegments();

		// Gets the tail segment of lizard
		BodySegment tail = liz.getTailSegment();

		// Removing the lizard from the cell where the tail was
		tail.getCell().removeLizard();

		int i;

		// Moving each body segment forward
		for (i = 0; i < segments.size() - 1; i++) {
			Cell nextCell = segments.get(i + 1).getCell();
			segments.get(i).setCell(nextCell);
		}

		// Updating the position of the head segment of the lizard to the new cell
		BodySegment head = segments.get(i);
		head.setCell(moveHeadCell);
	}

	/**
	 * Moves the tail segment of the lizard to the specified cell. It updates the
	 * position of the tail segment of the lizard to the specified cell. It also
	 * adjusts the positions of the other segments to maintain the continuity of the
	 * lizard's body.
	 *
	 * @param liz          the lizard
	 * @param moveTailCell the cell to which the tail segment should be moved
	 * 
	 */
	private void moveTail(Lizard liz, Cell moveTailCell) {
		ArrayList<BodySegment> segments = liz.getSegments();

		// Gets the head segment of lizard
		BodySegment head = liz.getHeadSegment();

		// Removing the lizard from the cell where the head was
		head.getCell().removeLizard();

		int i;

		// Moving each body segment backward
		for (i = segments.size() - 1; i > 0; i--) {
			Cell nextCell = segments.get(i - 1).getCell();
			segments.get(i).setCell(nextCell);
		}

		// Updating the position of the tail segment of the lizard to the new cell
		BodySegment tail = segments.get(i);
		tail.setCell(moveTailCell);
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}

	@Override
	public String toString() {
		String str = "---------- GRID ----------\n";
		str += "Dimensions:\n";
		str += getWidth() + " " + getHeight() + "\n";
		str += "Layout:\n";
		for (int y = 0; y < getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			for (int x = 0; x < getWidth(); x++) {
				str += getCell(x, y);
			}
		}
		str += "\nLizards:\n";
		for (Lizard l : getLizards()) {
			str += l;
		}
		str += "\n--------------------------\n";
		return str;
	}
}
