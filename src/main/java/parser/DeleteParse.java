package parser;

import commands.Command;
import commands.DeleteCommand;
import commons.DukeConstants;
import commons.DukeLogger;
import commons.ModCodeChecker;
import dukeexceptions.DukeInvalidCommandException;
import dukeexceptions.DukeInvalidDateTimeException;
import dukeexceptions.DukeInvalidFormatException;
import tasks.Deadline;
import tasks.Event;
import java.text.ParseException;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for DeleteParse.
 */
public class DeleteParse extends Parse {
    private static String[] modCodeAndDescriptionAndDate;
    private static String[] modCodeAndDescriptionSplit;
    private static String fullCommand;
    private final Logger logger = DukeLogger.getLogger(DeleteParse.class);
    private ModCodeChecker modCodeChecker = ModCodeChecker.getInstance();

    /**
     * Creates a DeleteParse object.
     * @param fullCommand The full command that calls for DeleteParse
     */
    public DeleteParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * This executes the data processing for DeleteParse.
     * @return Command which represents the parsed DeleteCommand
     * @throws Exception Returned if command does not adhere to format
     */
    @Override
    public Command parse() throws DukeInvalidFormatException, DukeInvalidCommandException,
            DukeInvalidDateTimeException {
        if (fullCommand.trim().startsWith(DukeConstants.DELETE_EVENT_HEADER)) {
            try {
                String activity = fullCommand.trim().replaceFirst(DukeConstants.DELETE_EVENT_HEADER,
                        DukeConstants.NO_FIELD);
                modCodeAndDescriptionAndDate = activity.split(DukeConstants.EVENT_DATE_DESCRIPTION_SPLIT_KEYWORD);
                modCodeAndDescriptionSplit = modCodeAndDescriptionAndDate[0].trim().split(DukeConstants.BLANK_SPACE);
                String fullDescriptionAndModCode = modCodeAndDescriptionAndDate[0].trim();
                if (!super.isValidModCodeAndDescription(fullDescriptionAndModCode)) {
                    throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                            + DukeConstants.EVENT_EMPTY_MODCODE_DESCRIPTION_ERROR);
                }
                String modCode = modCodeAndDescriptionSplit[0];
                if (!modCodeChecker.isModCode(modCode)) {
                    throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                            + DukeConstants.INVALID_MODCODE_ERROR);
                }
                if (!super.isValidDescription(modCodeAndDescriptionSplit)) {
                    throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                            + DukeConstants.EVENT_EMPTY_DESCRIPTION_ERROR);
                }
                String timePeriod = modCodeAndDescriptionAndDate[1];
                if (!super.isValidTimePeriod(timePeriod)) {
                    throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                            + DukeConstants.EVENT_TIME_FORMAT_ERROR);
                }
                String eventDate = modCodeAndDescriptionAndDate[1];
                String[] out = DateTimeParser.eventParse(eventDate);
                String date = out[0];
                String startTime = out[1];
                String endTime = out[2];
                return new DeleteCommand(DukeConstants.EVENT_LIST, new Event(fullDescriptionAndModCode, date,
                        startTime, endTime));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                logger.severe("Invalid format for deleting event");
                throw new DukeInvalidFormatException(DukeConstants.EVENT_FORMAT);
            }
        } else if (fullCommand.trim().startsWith(DukeConstants.DELETE_DEADLINE_HEADER)) {
            try {
                String activity = fullCommand.trim().replaceFirst(DukeConstants.DELETE_DEADLINE_HEADER,
                        DukeConstants.NO_FIELD);
                modCodeAndDescriptionAndDate = activity.split(DukeConstants.DEADLINE_DATE_DESCRIPTION_SPLIT_KEYWORD);
                modCodeAndDescriptionSplit = modCodeAndDescriptionAndDate[0].trim().split(DukeConstants.BLANK_SPACE);
                String fullDescriptionAndModCode = modCodeAndDescriptionAndDate[0].trim();
                if (!super.isValidModCodeAndDescription(fullDescriptionAndModCode)) {
                    throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                            + DukeConstants.DEADLINE_EMPTY_MODCODE_DESCRIPTION_ERROR);
                }
                String modCode = modCodeAndDescriptionSplit[0];
                if (!modCodeChecker.isModCode(modCode)) {
                    throw new DukeInvalidFormatException(DukeConstants.SAD_FACE + DukeConstants.INVALID_MODCODE_ERROR);
                }
                if (!super.isValidDescription(modCodeAndDescriptionSplit)) {
                    throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                            + DukeConstants.DEADLINE_EMPTY_DESCRIPTION_ERROR);
                }
                String timePeriod = modCodeAndDescriptionAndDate[1];
                if (!super.isValidTime(timePeriod)) {
                    throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                            + DukeConstants.DEADLINE_TIME_FORMAT_ERROR);
                }
                String deadlineDate = modCodeAndDescriptionAndDate[1];
                String[] out = DateTimeParser.deadlineParse(deadlineDate);
                String date = out[0];
                String time = out[1];
                return new DeleteCommand(DukeConstants.DEADLINE_LIST, new Deadline(fullDescriptionAndModCode, date,
                        time));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                logger.severe("Invalid format for deleting deadline");
                throw new DukeInvalidFormatException(DukeConstants.DEADLINE_FORMAT);
            }
        } else {
            throw new DukeInvalidCommandException(DukeConstants.SAD_FACE + DukeConstants.UNKNOWN_MEANING);
        }
    }
}