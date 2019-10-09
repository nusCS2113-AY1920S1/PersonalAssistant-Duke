package parser;

import command.AddCommand;
import command.Command;
import command.DeleteCommand;
import command.DoneCommand;
import command.ExitCommand;
import command.FindCommand;
import command.ListCommand;
import command.PostponeCommand;
import command.RemindCommand;
import command.SearchCommand;
import command.ViewCommand;
import command.IgnoreCommand;
import exception.DukeException;
import ui.Ui;

import java.time.LocalDateTime;
import java.text.ParseException;

import static parser.DateTimeExtractor.NULL_DATE;

/**
 * The parser class is used to parse and make sense of the different queries the
 * user inputs into the program and tag them for further processing.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */

public class Parser {

    /**
     * Parses the user input of string type and returns the respective command type.
     *
     * @param userInput This string is provided by the user to ask 'Duke' to perform
     *                  a particular action
     * @return Command After processing the user's input it returns the correct
     *         command for further processing
     * @throws DukeException The DukeException class has all the respective methods
     *                       and messages!
     *
     */
    public static Command parse(String userInput) throws DukeException {

        String command = userInput.split("\\s+", 2)[0].trim();
        String taskFeatures;
        String checkType;
        String description;
        Integer indexOfTask;
        LocalDateTime nullDate = LocalDateTime.of(1, 1, 1, 1, 1, 1, 1);
        LocalDateTime atDate = nullDate;
        LocalDateTime toDate = nullDate;
        LocalDateTime fromDate = nullDate;
        final String dateTimeFromUser;

        switch (command) {
        case "todo":
            return parseTodo(command, userInput);
        case "deadline":
            return parseDeadline(command, userInput);
        case "event":
            return parseEvent(command, userInput);
        case "find":
            String findKeyWord = userInput.split(command, 2)[1].trim();
            if (findKeyWord.isEmpty()) {
                throw new DukeException(DukeException.emptyUserDescription());
            }
            return new FindCommand(findKeyWord);

        case "delete":
            description = userInput.split(command, 2)[1].trim();
            if (description.isEmpty()) {
                throw new DukeException(DukeException.emptyUserDescription());
            }
            indexOfTask = Integer.parseInt(description) - 1;
            return new DeleteCommand(indexOfTask);

        case "done":
            description = userInput.split(command, 2)[1].trim();
            if (description.isEmpty()) {
                throw new DukeException(DukeException.unknownUserCommand());
            }
            indexOfTask = Integer.parseInt(description) - 1;
            return new DoneCommand(indexOfTask);

        case "remind":
            return parseRemind(command, userInput);

        case "postpone":
            atDate = nullDate;
            toDate = nullDate;
            fromDate = nullDate;

            checkType = "/to";

            if (!userInput.contains(checkType)) {
                throw new DukeException("No checkType(/to)");
            }
            description = userInput.substring(userInput.indexOf(command) + 8, userInput.indexOf(checkType)).trim();
            dateTimeFromUser = userInput.split(checkType, 2)[1].trim();

            if (description.isEmpty()) {
                throw new DukeException(DukeException.emptyUserDescription());
            }
            if (dateTimeFromUser.isEmpty()) {
                throw new DukeException(DukeException.emptyDateOrTime());
            }

            indexOfTask = Integer.parseInt(description) - 1;
            try {
                if (dateTimeFromUser.contains("-")) {
                    String obtainStartDate = dateTimeFromUser.split("-", 2)[0].trim();
                    fromDate = DateTimeExtractor.extractDateTime(obtainStartDate, command);
                    String obtainEndDate = dateTimeFromUser.split("-", 2)[1].trim();
                    toDate = DateTimeExtractor.extractDateTime(obtainEndDate, command);
                } else {
                    atDate = DateTimeExtractor.extractDateTime(dateTimeFromUser, command);
                }
            } catch (ParseException e) {
                throw new DukeException(DukeException.wrongDateOrTime());
            }
            return new PostponeCommand(indexOfTask, atDate, fromDate, toDate);
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
            return parseIgnore(command, userInput);
        default:
            // Empty string or unknown command.
            Ui.printUnknownInput();
            throw new DukeException(DukeException.unknownUserCommand());
        }
    }

    private static Command parseTodo(String command, String userInput) throws DukeException {
        String taskFeatures;

        try {
            taskFeatures = userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyUserDescription());
        }

        String checkType = "/between";
        String[] taskDetails = taskFeatures.split(checkType, 2);
        if (taskDetails.length == 1) {
            return new AddCommand(command, taskDetails[0], NULL_DATE, NULL_DATE);
        } else {
            return parseToDoPeriod(taskFeatures, taskDetails, checkType, command);
        }
    }

    private static Command parseToDoPeriod(String taskFeatures, String[] taskDetails, String checkType,
                                           String command) throws DukeException {
        String dateTimeFromUser = taskDetails[1];
        String taskDescription = taskFeatures.split(checkType, 2)[0].trim();
        String fromDate;
        String toDate;
        try {
            fromDate = dateTimeFromUser.split("-", 2)[0].trim();
            toDate = dateTimeFromUser.split("-", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        }
        LocalDateTime to;
        LocalDateTime from;
        try {
            to = DateTimeExtractor.extractDateTime(toDate, command);
            from = DateTimeExtractor.extractDateTime(fromDate, command);
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
        return new AddCommand(command, taskDescription, from, to);
    }

    private static Command parseRemind(String command, String userInput) throws DukeException {
        String description;
        int indexOfTask;
        int days;

        description = extractDescription(command, userInput);
        indexOfTask = extractIndexOfTask(description);
        days = extractReminderValue(description);

        return new RemindCommand(indexOfTask, days);
    }

    private static String extractDescription(String command, String userInput) throws DukeException {
        try {
            return userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyUserDescription());
        }
    }

    private static int extractIndexOfTask(String description) {
        return Integer.parseInt(description.split("in", 2)[0].trim()) - 1;
    }

    private static int extractReminderValue(String description) {
        String substring = description.split("in", 2)[1].trim();
        return Integer.parseInt(substring.split("\\s+", 2)[0].trim());
    }

    private static Command parseDeadline(String command, String userInput) throws DukeException {
        String taskFeatures;
        String checkType = "/by";

        taskFeatures = extractDescription(command, userInput);
        String taskDescription = taskFeatures.split(checkType, 2)[0].trim();
        if (taskDescription.isEmpty()) {
            throw new DukeException(DukeException.emptyUserDescription());
        }

        String dateTimeFromUser;
        LocalDateTime atDate = NULL_DATE;
        try {
            dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            atDate = DateTimeExtractor.extractDateTime(dateTimeFromUser, command);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
        assert !atDate.equals(NULL_DATE);
        return new AddCommand(command, taskDescription, atDate, NULL_DATE);
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
        LocalDateTime fromDate = NULL_DATE;
        LocalDateTime toDate = NULL_DATE;
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
        assert !toDate.equals(NULL_DATE);
        assert !fromDate.equals(NULL_DATE);
        return new AddCommand(command, taskDescription, fromDate, toDate);
    }

    private static Command parseIgnore(String command, String userInput) {
        int index = Integer.parseInt(userInput.split("\\s+",2)[1].trim()) - 1;
        return new IgnoreCommand(index);
    }

    private static int parseDuration(String userInput) throws DukeException {
        int duration;

        if (!userInput.contains("/for")) {
            return -1;
        }

        String substring = userInput.split("for", 2)[1].trim();
        try {
            duration = Integer.parseInt(substring.split("\\s+", 2)[0].trim());
        } catch (NumberFormatException e) {
            throw new DukeException("Invalid duration format");
        }
        return duration;
    }
}