package duke.parser;

import duke.command.Command;
import duke.command.RedoCommand;
import duke.command.UndoCommand;
import duke.commons.DukeException;
import duke.commons.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse user input into commands.
 */
public class Parser {

    private static final String COMMAND_UNDO = "undo";
    private static final String COMMAND_REDO = "redo";
    private static final String COMMAND_ORDER = "order";
    private static final String COMMAND_ORDER_ADD = "add";
    private static final String COMMAND_ORDER_DELETE = "remove";
    private static final String COMMAND_ORDER_EDIT = "edit";

    ///////////////////////////////////////////////////////////
    private static final String COMMAND_RECIPE = "recipe";
    private static final String COMMAND_RECIPE_ADD = "recipeAdd";

    /**
     * Parses user input into a command.
     *
     * @param line the user input.
     * @return the command from user input.
     * @throws DukeException if it is not valid command or command parameters are invalid.
     */
    public static Command getCommand(String line) throws DukeException {
        Map<String, List<String>> params = parseCommandAndParams(line);
        String commandWord = params.get("cmd").get(0);

        switch (commandWord) {
        case COMMAND_ORDER:
            return parseOrder(line);
        case COMMAND_UNDO:
            return parseUndo(line);
        case COMMAND_REDO:
            return parseRedo(line);

        case COMMAND_RECIPE:
            return parseRecipe(line);

        default:
            throw new DukeException(Message.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private static Map<String, List<String>> parseCommandAndParams(String line) throws DukeException {
        Map<String, List<String>> params = new HashMap<>();

        //Regex to get the command word and the sub command, and primary parameter.
        Pattern commandWordPattern = Pattern.compile("^(\\w+)\\s*(\\w+)?\\s*([^-]+)?");
        Matcher commandWordMatcher = commandWordPattern.matcher(line);
        if (!commandWordMatcher.find()) {
            throw new DukeException("Please enter a command");
        }

        params.put("cmd", new ArrayList<String>() {
            {
                add(commandWordMatcher.group(1).strip());
            }
        });

        if (commandWordMatcher.group(2) != null) {
            params.put("primary", new ArrayList<String>() {
                {
                    add(commandWordMatcher.group(2).strip());
                }
            });

            if (commandWordMatcher.group(3) != null) {
                params.put("secondary", new ArrayList<String>() {
                    {
                        add(commandWordMatcher.group(3).strip());
                    }
                });
            }
        }

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
                if (!params.containsKey(attrAndValueMatcher.group(3))) {
                    params.put(attrAndValueMatcher.group(3), new ArrayList<>() {
                        {
                            add("");
                        }
                    });
                } else {
                    params.get(attrAndValueMatcher.group(3)).add("");
                }
            } else {
                if (!params.containsKey(attrAndValueMatcher.group(1))) {
                    params.put(attrAndValueMatcher.group(1).strip(), new ArrayList<>() {
                        {
                            add(attrAndValueMatcher.group(2));
                        }
                    });
                } else {
                    params.get(attrAndValueMatcher.group(1).strip()).add(attrAndValueMatcher.group(2));
                }
            }
        }

        return params;
    }

    public static Map<String, List<String>> componentParser(String line) {
        Map<String, List<String>> params = new HashMap<>();
        // (\\w) one or more word character: for the four features: recipe, order...
        //0 or more white space
        //(\\w+)?: zero or one word: for add delete edit ...

        //this is for Recipe add stuff
        Pattern componentPattern = Pattern.compile("(\\w+)\\s*(\\w+)?\\s*([^-]+)");
        Matcher componentMatcher = componentPattern.matcher(line);

        //cmd
        params.put("cmd", new ArrayList<String>() {
            {
                add(componentMatcher.group(1).strip());
            }
        });
        //primary edit
        if (componentMatcher.group(2) != null) {
            params.put("primary", new ArrayList<String>() {
                {
                    add(componentMatcher.group(2).strip());
                }
            });

            if (componentMatcher.group(3) != null) {
                params.put("secondary", new ArrayList<String>() {
                    {
                        add(componentMatcher.group(3).strip());
                    }
                });
            }
        }

        Pattern paramsPattern = Pattern.compile("(-\\w+ [^-]+|-\\w+)");
        Matcher paramsMatcher = paramsPattern.matcher(line);

        while (paramsMatcher.find()) {
            String s = paramsMatcher.group().strip();
            if (s.isEmpty() || s.isBlank()) {
                continue;
            }

            //Regex to get parameter and value
            // "" | matches -at or -at some place
            //group 2 is "some place"
            Pattern attrAndValuePattern = Pattern.compile("-(\\w+) ([^-]+)|-(\\w+)");
            Matcher attrAndValueMatcher = attrAndValuePattern.matcher(s);

            // if only -at, instead of -at some place
            if(attrAndValueMatcher.group(2) == null) {
                if (!params.containsKey(attrAndValueMatcher.group(3))) {
                    params.put(attrAndValueMatcher.group(3), new ArrayList<>() {
                        {
                            add(""); //add a empty string;
                        }
                    });
                } else { // if it contains key, don't use put method, use add method.
                    params.get(attrAndValueMatcher).add("");
                }
            } else { //there is a some place
                if (!params.containsKey(attrAndValueMatcher.group(1))) {
                    params.put(attrAndValueMatcher.group(1).strip(), new ArrayList<>() {
                        {
                            add(attrAndValueMatcher.group(2));
                        }
                    });
                } else {
                    params.get(attrAndValueMatcher.group(1).strip()).add(attrAndValueMatcher.group(2));
                }
            }
        }
        return params;
    } // params all stored in the Hash map.

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
            default:
                throw new DukeException("Invalid command");
        }
    }

    private static Command parseRecipe(String line) throws DukeException {
        Map<String, List<String>> params = parseCommandAndParams(line);
        assert params.size() > 0;
        switch (params.get("primary").get(0)) {
        case "add":
            return CommandParser.parseRecipeAdd(params);
        case "delete":
            return CommandParser.parseRecipeDelete(params);

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

