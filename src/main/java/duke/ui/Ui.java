package duke.ui;

import duke.data.BusStop;
import duke.data.UniqueTaskList;
import duke.data.tasks.Task;
import duke.model.Venue;
import duke.ui.calendar.CalendarWindow;
import duke.ui.map.MapWindow;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Class that handles user input and messages shown to user of this application.
 */
public class Ui {
    private static final String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private static final String MESSAGE_WELCOME = "Hello! I'm duke.Duke\nWhat can I do for you?\n";
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";
    private static final String MESSAGE_MARK_DONE = "Nice! I've marked this task as done:\n  ";
    private static final String MESSAGE_ADDITION = "Got it. I've added this task:\n  ";
    private static final String MESSAGE_DELETE = "Alright! I've removed this task:\n  ";
    private static final String MESSAGE_UPDATE = "No problem! I've rescheduled this task:\n  ";
    private static final String MESSAGE_HELP = "Here is the list of commands:\n"
            + "Add Tasks:\n"
            + "    To Do: todo <desc>\n"
            + "    Event: event <desc> /at <time>\n"
            + "    Deadline: deadline <desc> /by <time>\n"
            + "    Recurring Task: repeat <desc> /by <time> /every <num of days>\n"
            + "    Do Within Task: within <desc> /between <time> /and <time>\n"
            + "\n"
            + "Modifying Tasks:\n"
            + "    Snooze: snooze <index> /to <date>\n"
            + "\n"
            + "Task Querying\n"
            + "    Reminder: reminder\n"
            + "    View by Date: fetch <date>\n";

    private VBox dialogContainer;
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/duke.png"));

    public Ui(VBox dialogContainer) {
        this.dialogContainer = dialogContainer;
    }

    /**
     * Prints a welcome message to the user, which happens at startup.
     */
    public void showWelcome() {
        show("Hello from\n" + logo);
        show(MESSAGE_WELCOME);
    }

    /**
     * Prints an error message to the user.
     */
    public void showError(String errorMessage) {
        show(errorMessage);
    }

    /**
     * Prints a bye message to the user, which happens upon exit.
     */
    public void showBye() {
        show(MESSAGE_BYE);
    }

    /**
     * Prints the list of duke.data.tasks.
     */
    public void showList(UniqueTaskList tasks) {
        String result = "Here are the list of tasks:\n";
        int i = 1;
        for (Task t : tasks) {
            result += (i + ". " + t + "\n");
            i += 1;
        }
        show(result);
    }

    /**
     * Prints the list of Recommended Locations.
     */
    public void showRecommendations(List<Venue> recommendations, String days) {
        StringBuilder result = new StringBuilder("Here are the list of Recommended Locations in "
                + days + " days:\n");
        int i = 1;
        int j = Integer.parseInt(days);
        if (j <= 1) {
            j = 1;
        } else if (j <= 3) {
            j = 3;
        } else if (j <= 5) {
            j = 5;
        } else {
            j = recommendations.size();
        }
        for (Venue t : recommendations) {
            result.append(i).append(". ").append(t.getAddress()).append("\n");
            i += 1;
            if (i >= j) {
                break;
            }
        }
        show(result.toString());
    }


    /**
     * Prints the description of a task.
     */
    public void showAdd(Task task) {
        show(MESSAGE_ADDITION + task);
    }

    /**
     * Prints the task that is mark done.
     */
    public void showMarkDone(Task task) {
        show(MESSAGE_MARK_DONE + task);
    }

    /**
     * Prints the task as rescheduled.
     */
    public void showUpdateTask(Task task) {
        show(MESSAGE_UPDATE + task);
    }

    /**
     * Prints the task that is deleted.
     */
    public void showDelete(Task task) {
        show(MESSAGE_DELETE + task);
    }

    /** Shows message(s) to the user.
     */
    public void show(String msg) {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(msg, dukeImage)
        );
    }

    /**
     * Shows a calendar.
     */
    public void showCalendar(UniqueTaskList tasks) {
        new CalendarWindow(tasks).show();
    }

    /**
     * Shows a map.
     */
    public void showMap(List<BusStop> busStops) {
        new MapWindow(busStops).show();
    }

    /**
     * Shows the help message.
     */
    public void showHelp() {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(MESSAGE_HELP, dukeImage)
        );
    }
}
