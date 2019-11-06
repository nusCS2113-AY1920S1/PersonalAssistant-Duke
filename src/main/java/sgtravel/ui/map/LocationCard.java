package sgtravel.ui.map;

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

    private LocationCard(Venue location, String id) {
        super(FXML);
        double offsetY = 900 - ((location.getLatitude() - 1.218) * 900 / (1.486 - 1.218));
        double offsetX = (location.getLongitude() - 103.622) * 1200 / (104.021 - 103.622);
        AnchorPane.setLeftAnchor(getRoot(), offsetX);
        AnchorPane.setTopAnchor(getRoot(), offsetY);
        Tooltip tooltip = new Tooltip(location.getAddress());
        Tooltip.install(getRoot(), tooltip);
        logger.log(Level.FINE, "Relative location: " + offsetX + " " + offsetY);

        if ("RouteNodeStart".equals(id) || "RouteNodeEnd".equals(id) || "RouteNodeIntermediate".equals(id)) {
            getRoot().setId(id);
        }
    }

    public static StackPane getCard(Venue location, String id) {
        return new LocationCard(location, id).getRoot();
    }
}
