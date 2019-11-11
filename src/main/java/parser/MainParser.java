package parser;

import commands.ByeCommand;
import commands.Command;
import commands.HelpCommand;
import commands.RetrievePreviousCommand;
import commons.DukeConstants;
import dukeexceptions.DukeInvalidCommandException;

/**
 * This class distinguishes the main command and calls for methods with respect to the main command.
 */
public class MainParser {
    /**
     * This method executes the processing of user input and directs to the relevant command.
     * @param fullCommand The user input
     * @return The selected command
     * @throws Exception If the input is not a valid command
     */
    public static Command parse(String fullCommand) throws Exception {
        fullCommand = fullCommand.trim().replaceAll(" +", " ");
        if (fullCommand.isEmpty()) {
            throw new DukeInvalidCommandException("Invalid input the command cannot be blank. "
                    + "Please type help to see all commands");
        }
        String [] stringSplit = fullCommand.split(DukeConstants.BLANK_SPACE);
        String command = stringSplit[0];
        switch (command) {
        case DukeConstants.ADD_EVENT_HEADER:
        case DukeConstants.ADD_DEADLINE_HEADER:
            return new AddParse(fullCommand).parse();

        case DukeConstants.DELETE_EVENT_HEADER:
        case DukeConstants.DELETE_DEADLINE_HEADER:
            return new DeleteParse(fullCommand).parse();

        case DukeConstants.DONE_EVENT_HEADER:
        case DukeConstants.DONE_DEADLINE_HEADER:
            return new DoneParse(fullCommand).parse();

        case DukeConstants.RECUR_WEEKLY_HEADER:
        case DukeConstants.RECUR_BIWEEKLY_HEADER:
        case DukeConstants.REMOVE_RECUR_WEEKLY_HEADER:
        case DukeConstants.REMOVE_RECUR_BIWEEKLY_HEADER:
            return new RecurParse(fullCommand).parse();

        case DukeConstants.REMIND_CHECK_HEADER:
        case DukeConstants.REMIND_SET_HEADER:
        case DukeConstants.REMOVE_REMIND_HEADER:
            return new RemindParse(fullCommand).parse();

        case DukeConstants.SHOW_WORKLOAD_HEADER:
            return new WorkloadParse(fullCommand).parse();

        case DukeConstants.SHOW_FILTER_HEADER:
            return new FilterParse(fullCommand).parse();
        case DukeConstants.HELP_HEADER:
            return new HelpCommand();
        case DukeConstants.FIND_TIME_HEADER:
            return new FindFreeTimesParse(fullCommand).parse();

        case DukeConstants.SHOW_PREVIOUS_HEADER:
            return new ShowPreviousParse(fullCommand).parse();

        case DukeConstants.RETRIEVE_TIME_HEADER:
            return new RetrieveFreeTimesParse(fullCommand).parse();

        case DukeConstants.RETRIEVE_PREVIOUS_HEADER:
            return new RetrievePreviousCommand(fullCommand);

        case DukeConstants.SHOW_WEEK_HEADER:
            return new WeekParse(fullCommand).parse();

        case DukeConstants.BYE_HEADER:
            return new ByeCommand();

        default:
            throw new DukeInvalidCommandException(DukeConstants.SAD_FACE + DukeConstants.INVALID_INPUT_ERROR);
        }
    }
}
