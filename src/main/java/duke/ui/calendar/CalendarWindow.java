package duke.ui.calendar;

import duke.commands.results.CommandResultCalender;
import duke.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.TaskList;
import duke.model.events.DoWithin;
import duke.model.events.Task;
import duke.model.events.TaskWithDates;
import duke.ui.UiPart;
import javafx.fxml.FXML;
import javafx.collections.transformation.SortedList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CalendarWindow extends UiPart<Stage> {
    private static final String FXML = "CalendarWindow.fxml";
    private YearMonth currentYearMonth;
    private int numOfDays;
    private int dayOfTheFirstDay;
    private String currentMonth;
    private List<TaskList> filteredTasks;
    private static TaskList tasks;
    private static final int MAX_ROW = 6;
    private static final int MAX_COL = 7;

    @FXML
    private VBox pane;
    @FXML
    private Text calendarTitle;
    @FXML
    private GridPane gridCalendar;

    @FXML
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        refreshCalendar();
    }

    @FXML
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        refreshCalendar();
    }

    /**
     * Refreshes the calendar.
     */
    private void refreshCalendar() {
        updateVariables();
        setCalendarTitle(currentYearMonth.getYear(), currentMonth);
        fillCalendarDays();
    }

    /**
     * Sets the title of the calendar according to a specific month and year.
     */
    private void setCalendarTitle(int year, String month) {
        calendarTitle.setText("♒" + month + " " + year + "♒");
        pane.setId(currentMonth);
    }

    /**
     * Fills up the calendar with the necessary information.
     */
    private void fillCalendarDays() {
        gridCalendar.getChildren().clear();
        boolean flag = false;
        int day = 1;
        for (int i = 0; i < MAX_ROW && day <= numOfDays; i++) {
            for (int j = 0; j < MAX_COL && day <= numOfDays; j++) {
                if (dayOfTheFirstDay == j) {
                    flag = true;
                }
                if (flag) {
                    gridCalendar.add(CalendarCard.getCalendarCard(day, filteredTasks.get(day)), j, i);
                    ++day;
                }
            }
        }
    }

    /**
     * Updates the relevant variables to contain information of the particular month.
     */
    private void updateVariables() {
        setCalendarTitle(currentYearMonth.getYear(), currentMonth);
        currentMonth = currentYearMonth.getMonth().toString();
        numOfDays = currentYearMonth.lengthOfMonth();
        findRelevantTasks();
        dayOfTheFirstDay = currentYearMonth.atDay(1).getDayOfWeek().getValue() % 7;
    }

    /**
     * Finds the tasks that needs to be displayed.
     */
    private void findRelevantTasks() {
        filteredTasks = new ArrayList<>();
        SortedList<Task> sortedTask = tasks.getChronoList();
        for (int i = 0; i <= numOfDays; ++i) {
            filteredTasks.add(new TaskList());
        }
        for (Task t : sortedTask) {
            try {
                tryAddingTask(t);
            } catch (DukeException e) {
                //remove this later
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Tries to add a task to the current calendar.
     *
     * @param t A task from the Duke's task list.
     * @throws DukeException If the task cannot be added.
     */
    private void tryAddingTask(Task t) throws DukeException {
        LocalDate startDate = ((TaskWithDates) t).getStartDate().toLocalDate();
        if (isSameYearMonth(startDate)) {
            filteredTasks.get(startDate.getDayOfMonth()).add(t);
            if (t instanceof DoWithin) {
                //do something
            }
        }
    }

    private boolean isSameYearMonth(Object date) {
        return currentYearMonth.getYear() == ((LocalDate) date).getYear()
                && currentYearMonth.getMonth() == ((LocalDate) date).getMonth();
    }

    /**
     * Sets the calendar layout.
     *
     * @param tasks The task list from Duke containing all the tasks.
     */
    private void setCalendarLayout(TaskList tasks) {
        setCalendarBasics(tasks);
        refreshCalendar();
    }

    /**
     * Sets the basic information of the calendar; current month, year and tasks.
     */
    private void setCalendarBasics(TaskList tasks) {
        ZoneId zoneId = ZoneId.systemDefault(); //GMT +8
        currentYearMonth = YearMonth.now(zoneId).minusMonths(0);
        CalendarWindow.tasks = tasks;
    }

    /**
     * Creates a new CalendarWindow.
     *
     * @param root Stage to use as the root of the CalendarWindow.
     */
    private CalendarWindow(Stage root, TaskList tasks) {
        super(FXML, root);
        root.getScene().getStylesheets().addAll(this.getClass().getResource("/css/calendarStyle.css").toExternalForm());
        setCalendarLayout(tasks);
    }

    /**
     * Creates a new CalendarWindow.
     */
    public CalendarWindow(CommandResultCalender commandResult) {
        this(new Stage(), commandResult.getTasks());
    }

    /**
     * Shows the Calendar window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        getRoot().show();
    }

    /**
     * Returns true if the Calendar window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the Calendar window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
