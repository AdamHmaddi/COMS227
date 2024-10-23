
package hw2;

/**
 * Models a simplified baseball-like game called Fuzzball.
 * 
 * @author ADAM_HMADDI
 */
public class FuzzballGame {
	/**
	 * Number of strikes causing a player to be out.
	 */
	public static final int MAX_STRIKES = 2;

	/**
	 * Number of balls causing a player to walk.
	 */
	public static final int MAX_BALLS = 5;

	/**
	 * Number of outs before the teams switch.
	 */
	public static final int MAX_OUTS = 3;

	// TODO: EVERTHING ELSE
	// Note that this code will not compile until you have put in stubs for all
	// the required methods.

	/*
	 * Number of innings for the game
	 */
	private int numInnings;

	/*
	 * Current inning of the game
	 */
	private int currentInning;

	/*
	 * Indicates if it is the top of the inning, which is team 0
	 */
	private boolean topOfInning;

	/*
	 * Count of balls for the current batter
	 */
	private int numBall;

	/*
	 * Count of strikes for the current batter
	 */
	private int numStrike;

	/*
	 * Number of outs for the current team at bat
	 */
	private int numOut;

	/*
	 * Boolean indicating if there is a runner on first base
	 */
	private boolean base1;

	/*
	 * Boolean indicating if there is a runner on second base
	 */
	private boolean base2;

	/*
	 * Boolean indicating if there is a runner on third base
	 */
	private boolean base3;

	/*
	 * Score for team 0
	 */
	private int score_0;

	/*
	 * Score for team 1
	 */
	private int score_1;

	/**
	 * Constructs a Fuzzball game with the given number of innings
	 * 
	 * @param givenNumInnings the number of innings for the game
	 */
	public FuzzballGame(int givenNumInnings) {
		numInnings = givenNumInnings;
		currentInning = 1;
		topOfInning = true;
		numBall = 0;
		numStrike = 0;
		numOut = 0;
		base1 = false;
		base2 = false;
		base3 = false;
		score_0 = 0;
		score_1 = 0;
	}

	/*
	 * This method indicates a bad pitch at which the batter did not swing. It adds
	 * 1 to the batter's ball count, which can result in a walk if the count reaches
	 * the maximum. It does nothing if the game has ended.
	 */
	public void ball() {
		if (!gameEnded()) {
			numBall++;

			if (numBall == MAX_BALLS) {
				walk();
				batterSwitch();
			}
		}
	}

	/*
	 * This is a helper method simulating a walk when the ball count reaches the
	 * maximum, which is 5.
	 */
	private void walk() {
		if (base1) {

			if (base2) {

				if (base3) {
					scoreResult();
				}

				else {
					base3 = true;
				}
			}

			else {
				base2 = true;
			}
		} else {
			base1 = true;
		}
	}

	/*
	 * This method indicates that the batter is out due to a caught fly. It also
	 * increments the number of outs. If it reaches the maximum of outs, it would
	 * switch teams. It does nothing if the game has ended.
	 */
	public void caughtFly() {
		if (!gameEnded()) {
			numOut++;
			batterSwitch();
			teamsSwitch();
		}
	}

	/**
	 * Checks if the game is over.
	 * 
	 * @return true if the game is over, false otherwise.
	 */
	public boolean gameEnded() {
		return currentInning > numInnings;
	}

	/**
	 * Gets the count of balls for the current batter.
	 * 
	 * @return the count of balls for the current batter.
	 */
	public int getBallCount() {
		return numBall;
	}

	/**
	 * Gets the number of called strikes for the current batter.
	 * 
	 * @return the number of called strikes for the current batter.
	 */
	public int getCalledStrikes() {
		return numStrike;
	}

	/**
	 * Gets the number of outs for the team currently at bat.
	 * 
	 * @return the number of outs for the team currently at bat.
	 */
	public int getCurrentOuts() {
		return numOut;
	}

	/**
	 * Gets the score for team 0.
	 * 
	 * @return the score for team 0.
	 */
	public int getTeam0Score() {
		return score_0;
	}

	/**
	 * Gets the score for team 1.
	 * 
	 * @return the score for team 1.
	 */
	public int getTeam1Score() {
		return score_1;
	}

	/**
	 * This method is called to indicate that the batter hit the ball. It gives an
	 * outcome based on the distance the ball traveled. The method does nothing if
	 * the game has ended
	 * 
	 * @param distance the distance the ball travels (possibly negative): if it is
	 *                 less than 15: the his is considered as a foul and the batter
	 *                 is out. If it is between 15 inclusive and 150 exclusive: the
	 *                 hit is a single. The runners advances by 1 to the next base.
	 *                 If it is between 150 inclusive and 200 exclusive: the hit is
	 *                 a double. The runners advances by 2. If it is between 200
	 *                 inclusive and 250 exclusive: the hit is a triple. The runners
	 *                 advances by 3. If it is 250 or more: the hit is a home run.
	 *                 All runners currently on base advance to home.
	 */
	public void hit(int distance) {
		if (!gameEnded()) {
			if (distance < 15) {
				numOut++;
				batterSwitch();
				teamsSwitch();
			} else {
				shiftRunner();
				base1 = true;
			}
			if (distance >= 150 && distance < 200) {
				shiftRunner();

			}
			if (distance >= 200 && distance < 250) {
				shiftRunner();
				shiftRunner();
			}
			if (distance >= 250) {
				shiftRunner();
				shiftRunner();
				shiftRunner();
			}
		}
		batterSwitch();
	}

	/*
	 * This helper method indicates a single hit, it can also be used as a double if
	 * you duplicate it and as a triple if it's called three times.
	 */
	private void shiftRunner() {
		if (base3) {
			scoreResult();
		}
		base3 = base2;
		base2 = base1;
		base1 = false;
	}

	/*
	 * This helper method keeps incrementing the score for the right team.
	 */
	private void scoreResult() {
		if (topOfInning) {
			score_0++;
		} else {
			score_1++;
		}
	}

	/**
	 * Checks if it is the first half of the inning (team 0 is at bat).
	 * 
	 * @return true if it's the first half of the inning, false otherwise.
	 */
	public boolean isTopOfInning() {
		return topOfInning;
	}

	/**
	 * Checks if there is a runner on the indicated base.
	 * 
	 * @param which that checks the base number.
	 * @return true if there is a runner on the indicated base, false otherwise.
	 */
	public boolean runnerOnBase(int which) {
		if (which == 1) {
			return base1;
		} else if (which == 2) {
			return base2;
		} else if (which == 3) {
			return base3;
		} else {
			return false;
		}
	}

	/**
	 * This method is called to indicate a strike for the current batter. The method
	 * does nothing if the game has ended.
	 * 
	 * @param swung if it is true, the batter is immediately out. Otherwise, 1 is
	 *              added to the batters current count of called strikes.
	 */
	public void strike(boolean swung) {
		if (!gameEnded()) {
			if (swung) {
				numOut++;
				batterSwitch();
			} else {
				numStrike++;
				if (numStrike == MAX_STRIKES) {
					numOut++;
					batterSwitch();
				}
			}
			teamsSwitch();
		}
	}

	/*
	 * This helper method makes the next batter to come and reset the ball count and
	 * strikes to 0.
	 */
	private void batterSwitch() {
		numBall = 0;
		numStrike = 0;
	}

	/*
	 * This helper method checks if the number of outs reached the maximum. If it is
	 * true, the teams will switch including reseting their bases and batters.
	 */
	private void teamsSwitch() {
		if (numOut == MAX_OUTS) {
			batterSwitch();
			base1 = false;
			base2 = false;
			base3 = false;
			numOut = 0;
			if (topOfInning) {
				topOfInning = false;
			} else {
				topOfInning = true;
				currentInning++;
			}
		}
	}

	/**
	 * Gets the current inning, or the number of innings plus one in case the game
	 * is over.
	 * 
	 * @return the current inning, or the number of innings plus one in case the
	 *         game is over.
	 */
	public int whichInning() {
		if (gameEnded()) {
			return numInnings + 1;
		} else {
			return currentInning;
		}
	}

	// The methods below are provided for you and you should not modify them.
	// The compile errors will go away after you have written stubs for the
	// rest of the API methods.

	/**
	 * Returns a three-character string representing the players on base, in the
	 * order first, second, and third, where 'X' indicates a player is present and
	 * 'o' indicates no player. For example, the string "oXX" means that there are
	 * players on second and third but not on first.
	 * 
	 * @return three-character string showing players on base
	 */
	public String getBases() {
		return (runnerOnBase(1) ? "X" : "o") + (runnerOnBase(2) ? "X" : "o") + (runnerOnBase(3) ? "X" : "o");
	}

	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * 
	 * <pre>
	 *      ooo Inning:1 [T] Score:0-0 Balls:0 Strikes:0 Outs:0
	 * </pre>
	 * 
	 * The first three characters represent the players on base as returned by the
	 * <code>getBases()</code> method. The 'T' after the inning number indicates
	 * it's the top of the inning, and a 'B' would indicate the bottom. The score
	 * always shows team 0 first.
	 * 
	 * @return a single line string representation of the state of the game
	 */
	public String toString() {
		String bases = getBases();
		String topOrBottom = (isTopOfInning() ? "T" : "B");
		String fmt = "%s Inning:%d [%s] Score:%d-%d Balls:%d Strikes:%d Outs:%d";
		return String.format(fmt, bases, whichInning(), topOrBottom, getTeam0Score(), getTeam1Score(), getBallCount(),
				getCalledStrikes(), getCurrentOuts());
	}
}
