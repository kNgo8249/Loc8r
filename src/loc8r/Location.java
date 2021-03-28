package loc8r;

/**
 * This class represents a location that has multiple fields relating to its
 * name, address, coordinates and rating
 * 
 * @author Kenny Ngo
 * @version 1.1
 * 
 */
public class Location {
	private String name;
	private String address;
	private String type;
	private double xpos;
	private double ypos;
	private int rating;

	/**
	 * Generates a new location with a name, address, type, and x and y coordinates.
	 * 
	 * @param name
	 * @param address
	 * @param type
	 * @param xpos
	 * @param ypos
	 */
	public Location(String name, String address, String type, double xpos, double ypos) {
		this.name = name;
		this.address = address;
		this.type = type;
		this.xpos = xpos;
		this.ypos = ypos;
		rating = 0;
	}

	/**
	 * Returns the rating of the location
	 * 
	 * @return Rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Allows the user to set the rating of the location
	 * 
	 * @param rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Returns the name of the location
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the address of the location
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Returns the type of the location
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the x (longitude) coordinate of the location
	 * 
	 * @return xpos
	 */
	public double getXpos() {
		return xpos;
	}

	/**
	 * Returns the y (latitude) coordinate of the location
	 * 
	 * @return ypos
	 */
	public double getYpos() {
		return ypos;
	}

	/**
	 * Returns the distance between the location and the xnow and ynow coordinates
	 * 
	 * @param xnow
	 * @param ynow
	 * @return distance
	 */
	public double getDistance(double xnow, double ynow) {
		double x2 = this.getXpos();
		double y2 = this.getYpos();
		double x = (x2 - xnow);
		double y = (y2 - ynow);

		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

}