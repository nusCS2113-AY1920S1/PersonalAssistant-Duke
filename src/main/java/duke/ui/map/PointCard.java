package duke.ui.map;

import duke.model.locations.Venue;
import duke.ui.UiPart;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.fxml.FXML;

public class PointCard extends UiPart<StackPane> {
    @FXML
    private Circle circle;

    private static final String FXML = "PointCard.fxml";
    private Venue venue;

    private PointCard(Venue venue, Paint color) {
        super(FXML);
        this.venue = venue;
        circle.setFill(color);
        setLocation();
    }

    private void setLocation() {
        double offsetY = 230 - ((venue.getLatitude() - 1.218) * 240 / (1.486 - 1.218));
        double offsetX = 30 + (venue.getLongitude() - 103.630) * 288  / (104.028 - 103.630);
        AnchorPane.setLeftAnchor(getRoot(), offsetX);
        AnchorPane.setTopAnchor(getRoot(), offsetY);
        Tooltip tooltip = new Tooltip(venue.getAddress());
        Tooltip.install(getRoot(), tooltip);
    }

    public static StackPane getCard(Venue venue, Paint color) {
        return new PointCard(venue, color).getRoot();
    }
}
