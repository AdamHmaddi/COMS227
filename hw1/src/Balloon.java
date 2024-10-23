package hw1;

/*
 * The Balloon class represents a hot air balloon simulation. It shows 
 * the behavior of a hot air balloon flying in the atmosphere, taking
 * into account some factors such as air temperature, wind direction, fuel, 
 * fuel burn rate, altitude, and mass 
 * @author Adam Hmaddi
 */
public class Balloon {

		private double initialAirTemp; // Initial air temperature outside the balloon
		private double initialWindDirection; // Initial wind direction
		private double airTemp; // Current air temperature outside the balloon
		private double windDirection; // Current wind direction
		private double balloonTemp; // Current balloon temperature
		private long simulationTime; // Current simulation time 
		private double altitude; // Current altitude of the balloon
		private double fuel; // Total fuel available 
		private double remainFuel; // Remaining fuel of the balloon
		private double burnRate; // Rate at which fuel is burned
		private double mass; // Mass of the balloon 
		private double velocity; // Velocity of the balloon
		private double tetherLength; // Length of the tether
				
		private final double heatLoss = 0.1; // Heat loss factor
		private final double balloonVolume = 61234; // Volume of air in the balloon in cubic meters
		private final double gravity = 9.81; // Acceleration due to gravity in meters per second square
		private final double constGas = 287.05; // Gas constant in Joule per Kilograms Kelvin 
		private final double STANDARD_PRESSURE = 1013.25; // Standard pressure in hectoPascal
		private final double KELVIN_AT_ZERO_CELSIUS = 273.15; // Kelvin degrees conversion at 0 degrees Celsius
		
		
		/**
		 * Constructor initializes the balloon with given 
		 * air temperature and wind direction. 
		 * @param airTemp The current air temperature outside the balloon
		 * @param windDirection The current wind direction
		 */
		public Balloon (double airTemp, double windDirection) {
			
			initialAirTemp = airTemp;
			initialWindDirection = windDirection;
			this.airTemp = airTemp;
			this.windDirection = windDirection;
			
			balloonTemp = airTemp;
			simulationTime = 0;
			altitude = 0;
			remainFuel = 0;
			burnRate = 0;
			mass = 0;
			velocity = 0;
			tetherLength = 0;
		}
		
		/**
		 * Gets the remaining fuel that can be used to heat
		 * the air in the balloon
		 * @return the remaining fuel
		 */
		public double getFuelRemaining() {
			return remainFuel;
		}
		
		/**
		 * Sets the remaining fuel available for heating the
		 * air in the balloon
		 * @param fuel The remaining fuel 
		 */
		public void setFuelRemaning(double fuel) {
			remainFuel = fuel;
		}
		
		/**
		 * Gets the mass of the balloon
		 * @return the mass of the balloon
		 */
		public double getBalloonMass() {
			return mass;
		}
		
		/**
		 * Sets the mass of the balloon
		 * @param balloonMass The mass of the balloon
		 */
		public void setBalloonMass(double balloonMass) {
			mass = balloonMass;
		}
		
		/**
		 * Gets the current outside air temperature
		 * @return The current outside air temperature
		 */
		public double getOutsideAirTemp() {
			
			return airTemp;
		}
		
		/**
		 * Sets the current outside air temperature
		 * @param temp The current outside air temperature
		 */
		public void setOutsideAirTemp(double temp) {
			
			airTemp = temp;
		}
		
		/**
		 * Gets the rate at which fuel is burned
		 * @return The burn rate 
		 */
		public double getFuelBurnRate() {
			return burnRate;
		}
		
		/**
		 * Sets the rate at which fuel is burned
		 * @param rate The burn rate
		 */
		public void setFuelBurnRate(double rate) {
			burnRate = rate;
		}
		
		/**
		 * Gets the current temperature inside the balloon
		 * @return The current balloon temperature
		 */
		public double getBalloonTemp() {
			return balloonTemp;
		}
		
		/**
		 * Sets the current temperature inside the balloon
		 * @param temp The current balloon temperature
		 */
		public void setBalloonTemp(double temp) {
			balloonTemp = temp;
		}
		
		/**
		 * Gets the velocity of the balloon
		 * @return The balloon velocity
		 */
		public double getVelocity() {
			return velocity;
		}
		
		/**
		 * Gets the current altitude of the balloon
		 * @return The current altitude of the balloon
		 */
		public double getAltitude() {
			return altitude;
		}
		
		/**
		 * Gets the length of the tether
		 * @return The length of the tether 
		 */
		public double getTetherLength() {
			return tetherLength;
		}
		
		/**
		 * Gets the remaining length of the tether
		 * @return The remaining length of the tether
		 */
		public double getTetherRemaining() {
			return tetherLength - altitude;
		}
		
		/**
		 * Sets the length of the tether
		 * @param length The length of the tether
		 */
		public void setTetherLength(double length) {
			tetherLength = length;
		} 
		
		/**
		 * Gets the current wind direction between 0 inclusively and 360 exclusively
		 * @return The current wind direction
		 */
		public double getWindDirection() {
			
			return windDirection;
		}
		
		/**
		 * Updates the wind direction by the given angle
		 * @param deg The angle by which to change the wind direction
		 */
		public void changeWindDirection(double deg) {
			
			windDirection = (windDirection + deg + 360) % 360; 
		}
		
		/**
		 * Gets the number of minutes that passed in the simulation
		 * @return The number of minutes passed
		 */
		public long getMinutes() {
			return simulationTime / 60;
		}
		
		/**
		 * Gets the number of seconds that passed in the simulation
		 * @return The number of seconds passed 
		 */
		public long getSeconds() {
			return simulationTime % 60;
		}
		
		/**
		 * Updates the state of the balloon after one time step
		 * Calculates new balloon temperature, altitude, velocity, and fuel consumption
		 */
		public void update() {
			simulationTime += 1;
			fuel = Math.min(remainFuel, burnRate);
			
			double differenceT = fuel + (airTemp - balloonTemp) * heatLoss; // Rate of change in the balloon's air temperature per second
			balloonTemp = balloonTemp + differenceT; // Temperature of the air inside the balloon after one second
			double airDensity = STANDARD_PRESSURE / (constGas * (airTemp + KELVIN_AT_ZERO_CELSIUS)); // Density of the surrounding air in Kilograms per cubic meters
			double balloonDensity = STANDARD_PRESSURE / (constGas * (balloonTemp + KELVIN_AT_ZERO_CELSIUS)); // Density of the balloon air in Kilograms per cubic meters
			double forceLift = balloonVolume * (airDensity - balloonDensity) * gravity; // Force of lift in Newtons
			double forceGravity = mass * gravity; // Force of gravity in Newtons
			double netForce = forceLift - forceGravity; // Net force in upward direction in Newtons
			double netAcceleration = netForce / mass; // Net acceleration in upward direction
			velocity = velocity + netAcceleration; // Velocity in upward direction in meters per seconds assuming 1 second of time
			altitude = altitude + velocity; // Altitude of the balloon after one second
			
			altitude = Math.max(altitude, 0); // It sets the altitude to 0 if it is below 0
			altitude = Math.min(altitude, tetherLength); // It sets the altitude to tether length if its above the tether length
			remainFuel -= fuel; // Substracting the fuel consumed from the reamining fuel
			remainFuel = Math.max(remainFuel, 0); // It makes sure that the fuel doesn't go below 0
			


		}
		
		/**
		 * Resets the balloon simulation to its initial state
		 * Sets all parameters to their initial value
		 * @author
		 */
		public void reset() {
			
			airTemp = initialAirTemp;
			windDirection = initialWindDirection;
			balloonTemp = airTemp;
			
			simulationTime = 0;
			altitude = 0;
			remainFuel = 0;
			burnRate = 0;
			mass = 0;
			velocity = 0;
			tetherLength = 0;
		}

}
