package duke.parsers;


import duke.commands.*;
import duke.exceptions.DukeException;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.ToDo;

import java.time.Period;
import java.util.Date;


/**
 * Parser is a public class that help to parse the command that is inputted from the user.
 * And generate the appropriate command with their appropriate arguments.
 */
public class Parser {
    /**
     * This is the main function that parse the command inputted by the user.
     * @param fullCommand the string the user input in the CLI
     * @return <code>new ExitCommand()</code> if the user input "bye"
     *         <code>new AddCommand(new ToDo())</code> if the user input "todo"
     *         <code>new AddCommand(new Event()</code> if the user input "event"
     *         <code>new ListCommand()</code> if the user input list
     *         <code>new MarkDoneCommand(index)</code> if the user input "done"
     *         <code>new FindCommand(description)</code> if the user input "find"
     *         <code>new DeleteCommand(index) </code> if the sure input "delete"
     * @throws DukeException either the command is entered incorrectly or the command is not recognized
     */
    public static Command parse(String fullCommand) throws DukeException {
        //TODO: Put error for invalid input and what not
        String[] splitCommand = fullCommand.split(" ", 2);
        String command = splitCommand[0];
        String description = "";

        if (splitCommand.length >= 2) {
            description = splitCommand[1];
        }
        if (command.equals("done") || command.equals("todo") || command.equals("event") || command.equals("deadline")) {
            if (description.trim().length() == 0) {
                throw new DukeException("\u2639 OOPS!!! The description of a " + command + " cannot be empty.");
            }
        }
        if (command.equals("bye")) {
            return new ExitCommand();
        } else if (command.equals("todo")) {
            if (description.contains("/needs")) {
                try {
                    String[] splitString = description.split(" /needs ", 2);
                    return new AddCommand(new ToDo(splitString[0], splitString[1]));
                } catch (Exception e) {
                    throw new DukeException("\u2639 OOPS!!! The todo command does not seem to be valid.");
                }
            } else if (description.contains("/between")) {
                try {
                    String[] splitString = description.split("/between", 2);
                    String[] splitString2 = splitString[1].split("-", 2);
                    return new AddCommand(new ToDo(splitString[0], splitString2[0], splitString2[1]));
                } catch (Exception e) {
                    throw new DukeException("\u2639 OOPS!!! The todo command does not seem to be valid.");
                }
            }
            return new AddCommand(new ToDo(description));
        } else if (command.equals("deadline")) {
            try {
                String[] splitString = description.split(" /by ");
                String taskDescriptionString = splitString[0];
                String deadlineString = splitString[1];
                if (splitString[1].contains(" /every ")) {
                    String recurringString = "";
                    String[] splitString2 = splitString[1].split(" /every ");
                    deadlineString = splitString2[0];
                    recurringString = splitString2[1];
                    Period recurringDuration = DateParser.parsePeriod(recurringString);
                    return new AddCommand(new Deadline(taskDescriptionString, deadlineString, recurringDuration));
                } else {
                    return new AddCommand(new Deadline(taskDescriptionString, deadlineString));
                }
            } catch (Exception e) {
                throw new DukeException("\u2639 OOPS!!! The deadline command does not seem to be valid.");
            }
        } else if (command.equals("event")) {
            String[] splitString;
            if (description.contains("/between")) {
                try {
                    splitString = description.split(" /between ", 2);
                    String[] splitString2 = splitString[1].split("-", 2);
                    return new AddCommand(new Event(splitString[0], splitString2[0],splitString2[1]));
                } catch (Exception e) {
                    throw new DukeException("\u2639 OOPS!!! The event command does not seem to be valid.");
                }
            }
            try {
                splitString = description.split(" /at ");
                return new AddCommand(new Event(splitString[0], splitString[1]));
            } catch (Exception e) {
                throw new DukeException("\u2639 OOPS!!! The event command does not seem to be valid.");
            }
        } else if (command.equals("list")) {
            return new ListCommand();
        } else if (command.equals("done")) {
            int index = Integer.parseInt(description);
            return (new MarkDoneCommand(index));
        } else if (command.equals("find")) {
            return new FindCommand(description);
        } else if (command.equals("delete")) {
            int index = 0;
            try {
                index = Integer.parseInt(description);
            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a number");
            }
            return new DeleteCommand(index);
        } else if (command.equals("remindme")) {
            int index = 0;
            try {
                index = Integer.parseInt(description);
            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a number");
            }
            return new RemindCommand(index);
        } else if (command.equals("findfreetime")) {
            int index = 0;
            try {
                index = Integer.parseInt(description);
            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a number");
            }
            return new FindFreeTimeCommand(index);
        } else if (command.equals("snooze")) {
            int index1;
            int index2;
            try {
                index1 = Integer.parseInt(description.split(" ", 2)[0]);
                index2 = Integer.parseInt(description.split(" ", 2)[1]);
            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a number");
            }
            if (index1 < 1 && index2 > 31 || index2 < 0 || index2 > 23) {
                throw new DukeException("Improper day and hour assignment");
            }
            return new SnoozeCommand(index1,index2);
        } else if (command.equals("schedule")) {
            Date date = DateParser.parseDate(description);
            return new ScheduleCommand(date);
        } else if (command.equals("tentative")) {
            return new TentativeCommand();
        } else if (command.equals("confirm")) {
            return new ConfirmCommand();
        } else if (command.equals("viewschedule")) {
            try {
                return new ViewScheduleCommand(description);
            } catch (Exception e) {
                throw new DukeException(e.getMessage());
            }
        } else {
            throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

}
