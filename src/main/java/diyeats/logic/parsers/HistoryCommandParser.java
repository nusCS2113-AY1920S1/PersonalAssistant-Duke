package diyeats.logic.parsers;

import diyeats.logic.commands.HistoryCommand;

//@@author HashirZahir
/**
 * Parser class to handle history command input from user.
 */
public class HistoryCommandParser implements ParserInterface<HistoryCommand> {

    /**
     * Parser for history.
     * @param userInputStr the user input to be parsed
     * @return <code>history</code> the object containing records of all the past commands taken
     */
    @Override
    public HistoryCommand parse(String userInputStr) {
        if (!userInputStr.isEmpty() && userInputStr.equals("clear")) {
            return new HistoryCommand(true);
        } else {
            return new HistoryCommand(false);
        }
    }
}
