package duke.Command;

import duke.CLIView;
import duke.data.Storage;
import duke.module.Schedule;
import duke.sports.ManageStudents;
import duke.sports.MyPlan;
import duke.task.TaskList;

/**
 * Represents a subclass of Command class which informs the user that the
 * user input was an invalid command.
 */
public class InvalidCommand extends Command {
    /**
     * Informs the user that the user input was an invalid command.
     * @param tasks The ArrayList of Task objects.
     * @param cliView The Ui object to manage user interface to user.
     * @param storage The Storage object to save and load user's tasks.
     * @param schedule The Schedule object to store classes in timeslots.
     * @param students The ManageStudents object to manage students in classes.
     * @param plan The MyPlan object to manage the training plans.
     */
    public void execute(final TaskList tasks, final CLIView cliView,
                        final Storage storage, final Schedule schedule,
                        final ManageStudents students, final MyPlan plan) {
        cliView.showDontKnow();
    }
}
