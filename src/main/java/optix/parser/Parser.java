package optix.parser;

import optix.commands.AddCommand;
import optix.commands.ByeCommand;
import optix.commands.Command;
import optix.commands.ListCommand;
import optix.commands.PostponeCommand;

public class Parser {
    public static Command parse(String fullCommand) {
        String[] splitStr = fullCommand.trim().split(" ", 2);

        switch (splitStr[0].toLowerCase()) {
        case "postpone":
            return parsePostpone(splitStr[1]);
        case "list":
            return new ListCommand();
        case "bye":
            return new ByeCommand();
        case "add":
            return parseAddShow(splitStr[1]);
        default:
            return null;
        }
    }

    private static Command parsePostpone(String postponeDetails) {
        String[] splitStr = postponeDetails.trim().split("\\|", 3);
        // need to check if size of array is 3, if not throw exception
        String showName = splitStr[0].trim();
        String oldDate = splitStr[1].trim();
        String newDate = splitStr[2].trim();

        return new PostponeCommand(showName, oldDate, newDate);
    }

    private static Command parseAddShow(String showDetails) {
        String[] splitStr = showDetails.trim().split("\\|", 3);
        // need to check if size of array is 3, if not throw exception
        String showName = splitStr[0].trim();
        String showDate = splitStr[1].trim();
        // need to add a NumberFormatException here
        double showCost = Double.parseDouble(splitStr[2]);

        return new AddCommand(showName, showDate, showCost);
    }
}
