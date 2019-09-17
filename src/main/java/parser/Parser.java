package parser;


import command.AddCommand;
import command.Command;
import command.DeleteCommand;
import command.DoneCommand;
import command.ExitCommand;
import command.FindCommand;
import command.ListCommand;
import command.RemindCommand;
import command.ScheduleCommand;
import exception.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @param commandType   The commandType of the command. e.g. list
     * @param commandParams Pieces of information of the command.
     * @return <code>Command</code> object.
     * @throws DukeException If user input is invalid.
     */
    private static Command packageCommand(String commandType, CommandParams commandParams) throws DukeException {
        switch (commandType) {
        case "todo": /*
            if (tokens.length > 1) {
                throw new DukeException("Please use other command to store time.");
            } else if (tokens[0].equals("")) {
                throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
            }
            description = tokens[0];
            return new AddCommand(commandType, description,"","");
            */
        case "deadline":
            /*
            if (tokens.length == 1 && !tokens[0].equals("")) {
                throw new DukeException("☹ OOPS!!! The time of a deadline cannot be empty.");
            } else if(tokens[0].equals("")) {
                throw new DukeException("☹ OOPS!!! The description of a deadline cannot be empty.");
            }
            description = tokens[0];
            ddl = tokens[1];
            return new AddCommand(commandType, description, ddl, "");
            */
        case "event":
            /*
            if (tokens.length == 1 && !tokens[0].equals("")) {
                throw new DukeException("☹ OOPS!!! The time of an event cannot be empty.");
            } else if(tokens[0].equals("")) {
                throw new DukeException("☹ OOPS!!! The description of an event cannot be empty.");
            }
            description = tokens[0];
            timePiece= tokens[1];
            return new AddCommand(commandType, description, "", timePiece);
            */
            return new AddCommand(commandParams);
        case "list":
            /*
            if(!tokens[0].equals("")) {
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            return new ListCommand();
            */
            return new ListCommand(commandParams);
        case "done":
            /*
            if (tokens[0].equals("")) {
                throw new DukeException("☹ OOPS!!! The index cannot be empty.");
            }
            try {
                index = Integer.parseInt(tokens[0]);
            } catch(NumberFormatException e) {
                throw new DukeException("☹ OOPS!!! The index should be numerical.");
            }
            return new DoneCommand(index-1); // 0-based

            */
            return new DoneCommand(commandParams);
        case "delete":
            /*
            if (tokens[0].equals("")) {
                throw new DukeException("☹ OOPS!!! The index cannot be empty.");
            }
            try {
                index = Integer.parseInt(tokens[0]);
            } catch(NumberFormatException e) {
                throw new DukeException("☹ OOPS!!! The index should be numerical.");
            }
            return new DeleteCommand(index-1); // 0-based
            */
            return new DeleteCommand(commandParams);
        case "find":
            /*
            if(tokens[0].equals("") || tokens.length > 1) {
                throw new DukeException("☹ OOPS!!! I don't what to find.");
            }
            return new FindCommand(tokens[0]);
            */
            return new FindCommand(commandParams);
        case "bye":
            /*
            if(!tokens[0].equals("")) {
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            return new ExitCommand();
            */
            return new ExitCommand(commandParams);
        case "schedule":
            return new ScheduleCommand(commandParams);
        case "remind":
            return new RemindCommand(commandParams);
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
        String regex = "todo|deadline|event|list|done|bye|delete|find|schedule|remind";
        m = Pattern.compile(regex).matcher(fullCommand);
        if (m.find()) {
            commandType = m.group();
            return packageCommand(commandType, new CommandParams(fullCommand));
        } else {
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
