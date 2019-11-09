package Parser;

import Commands.Command;
import Commands.RemindCommand;
import Commons.Duke;
import Commons.DukeConstants;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidFormatException;
import Tasks.Deadline;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for RemindParse.
 */
public class RemindParse extends Parse {
    private static final Integer LENGTH_OF_SET = 4;
    private static final Integer LENGTH_OF_RM = 3;
    private static final Integer LENGTH_OF_SPACE = 1;
    private static final Integer LENGTH_OF_REMIND = 6;
    private String[] modDescriptionCommandsplit;
    private String fullCommand;
    private String[] dateDescriptionSplit;
    private static final Logger LOGGER = DukeLogger.getLogger(RemindParse.class);

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
            if (fullCommand.contains(DukeConstants.REMIND_CHECK_KEYWORD)) {
                Date dummyDate = new Date();
                return new RemindCommand(new Deadline(DukeConstants.NO_FIELD, DukeConstants.NO_FIELD, DukeConstants.NO_FIELD), dummyDate, false);
            }
            boolean isRemind = false;
            String description;
            String activity = fullCommand.trim().substring(LENGTH_OF_REMIND);
            dateDescriptionSplit = activity.trim().split(DukeConstants.DEADLINE_DATE_DESCRIPTION_SPLIT_KEYWORD);
            String modDescriptionCommand = dateDescriptionSplit[0];
            modDescriptionCommandsplit = modDescriptionCommand.trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
            if (modDescriptionCommand.contains(DukeConstants.REMIND_SET_KEYWORD)) {
                description = modDescriptionCommand.substring(LENGTH_OF_SET).trim();
                isRemind = true;
            } else {
                description = modDescriptionCommand.substring(LENGTH_OF_RM).trim();
            }
            if (!isValidModCodeAndDescription(description)) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE + " OOPS!!! The ModCode and description of a deadline cannot be empty.");
            }
            String checkModCodeString = modDescriptionCommandsplit[1];
            if (!isModCode(checkModCodeString)) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE + " OOPS!!! The ModCode is invalid.");
            }
            String taskDescription;
            if (isRemind) {
                taskDescription = dateDescriptionSplit[0].substring(LENGTH_OF_SET + checkModCodeString.length() + LENGTH_OF_SPACE).trim();
            } else {
                taskDescription = dateDescriptionSplit[0].substring(LENGTH_OF_RM + checkModCodeString.length() + LENGTH_OF_SPACE).trim();
            }
            if (taskDescription.isEmpty()) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE + " OOPS!!! The description of a deadline cannot be empty.");
            }
            String deadlineDateRemindDateString = dateDescriptionSplit[1].trim();
            String[] dateTime = DateTimeParser.remindDateParse(deadlineDateRemindDateString);
            String deadlineDateString = dateTime[0];
            String deadlineTimeString = dateTime[1];
            String remindDateString = dateTime[2];
            Date remindDate = DateTimeParser.deadlineInputStringToDate(remindDateString);
            return new RemindCommand(new Deadline(description, deadlineDateString, deadlineTimeString), remindDate, isRemind);
        } catch (ParseException | ArrayIndexOutOfBoundsException e) {
            LOGGER.severe("Invalid remind format");
            throw new DukeInvalidFormatException("OOPS!!! Please enter remind as follows:\n" +
                    "remind/(set/rm) mod_code description /by week n.o day time /on week n.o day time\n" +
                    "For example: remind/set cs2100 hand in homework /by week 9 fri 1500 /on week 9 thu 1500");
        }
    }
}
