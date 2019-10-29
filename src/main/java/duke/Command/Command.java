package duke.Command;

import duke.Ui;
import duke.data.Storage;
import duke.module.Schedule;
import duke.sports.ManageStudents;
import duke.sports.MyPlan;
import duke.task.TaskList;

/**
 * Represents a Command class which will be inherited by specific *Command subclasses
 * to run their specific execute functions.
 */
public abstract class Command {
    /**
     * Ui object which will be shared by all subclasses.
     */
    private Ui ui;
    /**
     * The exit status of the program.
     */
    private boolean exit = false;
    /**
     * Getter function for the exit status of the program.
     * @return exit the program.
     */
    public boolean isExit() {
        return this.exit;
    }
    /**
     * Function to make the exit become true.
     */
    public void makeExitTrue() {
        this.exit = true;
    }

    /**
     * The execute method which will be shared by all subclasses.
     * @param tasks The ArrayList of Task objects.
     * @param ui The Ui object to manage user interface to user.
     * @param storage The Storage object to save and load user's tasks.
     * @param schedule The Schedule object to store classes in timeslots.
     * @param students The ManageStudents object to manage students in classes.
     * @param plan The MyPlan object to manage the training plans.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage,
                                 Schedule schedule, ManageStudents students,
                                 MyPlan plan);
}
