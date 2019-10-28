package Parser;

import Commands.Command;
import Commands.RecurringCommand;
import DukeExceptions.DukeException;
import DukeExceptions.DukeInvalidCommandException;
import DukeExceptions.DukeInvalidDateTimeException;
import DukeExceptions.DukeInvalidFormatException;
import Interface.Parser;
import Interface.LookupTable;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecurParse extends Parse {

    private static String fullCommand;
    private static String[] split1;
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());
    private static LookupTable LT = new LookupTable();

    public RecurParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command execute() throws Exception {
        try {
            // recur/e (CS1231 project meeting) /start (1/10/2019 to 15/11/2019 /from 1500 /to 1700)
            String activity = fullCommand.trim().substring(7);
            String[] fullCommandSplit = activity.split("/start");
            String modCodeAndDescription = fullCommandSplit[0].trim();
            split1 = modCodeAndDescription.trim().split(" ");
            if(!super.isModCode(split1[0])){
                throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
            }
            String dateAndTime = fullCommandSplit[1].trim();

            if (modCodeAndDescription.isEmpty()) {
                throw new DukeInvalidCommandException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
            }
            String[] in = DateTimeParser.recurringEventParse(dateAndTime);
            String startDateString = in[0];
            String endDateString = in[1];
            String startTimeString = in[2];
            String endTimeString = in[3];
            return new RecurringCommand(modCodeAndDescription, startDateString, endDateString, startTimeString, endTimeString);
        } catch (ParseException | ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, e.toString(), e);
            throw new DukeInvalidFormatException("OOPS!!! Please enter recurring event as follows:\n" +
                    "recur/e modCode name_of_event /start dd/MM/yyyy to dd/MM/yyyy /from HHmm /to HHmm\n" +
                    "For example: recur/e CS1231 project meeting /start 1/10/2019 to 15/11/2019 /from 1500 /to 1700");
        }
    }
}