package bearmaps;
import bearmaps.utils.Constants;
import bearmaps.utils.graph.streetmap.Node;
import bearmaps.utils.graph.streetmap.StreetMapGraph;
import bearmaps.utils.ps.KDTree;
import bearmaps.utils.ps.Point;
import edu.princeton.cs.algs4.TrieSET;
import org.apache.commons.math3.geometry.spherical.twod.Vertex;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    HashMap<Point, Long> pointMap = new HashMap<>();
    KDTree tree;
    private TrieSET myTrie;
    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);

        // You might find it helpful to uncomment the line below:
        List<Point> points = new ArrayList<>();
        List<Node> nodes = this.getNodes();
        myTrie = new TrieSET();
        HashMap<String, List<Node>> allNodeNames = new HashMap<>();

        for (Node node : nodes) {
            if(node.name() != null) {
                myTrie.add(cleanString(node.name()));
                if (!allNodeNames.containsKey(cleanString(node.name()))) {
                    allNodeNames.put(cleanString(node.name()), new ArrayList<>());
                }
                List returnName = allNodeNames.get(cleanString(node.name()));
                returnName.add(node);
            }
            double x = projectToX(node.lon(), node.lat());
            double y = projectToY(node.lon(), node.lat());
            pointMap.put(new Point(x, y), node.id());
            if (isNavigableNode(node)) {
                points.add(new Point(x, y));
            }
        }
        tree = new KDTree(points);
    }


    /**
     * For Project Part III
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {

        double x = projectToX(lon, lat);
        double y = projectToY(lon, lat);
        return pointMap.get(tree.nearest1(x, y));
    }

    static double projectToX(double lon, double lat) {
        double dlon = Math.toRadians(lon - ROOT_LON);
        double phi = Math.toRadians(lat);
        double b = Math.sin(dlon) * Math.cos(phi);
        return (K0 / 2) * Math.log((1 + b) / (1 - b));
    }
    static double projectToY(double lon, double lat) {
        double dlon = Math.toRadians(lon - ROOT_LON);
        double phi = Math.toRadians(lat);
        double con = Math.atan(Math.tan(phi) / Math.cos(dlon));
        return K0 * (con - Math.toRadians(ROOT_LAT));
    }


    /**
     * For Project Part IV (extra credit)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
     /**   List<String> allNames = new ArrayList<>();
        List<String> cleanedNames = (List<String>) myTrie.keysWithPrefix(cleanString(prefix));
        for (String firstName : cleanedNames) {
            if (allNames != null && !allNames.contains(firstName)) {
                for (Node node : cleanedNames)
                allNames.add(cleanedNames.get(node.name()));
            }
        }
        return allNames; */
     return new ArrayList<String>();
    }

    /**
     * For Project Part IV (extra credit)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        return new LinkedList<>();
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    private static final double K0 = 1.0;
    /** Latitude centered on Berkeley. */
    private static final double ROOT_LAT = (Constants.ROOT_ULLAT + Constants.ROOT_LRLAT) / 2;
    /** Longitude centered on Berkeley. */
    private static final double ROOT_LON = (Constants.ROOT_ULLON + Constants.ROOT_LRLON) / 2;
}
