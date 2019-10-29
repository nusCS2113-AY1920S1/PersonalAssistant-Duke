package oof.command;

import oof.SelectedInstance;
import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

public class AddLessonCommand extends Command {

    private String line;
    private static final int INDEX_NAME = 0;
    private static final int INDEX_DAY = 1;
    private static final int INDEX_START_TIME = 2;
    private static final int INDEX_END_TIME = 3;
    private static final String REGEX_SPLIT = "/day|/from|/to";

    /**
     * Constructor for AddLessonCommand.
     *
     * @param line Command inputted by user for processing.
     */
    public AddLessonCommand(String line) {
        super();
        this.line = line;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        String[] argumentSplit = line.split(REGEX_SPLIT);
        if (!hasName(argumentSplit)) {
            throw new OofException("OOPS!!! The lesson needs a name.");
        } else if (!hasStartTime(argumentSplit)) {
            throw new OofException("OOPS!!! The lesson needs a start time.");
        } else if (!hasEndTime(argumentSplit)) {
            throw new OofException("OOPS!!! The lesson needs an end time.");
        }
        String startTime = parseTime(argumentSplit[INDEX_START_TIME]);
        String endTime = parseTime(argumentSplit[INDEX_END_TIME]);
        if (!isDateValid(startTime) && !isDateValid(endTime)) {
            throw new OofException("OOPS!!! The start and end dates are invalid.");
        } else if (!isDateValid(startTime)) {
            throw new OofException("OOPS!!! The start date is invalid.");
        } else if (!isDateValid(endTime)) {
            throw new OofException("OOPS!!! The end date is invalid.");
        }
        SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm");
        try {
            Date newStartTime = format.parse(startTime);
            Date newEndTime = format.parse(endTime);
            if (!isStartDateBeforeEndDate(newStartTime, newEndTime)) {
                throw new OofException("OOPS!!! The start time of a lesson cannot be after the end time.");
            }
        } catch (ParseException e) {
            throw new OofException("Timestamp given is invalid! Please try again.");
        }
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new OofException("OOPS!! Please select a Module.");
        }
        String name = argumentSplit[INDEX_NAME].trim();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(argumentSplit[INDEX_DAY].trim().toUpperCase());
        Lesson lesson = new Lesson(module.getModuleCode(), name, dayOfWeek, startTime, endTime);
        module.addLesson(lesson);
        ui.printLessonAddedMessage(module.getModuleCode(), lesson);
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
        return lineSplit.length > 2 && lineSplit[INDEX_START_TIME].trim().length() > 0;
    }

    /**
     * Checks if input has an end time (argument given after "/to").
     *
     * @param lineSplit processed user input.
     * @return true if there is an end time and end time is not whitespace.
     */
    private boolean hasEndTime(String[] lineSplit) {
        return lineSplit.length > 3 && lineSplit[INDEX_END_TIME].trim().length() > 0;
    }

    /**
     * Checks if start and end date are chronologically accurate.
     *
     * @param startTime Start time of lesson being added.
     * @param endTime   End time of lesson being added.
     * @return true if start date occurs before end date, false otherwise.
     */
    private boolean isStartDateBeforeEndDate(Date startTime, Date endTime) {
        return startTime.compareTo(endTime) <= 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
