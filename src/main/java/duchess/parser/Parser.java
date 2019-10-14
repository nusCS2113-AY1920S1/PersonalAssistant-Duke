package duchess.parser;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddDeadlineCommand;
import duchess.logic.commands.AddEventCommand;
import duchess.logic.commands.AddModuleCommand;
import duchess.logic.commands.AddTodoCommand;
import duchess.logic.commands.ByeCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DeleteModuleCommand;
import duchess.logic.commands.DeleteTaskCommand;
import duchess.logic.commands.DoneCommand;
import duchess.logic.commands.FindCommand;
import duchess.logic.commands.ListModulesCommand;
import duchess.logic.commands.ListTasksCommand;
import duchess.logic.commands.LogCommand;
import duchess.logic.commands.RedoCommand;
import duchess.logic.commands.ReminderCommand;
import duchess.logic.commands.SnoozeCommand;
import duchess.logic.commands.UndoCommand;
import duchess.logic.commands.ViewScheduleCommand;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Parser {
    /**
     * Returns the command to execute after parsing user input.
     *
     * @param input the user input
     * @return the command to execute
     * @throws DuchessException the exception if user input is invalid
     */
    public static Command parse(String input) throws DuchessException {
        List<String> words = Arrays.asList(input.split(" "));
        String keyword = words.get(0);
        List<String> arguments = words.subList(1, words.size());

        // The entire parser is to be refactored
        // when implementing interactive commands.
        switch (keyword) {
        case "list":
            try {
                String secondKeyword = words.get(1);
                switch (secondKeyword) {
                case "tasks":
                    return new ListTasksCommand();
                case "modules":
                    return new ListModulesCommand();
                default:
                    throw new IllegalArgumentException();
                }
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                throw new DuchessException("Usage: list (tasks | modules)");
            }
        case "add":
            try {
                String secondKeyword = words.get(1);
                switch (secondKeyword) {
                case "module":
                    return new AddModuleCommand(words.subList(2, words.size()));
                default:
                    throw new IllegalArgumentException();
                }
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                throw new DuchessException("Usage: add module <module-code> <module-name>");
            }
        case "find":
            return new FindCommand(arguments);
        case "delete":
            try {
                String secondKeyword = words.get(1);
                switch (secondKeyword) {
                case "task":
                    return new DeleteTaskCommand(arguments);
                case "module":
                    return new DeleteModuleCommand(arguments);
                default:
                    throw new IllegalArgumentException();
                }
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                throw new DuchessException("Usage: delete (module|task) <number>");
            }
        case "done":
            try {
                return new DoneCommand(Integer.parseInt(words.get(0)) - 1);
            } catch (NumberFormatException e) {
                throw new DuchessException("Please supply a number. Eg: done 2");
            }
        case "todo":
            if (arguments.size() == 0) {
                throw new DuchessException("Format for todo: todo <task>");
            }
            if (arguments.get(arguments.size() - 1).charAt(0) == '#') {
                String description = String.join(" ", arguments.subList(0, arguments.size() - 1));
                String moduleCode = arguments.get(arguments.size() - 1).substring(1);
                return new AddTodoCommand(description, moduleCode);
            } else {
                String description = String.join(" ", arguments);
                return new AddTodoCommand(description);
            }
        case "deadline":
            int separatorIndex = arguments.indexOf("/by");
            if (arguments.size() == 0 || separatorIndex <= 0) {
                throw new DuchessException("Format for deadline: deadline <task> /by <deadline>");
            }
            if (arguments.get(arguments.size() - 1).charAt(0) == '#') {
                String description = String.join(" ", arguments.subList(0, separatorIndex));
                LocalDateTime deadline = Util
                        .parseDateTime(arguments.subList(0, arguments.size() - 1), separatorIndex + 1);
                String moduleCode = arguments.get(arguments.size() - 1).substring(1);
                return new AddDeadlineCommand(description, deadline, moduleCode);
            } else {
                String description = String.join(" ", arguments.subList(0, separatorIndex));
                LocalDateTime deadline = Util
                        .parseDateTime(arguments, separatorIndex + 1);
                return new AddDeadlineCommand(description, deadline);
            }
        case "event":
            int atSeparatorIndex = arguments.indexOf("/at");
            int toSeparatorIndex = arguments.indexOf("/to");
            if (arguments.size() == 0 || atSeparatorIndex <= 0 || toSeparatorIndex < atSeparatorIndex) {
                throw new DuchessException("Format for event: event <event> /at <start datetime> /to <end datetime>");
            }
            if (arguments.get(arguments.size() - 1).charAt(0) == '#') {
                String description = String.join(" ", arguments.subList(0, atSeparatorIndex));
                LocalDateTime end = Util
                        .parseDateTime(arguments.subList(0, arguments.size() - 1), toSeparatorIndex + 1);
                LocalDateTime start = Util
                        .parseDateTime(arguments.subList(0, arguments.size() - 1), atSeparatorIndex + 1);
                String moduleCode = arguments.get(arguments.size() - 1).substring(1);
                return new AddEventCommand(description, end, start, moduleCode);
            } else {
                String description = String.join(" ", arguments.subList(0, atSeparatorIndex));
                LocalDateTime end = Util.parseDateTime(arguments, toSeparatorIndex + 1);
                LocalDateTime start = Util.parseDateTime(arguments, atSeparatorIndex + 1);
                return new AddEventCommand(description, end, start);
            }
        case "reminder":
            return new ReminderCommand();
        case "snooze":
            return new SnoozeCommand(arguments);
        case "schedule":
            try {
                String view = words.get(2);
                boolean isInvalidView = !view.equals("day") && !view.equals("week");
                boolean isIllegalArgument = isInvalidView && (words.size() > 3);
                if (isIllegalArgument) {
                    throw new IllegalArgumentException();
                }
                String date = words.get(1);
                return new ViewScheduleCommand(date, view);
            } catch (IndexOutOfBoundsException e) {
                throw new DuchessException("Usage: schedule <date> (day | week)");
            }
        case "bye":
            return new ByeCommand();
        case "log":
            return new LogCommand();
        case "undo":
            return new UndoCommand(arguments);
        case "redo":
            return new RedoCommand(arguments);
        default:
            throw new DuchessException("Please enter a valid command.");
        }
    }
}
