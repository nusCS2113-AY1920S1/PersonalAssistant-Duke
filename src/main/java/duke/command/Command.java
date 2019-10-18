package duke.command;

import duke.Ui;
import duke.sports.ManageStudents;

/**
 * An abstract class to handle commands
 */
public abstract class Command {
    abstract public String execute(ManageStudents manageStudents, Ui ui);
}
