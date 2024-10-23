package hw3;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

import api.BodySegment;

import api.Cell;
import api.Exit;
import api.Wall;

/**
 * Utility class with static methods for loading game files.
 * 
 * @author Adam Hmaddi
 */
public class GameFileUtil {
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, LizardGame game) {

		// Opens the file for reading
		File file = new File(filePath);
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(file);
		} catch (Exception e) {

			// If the file can't be opened, return without modifying anything to the game
			return;
		}

		// Reading the dimensions of the grid beginning from the first line (start) of
		// that file
		String dimensionString = fileScanner.nextLine();
		String[] dimensionsArr = dimensionString.split("x");
		int width = Integer.parseInt(dimensionsArr[0]);
		int height = Integer.parseInt(dimensionsArr[1]);
		game.resetGrid(width, height);

		// Loading the grid configuration from the file
		for (int i = 0; i < height; i++) {
			String line = fileScanner.nextLine();
			for (int j = 0; j < width; j++) {
				char c = line.charAt(j);
				if (c == 'W') {

					// Add a wall to the game
					Cell cell = game.getCell(j, i);
					game.addWall(new Wall(cell));
				} else if (c == 'E') {

					// Add an exit to the game
					Cell cell = game.getCell(j, i);
					game.addExit(new Exit(cell));
				}
			}
		}

		// Loading the lizard configuration from our file
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			String first = lineScanner.next();
			if (first.equals("L")) {

				// Creating a new lizard and adding body segments
				ArrayList<BodySegment> segments = new ArrayList<BodySegment>();
				Lizard lizard = new Lizard();
				while (lineScanner.hasNext()) {
					String segmentLocation = lineScanner.next();
					String[] coordinates = segmentLocation.split(",");
					int col = Integer.parseInt(coordinates[0]);
					int row = Integer.parseInt(coordinates[1]);
					Cell cell = game.getCell(col, row);
					BodySegment segment = new BodySegment(lizard, cell);
					segments.add(segment);
				}

				// Setting the segments of the lizard and also adding it to our game
				lizard.setSegments(segments);
				game.addLizard(lizard);

			}

			lineScanner.close();

		}

		// Closing the file scanner
		fileScanner.close();

	}
}
