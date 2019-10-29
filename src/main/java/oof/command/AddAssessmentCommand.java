package oof.command;

import oof.SelectedInstance;
import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.task.Assessment;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

public class AddAssessmentCommand extends AddEventCommand {

    private String line;
    private static final int INDEX_NAME = 0;
    private static final int INDEX_TIMES = 1;
    private static final int INDEX_START_TIME = 0;
    private static final int INDEX_END_TIME = 1;

    /**
     * Constructor for AddAssessmentCommand.
     *
     * @param line Command inputted by user for processing.
     */
    public AddAssessmentCommand(String line) {
        super(line);
        this.line = line;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) throws OofException {
        String[] argumentSplit = line.split(" /from ");
        if (!hasName(argumentSplit)) {
            throw new OofException("OOPS!!! The assessment needs a name.");
        } else if (!hasStartTime(argumentSplit)) {
            throw new OofException("OOPS!!! The assessment needs a start time.");
        } else if (!hasEndTime(argumentSplit)) {
            throw new OofException("OOPS!!! The assessment needs an end time.");
        }
        String[] dateSplit = argumentSplit[INDEX_TIMES].split(" /to ");
        String startDate = parseTimeStamp(dateSplit[INDEX_START_TIME]);
        String endDate = parseTimeStamp(dateSplit[INDEX_END_TIME]);
        if (!isDateValid(startDate) && !isDateValid(endDate)) {
            throw new OofException("OOPS!!! The start and end dates are invalid.");
        } else if (!isDateValid(startDate)) {
            throw new OofException("OOPS!!! The start date is invalid.");
        } else if (!isDateValid(endDate)) {
            throw new OofException("OOPS!!! The end date is invalid.");
        }
        if (hasClashes(taskList, ui, dateSplit)) {
            throw new OofException("OOPS!!! The start time of an assessment cannot be after the end time.");
        }
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new OofException("OOPS!! Please select a Module.");
        }
        String name = argumentSplit[INDEX_NAME].trim();
        Assessment assessment = new Assessment(module.getModuleCode(), name, startDate, endDate);
        module.addAssessment(assessment);
        ui.printAssessmentAddedMessage(assessment);
        storage.writeSemesters(semesterList);
    }

    /**
     * Checks if input has a name.
     *
     * @param lineSplit processed user input.
     * @return true if name is more than length 0 and is not whitespace.
     */
    private boolean hasName(String[] lineSplit) {
        return lineSplit[INDEX_NAME].trim().length() > 0;
    }

    /**
     * Checks if input has a start time (argument given before "/to").
     *
     * @param lineSplit processed user input.
     * @return true if there is a start time and start time is not whitespace.
     */
    private boolean hasStartTime(String[] lineSplit) {
        return lineSplit.length > 1 && lineSplit[INDEX_TIMES].split(" /to ")[INDEX_START_TIME].trim().length() > 0;
    }

    /**
     * Checks if input has an end time (argument given after "/to").
     *
     * @param lineSplit processed user input.
     * @return true if there is an end time and end time is not whitespace.
     */
    private boolean hasEndTime(String[] lineSplit) {
        String[] timeSplit = lineSplit[INDEX_TIMES].split(" /to ");
        return timeSplit.length > 1 && timeSplit[INDEX_END_TIME].trim().length() > 0;
    }

}
