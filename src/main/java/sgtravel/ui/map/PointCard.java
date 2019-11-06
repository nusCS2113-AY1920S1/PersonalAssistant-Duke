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
        double offsetY = 230 - ((venue.getLatitude() - 1.218) * 240 / (1.486 - 1.218));
        double offsetX = 30 + (venue.getLongitude() - 103.630) * 288  / (104.028 - 103.630);
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
