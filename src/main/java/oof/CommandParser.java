package oof;

import java.util.ArrayList;

import oof.command.AddAssessmentCommand;
import oof.command.AddAssignmentCommand;
import oof.command.AddDeadlineCommand;
import oof.command.AddEventCommand;
import oof.command.AddLessonCommand;
import oof.command.AddModuleCommand;
import oof.command.AddSemesterCommand;
import oof.command.AddToDoCommand;
import oof.command.ByeCommand;
import oof.command.CalendarCommand;
import oof.command.Command;
import oof.command.DeleteLessonCommand;
import oof.command.DeleteModuleCommand;
import oof.command.DeleteSemesterCommand;
import oof.command.DeleteTaskCommand;
import oof.command.DoneCommand;
import oof.command.FindCommand;
import oof.command.FreeCommand;
import oof.command.HelpCommand;
import oof.command.ListCommand;
import oof.command.RecurringCommand;
import oof.command.ScheduleCommand;
import oof.command.SelectModuleCommand;
import oof.command.SelectSemesterCommand;
import oof.command.SnoozeCommand;
import oof.command.SummaryCommand;
import oof.command.ThresholdCommand;
import oof.command.TrackerCommand;
import oof.command.ViewAllModuleCommand;
import oof.command.ViewAllSemesterCommand;
import oof.command.ViewLessonCommand;
import oof.command.ViewSelectedModuleCommand;
import oof.command.ViewSelectedSemesterCommand;
import oof.command.ViewWeekCommand;
import oof.exception.OofException;

/**
 * Represents a parser to process the commands inputted by the user.
 */
public class CommandParser {

    private static final String DELIMITER = "||";

    /**
     * Parses the input given by user and calls specific Commands
     * after checking the validity of the input.
     *
     * @param input Command entered by user.
     * @return Command based on the user input.
     * @throws OofException Catches invalid commands given by user.
     */
    public static Command parse(String input) throws OofException {
        if (containsIllegalInput(input)) {
            throw new OofException("Your command contains illegal input!");
        }
        String command = getFirstWord(input);
        input = input.replaceFirst(command, "").trim();
        switch (command) {
        case ByeCommand.COMMAND_WORD:
            return new ByeCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand(input);

        case DoneCommand.COMMAND_WORD:
            return parseDoneCommand(input);

        case AddToDoCommand.COMMAND_WORD:
            return parseAddToDoCommand(input);

        case AddAssignmentCommand.COMMAND_WORD:
            return parseAddAssignmentCommand(input);

        case AddDeadlineCommand.COMMAND_WORD:
            return parseAddDeadlineCommand(input);

        case AddAssessmentCommand.COMMAND_WORD:
            return parseAddAssessmentCommand(input);

        case AddEventCommand.COMMAND_WORD:
            return parseAddEventCommand(input);

        case DeleteTaskCommand.COMMAND_WORD:
            return parseDeleteCommand(input);

        case FindCommand.COMMAND_WORD:
            return new FindCommand(input);

        case SnoozeCommand.COMMAND_WORD:
            return parseSnooze(input);

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommand(input);

        case SummaryCommand.COMMAND_WORD:
            return new SummaryCommand();

        case RecurringCommand.COMMAND_WORD:
            return new RecurringCommand(input);

        case CalendarCommand.COMMAND_WORD:
            return new CalendarCommand(input);

        case ViewWeekCommand.COMMAND_WORD:
            return parseViewWeekCommand(input);

        case FreeCommand.COMMAND_WORD:
            return new FreeCommand(input);

        case TrackerCommand.COMMAND_WORD:
            return new TrackerCommand(input);

        case ThresholdCommand.COMMAND_WORD:
            return parseThresholdCommand(input);

        case AddSemesterCommand.COMMAND_WORD:
            return parseSemesterCommand(input);

        case AddModuleCommand.COMMAND_WORD:
            return parseModuleCommand(input);

        case AddLessonCommand.COMMAND_WORD:
            return parseLessonCommand(input);

        default:
            throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Checks if user input contains illegal characters.
     *
     * @param line User input.
     * @return True if user input contains illegal characters, false otherwise.
     */
    private static boolean containsIllegalInput(String line) {
        return line.contains(DELIMITER);
    }

    /**
     * Extracts the Command type from user input.
     *
     * @param input User input.
     * @return String containing type of command.
     */
    private static String getFirstWord(String input) {
        int index = input.indexOf(' ');
        if (index == -1) { // Input only contains a single word
            return input;
        } else {
            return input.substring(0, index).trim(); // Extracts first word.
        }
    }

    /**
     * Tokenize the user input into an argument array.
     *
     * @param input User input.
     * @param argumentDelimiters Specific delimiters for the command to be tokenized.
     * @return ArrayList of tokenized user input.
     */
    private static ArrayList<String> tokenizeToStringArray(String input, String[] argumentDelimiters) {
        int argumentDelimitersIndex = 0;
        String[] inputSplit = input.split(" ");
        ArrayList<String> argumentArray = new ArrayList<>();
        StringBuilder argument = new StringBuilder();
        for (int index = 0; index < inputSplit.length; index++) {
            if (argumentDelimitersIndex >= argumentDelimiters.length) {
                argument.append(inputSplit[index]).append(" ");
            } else if (inputSplit[index].equals(argumentDelimiters[argumentDelimitersIndex])) {
                argumentArray.add(argument.toString().trim());
                argumentDelimitersIndex++;
                argument = new StringBuilder();
            } else {
                argument.append(inputSplit[index]).append(" ");
            }
        }
        argumentArray.add(argument.toString().trim());
        return argumentArray;
    }

    /**
     * Parses input if the user input starts with done.
     *
     * @param input Command inputted by user in string format.
     * @return Instance of CompleteCommand with parsed input as arguments
     * @throws OofException Throws exception if input is empty or not a valid integer.
     */
    private static Command parseDoneCommand(String input) throws OofException {
        if (input.isEmpty()) {
            throw new OofException("OOPS!!! Please enter a number!");
        } else {
            try {
                int index = Integer.parseInt(input) - 1;
                return new DoneCommand(index);
            } catch (NumberFormatException e) {
                throw new OofException("OOPS!!! Please enter a valid number!");
            }
        }
    }

    /**
     * Parses command for adding todo tasks.
     *
     * @param input User input.
     * @return Instance of AddToDoCommand if the user input is successfully tokenized.
     */
    private static Command parseAddToDoCommand(String input) {
        String[] argumentDelimiters = {"/on"};
        ArrayList<String> arguments = tokenizeToStringArray(input, argumentDelimiters);
        return new AddToDoCommand(arguments);
    }

    /**
     * Parses command for adding assignment tasks.
     *
     * @param input User input.
     * @return Instance of AddAssignmentCommand if the user input is successfully tokenized.
     */
    private static Command parseAddAssignmentCommand(String input) {
        String[] argumentDelimiters = {"/by"};
        ArrayList<String> arguments = tokenizeToStringArray(input, argumentDelimiters);
        return new AddAssignmentCommand(arguments);
    }

    /**
     * Parses command for adding deadline tasks.
     *
     * @param input User input.
     * @return Instance of AddDeadlineCommand if the user input is successfully tokenized.
     */
    private static Command parseAddDeadlineCommand(String input) {
        String[] argumentDelimiters = {"/by"};
        ArrayList<String> arguments = tokenizeToStringArray(input, argumentDelimiters);
        return new AddDeadlineCommand(arguments);
    }

    /**
     * Parses command for adding Assessment tasks.
     *
     * @param input User input.
     * @return Instance of AddAssessmentCommand if the user input is successfully tokenized.
     */
    private static Command parseAddAssessmentCommand(String input) {
        String[] argumentDelimiters = {"/from", "/to"};
        ArrayList<String> arguments = tokenizeToStringArray(input, argumentDelimiters);
        return new AddAssessmentCommand(arguments);
    }

    /**
     * Parses command for adding event tasks.
     *
     * @param input User input.
     * @return Instance of AddEventCommand if the user input is successfully tokenized.
     */
    private static Command parseAddEventCommand(String input) {
        String[] argumentDelimiters = {"/from", "/to"};
        ArrayList<String> arguments = tokenizeToStringArray(input, argumentDelimiters);
        return new AddEventCommand(arguments);
    }

    /**
     * Parses input if the user input starts with delete.
     *
     * @param input Command inputted by user in string format.
     * @return Instance of DeleteCommand if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseDeleteCommand(String input) throws OofException {
        if (input.isEmpty()) {
            throw new OofException("OOPS!!! Please enter a number!");
        } else {
            try {
                int index = Integer.parseInt(input) - 1;
                return new DeleteTaskCommand(index);
            } catch (NumberFormatException e) {
                throw new OofException("OOPS!!! Please enter a valid number!");
            }
        }
    }

    /**
     * Parses input if the user input starts with snooze.
     *
     * @param input Command inputted by user in string format
     * @return Returns an instance of SnoozeCommand
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseSnooze(String input) throws OofException {
        try {
            int index = Integer.parseInt(input) - 1;
            return new SnoozeCommand(index);
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!!! Please enter a valid number!");
        }
    }

    /**
     * Parses input for ThresholdCommand.
     *
     * @param input Command input by user
     * @return instance of ThresholdCommand with parsed input as arguments
     * @throws OofException Throws exception if input is empty or not a valid integer.
     */
    private static Command parseThresholdCommand(String input) throws OofException {
        if (input.isEmpty()) {
            throw new OofException("OOPS!!! Please enter a number!");
        } else {
            try {
                int threshold = Integer.parseInt(input);
                return new ThresholdCommand(threshold);
            } catch (NumberFormatException e) {
                throw new OofException("OOPS!!! Please enter a valid number!");
            }
        }
    }

    /**
     * Parses input for ViewWeekCommand.
     *
     * @param input User input.
     * @return Instance of ViewWeekCommand with parsed input as arguments.
     */
    private static Command parseViewWeekCommand(String input) {
        String[] argumentArray = input.split(" ");
        return new ViewWeekCommand(argumentArray);
    }


    /**
     * Parses input if the user input starts with semester.
     *
     * @param input Command inputted by user in string format.
     * @return Returns relevant Semester Commands if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseSemesterCommand(String input) throws OofException {
        if (input.isEmpty()) {
            return new ViewSelectedSemesterCommand();
        } else {
            String commandFlag = getFirstWord(input);
            input = input.replaceFirst(commandFlag, "").trim();
            switch (commandFlag) {
            case "/add":
                return parseSemesterAdd(input);
            case "/delete":
                return parseSemesterDelete(input);
            case "/select":
                return parseSemesterSelect(input);
            case "/view":
                return new ViewAllSemesterCommand();
            default:
                throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private static Command parseSemesterAdd(String input) {
        String[] argumentDelimiters = {"/name", "/from", "/to"};
        ArrayList<String> argumentArray = tokenizeToStringArray(input, argumentDelimiters);
        return new AddSemesterCommand(argumentArray);
    }

    private static Command parseSemesterDelete(String input) throws OofException {
        try {
            int deleteIndex = Integer.parseInt(input) - 1;
            return new DeleteSemesterCommand(deleteIndex);
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!!! Please enter a valid number!");
        }
    }

    private static Command parseSemesterSelect(String input) throws OofException {
        try {
            int index = Integer.parseInt(input) - 1;
            return new SelectSemesterCommand(index);
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!!! Please enter a valid number!");
        }
    }

    /**
     * Parses input if the user input starts with module.
     *
     * @param input Command inputted by user in string format.
     * @return Returns relevant Module Commands if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseModuleCommand(String input) throws OofException {
        if (input.isEmpty()) {
            return new ViewSelectedModuleCommand();
        } else {
            String commandFlag = getFirstWord(input);
            input = input.replaceFirst(commandFlag, "").trim();
            switch (commandFlag) {
            case "/add":
                return parseModuleAdd(input);
            case "/delete":
                return parseModuleDelete(input);
            case "/select":
                return parseModuleSelect(input);
            case "/view":
                return new ViewAllModuleCommand();
            default:
                throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private static Command parseModuleAdd(String input) {
        String[] argumentDelimiters = {"/name"};
        ArrayList<String> argumentArray = tokenizeToStringArray(input, argumentDelimiters);
        return new AddModuleCommand(argumentArray);
    }

    private static Command parseModuleDelete(String input) throws OofException {
        try {
            int deleteIndex = Integer.parseInt(input) - 1;
            return new DeleteModuleCommand(deleteIndex);
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!!! Please enter a valid number!");
        }
    }

    private static Command parseModuleSelect(String input) throws OofException {
        try {
            int index = Integer.parseInt(input) - 1;
            return new SelectModuleCommand(index);
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!!! Please enter a valid number!");
        }
    }

    /**
     * Parses input if the user input starts with lesson.
     *
     * @param input Command inputted by user in string format.
     * @return Returns relevant Lesson Commands if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseLessonCommand(String input) throws OofException {
        if (input.isEmpty()) {
            return new ViewLessonCommand();
        } else {
            String commandFlag = getFirstWord(input);
            input = input.replaceFirst(commandFlag, "").trim();
            switch (commandFlag) {
            case "/add":
                return parseLessonAdd(input);
            case "/delete":
                return parseLessonDelete(input);
            default:
                throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private static Command parseLessonAdd(String input) {
        String[] argumentDelimiters = {"/day", "/from", "/to"};
        ArrayList<String> argumentArray = tokenizeToStringArray(input, argumentDelimiters);
        return new AddLessonCommand(argumentArray);
    }

    private static Command parseLessonDelete(String input) throws OofException {
        try {
            int deleteIndex = Integer.parseInt(input) - 1;
            return new DeleteLessonCommand(deleteIndex);
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!!! Please enter a valid number!");
        }
    }
}
