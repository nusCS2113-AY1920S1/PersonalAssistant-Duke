package duke.parser;

import duke.commands.AddCommand;
import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.FindCommand;
import duke.commands.FindFreeTimeCommand;
import duke.commands.ListCommand;
import duke.commands.RemindersCommand;
import duke.commands.SnoozeCommand;
import duke.commands.ViewCommand;
import duke.exception.DukeException;

public class Parser {

    /**
     * Parse the date and return AddCommand class for Event task type.
     * @param taskType The type of task
     * @param command command to be parse to description and dateTime
     * @return AddCommand for Event task type
     */
    private static Command parseEvent(String taskType, String command) throws DukeException {
        String[] splitStr = command.split("/at ", 2);
        if (splitStr.length == 1) {
            throw new DukeException("Invalid command\n");
        }
        return new AddCommand(taskType, splitStr[0], splitStr[1]);
    }

    /**
     * Parse the date and return AddCommand class for Deadline task type.
     * @param taskType The type of task
     * @param command command to be parse to description and dateTime
     * @return AddCommand for Deadline task type
     */
    private static Command parseDeadline(String taskType, String command) throws DukeException {
        String[] splitStr = command.split("/by ", 2);
        if (splitStr.length == 1) {
            throw new DukeException("Invalid command\n");
        }

        return new AddCommand(taskType, splitStr[0], splitStr[1]);
    }

    /**
     * Mark the task indicated by user to be done.
     * @param command The taskNo to be converted to an integer
     * @return DoneCommand with the index of item to be mark as done
     * @throws NumberFormatException if command has characters
     */
    private static Command parseDone(String command) throws NumberFormatException {
        int index = Integer.parseInt(command);

        return new DoneCommand(index);
    }

    /**
     * Delete the task indicated by user.
     * @param command The taskNo to be converted to an integer
     * @return DeleteCommand with the index of item to be deleted
     * @throws NumberFormatException if command has characters.
     */
    private static Command parseDelete(String command) throws NumberFormatException {
        int index = Integer.parseInt(command);

        return new DeleteCommand(index);
    }

    /**
     * Snooze the task indicated by user.
     * @param command The taskNo to be converted to an integer.
     * @return DeleteCommand with the index of item to be snoozed.
     * @throws NumberFormatException if command has characters.
     */
    private static Command parseSnooze(String command) throws NumberFormatException {
        String[] details = command.split(" ");
        int index = Integer.parseInt(details[0]);
        int value = Integer.parseInt(details[1]);
        String units = details[2];
        return new SnoozeCommand(index, value, units);
    }


    /**
     * find free time according to period desired
     * @param s String containing details like amount of time . e.g. 5 min
     * @return FindFreeTimeCommand a command to find the time
     * @throws DukeException when s is invalid.
     */
    private static Command parseFindFreeTime(String s) throws DukeException {
        String[] details = s.split(" ");
        int value = Integer.parseInt(details[0]);
        String units = details[1];
        return new FindFreeTimeCommand(value, units);
    }
/**
 * Return the correct command given by user, Class method.
     * @param fullCommand command input by user to be parse
     * @return The correct command class as defined by first word
     * @throws NumberFormatException if command has characters.
     * @throws DukeException if the command is invalid or there are missing parameters.
     */
    public static Command parse(String fullCommand) throws NumberFormatException, DukeException {
        String[] splitStr = fullCommand.trim().split(" ", 2);

        switch (splitStr.length) {
            case 1:
                switch (splitStr[0].toLowerCase()) {
                    case "list":
                        return new ListCommand();
                    case "bye":
                        return new ByeCommand();
                    case "reminders":
                        return new RemindersCommand();
                    default:
                        throw new DukeException("Invalid command\n");
                }
            case 2:
                if (!splitStr[1].isEmpty()) {
                    splitStr[1] = splitStr[1].trim();
                    switch (splitStr[0].toLowerCase()) {
                        case "todo":
                            return new AddCommand(splitStr[0], splitStr[1]);
                        case "deadline":
                            return parseDeadline(splitStr[0], splitStr[1]);
                        case "event":
                            return parseEvent(splitStr[0], splitStr[1]);
                        case "find":
                            return new FindCommand(splitStr[1]);
                        case "done":
                            return parseDone(splitStr[1]);
                        case "delete":
                            return parseDelete(splitStr[1]);
                        case "snooze":
                            return parseSnooze(splitStr[1]);
                        case "findfreetime":
                            return parseFindFreeTime(splitStr[1]);
                        case "view":
                            return new ViewCommand(splitStr[1]);
                        default:
                            throw new DukeException("Invalid command\n");
                    }
                } else {
                    throw new DukeException("Invalid command\n");
                }
            default:
                throw new DukeException("Invalid command\n");
        }
    }
}
