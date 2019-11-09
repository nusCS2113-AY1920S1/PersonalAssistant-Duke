package duke.ui;

import duke.Duke;

//@@author gervaiseang-unused
/**
 * Represents a ui that informs the user.
 */
public class Ui {
    /**
     * Code is not used as we are narrowing down the reminder features in our program to only
     * include the reminder notification due in 3 days when program first boots up
     * instead of allowing users to create/set their own reminders
     */

    /**
     * Outputs task that is successfully sets a reminder to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index indicated number of days to set the reminder
     * @return String of the task that is completed.
     */
    public String showReminderGui(TaskList items, int index) {
        String str = "     You have set a reminder for this task \n"
                + items.get(index);
        return str;
    }
}
