package duke.ui;

import duke.task.Task;
import duke.tasklist.TaskList;

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
    public static void print(Ui ui, TaskList list) {
        int taskCount = list.size();
        ui.showLine("ID | Priority | Recurrence | Duration | Done? | Description");
        for (int i = 0; i < taskCount; i++) {
            ui.showLine("-- | -------- | ---------- | -------- | ----- | -----------");
            String curr;
            if (i < 9) {
                curr = "0" + (i + 1);
            } else {
                curr = Integer.toString(i + 1);
            }
            Task t = list.get(i);
            curr += " | " + padPriority(t.getPriority());
            curr += " | " + padRecurrence(t.getRecurrenceCode());
            curr += " | " + padDuration(t.getDuration());
            curr += " |   " + t.getStatusIcon();
            curr += "   | " + t.getDescription();
            ui.showLine(curr);
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
        String result = "";
        for (int i = 0; i < front; i++) {
            result += " ";
        }
        result += duration;
        for (int i = 0; i < back; i++) {
            result += " ";
        }
        return result;
    }
}
