package duke.parser;

import duke.command.*;
import duke.commons.DukeException;
import duke.commons.Message;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse user input into commands.
 */
public class Parser {

    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SEARCH = "find";

    /**
     * Parse user input.
     *
     * @param line user input.
     * @return the duke.command from user input.
     * @throws DukeException if it is not valid duke.command or duke.command parameters are invalid.
     */
    public static Command getCommand(String line) throws DukeException {

        Dictionary<String, String> params = parseCommandAndParams(line);

        String commandWord = params.get("cmd");

        switch (commandWord) {
            case COMMAND_TODO:
                return parseTodo(line);
            case COMMAND_DEADLINE:
                return parseDeadline(line);
            case COMMAND_EVENT:
                return parseEvent(line);
            case COMMAND_LIST:
                return parseList(line);
            case COMMAND_DONE:
                return parseDone(line);
            case COMMAND_BYE:
                return parseExit(line);
            case COMMAND_DELETE:
                return parseDeletion(line);
            case COMMAND_SEARCH:
                return parseSearch(line);
        }

        throw new DukeException(Message.MESSAGE_UNKNOWN_COMMAND);
    }

    private static Dictionary<String, String> parseCommandAndParams(String line) throws DukeException {
        Dictionary<String, String> params = new Hashtable<>();

        Pattern commandWordPattern = Pattern.compile("^(\\w+)(\\s+[^/]+)?");
        Matcher commandWordMatcher = commandWordPattern.matcher(line);
        if (!commandWordMatcher.find()) {
            throw new DukeException("Please enter a duke.command");
        }
        params.put("cmd", commandWordMatcher.group(1).strip());
        if (commandWordMatcher.group(2) != null) {
            params.put("primary", commandWordMatcher.group(2).strip());
        }

        Pattern paramsPattern = Pattern.compile("(/\\w+ [^/]+|/\\w+)");
        Matcher paramsMatcher = paramsPattern.matcher(line);

        while (paramsMatcher.find()) {
            String s = paramsMatcher.group().strip();
            if (s.isEmpty() || s.isBlank()) continue;
            Pattern attrAndValuePattern = Pattern.compile("/(\\w+) ([^/]+)|/(\\w+)");
            Matcher attrAndValueMatcher = attrAndValuePattern.matcher(s);
            if (!attrAndValueMatcher.find()) {
                throw new DukeException("Please enter valid parameters");
            }

            if (attrAndValueMatcher.group(2) == null) {
                params.put(attrAndValueMatcher.group(3), "");
            } else {
                params.put(attrAndValueMatcher.group(1), attrAndValueMatcher.group(2));
            }


        }

        return params;
    }

    private static Command parseTodo(String line) throws DukeException {
        Dictionary<String, String> args = parseCommandAndParams(line);
        Todo todo = new Todo(args.get("primary"));

        if (args.get("primary") == null) {
            throw new DukeException("Please enter todo description");
        }

        return new AddCommand(todo);
    }

    private static Command parseDeadline(String line) throws DukeException {
        Dictionary<String, String> args = parseCommandAndParams(line);
        if (args.get("primary") == null) {
            throw new DukeException("Please enter deadline description");
        }
        if (args.get("by") == null) {
            throw new DukeException("Please enter deadline date");
        }

        Deadline ddl = new Deadline(args.get("primary"), TimeParser.convertStringToDate(args.get("by")));
        return new AddCommand(ddl);
    }

    private static Command parseEvent(String line) throws DukeException {
        Dictionary<String, String> args = parseCommandAndParams(line);

        if (args.get("primary") == null) {
            throw new DukeException("Please enter event description");
        }
        if (args.get("at") == null) {
            throw new DukeException("Please enter event date");
        }

        String[] dates = args.get("at").strip().split("to");
        if (dates.length < 2 || dates[0].equals("") || dates[1].equals("")) {
            throw new DukeException("Please enter event date");
        }

        Event evt = new Event(args.get("primary"), TimeParser.convertStringToDate(dates[0].strip()), TimeParser.convertStringToDate(dates[1].strip()));
        return new AddCommand(evt);
    }

    private static Command parseList(String line) {
        return new ListCommand();
    }

    private static Command parseDone(String line) throws DukeException {
        Dictionary<String, String> args = parseCommandAndParams(line);
        try {
            int index = Integer.parseInt(args.get("primary"));
            return new DoneCommand(index);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DukeException("Please enter a valid index number");
        }
    }

    private static Command parseExit(String line) {
        return new ExitCommand();
    }

    private static Command parseDeletion(String line) throws DukeException {
        Dictionary<String, String> args = parseCommandAndParams(line);
        try {
            int index = Integer.parseInt(args.get("primary"));
            return new DeleteCommand(index);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DukeException("Please enter a valid index number");
        }
    }

    private static Command parseSearch(String line) throws DukeException {
        Dictionary<String, String> args = parseCommandAndParams(line);
        if (args.get("primary") == null) {
            throw new DukeException("Please enter a keyword");
        }
        return new FindCommand(args.get("primary"));
    }
}
