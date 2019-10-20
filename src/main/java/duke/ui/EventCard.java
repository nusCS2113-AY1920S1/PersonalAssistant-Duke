package duke.ui;

import duke.model.events.Event;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class EventCard extends UiPart<StackPane> {
    private static final String FXML = "EventCard.fxml";

    private EventCard(Event event) {
        super(FXML);
        double offsetY = 300 - ((event.getLocation().getLatitude() - 1.218) * 300 / (1.486 - 1.218));
        double offsetX = 400 + (event.getLocation().getLongitude() - 103.622) * 400  / (104.021 - 103.622);
        AnchorPane.setLeftAnchor(getRoot(), offsetX);
        AnchorPane.setTopAnchor(getRoot(), offsetY);
        Tooltip tooltip = new Tooltip(event.toString());
        Tooltip.install(getRoot(), tooltip);
    }

    public static StackPane getCard(Event event) {
        return new EventCard(event).getRoot();
    }
}
