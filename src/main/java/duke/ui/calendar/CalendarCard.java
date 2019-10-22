package duke.ui.calendar;

import duke.model.lists.EventList;
import duke.model.events.Event;
import duke.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

public class CalendarCard extends UiPart<StackPane> {
    @FXML
    private Label day;
    @FXML
    private Label task;
    @FXML
    private Label description;
    @FXML
    private StackPane calendarCard;

    private final EventList events;
    private static final String FXML = "CalendarCard.fxml";
    private static final String CLASSNAME = "class duke.model.events.";
    private static int currentIndex = 0;
    private boolean isCalendarCardClass = true;

    private CalendarCard(int day, EventList events) {
        super(FXML);
        this.day.setText(Integer.toString(day));
        this.events = events;
        calendarCard.getStyleClass().add("calendarCard");
        assert (events != null);
        if (!events.isEmpty()) {
            loadInfoOnToCard();
        }
    }

    private void loadInfoOnToCard() {
        task.setText(events.get(currentIndex).getClass().toString().replace(CLASSNAME, ""));
        description.setText(events.get(currentIndex).getDescription());
        StringBuilder result = new StringBuilder();
        for (Event t : events) {
            result.append(t.toString()).append("\n");
        }
        Tooltip tooltip = new Tooltip(result.toString());
        Tooltip.install(getRoot(), tooltip);
    }

    protected static StackPane getCalendarCard(int day, EventList events) {
        return new CalendarCard(day, events).getRoot();
    }

    @FXML
    private void onClick() {
        if (!events.isEmpty()) {
            currentIndex = (currentIndex + 1) % events.size();
            loadInfoOnToCard();
        }
    }

    @FXML
    private void onMouseEnter() {
        calendarCard.getStyleClass().add("calendarCardHover");
        calendarCard.getStyleClass().remove("calendarCard");
        isCalendarCardClass = false;
    }

    @FXML
    private void onMouseExit() {
        if (!isCalendarCardClass) {
            calendarCard.getStyleClass().add("calendarCard");
            calendarCard.getStyleClass().remove("calendarCardHover");
            isCalendarCardClass = true;
        }
    }
}
