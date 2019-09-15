package parser;

import command.*;
import exception.DukeException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Parses the command line from user input to tokens and
 * packages the tokens to <code>Command</code> object.
 */
public class Parser {
    private static String commandType;
    private static String description;
    private static String ddl;
    private static String timePiece;
    private static Matcher m;
    private static int index;

    /**
     * Packages the <code>Command</code> object with commandType and
     * tokens of command information.
     * Returns the packaged command.
     *
     * @param commandType The commandType of the command. e.g. list
     * @param tokens Pieces of information of the command.
     * @return <code>Command</code> object.
     * @throws DukeException If user input is invalid.
     */
    private static Command packageCommand(String commandType, String[] tokens) throws DukeException {
        switch(commandType) {
            case "todo":
                if (tokens.length > 1) {
                    throw new DukeException("Please use other command to store time.");
                } else if (tokens[0].equals("")) {
                    throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
                }
                description = tokens[0];
                return new AddCommand(commandType, description,"","");

            case "deadline":
                if (tokens.length == 1 && !tokens[0].equals("")) {
                    throw new DukeException("☹ OOPS!!! The time of a deadline cannot be empty.");
                } else if(tokens[0].equals("")) {
                    throw new DukeException("☹ OOPS!!! The description of a deadline cannot be empty.");
                }
                description = tokens[0];
                ddl = tokens[1];
                return new AddCommand(commandType, description, ddl, "");

            case "event":
                if (tokens.length == 1 && !tokens[0].equals("")) {
                    throw new DukeException("☹ OOPS!!! The time of an event cannot be empty.");
                } else if(tokens[0].equals("")) {
                    throw new DukeException("☹ OOPS!!! The description of an event cannot be empty.");
                }
                description = tokens[0];
                timePiece= tokens[1];
                return new AddCommand(commandType, description, "", timePiece);
            case "list":
                if(!tokens[0].equals("")) {
                    throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
                return new ListCommand();
            case "done":
                if (tokens[0].equals("")) {
                    throw new DukeException("☹ OOPS!!! The index cannot be empty.");
                }
                try {
                    index = Integer.parseInt(tokens[0]);
                } catch(NumberFormatException e) {
                    throw new DukeException("☹ OOPS!!! The index should be numerical.");
                }
                return new DoneCommand(index-1); // 0-based
            case "delete":
                if (tokens[0].equals("")) {
                    throw new DukeException("☹ OOPS!!! The index cannot be empty.");
                }
                try {
                    index = Integer.parseInt(tokens[0]);
                } catch(NumberFormatException e) {
                    throw new DukeException("☹ OOPS!!! The index should be numerical.");
                }
                return new DeleteCommand(index-1); // 0-based
            case "find":
                if(tokens[0].equals("") || tokens.length > 1) {
                    throw new DukeException("☹ OOPS!!! I don't what to find.");
                }
                return new FindCommand(tokens[0]);

            case "bye":
                if(!tokens[0].equals("")) {
                    throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
                return new ExitCommand();
            default:
                throw new DukeException("Unknown error!");
        }

    }

    /**
     * Converts the <code>String</code> fullCommand into <code>Command</code> object,
     * by using <code>packageCommand</code> method.
     * Returns the <code>Command</code> object.
     *
     * @param fullCommand The command line read from user input.
     * @return <code>Command</code> object converted from fullCommand.
     * @throws DukeException If user input is invalid.
     */
    public static Command parse(String fullCommand) throws DukeException {
        String regex = "todo|deadline|event|list|done|bye|delete|find";
        m = Pattern.compile(regex).matcher(fullCommand); // AddCommmand
        if(m.find()) {
            commandType = m.group();
            fullCommand = m.replaceFirst("").trim();
            String[] tokens = fullCommand.split("/by|/at");
            for(int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
            }
            return packageCommand(commandType, tokens);
        } else {
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
