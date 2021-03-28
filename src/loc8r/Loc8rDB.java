package loc8r;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains a database in the form of a {@code HashMap} that stores
 * the locations of all of the business types.
 * 
 * @author Kenny Ngo
 * @version 1.1
 *
 */

public class Loc8rDB {

	private HashMap<String, ArrayList<Location>> businesses = new HashMap<>();

	/**
	 * Returns the HashMap which contains all of the businesses.
	 * 
	 * @return businesses
	 */
	public HashMap<String, ArrayList<Location>> getBusiness() {
		return businesses;
	}

	/**
	 * Constructs a new {@code Loc8rDB} by taking the contents of the
	 * movieReviews.txt file and placing them within the {@code HashMap}
	 */
	public Loc8rDB() {
		try {
			Scanner fin = new Scanner(new File("locations.txt"));
			while (fin.hasNextLine()) {
				String businessEntry = fin.nextLine();
				String[] entryParts = businessEntry.split("\\t");

				String name = entryParts[0];
				String address = entryParts[1];
				String type = entryParts[2];
				double xpos = Double.parseDouble(entryParts[3]);
				double ypos = Double.parseDouble(entryParts[4]);

				if (!businesses.containsKey(type)) {
					businesses.put(type, new ArrayList<Location>());
				}
				if (!businesses.get(type).contains(new Location(name, address, type, xpos, ypos)))
					businesses.get(type).add(new Location(name, address, type, xpos, ypos));

			}

			fin.close();

		}
		catch (FileNotFoundException e) {
			System.out.println("ERROR: Could not load database.");
		}

	}

	/**
	 * Finds the top 8 locations based on a specific location type, and x and y
	 * coordinates
	 * 
	 * @param type
	 * @param xcoord
	 * @param ycoord
	 * @return top 8 locations
	 */
	public List<Location> determine8(String type, double xcoord, double ycoord) {
		type = type.toLowerCase();
		if (!(businesses.containsKey(type))) {
			return null;
		}

		ArrayList<Location> businessOfType = businesses.get(type);
		ArrayList<Location> topLocations = new ArrayList<>();
		ArrayList<Location> remainingLocations = new ArrayList<>();

		for (Location l : businessOfType) {
			if (l.getRating() == 1 && topLocations.size() < 8) {
				topLocations.add(l);
			}
		}

		Collections.sort(topLocations, new Comparator<Location>() {
			public int compare(Location loc1, Location loc2) {
				return Double.compare(loc1.getDistance(xcoord, ycoord), loc2.getDistance(xcoord, ycoord));
			}
		});

		for (Location l : businessOfType) {
			if (l.getRating() == 0 && remainingLocations.size() + topLocations.size() < 8) {
				remainingLocations.add(l);
			}
		}

		Collections.sort(remainingLocations, new Comparator<Location>() {
			public int compare(Location loc1, Location loc2) {
				return Double.compare(loc1.getDistance(xcoord, ycoord), loc2.getDistance(xcoord, ycoord));
			}
		});

		topLocations.addAll(remainingLocations);

		return topLocations;
	}
}