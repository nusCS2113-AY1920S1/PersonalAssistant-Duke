package duke.ui;

import duke.model.events.Event;
import duke.model.locations.Venue;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class EventPointCard extends UiPart<StackPane> {
    @FXML
    private Circle circle;

    private static final String FXML = "EventPointCard.fxml";
    private Event event;
    private Venue venue;

    public EventPointCard(Event event) {
        super(FXML);
        this.event = event;
        venue = event.getLocation();
        setLocation();
    }

    public void setColor(Paint paint) {
        circle.setFill(paint);
    }

    private void setLocation() {
        double offsetY = 300 - ((venue.getLatitude() - 1.218) * 300 / (1.486 - 1.218));
        double offsetX = (venue.getLongitude() - 103.622) * 400  / (104.021 - 103.622);
        AnchorPane.setLeftAnchor(getRoot(), offsetX);
        AnchorPane.setTopAnchor(getRoot(), offsetY);
        Tooltip tooltip = new Tooltip(event.toString());
        Tooltip.install(getRoot(), tooltip);
    }
}
