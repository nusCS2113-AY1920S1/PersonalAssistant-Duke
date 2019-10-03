package MooMoo;

import java.util.Scanner;

public class Parser {

    static public boolean isExit = false;

    public static Command parseCommand(String command) throws MooMooException {
        Scanner scanner = new Scanner(command);
        String commandType = scanner.next();
        switch (commandType) {
        case ("bye"): return new ByeCommand();
        default: throw new MooMooException("Sorry I did not recognize that command.");
        }
    }
}
