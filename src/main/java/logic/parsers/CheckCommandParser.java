package logic.parsers;

import logic.commands.CheckAnomaliesCommand;
import logic.commands.Command;

public class CheckCommandParser {
    public static Command parse(String userInput) {
        return new CheckAnomaliesCommand();// TODO
    }
}
