package optix.parser;

import optix.commands.AddCommand;
import optix.commands.Command;

public class Parser {
    public static Command parse(String fullCommand) {
        String[] splitStr = fullCommand.trim().split(" ", 2);

        switch (splitStr[0].toLowerCase()) {
        case "add":
            return parseAddShow(splitStr[1]);
        default:
            return null;
        }
    }

    private static Command parseAddShow(String showDetails) {
        String[] splitStr = showDetails.trim().split("\\|", 3);
        return new AddCommand(splitStr[0].trim(), splitStr[1].trim(), Double.parseDouble(splitStr[2]));
    }
}
