package oof.logic.command.task;

import java.util.ArrayList;

import oof.model.semester.SelectedInstance;
import oof.ui.Ui;
import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.commons.exceptions.command.MissingArgumentException;
import oof.model.semester.Module;
import oof.model.semester.SemesterList;
import oof.model.task.Assessment;
import oof.model.task.Event;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

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
     * @throws CommandException if user input contains missing or invalid arguments.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager) throws
            CommandException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (arguments.get(INDEX_DESCRIPTION).isEmpty()) {
            throw new MissingArgumentException("OOPS!!! The assessment needs a name.");
        } else if (arguments.size() < ARRAY_SIZE_DATE_TIME_START || arguments.get(INDEX_DATE_TIME_START).isEmpty()) {
            throw new MissingArgumentException("OOPS!!! The assessment needs a start time.");
        } else if (arguments.size() < ARRAY_SIZE_DATE_TIME_END || arguments.get(INDEX_DATE_TIME_END).isEmpty()) {
            throw new MissingArgumentException("OOPS!!! The assessment needs an end time.");
        }
        String description = arguments.get(INDEX_DESCRIPTION);
        String startDateTime = parseDateTime(arguments.get(INDEX_DATE_TIME_START));
        String endDateTime = parseDateTime(arguments.get(INDEX_DATE_TIME_END));
        if (!isDateValid(startDateTime)) {
            throw new InvalidArgumentException("OOPS!!! The start date is invalid.");
        } else if (!isDateValid(endDateTime)) {
            throw new InvalidArgumentException("OOPS!!! The end date is invalid.");
        } else {
            ArrayList<Event> eventClashes = checkClashes(taskList, startDateTime, endDateTime);
            ui.printClashWarning(eventClashes);
            String moduleCode = module.getModuleCode();
            Assessment assessment = new Assessment(moduleCode, description, startDateTime, endDateTime);
            if (exceedsMaxLength(assessment.getDescription())) {
                throw new InvalidArgumentException("Task exceeds maximum description length!");
            }
            taskList.addTask(assessment);
            module.addAssessment(assessment);
            ui.addTaskMessage(assessment, taskList.getSize());
            storageManager.writeTaskList(taskList);
        }
    }
}
