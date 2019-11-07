package sgtravel.ui.calendar;

import sgtravel.model.lists.EventList;
import sgtravel.model.Event;
import sgtravel.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

/**
 * Represents a calender card as a part of the UI.
 */
public class CalendarCard extends UiPart<StackPane> {
    private final EventList events;
    private static final String FXML = "CalendarCard.fxml";
    private static final String CLASSNAME = "class sgtravel.model.";
    private int currentIndex = 0;
    private boolean isCalendarCardClass = true;

    @FXML
    private Label day;
    @FXML
    private Label task;
    @FXML
    private Label description;
    @FXML
    private StackPane calendarCard;

    /**
     * Creates the CalenderCard.
     *
     * @param day The number of the day.
     * @param events The EventsList.
     */
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

    /**
     * Loads the information onto the CalenderCard.
     */
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

    /**
     * Gets the CalenderCard for a day.
     *
     * @param day The number of the day.
     * @param events The EventsList.
     * @return The CalenderCard created.
     */
    protected static StackPane getCalendarCard(int day, EventList events) {
        return new CalendarCard(day, events).getRoot();
    }

    /**
     * onClick event handler.
     */
    @FXML
    private void onClick() {
        if (!events.isEmpty()) {
            currentIndex = (currentIndex + 1) % events.size();
            loadInfoOnToCard();
        }
    }

    /**
     * onHover handler to apply CSS.
     */
    @FXML
    private void onMouseEnter() {
        calendarCard.getStyleClass().add("calendarCardHover");
        calendarCard.getStyleClass().remove("calendarCard");
        isCalendarCardClass = false;
    }

    /**
     * onHover handler to remove CSS.
     */
    @FXML
    private void onMouseExit() {
        if (!isCalendarCardClass) {
            calendarCard.getStyleClass().add("calendarCard");
            calendarCard.getStyleClass().remove("calendarCardHover");
            isCalendarCardClass = true;
        }
    }
}
