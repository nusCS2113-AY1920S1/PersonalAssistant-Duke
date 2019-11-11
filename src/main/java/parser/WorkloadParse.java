package parser;

import commands.Command;
import commands.ShowWorkloadCommand;
import commons.DukeConstants;
import commons.DukeLogger;
import dukeexceptions.DukeInvalidFormatException;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for WorkloadParse.
 */
public class WorkloadParse extends Parse {
    private static String fullCommand;
    private static final int HOURS = 24;
    private static final int MINUTES = 60;
    private static final int SECONDS = 60;
    private static final int MILLISECONDS = 1000;
    private static final int ONE_WEEK = 7;
    private final Logger logger = DukeLogger.getLogger(WorkloadParse.class);

    /**
     * Creates a WorkloadParse object.
     * @param fullCommand The user's input
     */
    public WorkloadParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * This method gets the date of 7 days later.
     * @param inDate date
     * @return date of 7 days later
     */
    private Date getNextWeekDate(Date inDate) {
        Date nextWeek = new Date(inDate.getTime() + ONE_WEEK * HOURS * MINUTES * SECONDS * MILLISECONDS);
        return nextWeek;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        try {
            Date today = Calendar.getInstance().getTime();
            Date nextWeek = getNextWeekDate(today);
            String nextWeekDate = DukeConstants.EVENT_DATE_INPUT_FORMAT.format(nextWeek);
            return new ShowWorkloadCommand(nextWeekDate);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.severe("Invalid show workload format");
            throw new DukeInvalidFormatException(DukeConstants.SAD_FACE + DukeConstants.SHOW_WORKLOAD_FORMAT);
        }
    }
}
