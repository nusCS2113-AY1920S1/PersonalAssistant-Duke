package parser;

import command.AddCommand;
import command.Command;
import command.CommentCommand;
import command.DeleteCommand;
import command.DoneCommand;
import command.ExitCommand;
import command.FindCommand;
import command.ListCommand;
import command.LocationCommand;
import command.PostponeCommand;
import command.PriorityCommand;
import command.RemindCommand;
import command.SearchCommand;
import command.ViewCommand;
import command.EditCommand;
import command.IgnoreCommand;
import exception.DukeException;
import task.Deadline;
import ui.Ui;

import java.time.LocalDateTime;
import java.text.ParseException;
import java.util.Date;

/**
 * The parser class is used to parse and make sense of the different queries the
 * user inputs into the program and tag them for further processing.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */

public class ParserFactory {

    /**
     * Parses the user input of string type and returns the respective command type.
     *
     * @param userInput This string is provided by the user to ask 'Duke' to perform
     *                  a particular action
     * @return Command After processing the user's input it returns the correct
     *         command for further processing
     * @throws DukeException The DukeException class has all the respective methods
     *                       and messages!
     */
    public static Command parse(String userInput) throws DukeException {

        String command = userInput.split("\\s+", 2)[0].trim();
        Integer indexOfTask;

        switch (command) {
        case "todo":
            if (userInput.contains(Flag.BETWEEN.getFlag())) {
                return new TodoWithinPeriodParser(userInput, command).parse();
            }
            if (userInput.contains(Flag.FOR.getFlag())) {
                return new TodoWithDurationParser(userInput, command).parse();
            }
            return new TodoParser(userInput, command).parse();
        case "deadline":
            return new DeadlineParser(userInput, command).parse();
        case "event":
            return parseEvent(command, userInput);
        case "find":
            String findKeyWord = userInput.split(command, 2)[1].trim();
            if (findKeyWord.isEmpty()) {
                throw new DukeException(DukeException.emptyUserDescription());
            }
            return new FindCommand(findKeyWord);

        case "edit":
            return new EditParser(userInput, command).parse();

        case "delete":
            return new DeleteParser(userInput, command).parse();

        case "priority":
            return new PriorityParser(userInput, command).parse();

        case "done":
            return new DoneParser(userInput, command).parse();

        case "remind":
            return new RemindParser(userInput, command).parse();

        case "postpone":
            String dateTimeString;
            LocalDateTime newStartDate;
            LocalDateTime fromDate;
            LocalDateTime toDate;
            String[] postponeCommandParts = userInput.split(" ", 3);
            try {
                indexOfTask = Integer.parseInt(postponeCommandParts[1]) - 1;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(DukeException.invalidIndex());
            }
            try {
                dateTimeString = postponeCommandParts[2].trim();
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(DukeException.emptyDateOrTime());
            }
            try {
                if (dateTimeString.contains("-")) {
                    String obtainStartDate = dateTimeString.split("-", 2)[0].trim();
                    fromDate = DateTimeExtractor.extractDateTime(obtainStartDate, command);
                    String obtainEndDate = dateTimeString.split("-", 2)[1].trim();
                    toDate = DateTimeExtractor.extractDateTime(obtainEndDate, command);
                    return new PostponeCommand(indexOfTask, fromDate, toDate);
                } else {
                    newStartDate = DateTimeExtractor.extractDateTime(dateTimeString, command);
                    return new PostponeCommand(indexOfTask, newStartDate);
                }
            } catch (ParseException e) {
                throw new DukeException(DukeException.wrongDateOrTime());
            }

        case "view":
            String userScheduleDate = userInput.split(" ", 2)[1].trim();
            return new ViewCommand(userScheduleDate);

        case "list":
            return new ListCommand();

        case "bye":
            return new ExitCommand();

        case "search":
            long duration;
            try {
                duration = Long.parseLong(userInput.split(command, 2)[1].trim());
            } catch (NumberFormatException e) {
                throw new DukeException(DukeException.wrongDateOrTime());
            }
            return new SearchCommand(duration);
        case "ignore":
            return parseIgnore(userInput, true);
        case "unignore":
            return parseIgnore(userInput, false);
        case "comment":
            return new CommentParser(userInput, command).parse();
        case "location":
            try {
                String userIndex = userInput.split("\\s", 5)[2].trim();
                indexOfTask = Integer.parseInt(userIndex) - 1;
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                throw new DukeException(DukeException.invalidIndex());
            }
            String location = "Default: Null Location";
            try {
                location = userInput.split("\\s", 5)[4].trim();
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(DukeException.invalidLocation());
            }
            return new LocationCommand(indexOfTask, location);

        default:
            // Empty string or unknown command.
            Ui.printUnknownInput();
            Ui.userOutputForUI = "Wrong Command";
            throw new DukeException(DukeException.unknownUserCommand());
        }
    }

    private static String extractDescription(String command, String userInput) throws DukeException {
        try {
            return userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyUserDescription());
        }
    }

    private static Command parseEvent(String command, String userInput) throws DukeException {
        String taskFeatures;
        String checkType = "/at";

        taskFeatures = extractDescription(command, userInput);
        String taskDescription = taskFeatures.split(checkType, 2)[0].trim();
        if (taskDescription.isEmpty()) {
            throw new DukeException(DukeException.emptyUserDescription());
        }

        String dateTimeFromUser;
        LocalDateTime fromDate;
        LocalDateTime toDate;
        try {
            dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            String obtainStartDate = dateTimeFromUser.split("-", 2)[0].trim();
            fromDate = DateTimeExtractor.extractDateTime(obtainStartDate, command);
            String obtainEndDate = dateTimeFromUser.split("-", 2)[1].trim();
            toDate = DateTimeExtractor.extractDateTime(obtainEndDate, command);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
        assert toDate != null;
        assert fromDate != null;
        return new AddCommand(command, taskDescription, fromDate, toDate);
    }

    private static Command parseIgnore(String userInput, Boolean isIgnore) {
        int index = Integer.parseInt(userInput.split("\\s+", 2)[1].trim()) - 1;
        return new IgnoreCommand(index, isIgnore);
    }
}