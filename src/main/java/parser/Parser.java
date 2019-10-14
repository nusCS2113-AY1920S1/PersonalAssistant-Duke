package parser;

import command.AddCommand;
import command.Command;
import command.CommentCommand;
import command.DeleteCommand;
import command.DoneCommand;
import command.ExitCommand;
import command.FindCommand;
import command.ListCommand;
import command.PostponeCommand;
import command.PriorityCommand;
import command.RemindCommand;
import command.SearchCommand;
import command.ViewCommand;
import command.EditCommand;
import command.IgnoreCommand;
import exception.DukeException;
import ui.Ui;

import java.time.LocalDateTime;
import java.text.ParseException;

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
     */
    public static Command parse(String userInput) throws DukeException {

        String command = userInput.split("\\s+", 2)[0].trim();
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
            if (userInput.contains(Flag.BETWEEN.getFlag())) {
                return new TodoWithinPeriodParser(userInput).parse();
            }
            if (userInput.contains(Flag.FOR.getFlag())) {
                return new ;
            }
            return new TodoParser(userInput).parse();
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

        case "edit":
            String[] editCommandParts = userInput.split(" ", 3);
            indexOfTask = Integer.parseInt(editCommandParts[1]) - 1;
            description = editCommandParts[2];
            if (description.isEmpty()) {
                throw new DukeException(DukeException.emptyUserDescription());
            }
            return new EditCommand(indexOfTask, description);

        case "delete":
            description = userInput.split(command, 2)[1].trim();
            if (description.isEmpty()) {
                throw new DukeException(DukeException.emptyUserDescription());
            }
            indexOfTask = Integer.parseInt(description) - 1;
            return new DeleteCommand(indexOfTask);

        case "priority":
            String[] priorityCommandParts = userInput.split(" ", 3);
            String priorityLevel = priorityCommandParts[2];
            indexOfTask = Integer.parseInt(priorityCommandParts[1]) - 1;
            if (priorityLevel.isEmpty()) {
                throw new DukeException(DukeException.emptyPriorityLevel());
            }
            return new PriorityCommand(indexOfTask, priorityLevel);

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
            return parseIgnore(userInput, true);
        case "unignore":
            return parseIgnore(userInput, false);
        case "comment":
            int index;
            try {
                String rawIndex = userInput.split("\\s+", 3)[1].trim();
                index = Integer.parseInt(rawIndex) - 1;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(DukeException.invalidIndex());
            } catch (NumberFormatException e) {
                throw new DukeException(DukeException.invalidIndex());
            }
            String comment = "";
            try {
                comment = userInput.split("\\s+", 3)[2].trim();
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(DukeException.emptyUserDescription());
            }
            return new CommentCommand(index, comment);
        default:
            // Empty string or unknown command.
            Ui.printUnknownInput();
            Ui.userOutputForUI = "Wrong Command";
            throw new DukeException(DukeException.unknownUserCommand());
        }
    }

    private static Command parseRemind(String command, String userInput) throws DukeException {
        String description;
        int indexOfTask;
        int days;

        description = extractDescription(command, userInput);
        indexOfTask = extractIndexOfTask(description);
        days = extractReminderValue(description);

        assert indexOfTask >= 0;
        assert days > 0;
        return new RemindCommand(indexOfTask, days);
    }

    private static String extractDescription(String command, String userInput) throws DukeException {
        try {
            return userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyUserDescription());
        }
    }

    private static int extractIndexOfTask(String description) throws DukeException {
        int index;
        index = Integer.parseInt(description.split("in", 2)[0].trim()) - 1;
        if (index <= 0) {
            throw new DukeException("Non-positive number for index detected." +
                    " Please input a positive number for task index.");
        }
        return index;
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
        LocalDateTime atDate;
        try {
            dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            atDate = DateTimeExtractor.extractDateTime(dateTimeFromUser, command);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
        assert atDate != null;
        return new AddCommand(command, taskDescription, atDate, null);
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