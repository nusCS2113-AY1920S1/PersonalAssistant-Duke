package duke.command;

import duke.Ui;
import duke.sports.ManageStudents;

public class FindCommand extends Command {
    /**
     * Name of student to search.
     */
    private String findStudent;

    /**
     * A method to find a particular student.
     * @param findStudent name of particular student to find.
     */
    public FindCommand(final String findStudent) {
        this.findStudent = findStudent;
    }


    @Override
    public String execute(final ManageStudents manageStudents, final Ui ui) {
        return manageStudents.findName(findStudent);
    }
}
