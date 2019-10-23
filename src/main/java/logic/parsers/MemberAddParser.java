package logic.parsers;

import logic.commands.Command;
import logic.commands.MemberAddCommand;
import utils.DukeException;

public class MemberAddParser {
    public static Command parse(String userInput) throws DukeException {
        return new MemberAddCommand(userInput);
    }
}
