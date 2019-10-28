package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.module.Module;
import oof.model.task.Assignment;
import oof.model.task.TaskList;

public class AddAssignmentCommand extends Command {

    private String line;
    private static final int INDEX_NAME = 0;
    private static final int INDEX_DATE_BY = 1;

    /**
     * Constructor for AddAssignmentCommand.
     *
     * @param line Command inputted by user for processing.
     */
    public AddAssignmentCommand(String line) {
        super();
        this.line = line;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        String[] nameAndDueDate = line.split(" /by ");
        if (!hasName(nameAndDueDate)) {
            throw new OofException("OOPS!!! The assignment needs a name.");
        } else if (!hasDueDate(nameAndDueDate)) {
            throw new OofException("OOPS!!! The assignment needs a due date.");
        }
        String name = nameAndDueDate[INDEX_NAME].trim();
        String date = parseTimeStamp(nameAndDueDate[INDEX_DATE_BY].trim());
        if (isDateValid(date)) {
            Assignment task = new Assignment(Module.getModuleName(),name, date);
            tasks.addTask(task);
            ui.printAssignmentAddedMessage(task);
            storage.writeTaskList(tasks);
        } else {
            throw new OofException("OOPS!!! The due date is invalid.");
        }
    }

    /**
     * Checks if assignment has a name.
     *
     * @param lineSplit processed user input.
     * @return true if name is more than length 0 and is not whitespace
     */
    private boolean hasName(String[] lineSplit) {
        return lineSplit[INDEX_NAME].trim().length() > 0;
    }

    /**
     * Checks if assignment has a due date.
     *
     * @param lineSplit processed user input.
     * @return true if there is a due date and due date is not whitespace.
     */
    private boolean hasDueDate(String[] lineSplit) {
        return lineSplit.length != 1 && lineSplit[INDEX_DATE_BY].trim().length() > 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
