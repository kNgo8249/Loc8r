import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/**
 * This class contains a database in the form of a {@code HashMap} that stores the locations of all of the business types. 
 * @author Kenny
 * @version 1.0
 *   
 * NOTE: The actual spelling of Loc8er is Loc8r
 *
*/

public class Loc8erDB {

    private static HashMap<String, ArrayList<Location>> businesses = new HashMap<>();

    /**
     * Returns the HashMap which contains all of the businesses.
     * @return businesses
     */
    public static HashMap<String, ArrayList<Location>> getBusiness() {
        return businesses;
    }

    /**
     * Constructs a new {@code Loc8erDB} by taking the contents of the movieReviews.txt file and
     * placing them within the {@code HashMap}
     */
    public Loc8erDB() {
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
                if(!businesses.get(type).contains(new Location(name, address, type, xpos, ypos)))
                    businesses.get(type).add(new Location(name, address, type, xpos, ypos));

            }

            fin.close();

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Could not load database.");
        }

    }


    /**
     * Finds the top 8 locations based on a specific location type, and x and y coordinates
     * @param type
     * @param xcoord
     * @param ycoord
     * @return top 8 locations
     */
    public static List<Location> determine8(String type, double xcoord, double ycoord) {
        type = type.toLowerCase();
        if(!(businesses.containsKey(type))) {
            return null;
        }

        ArrayList<Location> businessOfType = businesses.get(type);
        ArrayList<Location> topRatings = new ArrayList<>();
        for(Location l : businessOfType) {
            if(l.getRating() == 1) {
                topRatings.add(l);
            }
        }
        for(Location l : businessOfType) {
            if(l.getRating() == 0 && topRatings.size() < 8) {
                topRatings.add(l);
            }
        }

        int n = topRatings.size();
        for (int i=1; i<n; ++i)
        {
            Location val = topRatings.get(i);
            double key = val.getDistance(xcoord, ycoord);
            int j = i-1;
            while (j>=0 && topRatings.get(j).getDistance(xcoord, ycoord) > key)
            {
                topRatings.set(j+1, topRatings.get(j));

                j = j-1;
            }
            topRatings.set(j+1, val);
        }

        return topRatings;
    }
}