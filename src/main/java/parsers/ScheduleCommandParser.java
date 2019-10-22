package parsers;

import commands.Command;
import commands.ViewScheCommand;

public class ScheduleCommandParser {
    public static Command parse(String userInput) {
        return new ViewScheCommand(userInput); //TODO
    }
}
