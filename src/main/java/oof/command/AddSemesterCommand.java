package oof.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

public class AddSemesterCommand extends Command {
    private String line;
    private static final int INDEX_YEAR = 0;
    private static final int INDEX_NAME = 1;
    private static final int INDEX_DATE_START = 2;
    private static final int INDEX_DATE_END = 3;
    private static final String REGEX_DELIMITER = "/add|/name|/from|/to";

    /**
     * Constructor for AddSemesterCommand.
     *
     * @param line Command inputted by user for processing.
     */
    public AddSemesterCommand(String line) {
        super();
        this.line = line;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        String[] yearNameSplit = line.split(REGEX_DELIMITER);
        if (!hasYear(yearNameSplit)) {
            throw new OofException("OOPS!! The semester needs a year.");
        }
        if (!hasName(yearNameSplit)) {
            throw new OofException("OOPS!! The semester needs a name.");
        }
        if (!hasStartDate(yearNameSplit)) {
            throw new OofException("OOPS!! The semester needs a start date,");
        }
        if (!hasEndDate(yearNameSplit)) {
            throw new OofException("OOPS!! The semester needs an end date.");
        }
        String year = yearNameSplit[INDEX_YEAR];
        String name = yearNameSplit[INDEX_NAME];
        String startDate = yearNameSplit[INDEX_DATE_START];
        String endDate = yearNameSplit[INDEX_DATE_END];
        if (isDateValid(startDate) && isDateValid(endDate)) {
            if (hasClashes(semesterList, startDate, endDate)) {
                throw new OofException("OOPS!! The semester clashes with another semester.");
            }
        }
        Semester semester = new Semester(year, name, startDate, endDate);
        semesterList.addSemester(semester);
        ui.printSemesterAddedMessage(semester);
        storage.writeSemesters(semesterList);
    }

    /**
     * Checks if semester has a year.
     *
     * @param yearNameSplit Array containing processed command arguments.
     * @return true if year is not whitespace.
     */
    private boolean hasYear(String[] yearNameSplit) {
        return yearNameSplit[INDEX_YEAR].trim().length() > 0;
    }

    /**
     * Checks if semester has a name.
     *
     * @param yearNameSplit Array containing processed command arguments.
     * @return true if name is not whitespace.
     */
    private boolean hasName(String[] yearNameSplit) {
        return yearNameSplit.length > INDEX_NAME && yearNameSplit[INDEX_NAME].trim().length() > 0;
    }

    /**
     * Checks if input has a start date (argument given before "/to").
     *
     * @param yearNameSplit Array containing processed command arguments.
     * @return true if there is a start date and start date is not whitespace.
     */
    private boolean hasStartDate(String[] yearNameSplit) {
        return yearNameSplit.length > INDEX_DATE_START && yearNameSplit[INDEX_DATE_START].trim().length() > 0;
    }

    /**
     * Checks if input has an end date (argument given after "/to").
     *
     * @param yearNameSplit Array containing processed command arguments.
     * @return true if there is an end date and end date is not whitespace.
     */
    private boolean hasEndDate(String[] yearNameSplit) {
        return yearNameSplit.length > INDEX_DATE_END && yearNameSplit[INDEX_DATE_END].trim().length() > 0;
    }

    /**
     * Checks if semester being added clashes with other semesters.
     *
     * @param semesterList Instance of SemesterList that stores Semesters.
     * @param startDate    Start date of Semester being added.
     * @param endDate      End date of Semester being added.
     * @return true if semester being added clashes with other semesters, false otherwise.
     * @throws OofException if start date is after end date or if date is invalid.
     */
    private boolean hasClashes(SemesterList semesterList, String startDate, String endDate) throws OofException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date dateStart = format.parse(startDate);
            Date dateEnd = format.parse(endDate);
            if (!isStartDateBeforeEndDate(dateStart, dateEnd)) {
                throw new OofException("OOPS!! The start date of a semester cannot be after the end date.");
            }
            for (Semester semester : semesterList.getSemesterList()) {
                Date semesterDateStart = format.parse(semester.getStartDate());
                Date semesterEndStart = format.parse(semester.getEndDate());
                if (isClash(dateStart, dateEnd, semesterDateStart, semesterEndStart)) {
                    return true;
                }
            }
        } catch (ParseException e) {
            throw new OofException("OOPS!! The date is invalid.");
        }
        return false;
    }

    /**
     * Checks if start and end date are chronologically accurate.
     *
     * @param startTime Start date of semester being added.
     * @param endTime   End date of semester being added.
     * @return true if start date occurs before end date, false otherwise.
     */
    private boolean isStartDateBeforeEndDate(Date startTime, Date endTime) {
        return startTime.compareTo(endTime) <= 0;
    }

    /**
     * Checks if there is an overlap of semester date.
     *
     * @param newStartTime  Start date of semester being added.
     * @param newEndTime    End date of semester being added.
     * @param currStartTime Start date of semester being compared.
     * @param currEndTime   End date of semester being added.
     * @return true if there is an overlap of event timing.
     */
    private boolean isClash(Date newStartTime, Date newEndTime, Date currStartTime, Date currEndTime) {
        return (newStartTime.compareTo(currStartTime) >= 0 && newStartTime.compareTo(currEndTime) < 0)
                || (newEndTime.compareTo(currStartTime) > 0 && newEndTime.compareTo(currEndTime) <= 0);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
