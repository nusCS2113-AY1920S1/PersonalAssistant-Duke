package duke.command;

import duke.exceptions.ModEmptyListException;
import duke.modules.Timetable;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public class PrintTimetable extends Command {

    private Timetable timetables;

    public PrintTimetable() {
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws ModEmptyListException {
        boolean isEmpty = tasks.getTasks().isEmpty();
        if (isEmpty) {
            throw new ModEmptyListException();
        } else {
            ui.timetableMsg();
            ui.printTimetable(timetables.getTimetable());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
