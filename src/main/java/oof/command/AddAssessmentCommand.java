package oof.command;

import java.util.ArrayList;

import oof.SelectedInstance;
import oof.model.task.Event;
import oof.Ui;
import oof.exception.OofException;
import oof.model.task.Assessment;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

public class AddAssessmentCommand extends AddEventCommand {

    public static final String COMMAND_WORD = "assessment";

    /**
     * Constructor for AddAssessmentCommand.
     *
     * @param arguments Command inputted by user.
     */
    public AddAssessmentCommand(ArrayList<String> arguments) {
        super(arguments);
    }

    /**
     * Adds an event task to taskList.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new OofException("OOPS!! Please select a Module.");
        } else if (arguments.get(INDEX_DESCRIPTION).isEmpty()) {
            throw new OofException("OOPS!!! The assessment needs a name.");
        } else if (arguments.size() < ARRAY_SIZE_DATE_TIME_START || arguments.get(INDEX_DATE_TIME_START).isEmpty()) {
            throw new OofException("OOPS!!! The assessment needs a start time.");
        } else if (arguments.size() < ARRAY_SIZE_DATE_TIME_END || arguments.get(INDEX_DATE_TIME_END).isEmpty()) {
            throw new OofException("OOPS!!! The assessment needs an end time.");
        }
        String description = arguments.get(INDEX_DESCRIPTION);
        String startDateTime = arguments.get(INDEX_DATE_TIME_START);
        String endDateTime = arguments.get(INDEX_DATE_TIME_END);
        if (!isDateValid(startDateTime)) {
            throw new OofException("OOPS!!! The start date is invalid.");
        } else if (!isDateValid(endDateTime)) {
            throw new OofException("OOPS!!! The end date is invalid.");
        } else {
            ArrayList<Event> eventClashes = checkClashes(taskList, startDateTime, endDateTime);
            ui.printClashWarning(eventClashes);
            String moduleCode = module.getModuleCode();
            Assessment assessment = new Assessment(moduleCode, description, startDateTime, endDateTime);
            if (exceedsMaxLength(assessment.getDescription())) {
                throw new OofException("Task exceeds maximum description length!");
            }
            taskList.addTask(assessment);
            module.addAssessment(assessment);
            ui.addTaskMessage(assessment, taskList.getSize());
            storageManager.writeTaskList(taskList);
        }
    }
}
