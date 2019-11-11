package duchess.parser.states;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.ByeCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
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
import duchess.parser.commands.DoneCommandParser;
import duchess.parser.commands.LessonCommandParser;
import duchess.parser.commands.ListCommandParser;
import duchess.parser.states.add.AddState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DefaultState extends ParserState {
    private static final String LIST_KEYWORD = "list";
    private static final String USAGE_SCHEDULE_DATE_DAY_WEEK = "Usage: schedule <date> (day | week)";
    private static final String ADD_KEYWORD = "add";
    private static final String FIND_KEYWORD = "find";
    private static final String DELETE_KEYWORD = "delete";
    private static final String DONE_KEYWORD = "done";
    private static final String REMINDER_KEYWORD = "reminder";
    private static final String SNOOZE_KEYWORD = "snooze";
    private static final String SCHEDULE_KEYWORD = "schedule";
    private static final String DAY_KEYWORD = "day";
    private static final String WEEK_KEYWORD = "week";
    private static final String CALENDAR_KEYWORD = "calendar";
    private static final String LESSON_KEYWORD = "lesson";
    private static final String BYE_KEYWORD = "bye";
    private static final String HISTORY_KEYWORD = "history";
    private static final String UNDO_COMMAND = "undo";
    private static final String REDO_KEYWORD = "redo";
    private static final String EXIT_KEYWORD = "exit";
    private static final String ABORTED_MSG = "Operation aborted.";
    private static final String INVALID_COMMAND_MSG = "Please enter a valid command.";
    private static final String PARSING_ERROR_MSG = "An unexpected error occurred while processing your command.";

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

        if (LIST_KEYWORD.equals(keyword)) {
            return ListCommandParser.parse(parameters);
        } else if (ADD_KEYWORD.equals(keyword)) {
            return this.parser
                    .setParserState(new AddState(this.parser))
                    .continueParsing(parameters);
        } else if (FIND_KEYWORD.equals(keyword)) {
            return new FindCommand(arguments);
        } else if (DELETE_KEYWORD.equals(keyword)) {
            return DeleteCommandParser.parse(parameters);
        } else if (DONE_KEYWORD.equals(keyword)) {
            return DoneCommandParser.parse(parameters);
        } else if (REMINDER_KEYWORD.equals(keyword)) {
            return new ReminderCommand();
        } else if (SNOOZE_KEYWORD.equals(keyword)) {
            return new SnoozeCommand(arguments);
        } else if (SCHEDULE_KEYWORD.equals(keyword)) {
            try {
                String view = words.get(2);
                boolean isInvalidView = !view.equals(DAY_KEYWORD) && !view.equals(WEEK_KEYWORD);
                boolean isIllegalArgument = isInvalidView && (words.size() > 3);
                if (isIllegalArgument) {
                    throw new IllegalArgumentException();
                }
                String date = words.get(1);
                return new ViewScheduleCommand(date, view);
            } catch (IndexOutOfBoundsException e) {
                throw new DuchessException(USAGE_SCHEDULE_DATE_DAY_WEEK);
            }
        } else if (CALENDAR_KEYWORD.equals(keyword)) {
            return CalendarCommandParser.parse(parameters);
        } else if (LESSON_KEYWORD.equals(keyword)) {
            return LessonCommandParser.parse(parameters);
        } else if (BYE_KEYWORD.equals(keyword)) {
            return new ByeCommand();
        } else if (HISTORY_KEYWORD.equals(keyword)) {
            return new HistoryCommand();
        } else if (UNDO_COMMAND.equals(keyword)) {
            return new UndoCommand(arguments);
        } else if (REDO_KEYWORD.equals(keyword)) {
            return new RedoCommand(arguments);
        } else if (EXIT_KEYWORD.equals(keyword)) {
            return new DisplayCommand(ABORTED_MSG);
        } else {
            throw new DuchessException(INVALID_COMMAND_MSG);
        }
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        throw new DuchessException(PARSING_ERROR_MSG);
    }
}
