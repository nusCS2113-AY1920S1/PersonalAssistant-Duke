package duke.command;

import duke.Ui;
import duke.sports.ManageStudents;

/**
 * An abstract class to handle commands.
 */
public abstract class Command {

    /**
     * Constructor for abstract method which will be inherited.
     * @param manageStudents The ManageStudents object to be used.
     * @param ui The Ui object to be used.
     * @return A string to be printed out in parser.
     */
    abstract String execute(ManageStudents manageStudents, Ui ui);
}
