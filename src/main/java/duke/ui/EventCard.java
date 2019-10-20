package duke.ui;

import duke.model.events.BindableEvent;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class EventCard extends UiPart<StackPane> {
    @FXML
    private Label address;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label isDone;

    private static final String FXML = "EventCard.fxml";

    private EventCard(BindableEvent event) {
        super(FXML);
        address.textProperty().bind(event.addressProperty());
        startDate.textProperty().bind(Bindings.convert(event.startDateProperty()));
        endDate.textProperty().bind(Bindings.convert(event.endDateProperty()));
        isDone.textProperty().bind(Bindings.convert(event.isDoneProperty()));
    }

    public static StackPane getEventCard(BindableEvent event) {
        return new EventCard(event).getRoot();
    }
}
