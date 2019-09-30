package duke.parser;

import duke.command.*;
import duke.commons.DukeException;
import duke.commons.Message;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse user input into commands.
 */
public class Parser {
    private static final Set<String> reservedParameters = new HashSet<>(Arrays.asList(
            "line", "primary", "secondary", "cmd"
    ));
    private static final String COMMAND_UNDO = "undo";
    private static final String COMMAND_REDO = "redo";
    private static final String COMMAND_ORDER = "order";
    private static final String COMMAND_ORDER_ADD = "add";
    private static final String COMMAND_ORDER_DELETE = "remove";
    private static final String COMMAND_ORDER_EDIT = "edit";
    private static final String COMMAND_ORDER_COMPLETE = "done";
    private static final String COMMAND_SHORTCUT = "short";

    /**
     * Parses user input into a <code>Command</code> object.
     *
     * @param line the user input.
     * @return the command from user input.
     * @throws DukeException if input is not valid command or command parameters are invalid.
     */
    public static Command getCommand(String line, Map<String, ExecuteShortcutCommand> shortcuts) throws DukeException {

        if (shortcuts.containsKey(line.strip())) {
            return shortcuts.get(line.strip());
        }

        Map<String, List<String>> params = parseCommandAndParams(line);
        String commandWord = params.get("cmd").get(0);

        switch (commandWord) {
            case COMMAND_ORDER:
                return parseOrder(line);
            case COMMAND_UNDO:
                return parseUndo(line);
            case COMMAND_REDO:
                return parseRedo(line);
            case COMMAND_SHORTCUT:
                return new SetShortcutCommand(line);
        default:
            throw new DukeException(Message.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private static Map<String, List<String>> parseCommandAndParams(String line) throws DukeException {
        Map<String, List<String>> params = new HashMap<>();

        addToParameter("line", line, params);
        parseCommandAndPrimaryAndSecondary(line, params);
        parseParameterBlocks(line, params);

        return params;
    }

    private static void parseCommandAndPrimaryAndSecondary(String line, Map<String, List<String>> params)
            throws DukeException {
        //Regex to get the command word and the sub command, and primary parameter.
        Pattern commandWordPattern = Pattern.compile("^(\\w+)\\s*(\\w+)?\\s*([^-]+)?");
        Matcher commandWordMatcher = commandWordPattern.matcher(line);
        if (!commandWordMatcher.find()) {
            throw new DukeException("Please enter a command");
        }

        //In "order remove 1", "order" is "cmd".
        addToParameter("cmd", commandWordMatcher.group(1).strip(), params);

        if (commandWordMatcher.group(2) != null) {

            //In "order remove 1", "remove" is "primary".
            addToParameter("primary", commandWordMatcher.group(2).strip(), params);

            if (commandWordMatcher.group(3) != null) {
                //In "order remove 1", "1" is "secondary".
                addToParameter("secondary", commandWordMatcher.group(3).strip(), params);
            }
        }
    }

    private static void parseParameterBlocks(String line, Map<String, List<String>> params) throws DukeException {
        //Regex to get each parameter block. e.g. "-at some place" is one command block.
        Pattern paramsPattern = Pattern.compile("(-\\w+ [^-]+|-\\w+)");
        Matcher paramsMatcher = paramsPattern.matcher(line);

        while (paramsMatcher.find()) {
            String s = paramsMatcher.group().strip();
            if (s.isEmpty() || s.isBlank()) {
                continue;
            }

            //Regex to get parameter and value.
            // e.g. in "-at some place", "at" is the parameter and "some place" is the value.
            Pattern attrAndValuePattern = Pattern.compile("-(\\w+) ([^-]+)|-(\\w+)");
            Matcher attrAndValueMatcher = attrAndValuePattern.matcher(s);

            if (!attrAndValueMatcher.find()) {
                throw new DukeException("Please enter valid parameters");
            }

            if (attrAndValueMatcher.group(2) == null) {
                if (reservedParameters.contains(attrAndValueMatcher.group(3))) {
                    throw new DukeException("Parameters contain reserved words");
                }
                if (!params.containsKey(attrAndValueMatcher.group(3))) {
                    addToParameter(attrAndValueMatcher.group(3), "", params);
                } else {
                    params.get(attrAndValueMatcher.group(3)).add("");
                }
            } else {
                if (reservedParameters.contains(attrAndValueMatcher.group(1))) {
                    throw new DukeException("Parameters contain reserved words");
                }
                if (!params.containsKey(attrAndValueMatcher.group(1))) {
                    addToParameter(attrAndValueMatcher.group(1).strip(), attrAndValueMatcher.group(2), params);
                } else {
                    params.get(attrAndValueMatcher.group(1).strip()).add(attrAndValueMatcher.group(2));
                }
            }
        }

    }

    private static void addToParameter(String key, String value, Map<String, List<String>> params) {
        params.put(key, new ArrayList<String>() {
            {
                add(value);
            }
        });
    }
    private static Command parseOrder(String line) throws DukeException {
        Map<String, List<String>> params = parseCommandAndParams(line);
        assert params.size() > 0;
        switch (params.get("primary").get(0)) {
            case COMMAND_ORDER_ADD:
                return CommandParser.parseOrderAdd(params);
            case COMMAND_ORDER_DELETE:
                return CommandParser.parseOrderDelete(params);
            case COMMAND_ORDER_EDIT:
                return CommandParser.parseOrderEdit(params);
            case COMMAND_ORDER_COMPLETE:
                return new CompleteOrderCommand(params);
            default:
                throw new DukeException("Invalid command");
        }
    }

    private static Command parseUndo(String line) throws DukeException {
        return new UndoCommand();
    }

    private static Command parseRedo(String line) throws DukeException {
        return new RedoCommand();
    }
}

