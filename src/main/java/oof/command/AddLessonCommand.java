package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddLessonCommand extends Command {

    private String line;
    private static final int INDEX_NAME = 0;
    private static final int INDEX_TIMES = 1;
    private static final int INDEX_START_TIME = 0;
    private static final int INDEX_END_TIME = 1;
    private Module module;
    private Semester semester;

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
        String[] nameAndDates = line.split(" /from ");
        if (!hasName(nameAndDates)) {
            throw new OofException("OOPS!!! The lesson needs a name.");
        } else if (!hasStartTime(nameAndDates)) {
            throw new OofException("OOPS!!! The lesson needs a start time.");
        } else if (!hasEndTime(nameAndDates)) {
            throw new OofException("OOPS!!! The lesson needs an end time.");
        }
        String name = nameAndDates[INDEX_NAME].trim();
        String[] dateSplit = nameAndDates[INDEX_TIMES].split(" /to ");
        String startDate = parseTimeStamp(dateSplit[INDEX_START_TIME]);
        String endDate = parseTimeStamp(dateSplit[INDEX_END_TIME]);
        if (isDateValid(startDate) && isDateValid(endDate)) {
            SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
            try {
                Date newStartTime = format.parse(dateSplit[INDEX_START_TIME]);
                Date newEndTime = format.parse(dateSplit[INDEX_END_TIME]);
                if (!isStartDateBeforeEndDate(newStartTime, newEndTime)) {
                    throw new OofException("OOPS!!! The start time of a lesson cannot be after the end time.");
                }
            } catch (ParseException e) {
                throw new OofException("Timestamp given is invalid! Please try again.");
            }
            Lesson lesson = new Lesson(name, startDate, endDate);
            Module.addLesson(lesson);
            ui.printLessonAddedMessage(Module.getModuleCode(), lesson);
            storage.writeSemesterList(semesterList, semester, module);
        } else if (isDateValid(startDate)) {
            throw new OofException("OOPS!!! The end date is invalid.");
        } else if (isDateValid(endDate)) {
            throw new OofException("OOPS!!! The start date is invalid.");
        } else {
            throw new OofException("OOPS!!! The start and end dates are invalid.");
        }
    }

    /**
     * Checks if input has a name.
     *
     * @param lineSplit processed user input.
     * @return true if name is more than length 0 and is not whitespace.
     */
    private boolean hasName(String[] lineSplit) {
        return lineSplit[0].trim().length() > 0;
    }

    /**
     * Checks if input has a start time (argument given before "/to").
     *
     * @param lineSplit processed user input.
     * @return true if there is a start time and start time is not whitespace.
     */
    private boolean hasStartTime(String[] lineSplit) {
        return lineSplit.length > 1 && lineSplit[1].split(" /to ")[0].trim().length() > 0;
    }

    /**
     * Checks if input has an end time (argument given after "/to").
     *
     * @param lineSplit processed user input.
     * @return true if there is an end time and end time is not whitespace.
     */
    private boolean hasEndTime(String[] lineSplit) {
        String[] timeSplit = lineSplit[1].split(" /to ");
        return timeSplit.length > 1 && timeSplit[1].trim().length() > 0;
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
