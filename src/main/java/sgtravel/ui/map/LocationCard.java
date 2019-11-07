package sgtravel.ui.map;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import sgtravel.model.locations.Venue;
import sgtravel.ui.UiPart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a location card in the UI.
 */
public class LocationCard extends UiPart<StackPane> {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String FXML = "LocationCard.fxml";
    private static final double MAP_WINDOW_SCALING = 0.667;
    private static final double MAP_IMAGE_ASPECT_RATIO = 1.47154;
    private static final double MAP_IMAGE_X_START = 104.025;
    private static final double MAP_IMAGE_X_END = 103.63;
    private static final double MAP_IMAGE_Y_START = 1.482;
    private static final double MAP_IMAGE_Y_END = 1.2;

    /**
     * Creates the LocationCard object.
     *
     * @param location The location for the LocationCard.
     * @param id The id of the location.
     */
    private LocationCard(Venue location, String id) {
        super(FXML);
        setToolTip(location);
        if ("RouteNodeStart".equals(id) || "RouteNodeEnd".equals(id) || "RouteNodeIntermediate".equals(id)) {
            getRoot().setId(id);
        }
    }

    /**
     * Creates the tooltip card.
     *
     * @param location The location for the LocationCard.
     * @param id The id of the location.
     * @return The StackPane for the tooltip.
     */
    public static StackPane getCard(Venue location, String id) {
        return new LocationCard(location, id).getRoot();
    }

    /**
     * Sets the Tooltip of the locationCard.
     *
     * @param location The location for the LocationCard.
     */
    private void setToolTip(Venue location) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double lengthY = primaryScreenBounds.getHeight() * MAP_WINDOW_SCALING;
        double lengthX = primaryScreenBounds.getHeight() * MAP_WINDOW_SCALING * MAP_IMAGE_ASPECT_RATIO;
        double offsetY = lengthY
                - ((location.getLatitude() - MAP_IMAGE_Y_END) * lengthY / (MAP_IMAGE_Y_START - MAP_IMAGE_Y_END));
        double offsetX = (location.getLongitude() - MAP_IMAGE_X_END) * lengthX / (MAP_IMAGE_X_START - MAP_IMAGE_X_END);

        AnchorPane.setLeftAnchor(getRoot(), offsetX);
        AnchorPane.setTopAnchor(getRoot(), offsetY);
        Tooltip tooltip = new Tooltip(location.getAddress());
        Tooltip.install(getRoot(), tooltip);
        logger.log(Level.FINE, "Relative location: " + offsetX + " " + offsetY);
    }
}
