package moomoo.task;

import moomoo.command.BudgetCommand;
import moomoo.command.Command;
import moomoo.command.ExitCommand;
import moomoo.command.GraphCommand;

/**
 * Takes in a string and parses it to return a valid command to be ran.
 */
public class Parser {
    /**
     * Takes in input from user and returns a command based on the input given.
     * @param input String given by the user
     * @return The command object corresponding to the user input
     * @throws MooMooException Thrown when an invalid input is given
     */
    public static Command parse(String input) throws MooMooException {
        if (input.equals("bye")) {
            return new ExitCommand(true, "");
        } else if (input.startsWith("budget")) {
            return new BudgetCommand(false, input);
        } else if (input.startsWith("graph")) {
            return new GraphCommand(input);
        } else {
            throw new MooMooException("OOPS!!! I'm sorry, but I don't know what that means :(");
        }
    }
}
