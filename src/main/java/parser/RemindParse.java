package parser;

import commands.Command;
import commands.RemindCommand;
import commons.DukeConstants;
import commons.DukeLogger;
import commons.ModCodeChecker;
import dukeexceptions.DukeInvalidDateTimeException;
import dukeexceptions.DukeInvalidFormatException;
import tasks.Deadline;

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
    private static final Logger logger = DukeLogger.getLogger(RemindParse.class);
    private ModCodeChecker modCodeChecker = ModCodeChecker.getInstance();

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
                return new RemindCommand(new Deadline(DukeConstants.NO_FIELD, DukeConstants.NO_FIELD,
                        DukeConstants.NO_FIELD), dummyDate, false);
            }
            boolean isRemind = false;
            String description;
            String activity = fullCommand.trim().substring(LENGTH_OF_REMIND);
            dateDescriptionSplit = activity.trim().split(DukeConstants.DEADLINE_DATE_DESCRIPTION_SPLIT_KEYWORD);
            String modDescriptionCommand = dateDescriptionSplit[0];
            modDescriptionCommandsplit = modDescriptionCommand.trim().split(DukeConstants.BLANK_SPACE);
            if (modDescriptionCommand.contains(DukeConstants.REMIND_SET_KEYWORD)) {
                description = modDescriptionCommand.substring(LENGTH_OF_SET).trim();
                isRemind = true;
            } else {
                description = modDescriptionCommand.substring(LENGTH_OF_RM).trim();
            }
            if (!super.isValidModCodeAndDescription(description)) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                        + DukeConstants.DEADLINE_EMPTY_MODCODE_DESCRIPTION_ERROR);
            }
            String checkModCodeString = modDescriptionCommandsplit[1];
            if (!modCodeChecker.isModCode(checkModCodeString)) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE + DukeConstants.INVALID_MODCODE_ERROR);
            }
            String taskDescription;
            if (isRemind) {
                taskDescription = dateDescriptionSplit[0].substring(LENGTH_OF_SET + checkModCodeString.length()
                        + LENGTH_OF_SPACE).trim();
            } else {
                taskDescription = dateDescriptionSplit[0].substring(LENGTH_OF_RM + checkModCodeString.length()
                        + LENGTH_OF_SPACE).trim();
            }
            if (taskDescription.isEmpty()) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                        + DukeConstants.DEADLINE_EMPTY_DESCRIPTION_ERROR);
            }
            String deadlineDateRemindDateString = dateDescriptionSplit[1].trim();
            String[] deadlineDateRemindDateSplit = deadlineDateRemindDateString.split(
                    DukeConstants.REMIND_DATE_DEADLINE_DATE_SPLIT_KEYWORD);
            String deadlineDateString = deadlineDateRemindDateSplit[0];
            String remindDateString = deadlineDateRemindDateSplit[1];
            if (!super.isValidTime(deadlineDateString)) {
                throw new DukeInvalidDateTimeException(DukeConstants.SAD_FACE
                        + DukeConstants.DEADLINE_TIME_FORMAT_ERROR);
            }
            if (!super.isValidTime(remindDateString)) {
                throw new DukeInvalidDateTimeException(DukeConstants.SAD_FACE + DukeConstants.REMIND_TIME_FORMAT_ERROR);
            }
            String[] dateTime = DateTimeParser.remindDateParse(deadlineDateRemindDateString);
            deadlineDateString = dateTime[0];
            String deadlineTimeString = dateTime[1];
            remindDateString = dateTime[2];
            Date remindDate = DateTimeParser.deadlineInputStringToDate(remindDateString);
            return new RemindCommand(new Deadline(description, deadlineDateString, deadlineTimeString),
                    remindDate, isRemind);
        } catch (ParseException | ArrayIndexOutOfBoundsException e) {
            logger.severe("Invalid remind format");
            throw new DukeInvalidFormatException(DukeConstants.REMIND_FORMAT);
        }
    }
}
