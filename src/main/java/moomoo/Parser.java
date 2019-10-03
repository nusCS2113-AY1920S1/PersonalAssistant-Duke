package moomoo;

import java.util.Scanner;

public class Parser {

    /**
     * Processes command from user input.
     * @param command user's input
     * @return recognized command
     * @throws MooMooException user input is not a valid command
     */
    public static Command parseCommand(String command) throws MooMooException {
        Scanner scanner = new Scanner(command);
        String commandType = scanner.next();
        switch (commandType) {
        case ("bye"): return new ByeCommand();
        default: throw new MooMooException("Sorry I did not recognize that command.");
        }
    }
}
