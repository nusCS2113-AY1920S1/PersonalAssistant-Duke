package logic.parser;

import common.DukeException;
import logic.command.MatchCommand;

import java.util.HashMap;

//@@author JustinChia1997
public class MatchCommandParser {
    /**
     * Parses match command
     *
     * @param userInput desired class
     */
    public static MatchCommand parseMatch(String userInput) throws DukeException {
        MatchCommand command;
        HashMap<String, String> argumentMultiMap = ArgumentTokenizer.tokenize(userInput);
        String name = argumentMultiMap.get("");
        if (name.length() != 0) {
            command = new MatchCommand(name);
        } else {
            throw new DukeException("MEMBER_NO_NAME_MESSAGE");
        }
        return command;
    }
}
