package query;

import filters.Filter;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import twitter4j.Status;
import ui.MapMarkerPretty;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static util.Util.imageFromURL;
import static util.Util.statusCoordinate;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A query over the twitter stream.
 * TODO: Task 4: you are to complete this class.
 */
public class Query implements Observer {

    // The map on which to display markers when the query matches
    private final JMapViewer map;

    // Each query has its own "layer" so they can be turned on and off all at once
    private Layer layer;

    // The color of the outside area of the marker
    private final Color color;

    // The string representing the filter for this query
    private final String queryString;

    // The filter parsed from the queryString
    private final Filter filter;

    // The checkBox in the UI corresponding to this query (so we can turn it on and off and delete it)
    private JCheckBox checkBox;

    // A list to keep track of all markers associated with this query
    private final List<MapMarkerCircle> markers;

    private static final Logger LOGGER = Logger.getLogger(Query.class.getName());

    /**
     * Constructor for creating a new Query object.
     *
     * @param queryString The string that represents the filter for this query.
     * @param color The color used for the marker outline.
     * @param map The map object where markers will be displayed.
     */
    public Query(String queryString, Color color, JMapViewer map) {
        this.queryString = queryString;
        this.filter = Filter.parse(queryString);
        this.color = color;
        this.layer = new Layer(queryString);
        this.map = map;
        markers = new ArrayList<>();
    }

    /**
     * @return The color used for the marker outline.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return The string representation of the query filter.
     */
    public String getQueryString() {
        return queryString;
    }

    /**
     * @return The filter object parsed from the query string.
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * @return The layer associated with this query.
     */
    public Layer getLayer() {
        return layer;
    }

    /**
     * @return The checkbox associated with this query in the UI.
     */
    public JCheckBox getCheckBox() {
        return checkBox;
    }

    /**
     * Sets the checkbox associated with this query.
     *
     * @param checkBox The checkbox component to associate with this query.
     */
    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    /**
     * Sets the visibility of the markers related to this query.
     *
     * @param visible If true, markers will be visible on the map; otherwise, they will be hidden.
     */
    public void setVisible(boolean visible) {
        layer.setVisible(visible);
    }

    /**
     * @return True if the markers related to this query are visible, false otherwise.
     */
    public boolean getVisible() {
        return layer.isVisible();
    }

    /**
     * Provides a string representation of the Query object.
     *
     * @return A string describing the query.
     */
    @Override
    public String toString() {
        return "Query: " + queryString;
    }

    /**
     * This query is no longer interesting, so terminate it and remove all traces of its existence.
     */
    public void terminate() {
        // Remove all markers associated with this query from the map.
        for (MapMarkerCircle marker : markers) {
            map.removeMapMarker(marker);
        }

        // Clear the markers list to free up memory.
        markers.clear();

        // Optionally, set layer and checkBox to null to further release resources.
        layer = null;
        checkBox = null;
    }

    /**
     * Updates the map with a new marker if the status matches the filter.
     *
     * @param o Observable object, usually representing a Twitter stream.
     * @param arg Argument passed to the update method, expected to be a Status object.
     */

    @Override
    public void update(Observable o, Object arg) {
        Status status = (Status) arg;

        // Check if the status matches the filter criteria.
        if (filter.matches(status)) {
            // Get the coordinates of the status, which is guaranteed to be non-null.
            Coordinate coordinate = statusCoordinate(status);

            // Extract the status ID, user image URL, and status text.
            long ID = status.getId();
            String imageURL = status.getUser().getProfileImageURL();

            // Declare image variable, it will be assigned in the try block.
            Image image;

            try {
                image = imageFromURL(imageURL);
            } catch (Exception e) {
                // Log the error instead of printing the stack trace.
                LOGGER.log(Level.SEVERE, "Failed to load image from URL: " + imageURL, e);
                return;
            }

            // Create a new marker with the gathered information and add it to the map.
            MapMarkerPretty marker = new MapMarkerPretty(layer, color, coordinate, ID, imageURL, image, status.getText());
            markers.add(marker); // Add marker to the list for future management.
            map.addMapMarker(marker);
        }
    }
}