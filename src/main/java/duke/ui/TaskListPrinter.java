package duke.ui;

import duke.exception.DukeException;
import duke.task.Event;
import duke.task.Task;
import duke.tasklist.TaskList;

import java.util.Optional;

/**
 * Class that handles the printing of the TaskList for the user to view
 */
public class TaskListPrinter {
    /**
     * Method that handles bulk of the logic of printing the TaskList for the user to view
     *
     * @param ui the Ui object that handles user interactions
     * @param list the TaskList to be printed for the user
     */
    public static void print(Ui ui, TaskList list) throws DukeException {
        int taskCount = list.size();
        int filterLength = list.getLongestFilter();
        String filterHead = createFilterHead(filterLength);
        filterLength = filterHead.length();
        ui.showLine("ID | " + filterHead + " |  Type  | Priority | Recurrence | " +
                "Duration | Done? |     DateTime     | Description");
        String rowBreak = createRowBreak(filterLength);
        for (int i = 0; i < taskCount; i++) {
            ui.showLine(rowBreak);
            StringBuilder curr;
            if (i < 9) {
                curr = new StringBuilder("0" + (i + 1));
            } else {
                curr = new StringBuilder(Integer.toString(i + 1));
            }
            Task t = list.get(i);
            curr.append(" | ").append(padFilter(t.getFilter(), filterLength));
            curr.append(" | ").append(padType(t));
            curr.append(" | ").append(padPriority(t.getPriority()));
            curr.append(" | ").append(padRecurrence(t.getRecurrenceCode()));
            curr.append(" | ").append(padDuration(t.getDuration()));
            curr.append(" |   ").append(t.getStatusIcon());
            curr.append("   | ").append(t.getDTString());
            curr.append(" | ").append(t.getDescription());
            ui.showLine(curr.toString());
        }
    }

    private static String padType(Task t) {
        if (t instanceof Event) {
            return " Event";
        } else {
            return " Task ";
        }
    }

    /**
     * Method to modify each Task's priority level to fit within the print output in a visually pleasing manner
     *
     * @param priority given priority level of the task
     * @return padded string containing given priority level
     */
    private static String padPriority(String priority) {
        switch (priority.length()) {
        case 6:
            return " " + priority + " ";
        case 4:
            return "  " + priority + "  ";
        default:
            return "  " + priority + "   ";
        }
    }

    /**
     * Method to modify each Task's recurrence period to fit within the print output in a visually pleasing manner
     *
     * @param recurrence given recurrence period of the task
     * @return padded string containing given recurrence period
     */
    private static String padRecurrence(String recurrence) {
        switch (recurrence.length()) {
        case 6:
            return "  " + recurrence + "  ";
        case 5:
            return "   " + recurrence + "  ";
        default:
            return "    " + recurrence + "    ";
        }
    }

    /**
     * Method to modify each Task's duration needed to complete to fit within the print output in a visually
     * pleasing manner
     *
     * @param duration given duration to complete the task
     * @return padded string containing given duration
     */
    private static String padDuration(String duration) {
        int toPad = 8 - duration.length();
        int front;
        int back;
        if (toPad % 2 == 0) {
            front = toPad / 2;
            back = toPad / 2;
        } else {
            front = toPad / 2 + 1;
            back = toPad / 2;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < front; i++) {
            result.append(" ");
        }
        result.append(duration);
        for (int i = 0; i < back; i++) {
            result.append(" ");
        }
        return result.toString();
    }

    private static String createFilterHead(int filterLength) {
        StringBuilder result = new StringBuilder("Filter");
        if (filterLength > 6) {
            int difference = filterLength - 6;
            int front = difference % 2 == 0 ? difference / 2 : difference / 2 + 1;
            int back = difference / 2;
            while (front-- > 0) {
                result.insert(0, " ");
            }
            while (back-- > 0) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    private static String createRowBreak(int filterLength) {
        StringBuilder result = new StringBuilder("-- | ");
        while (filterLength-- > 0) {
            result.append("-");
        }
        return result + " | ------ | -------- | ---------- | -------- | ----- | ---------------- | -----------";
    }

    private static String padFilter(Optional<String> filter, int filterLength) {
        StringBuilder result;
        if (filter.isEmpty()) {
            result = new StringBuilder("");
        } else {
            result = new StringBuilder(filter.get());
        }
        int difference = filterLength - result.length();
        int front = difference % 2 == 0 ? difference / 2 : difference / 2 + 1;
        int back = difference / 2;
        while (front-- > 0) {
            result.insert(0, " ");
        }
        while (back-- > 0) {
            result.append(" ");
        }
        return result.toString();
    }
}
