package sgtravel.ui.map;

import sgtravel.model.locations.Venue;
import sgtravel.ui.UiPart;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.fxml.FXML;

/**
 * Represents a point on the map.
 */
public class PointCard extends UiPart<StackPane> {
    private static final String FXML = "PointCard.fxml";
    private static final double LENGTH_Y = 240;
    private static final double LENGTH_Y_START = 230;
    private static final double MAP_IMAGE_Y_LATITUDE_START = 1.486;
    private static final double MAP_IMAGE_Y_LATITUDE_END = 1.218;
    private static final double LENGTH_X = 288;
    private static final double LENGTH_X_START = 30;
    private static final double MAP_IMAGE_X_LONGITUDE_START = 104.028;
    private static final double MAP_IMAGE_X_LONGITUDE_END = 103.630;
    private Venue venue;

    @FXML
    private Circle circle;

    /**
     * Creates the PointCard object.
     *
     * @param venue The Venue for the PointCard.
     * @param color The color for the PointCard.
     */
    private PointCard(Venue venue, Paint color) {
        super(FXML);
        this.venue = venue;
        circle.setFill(color);
        setLocation();
    }

    /**
     * Sets the location of the PointCard.
     */
    private void setLocation() {
        double offsetY = LENGTH_Y_START
                - ((venue.getLatitude() - MAP_IMAGE_Y_LATITUDE_END) * LENGTH_Y /
                        (MAP_IMAGE_Y_LATITUDE_START - MAP_IMAGE_Y_LATITUDE_END));
        double offsetX = LENGTH_X_START
                + (venue.getLongitude() - MAP_IMAGE_X_LONGITUDE_END) * LENGTH_X /
                (MAP_IMAGE_X_LONGITUDE_START - MAP_IMAGE_X_LONGITUDE_END);

        AnchorPane.setLeftAnchor(getRoot(), offsetX);
        AnchorPane.setTopAnchor(getRoot(), offsetY);
        Tooltip tooltip = new Tooltip(venue.getAddress());
        Tooltip.install(getRoot(), tooltip);
    }

    /**
     * Creates a new PointCard.
     *
     * @param venue The Venue for the PointCard.
     * @param color The color for the PointCard.
     * @return The PointCard.
     */
    public static StackPane getCard(Venue venue, Paint color) {
        return new PointCard(venue, color).getRoot();
    }
}
