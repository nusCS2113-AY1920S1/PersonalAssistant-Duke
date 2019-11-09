package parser;

import command.*;
import exception.DukeException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Parser class.
 * Handles Raw Input and determines if it is a valid command.
 * If not a valid command, it will return a BadCommand.
 */
public class Parser {
    public static String deadline = "\\s*/by\\s*";
    public static String event = "\\s*/at\\s*";
    public static String recurring = "\\s*/every\\s*";
    public static String after = "\\s*/after\\s*";
    public static String within = "\\s*/between\\s*";
    public static String priority = "\\s*/priority\\s*";
    public static String taskSeparator = "\\s*\\|\\s*";
    public static String dateSeparator = "\\s*\\&\\s*";
    public static String postpone = "\\s*/to\\s*";
    //public static String swap = "\\s+\\s+\\s+";
    public static String newLine = "\n";
    public static int windowWidth = 80;
    public static String acceptedExtensions = "txt|csv";
    public static String moduleFormat = "[A-Z]{2,3}[1-9]([0-9]{3}|X{3})[A-Z]{0,1}";
    public static final Map<String, String> userPriorityMap;
    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("very high", "Very High");
        aMap.put("high", "High");
        aMap.put("normal", "Normal");
        aMap.put("low", "Low");
        userPriorityMap = Collections.unmodifiableMap(aMap);
    }


    Parser() {
    }

    /**
     * Returns the appropiate command based on line input.
     *
     * @param line String which is the next line of input.
     * @return Command is of the appropriate type.
     * @throws DukeException throws when incorrect number of arguments passed.
     */
    public static Command parse(String line) throws DukeException {
        Scanner temp = new Scanner(line);


        if (!temp.hasNext()) {
            throw new DukeException("Empty Command!");
        }

        String command = temp.next();
        if (command.matches("tasks|bye|choices")) {
            if (temp.hasNextLine()) {
                throw new DukeException(command + " should not have any other arguments (whitespace acceptable)");
            } else {
                if (command.matches("tasks")) {
                    return new PrintCommand(command) {
                    };
                } else if (command.matches("bye")) {
                    return new ExitCommand();
                } else if (command.matches("choices")) {
                    return new PrintCommand(command);
                }
            }
        } else if (command.matches("help")) {
            if (!temp.hasNextLine()) { //if the user wants to display all commands
                return new HelpCommand(command);
            } else { //if the user wants to display help for only one command
                String input = temp.nextLine();
                input = input.strip();
                if (input.matches("help|detail|compare|add|degreelist|swap|replace|delete|clear|custom"
                        + "|bye|undo|redo")) {
                    return new HelpCommand(command, input);
                } else {
                    throw new DukeException("I do not understand that command. "
                            + "Type \"help\" for a full list of available commands");
                }
            }

        } else if (command.matches("todo|deadline|event|done|delete|find|select"
                + "|snooze|schedule|add|remove|swap|sort|detail|compare|view_employment|cohort_size")) {
            if (!temp.hasNextLine() && command.matches("detail")) {
                throw new DukeException("You can try \" detail come \" to show information on Computer Engineering!");
            } else if (!temp.hasNextLine() && command.matches("compare")) {
                throw new DukeException("You can try \" compare bme come \" to show differences between "
                        + "Biomedical Engineering and Computer Engineering!");
            } else if (!temp.hasNextLine()) {
                throw new DukeException("OOPS!!! The description of a " + command + " cannot be empty.");
            }
                String input = temp.nextLine();
            input = input.strip();
            //System.out.println("input is" + input + "\nCommand is" + command);

            if (input.isBlank()) {
                throw new DukeException("OOPS!!! The description of a " + command + " cannot be empty.");
            } else {
                //add new tasks
                if (command.matches("todo|deadline|event|add")) {
                    return new AddCommand(command, input);
                } else if (command.matches("done|delete|select|snooze|remove")) {
                    return new ModCommand(command, input);
                } else if (command.matches("find|schedule")) { //reading task list
                    return new SearchCommand(command, input);
                } else if (command.matches("detail|compare")) {
                    return new PrintCommand(command, input);
                } else if (command.matches("swap")) {
                    return new SwapCommand(command, input);
                } else if (command.matches("sort")) {
                    return new SortCommand(command, input);
                } else if (command.matches("view_employment|cohort_size")) {
                    return new ViewCommand(command, input);
                }
            }
        }
        return new BadCommand("bad", "");
    }
}