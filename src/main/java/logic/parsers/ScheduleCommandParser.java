package logic.parsers;

import logic.commands.Command;
import logic.commands.ViewScheCommand;

public class ScheduleCommandParser {
    public static Command parse(String userInput) {
        return new ViewScheCommand(userInput); //TODO
    }
}
