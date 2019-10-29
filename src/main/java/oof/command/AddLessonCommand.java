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
    private static final int INDEX_DAY = 0;
    private static final int INDEX_START_TIME = 0;
    private static final int INDEX_END_TIME = 1;
    private static final int INDEX_DETAILS = 1;
    private static final String REGEX_DAY = "/day";
    private static final String REGEX_FROM = "/from";
    private static final String REGEX_TO = "/to";

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
        String[] nameSplit = line.split(REGEX_DAY);
        if (!hasName(nameSplit)) {
            throw new OofException("OOPS!!! The lesson needs a name.");
        }
        if (!hasDay(nameSplit)) {
            throw new OofException("OOPS!!! The lesson needs a day.");
        }
        String[] daySplit = nameSplit[INDEX_DETAILS].split(REGEX_FROM);
        if (!hasStartTime(daySplit)) {
            throw new OofException("OOPS!!! The lesson needs a start time.");
        }
        String[] timeSplit = daySplit[INDEX_DETAILS].split(REGEX_TO);
        if (!hasEndTime(timeSplit)) {
            throw new OofException("OOPS!!! The lesson needs an end time.");
        }
        String startTime = parseTime(timeSplit[INDEX_START_TIME].trim());
        String endTime = parseTime(timeSplit[INDEX_END_TIME].trim());
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
        String name = nameSplit[INDEX_NAME].trim();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(daySplit[INDEX_DAY].trim().toUpperCase());
        Lesson lesson = new Lesson(module.getModuleCode(), name, dayOfWeek, startTime, endTime);
        module.addLesson(lesson);
        ui.printLessonAddedMessage(module.getModuleCode(), lesson);
        storage.writeSemesters(semesterList);
    }

    /**
     * Checks if input has a name.
     *
     * @param daySplit processed user input.
     * @return true if name is more than length 0 and is not whitespace.
     */
    private boolean hasName(String[] daySplit) {
        return daySplit[INDEX_NAME].trim().length() > 0;
    }

    /**
     * Checks if input has a day.
     *
     * @param nameSplit processed user input.
     * @return true if day is more than length 0 and is not whitespace.
     */
    private boolean hasDay(String[] nameSplit) {
        return nameSplit.length > 1 && nameSplit[INDEX_DETAILS].trim().length() > 0;
    }

    /**
     * Checks if input has a start time (argument given before "/to").
     *
     * @param daySplit processed user input.
     * @return true if there is a start time and start time is not whitespace.
     */
    private boolean hasStartTime(String[] daySplit) {
        return daySplit.length > 1 && daySplit[INDEX_START_TIME].trim().length() > 0;
    }

    /**
     * Checks if input has an end time (argument given after "/to").
     *
     * @param timeSplit processed user input.
     * @return true if there is an end time and end time is not whitespace.
     */
    private boolean hasEndTime(String[] timeSplit) {
        return timeSplit.length > 1 && timeSplit[INDEX_END_TIME].trim().length() > 0;
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
