package oof.command;

import java.util.ArrayList;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.module.Module;
import oof.model.task.Assignment;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

public class AddAssignmentCommand extends AddDeadlineCommand {

    public static final String COMMAND_WORD = "assignment";

    /**
     * Constructor for AddAssignmentCommand.
     *
     * @param arguments Command inputted by user for processing.
     */
    public AddAssignmentCommand(ArrayList<String> arguments) {
        super(arguments);
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new OofException("OOPS!! Please select a Module.");
        }
        String moduleCode = module.getModuleCode();
        String description = arguments.get(INDEX_DESCRIPTION);
        if (arguments.get(INDEX_DESCRIPTION).equals("")) {
            throw new OofException("OOPS!!! The assignment needs a name.");
        }
        if (arguments.size() < ARRAY_SIZE_DATE || arguments.get(INDEX_DATE).equals("")) {
            throw new OofException("OOPS!!! The assignment needs a due date.");
        }
        String date = parseDateTime(arguments.get(INDEX_DATE));
        if (!isDateValid(date)) {
            throw new OofException("OOPS!!! The due date is invalid.");
        } else {
            Assignment assignment = new Assignment(moduleCode, description, date);
            if (exceedsMaxLength(assignment.getDescription())) {
                throw new OofException("OOPS!!! Task exceeds maximum description length!");
            }
            taskList.addTask(assignment);
            module.addAssignment(assignment);
            ui.addTaskMessage(assignment, taskList.getSize());
            storageManager.writeTaskList(taskList);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
