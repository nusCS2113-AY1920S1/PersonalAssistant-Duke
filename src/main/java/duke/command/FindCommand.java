package duke.command;

import duke.Ui;
import duke.sports.ManageStudents;

public final class FindCommand extends Command {

    /**
     * Represents a name of the student to find.
     */
    private String findStudent;

    /**
     * Setter method to set the name of the student.
     * @param myFindStudent The name of the student to set.
     */
    public FindCommand(final String myFindStudent) {
        this.findStudent = myFindStudent;
    }

    @Override
    public String execute(final ManageStudents manageStudents, final Ui ui) {
        return manageStudents.findName(findStudent);
    }
}
