package duchess.logic.parser;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddDeadlineCommand;
import duchess.logic.commands.AddEventCommand;
import duchess.logic.commands.AddModuleCommand;
import duchess.logic.commands.AddTodoCommand;
import duchess.logic.commands.ByeCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DeleteCommand;
import duchess.logic.commands.DoneCommand;
import duchess.logic.commands.FindCommand;
import duchess.logic.commands.ListModulesCommand;
import duchess.logic.commands.ListTasksCommand;
import duchess.logic.commands.LogCommand;
import duchess.logic.commands.ReminderCommand;
import duchess.logic.commands.SnoozeCommand;
import duchess.logic.commands.UndoCommand;
import duchess.logic.commands.ViewScheduleCommand;

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
            return new DeleteCommand(arguments);
        case "done":
            return new DoneCommand(arguments);
        case "todo":
            return new AddTodoCommand(arguments);
        case "deadline":
            return new AddDeadlineCommand(arguments);
        case "event":
            return new AddEventCommand(arguments);
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
        default:
            throw new DuchessException("Please enter a valid command.");
        }
    }
}
