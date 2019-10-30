package duchess.parser.states;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.ByeCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.logic.commands.DoneCommand;
import duchess.logic.commands.FindCommand;
import duchess.logic.commands.HistoryCommand;
import duchess.logic.commands.RedoCommand;
import duchess.logic.commands.ReminderCommand;
import duchess.logic.commands.SnoozeCommand;
import duchess.logic.commands.UndoCommand;
import duchess.logic.commands.ViewScheduleCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.commands.CalendarCommandParser;
import duchess.parser.commands.DeleteCommandParser;
import duchess.parser.commands.LessonCommandParser;
import duchess.parser.commands.ListCommandParser;
import duchess.parser.states.add.AddState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DefaultState extends ParserState {
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

        if ("list".equals(keyword)) {
            return ListCommandParser.parse(parameters);
        } else if ("add".equals(keyword)) {
            return this.parser
                    .setParserState(new AddState(this.parser))
                    .continueParsing(parameters);
        } else if ("find".equals(keyword)) {
            return new FindCommand(arguments);
        } else if ("delete".equals(keyword)) {
            return DeleteCommandParser.parse(parameters);
        } else if ("done".equals(keyword)) {
            try {
                return new DoneCommand(Integer.parseInt(arguments.get(0)) - 1);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw new DuchessException("Please supply a number. Eg: done 2");
            }
        } else if ("reminder".equals(keyword)) {
            return new ReminderCommand();
        } else if ("snooze".equals(keyword)) {
            return new SnoozeCommand(arguments);
        } else if ("schedule".equals(keyword)) {
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
        } else if ("calendar".equals(keyword)) {
            return CalendarCommandParser.parse(parameters);
        } else if ("lesson".equals(keyword)) {
            return LessonCommandParser.parse(parameters);
        } else if ("bye".equals(keyword)) {
            return new ByeCommand();
        } else if ("history".equals(keyword)) {
            return new HistoryCommand();
        } else if ("undo".equals(keyword)) {
            return new UndoCommand(arguments);
        } else if ("redo".equals(keyword)) {
            return new RedoCommand(arguments);
        } else if ("exit".equals(keyword)) {
            return new DisplayCommand("Operation aborted.");
        } else {
            throw new DuchessException("Please enter a valid command.");
        }
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        throw new DuchessException("An unexpected error occurred while processing your command.");
    }
}
