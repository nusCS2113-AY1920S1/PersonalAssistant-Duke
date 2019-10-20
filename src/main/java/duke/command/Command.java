package duke.command;

import duke.Ui;
import duke.sports.ManageStudents;

/**
 * An abstract class to handle commands.
 */
public abstract class Command {
    /**
     * A method to execute the respective commands.
     * @param manageStudents file type
     * @param ui of the respective commands.
     * @return the commands.
     */
    abstract String execute(ManageStudents manageStudents, Ui ui);
}
