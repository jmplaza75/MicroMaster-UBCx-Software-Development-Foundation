package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import java.awt.Color;

public class MapMarkerSimple extends MapMarkerCircle {
    private static final double DEFAULT_MARKER_SIZE = 5.0;
    private static final Color DEFAULT_COLOR = Color.RED;
    private static final Color BORDER_COLOR = Color.BLACK;

    public MapMarkerSimple(Layer layer, Coordinate coordinate) {
        super(layer, null, coordinate, DEFAULT_MARKER_SIZE, STYLE.FIXED, getDefaultStyle());
        setColor(BORDER_COLOR);
        setBackColor(DEFAULT_COLOR);
    }
}
