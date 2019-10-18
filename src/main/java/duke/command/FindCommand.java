package duke.command;

import duke.Ui;
import duke.sports.ManageStudents;

public class FindCommand extends Command {
    private String findStudent;

    public FindCommand(String findStudent) {
        this.findStudent = findStudent;
    }

    @Override
    public String execute(ManageStudents manageStudents, Ui ui) {
        return manageStudents.findName(findStudent);
    }
}
