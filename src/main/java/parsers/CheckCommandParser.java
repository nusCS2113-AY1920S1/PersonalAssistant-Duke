package parsers;

import commands.CheckAnomaliesCommand;
import commands.Command;

public class CheckCommandParser {
    public static Command parse(String userInput) {
        return new CheckAnomaliesCommand();// TODO
    }
}
