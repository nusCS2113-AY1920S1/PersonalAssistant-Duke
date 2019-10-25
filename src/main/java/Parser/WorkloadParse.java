package Parser;
import Commands.AddCommand;
import Commands.Command;
import Commands.RecurringCommand;
import Commands.ShowWorkloadCommand;
import Interface.*;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.TaskList;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
public class WorkloadParse extends Parse{

    private static String fullCommand;
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());
    private static LookupTable LT;

    static {
        try {
            LT = new LookupTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public WorkloadParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    private Date getNextWeekDate (Date inDate) {
        Date nextWeek = new Date(inDate.getTime() + 7 * 24 * 60 * 60 * 1000);
        return nextWeek;
    }

    @Override
    public Command execute() throws Exception {
        try {
            Date today = Calendar.getInstance().getTime();
           // Date nextWeek = new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000);
            Date nextWeek = getNextWeekDate(today);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String nextWeekDate = formatter.format(nextWeek);
            return new ShowWorkloadCommand(nextWeekDate);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! Please enter show workload as follows:\n" +
                    "/show workload");
        }
    }
}
