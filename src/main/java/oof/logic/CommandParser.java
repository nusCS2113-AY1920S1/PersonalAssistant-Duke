package oof.logic;

import java.util.ArrayList;

import oof.model.semester.SelectedInstance;
import oof.logic.command.task.AddAssessmentCommand;
import oof.logic.command.task.AddAssignmentCommand;
import oof.logic.command.task.AddDeadlineCommand;
import oof.logic.command.task.AddEventCommand;
import oof.logic.command.lesson.AddLessonCommand;
import oof.logic.command.module.AddModuleCommand;
import oof.logic.command.semester.AddSemesterCommand;
import oof.logic.command.task.AddToDoCommand;
import oof.logic.command.ByeCommand;
import oof.logic.command.productivity.CalendarCommand;
import oof.logic.command.Command;
import oof.logic.command.lesson.DeleteLessonCommand;
import oof.logic.command.module.DeleteModuleCommand;
import oof.logic.command.semester.DeleteSemesterCommand;
import oof.logic.command.task.DeleteTaskCommand;
import oof.logic.command.organization.DoneCommand;
import oof.logic.command.organization.FindCommand;
import oof.logic.command.productivity.FreeCommand;
import oof.logic.command.HelpCommand;
import oof.logic.command.organization.ListCommand;
import oof.logic.command.organization.RecurringCommand;
import oof.logic.command.productivity.ScheduleCommand;
import oof.logic.command.module.SelectModuleCommand;
import oof.logic.command.semester.SelectSemesterCommand;
import oof.logic.command.organization.SnoozeCommand;
import oof.logic.command.productivity.SummaryCommand;
import oof.logic.command.productivity.ThresholdCommand;
import oof.logic.command.productivity.TrackerCommand;
import oof.logic.command.module.ViewAllModuleCommand;
import oof.logic.command.semester.ViewAllSemesterCommand;
import oof.logic.command.lesson.ViewLessonCommand;
import oof.logic.command.module.ViewSelectedModuleCommand;
import oof.logic.command.semester.ViewSelectedSemesterCommand;
import oof.logic.command.productivity.ViewWeekCommand;
import oof.commons.exceptions.CommandNotFoundException;
import oof.commons.exceptions.IllegalCommandException;
import oof.commons.exceptions.ParserException;
import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.commons.exceptions.command.MissingArgumentException;
import oof.logic.command.module.exceptions.ModuleNotSelectedException;
import oof.logic.command.semester.exceptions.SemesterNotSelectedException;
import oof.model.semester.Module;
import oof.model.semester.Semester;

//@@author KahLokKee

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
     * @throws ParserException  if command contains illegal strings or if command does not exists.
     * @throws CommandException if command contains invalid arguments.
     */
    public static Command parse(String input) throws ParserException, CommandException {
        if (containsIllegalInput(input)) {
            throw new IllegalCommandException("OOPS!!! Command contains illegal input!");
        }
        String command = getFirstWord(input);
        input = removeFirstWord(input);
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
            throw new CommandNotFoundException("OOPS!!! I'm sorry, but I don't know what that means :-(");
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
     * Extracts the first word from user input.
     *
     * @param input User input.
     * @return String containing the first word.
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
     * Removes the first word from user input.
     *
     * @param input User input.
     * @return string without first word or empty string if user input contains one word.
     */
    private static String removeFirstWord(String input) {
        int index = input.indexOf(' ');
        if (index == -1) { // Input only contains a single word
            return "";
        } else {
            return input.substring(index + 1).trim(); // Extracts after space.
        }
    }



    /**
     * Tokenize the user input into an argument array.
     *
     * @param input              User input.
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
     * @throws MissingArgumentException if input is empty or not a valid integer.
     */
    private static Command parseDoneCommand(String input) throws MissingArgumentException {
        if (input.isEmpty()) {
            throw new MissingArgumentException("OOPS!!! Please enter a number!");
        } else {
            try {
                int index = Integer.parseInt(input) - 1;
                return new DoneCommand(index);
            } catch (NumberFormatException e) {
                throw new MissingArgumentException("OOPS!!! Please enter a valid number!");
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
    private static Command parseAddAssignmentCommand(String input) throws ModuleNotSelectedException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new ModuleNotSelectedException("OOPS!! No module selected.");
        }
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
    private static Command parseAddAssessmentCommand(String input) throws ModuleNotSelectedException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new ModuleNotSelectedException("OOPS!! No module selected.");
        }
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
     * @throws MissingArgumentException if the arguments are missing.
     */
    private static Command parseDeleteCommand(String input) throws MissingArgumentException {
        if (input.isEmpty()) {
            throw new MissingArgumentException("OOPS!!! Please enter a number!");
        } else {
            try {
                int index = Integer.parseInt(input) - 1;
                return new DeleteTaskCommand(index);
            } catch (NumberFormatException e) {
                throw new MissingArgumentException("OOPS!!! Please enter a valid number!");
            }
        }
    }

    /**
     * Parses input if the user input starts with snooze.
     *
     * @param input Command inputted by user in string format
     * @return Returns an instance of SnoozeCommand
     * @throws InvalidArgumentException if the argument is invalid.
     */
    private static Command parseSnooze(String input) throws InvalidArgumentException {
        try {
            int index = Integer.parseInt(input) - 1;
            return new SnoozeCommand(index);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("OOPS!!! Please enter a valid number!");
        }
    }

    /**
     * Parses input for ThresholdCommand.
     *
     * @param input Command input by user
     * @return instance of ThresholdCommand with parsed input as arguments
     * @throws InvalidArgumentException if command argument is not a valid integer.
     * @throws MissingArgumentException if command argument is missing.
     */
    private static Command parseThresholdCommand(String input) throws InvalidArgumentException,
            MissingArgumentException {
        if (input.isEmpty()) {
            throw new MissingArgumentException("OOPS!!! Please enter a number!");
        } else {
            try {
                int threshold = Integer.parseInt(input);
                return new ThresholdCommand(threshold);
            } catch (NumberFormatException e) {
                throw new InvalidArgumentException("OOPS!!! Please enter a valid number!");
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
     * @throws CommandNotFoundException if command is invalid.
     * @throws InvalidArgumentException if command arguments are invalid.
     */
    private static Command parseSemesterCommand(String input) throws CommandNotFoundException,
            InvalidArgumentException {
        if (input.isEmpty()) {
            return new ViewSelectedSemesterCommand();
        } else {
            String commandFlag = getFirstWord(input);
            input = removeFirstWord(input);
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
                throw new CommandNotFoundException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private static Command parseSemesterAdd(String input) {
        String[] argumentDelimiters = {"/name", "/from", "/to"};
        ArrayList<String> argumentArray = tokenizeToStringArray(input, argumentDelimiters);
        return new AddSemesterCommand(argumentArray);
    }

    private static Command parseSemesterDelete(String input) throws InvalidArgumentException {
        try {
            int deleteIndex = Integer.parseInt(input) - 1;
            return new DeleteSemesterCommand(deleteIndex);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("OOPS!!! Please enter a valid number!");
        }
    }

    private static Command parseSemesterSelect(String input) throws InvalidArgumentException {
        try {
            int index = Integer.parseInt(input) - 1;
            return new SelectSemesterCommand(index);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("OOPS!!! Please enter a valid number!");
        }
    }

    /**
     * Parses input if the user input starts with module.
     *
     * @param input Command inputted by user in string format.
     * @return Returns relevant Module Commands if the parameters are valid.
     * @throws CommandNotFoundException if command is invalid.
     * @throws CommandException         if semester is not selected or if command arguments are invalid.
     */
    private static Command parseModuleCommand(String input) throws CommandNotFoundException, CommandException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (semester == null) {
            throw new SemesterNotSelectedException("OOPS!! Please select a semester!");
        }
        if (input.isEmpty()) {
            return new ViewSelectedModuleCommand();
        } else {
            String commandFlag = getFirstWord(input);
            input = removeFirstWord(input);
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
                throw new CommandNotFoundException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private static Command parseModuleAdd(String input) {
        String[] argumentDelimiters = {"/name"};
        ArrayList<String> argumentArray = tokenizeToStringArray(input, argumentDelimiters);
        return new AddModuleCommand(argumentArray);
    }

    private static Command parseModuleDelete(String input) throws InvalidArgumentException {
        try {
            int deleteIndex = Integer.parseInt(input) - 1;
            return new DeleteModuleCommand(deleteIndex);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("OOPS!!! Please enter a valid number!");
        }
    }

    private static Command parseModuleSelect(String input) throws CommandException {
        try {
            int index = Integer.parseInt(input) - 1;
            return new SelectModuleCommand(index);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("OOPS!!! Please enter a valid number!");
        }
    }

    /**
     * Parses input if the user input starts with lesson.
     *
     * @param input Command inputted by user in string format.
     * @return Returns relevant Lesson Commands if the parameters are valid.
     * @throws CommandNotFoundException if command is invalid.
     * @throws CommandException         if module is not selected or if command arguments are invalid.
     */
    private static Command parseLessonCommand(String input) throws CommandNotFoundException, CommandException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new ModuleNotSelectedException("OOPS!! No module selected.");
        } else if (input.isEmpty()) {
            return new ViewLessonCommand();
        } else {
            String commandFlag = getFirstWord(input);
            input = removeFirstWord(input);
            switch (commandFlag) {
            case "/add":
                return parseLessonAdd(input);
            case "/delete":
                return parseLessonDelete(input);
            default:
                throw new CommandNotFoundException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private static Command parseLessonAdd(String input) {
        String[] argumentDelimiters = {"/day", "/from", "/to"};
        ArrayList<String> argumentArray = tokenizeToStringArray(input, argumentDelimiters);
        return new AddLessonCommand(argumentArray);
    }

    private static Command parseLessonDelete(String input) throws InvalidArgumentException {
        try {
            int deleteIndex = Integer.parseInt(input) - 1;
            return new DeleteLessonCommand(deleteIndex);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("OOPS!!! Please enter a valid number!");
        }
    }
}
