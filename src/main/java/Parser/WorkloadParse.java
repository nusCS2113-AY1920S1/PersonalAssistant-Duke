package Parser;

import Commands.Command;
import Commands.ShowWorkloadCommand;
import DukeExceptions.DukeInvalidFormatException;
import Commons.LookupTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for WorkloadParse.
 */
public class WorkloadParse extends Parse{

    private static String fullCommand;
    private static final Logger LOGGER = Logger.getLogger(WorkloadParse.class.getName());

    /**
     * Creates a WorkloadParse object.
     * @param fullCommand
     */
    public WorkloadParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    private Date getNextWeekDate (Date inDate) {
        Date nextWeek = new Date(inDate.getTime() + 7 * 24 * 60 * 60 * 1000);
        return nextWeek;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        try {
            Date today = Calendar.getInstance().getTime();
            Date nextWeek = getNextWeekDate(today);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String nextWeekDate = formatter.format(nextWeek);
            return new ShowWorkloadCommand(nextWeekDate);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeInvalidFormatException("OOPS!!! Please enter show workload as follows:\n" +
                    "/show workload");
        }
    }
}
