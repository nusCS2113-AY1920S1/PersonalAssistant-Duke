package parser;

import command.Command;
import command.AddCommand;
import command.BadCommand;
import command.ExitCommand;
import command.ModCommand;
import command.PrintCommand;
import command.SearchCommand;
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
        if (command.matches("list|bye")) {
            if (temp.hasNextLine()) {
                throw new DukeException("List should not have any other arguments (whitespace acceptable");
            } else {
                if (command.matches("list")) {
                    return new PrintCommand() {
                    };
                } else if (command.matches("bye")) {
                    return new ExitCommand();
                }
            }
        } else if (command.matches("todo|deadline|event|done|delete|find|select|recurring|after|within|fixed"
                + "|snooze|schedule")) {
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
                if (command.matches("todo|deadline|event|recurring|after|within|fixed")) {
                    return new AddCommand(command, input);
                } else if (command.matches("done|delete|select|snooze")) {
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
     *//**
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
