package parser;

import command.*;
import exception.DukeException;

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
    public static String fixed = "\\s*/need\\s*";
    public static String taskSeparator = "\\s*\\|\\s*";
    public static String dateSeparator = "\\s*\\&\\s*";
    public static String postpone = "\\s*/to\\s*";
    public static String newLine = "\n";
    public static int windowWidth = 80;
    public static String acceptedExtensions = "txt|csv";
    private static String addCommand;
    private static String helpCommand;
    private static String modCommand;
    private static String printCommand;
    private static String searchCommand;
    //public static String custCommand;
    public static String moduleFormat = "[A-Z]{2,3}[1-9]([0-9]{3}|X{3})[A-Z]{0,1}";
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

        //Empty command check should already have been done outside, in duke
//        if (!temp.hasNext()) {
//            throw new DukeException("Empty Command!");
//        }

        String command = temp.next();
        if (command.matches("list|bye|choices")) {
            if (temp.hasNextLine()) {
                throw new DukeException(command + " should not have any other arguments (whitespace acceptable)");
            } else {
                if (command.matches("list")) {
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
                if (input.matches("help|detail|compare|add|degreelist|swap|replace|delete|clear|custom|bye")) {
                    return new HelpCommand(command, input);
                } else {
                    throw new DukeException("I do not understand that command. "
                            + "Type \"help\" for a full list of available commands");
                }
            }
        } else if (command.matches("todo|deadline|event|done|delete|find|select|recurring|after|within|fixed"
                + "|snooze|schedule|add|remove")) {
            if (!temp.hasNextLine()) {
                throw new DukeException("☹ OOPS!!! The description of a " + command + " cannot be empty.");
            }
            String input = temp.nextLine();
            input = input.strip();
            //System.out.println("input is" + input + "\nCommand is" + command);

            if (input.isBlank()) {
                throw new DukeException("☹ OOPS!!! The description of a " + command + " cannot be empty.");
            } else {
                //add new tasks
                if (command.matches("todo|deadline|event|recurring|after|within|fixed|add")) {
                    return new AddCommand(command, input);
                } else if (command.matches("done|delete|select|snooze|remove")) {
                    return new ModCommand(command, input);
                } else if (command.matches("find")) { //reading task list
                    return new SearchCommand(command, input);
                } else if (command.matches("schedule")) {
                    return new SearchCommand(command, input);
                }
            }
        }
        return new BadCommand("bad", "");
    }

    /*

     * Takes in the taskList to be used for recording tasks.
     * Every time this taskList is updated, it is saved to the save file.
     *
     * @param myList The task list to be used for duke.
     *//*
    //Currently, Parser.Parser will take in the entire raw input and taskList
    public void setTaskList(TaskList myList) {
        this.myList = myList;
    }*/

}