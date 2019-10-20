package duke.ui;

import duke.model.events.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class EventCard extends UiPart<StackPane> {
    @FXML
    private Label content;
    private static final String FXML = "EventCard.fxml";

    private EventCard(Event event) {
        super(FXML);
        content.setText(event.toString());
    }

    public static StackPane getEventCard(Event event) {
        return new EventCard(event).getRoot();
    }
}
