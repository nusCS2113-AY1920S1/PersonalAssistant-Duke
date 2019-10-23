package duchess.parser.states;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddDeadlineCommand;
import duchess.logic.commands.AddGradeCommand;
import duchess.logic.commands.AddTodoCommand;
import duchess.logic.commands.ByeCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DeleteModuleCommand;
import duchess.logic.commands.DeleteTaskCommand;
import duchess.logic.commands.DisplayCalendarCommand;
import duchess.logic.commands.DoneCommand;
import duchess.logic.commands.ExportCommand;
import duchess.logic.commands.FindCommand;
import duchess.logic.commands.LogCommand;
import duchess.logic.commands.RedoCommand;
import duchess.logic.commands.ReminderCommand;
import duchess.logic.commands.SnoozeCommand;
import duchess.logic.commands.UndoCommand;
import duchess.logic.commands.ViewScheduleCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.commands.ListCommandParser;
import duchess.parser.states.add.AddState;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DefaultState implements ParserState {
    private Parser parser;

    public DefaultState(Parser parser) {
        this.parser = parser;
    }

    /**
     * Returns the command to execute after parsing user input.
     *
     * @param input the user input
     * @return the command to execute
     * @throws DuchessException the exception if user input is invalid
     */
    public Command parse(String input) throws DuchessException {
        List<String> words = Arrays.asList(input.split(" "));
        String keyword = words.get(0);
        List<String> arguments = words.subList(1, words.size());
        Map<String, String> parameters = Util.parameterize(input);

        switch (keyword) {
        case "list":
            return ListCommandParser.parse(parameters);
        case "add":
            return this.parser
                    .setParserState(new AddState(this.parser))
                    .continueParsing(parameters);
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
        case "calendar":
            if (words.size() != 2) {
                throw new DuchessException("Usage: calendar <date>");
            }
            return new DisplayCalendarCommand(Util.parseToWeekDates(Util.parseDate(words.get(1))));
        case "export":
            if (words.size() != 2) {
                throw new DuchessException("Usage: export <date>");
            }
            return new ExportCommand(Util.parseToWeekDates(Util.parseDate(words.get(1))));
        case "grade":
            try {
                List<String> score = Arrays.asList(parameters.get("general").split("\\\\"));
                int marks = Integer.parseInt(score.get(0));
                int maxMarks = Integer.parseInt(score.get(1));
                List<String> gradeTokens = Arrays.asList(parameters.get("for").split(" "));
                String moduleCode = gradeTokens.get(0);
                String task = gradeTokens.get(1);
                int weightage = Integer.parseInt(parameters.getOrDefault("weightage", "0"));
                return new AddGradeCommand(marks, maxMarks, weightage, task, moduleCode);
            } catch (NumberFormatException | NullPointerException | IndexOutOfBoundsException e) {
                throw new DuchessException("Usage: grade <marks> /weightage <weightage> /for <module> <assessment>\n"
                        + "\te.g. grade 15\\30 /weightage 25 /for CS2113 midterm");
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

    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        throw new DuchessException("An unexpected error occurred while processing your command.");
    }
}
