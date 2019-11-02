package Parser;

import Commands.Command;
import Commands.RemindCommand;
import DukeExceptions.DukeInvalidFormatException;
import Tasks.Deadline;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for RemindParse.
 */
public class RemindParse extends Parse {
    private static final String NO_FIELD = "void";
    private String[] modDescriptionsplit;
    private String fullCommand;
    private String[] dateDescriptionSplit;
    private static final Logger LOGGER = Logger.getLogger(RemindCommand.class.getName());

    /**
     * Creates RemindParse object.
     * @param fullCommand The full Command that calls for RemindParse.
     */
    public RemindParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Parses the entire input to see if it is to remove or set reminder.
     * @return Returns RemindCommand
     * @throws Exception On invalid format that called for RemindParse
     */
    @Override
    public Command parse() throws Exception {
        try {
            boolean isRemind = false;
            String description = NO_FIELD;
            String activity = fullCommand.trim().substring(6);
            dateDescriptionSplit = activity.trim().split("/by");
            modDescriptionsplit = dateDescriptionSplit[0].trim().split(" ");
            if(!super.isModCode(modDescriptionsplit[1])){
                throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
            }
            if(dateDescriptionSplit[0].contains("/set")){
                description = dateDescriptionSplit[0].substring(4).trim();
                if (description.isEmpty()) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                }
                isRemind = true;
            } else {
                description = dateDescriptionSplit[0].substring(3).trim();
                if (description.isEmpty()) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                }
            }

            String[] dateTime = DateTimeParser.remindDateParse(dateDescriptionSplit[1].trim());
            Date remindDate = DateTimeParser.deadlineInputStringToDate(dateTime[2]);
            return new RemindCommand(new Deadline(description, dateTime[0], dateTime[1]), remindDate, isRemind);
        } catch (ParseException | ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, e.toString(), e);
            throw new DukeInvalidFormatException("OOPS!!! Please enter remind as follows:\n" +
                    "remind/(set/rm) mod_code description /by week n.o day time /to week n.o day time\n" +
                    "For example: remind/set cs2100 hand in homework /by week 9 fri 1500 /to week 9 thu 1500");
        }
    }
}
